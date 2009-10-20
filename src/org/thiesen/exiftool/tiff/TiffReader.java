package org.thiesen.exiftool.tiff;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.annotation.Nonnull;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public class TiffReader {

    private static final byte[] LITTLE_ENDIAN_BYTE_ORDER_MARK = new byte[] { (byte)0x49, (byte)0x49 };
    private static final byte[] BIG_ENDIAN_BYTE_ORDER_MARK = new byte[] { (byte)0x4D, (byte)0x4D };
    private static final short MAGIC_NUMBER = 42;
    
    public interface IntReader {
        public int readInt32( final InputStream in ) throws IOException;
        public short readInt16( final InputStream in ) throws IOException;
    }
    
    final byte[] _tiffData;
    
    private TiffReader( final @Nonnull byte[] tiffData ) {
        _tiffData = tiffData;
    }
    
    public static TiffReader read( final @Nonnull InputStream in ) throws IOException {
        return new TiffReader(IOUtils.toByteArray(in));
    }
    
    public static TiffReader wrap( final @Nonnull byte[] tiffData ) {
        return new TiffReader(tiffData);
    }
    
    public static TiffReader copyOf( final @Nonnull byte[] tiffData ) {
        return new TiffReader(tiffData.clone());
    }
    
    public ImmutableList<IFDEntry> parse() throws IOException {
        final int firstIFDOffsetInBytes = readHeaderAndFindFirstIFDOffset(_tiffData);
        
        return readIfdDirectory( firstIFDOffsetInBytes, _tiffData );
    }
    
    private ImmutableList<IFDEntry> readIfdDirectory(int offset, byte[] tiffData) throws IOException {
        final IntReader intReader = determineByteOrder(tiffData);
        final ByteArrayInputStream in = new ByteArrayInputStream(tiffData);
        in.skip(offset);
        
        final short numberOfDirectoryEntries = intReader.readInt16(in);
        
        Builder<IFDEntry> entryListBuilder = ImmutableList.builder();
        for ( int i = 0; i < numberOfDirectoryEntries; i++ ) {
            entryListBuilder.add( readIfdEntry( intReader, in ) );
        }
        
        int nextEntryOffset = intReader.readInt32(in);
        if ( nextEntryOffset != 0 ) {
            entryListBuilder.addAll(readIfdDirectory(nextEntryOffset, tiffData) );
        }
        
        return entryListBuilder.build();
    }
    
    private IFDEntry readIfdEntry(final @Nonnull IntReader intReader, final @Nonnull InputStream in) throws IOException {
        
        final short tagFieldIdentifier = intReader.readInt16(in);
        final short fieldType = intReader.readInt16(in);
        final int numberOfValues = intReader.readInt32(in);
        final int valueOrOffsetInBytes = intReader.readInt32(in);
        
        return new IFDEntry(tagFieldIdentifier, fieldType, numberOfValues, valueOrOffsetInBytes);
    }

    private int readHeaderAndFindFirstIFDOffset(final byte[] tiffData)
            throws IOException {
        final DataInputStream dataIn = new DataInputStream( new ByteArrayInputStream(tiffData) );
        
        final IntReader intReader = determineByteOrder(tiffData);
        
        if ( dataIn.skip(2) != 2 ) {
            throw new IllegalArgumentException("Premature end of file");
        }
        final short magicNumber = intReader.readInt16(dataIn);
        
        if ( magicNumber != MAGIC_NUMBER ) {
            throw new IllegalArgumentException("Magic number is expected to be " + MAGIC_NUMBER + ", but is " + magicNumber  );
        }
        
        final int firstIFDOffsetInBytes = intReader.readInt32(dataIn);
        
        return firstIFDOffsetInBytes;
    }

    private IntReader determineByteOrder(final byte[] dataIn)
            throws IOException {
        if ( dataIn.length < 2 ) {
            throw new IllegalArgumentException("Tiff files should at least have a 2 byte BOM");
        }
        final byte[] startingBytes = Arrays.copyOfRange(dataIn, 0, 2);
        
        if ( Arrays.equals( startingBytes, LITTLE_ENDIAN_BYTE_ORDER_MARK ) ) {
            return new LittleEndianReader();
        }
        if ( Arrays.equals( startingBytes, BIG_ENDIAN_BYTE_ORDER_MARK ) ) {
            return new BigEndianReader();
        }
        
        throw new IllegalArgumentException("Unknown TIFF file start byte order marker: " + Arrays.toString( startingBytes ) );
    }
    
    
    
}
