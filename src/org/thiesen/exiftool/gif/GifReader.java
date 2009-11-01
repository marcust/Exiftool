package org.thiesen.exiftool.gif;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

import javax.annotation.Nonnull;

import org.apache.commons.io.HexDump;
import org.apache.commons.io.IOUtils;
import org.thiesen.exiftool.gif.blocks.Block;
import org.thiesen.exiftool.gif.blocks.Header;
import org.thiesen.exiftool.gif.blocks.LogicalScreenDescriptor;
import org.thiesen.exiftool.gif.blocks.GlobalColorTable;
import org.thiesen.exiftool.gif.blocks.ImageDescriptor;
import org.thiesen.exiftool.gif.blocks.fields.ImageDescriptorPackedField;
import org.thiesen.exiftool.gif.blocks.fields.LogicalScreenDescriptorPackedField;
import org.thiesen.exiftool.util.IntReader;
import org.thiesen.exiftool.util.LittleEndianReader;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public class GifReader {

    private static final Charset ASCII = Charset.forName("ASCII");
    private static final IntReader INT_READER = new LittleEndianReader();
    
    private final byte[] _data;

    private GifReader( final @Nonnull byte[] data ) {
        _data = data;
    }

    public static GifReader copyOf( final @Nonnull byte[] data ) {
        return new GifReader( data.clone() );
    }

    public static GifReader wrap( final @Nonnull byte[] data ) {
        return new GifReader( data );
    }

    public static GifReader read( final @Nonnull InputStream stream ) throws IOException {
        return wrap( IOUtils.toByteArray( stream ) );
    }

    public @Nonnull ImmutableList<Block> parse() {
        Builder<Block> builder = ImmutableList.<Block>builder();

        final DataInputStream stream = new DataInputStream(new ByteArrayInputStream(_data));
        try {
            builder.add( readHeader( stream ) );
            
            final LogicalScreenDescriptor screenDescriptor = readLogicalScreenDescription( stream );
            builder.add( screenDescriptor );
            
            if ( screenDescriptor.hasGlobalColorTable() ) {
                builder.add( readGlobalColorTable( stream, screenDescriptor ) );
            }
            
            
            toString( readBytes(stream, 8) ); // apparently, here is a graphic control extension
            
            builder.add( readImageDescriptor( stream ) ); 
            
            
        } catch ( IOException e ) {
            throw new RuntimeException("IOException on an in memory stream", e );
        }

        return builder.build();
    }

    private void toString(byte[] readBytes) {
        for ( final byte b : readBytes ) {
            System.out.println(Integer.toHexString(0xff & b));
        }
        
    }

    private Block readImageDescriptor(DataInputStream stream) throws IOException {
        final byte imageSeperator = readByte(stream);
        if ( imageSeperator != 0x2C ) {
            throw new IllegalArgumentException("Not a valid image Seperator");
        }
        
        final int leftPosition = INT_READER.readUnsignedInt16(stream);
        final int topPosition = INT_READER.readUnsignedInt16(stream);
        final int width = INT_READER.readUnsignedInt16(stream);
        final int height = INT_READER.readUnsignedInt16(stream);
        final ImageDescriptorPackedField packedField = new ImageDescriptorPackedField(readByte(stream));
        
        return new ImageDescriptor( imageSeperator, leftPosition, topPosition, width, height, packedField );
    }

    private Block readGlobalColorTable(DataInputStream stream, LogicalScreenDescriptor descriptor) throws IOException {
        final int size = 3 * (1 << ( descriptor.getGlobalColorTableSize() + 1 ) );
        final byte[] readBytes = readBytes(stream, size);
        
        return new GlobalColorTable(readBytes); 
    }

    private LogicalScreenDescriptor readLogicalScreenDescription(final @Nonnull DataInputStream stream) throws IOException {
        final int logicalScreenWidth = INT_READER.readUnsignedInt16(stream);
        final int logicalScreenHeight = INT_READER.readUnsignedInt16(stream);
        final LogicalScreenDescriptorPackedField packedField = new LogicalScreenDescriptorPackedField(readByte(stream));
        final byte backgroundColorIndex = readByte(stream);
        final byte pixelAspectRatio = readByte( stream );
        
        return new LogicalScreenDescriptor( logicalScreenWidth, logicalScreenHeight, packedField, backgroundColorIndex, pixelAspectRatio );
        
    }

    private byte readByte(DataInputStream stream) throws IOException {
        return readBytes(stream, 1)[0];
    }

    private Block readHeader(final @Nonnull DataInputStream stream) throws IOException {
        readAndCompareAsciiBytes( stream, "GIF" );
        final byte[] version = readBytes(stream, 3); 
        
        return new Header( new String( version, ASCII ) );
    }

    private void readAndCompareAsciiBytes( final @Nonnull DataInputStream stream, final @Nonnull String chars ) throws IOException {
        final int length = chars.length();
        final byte[] bytes = readBytes(stream, length);
        if ( !asciiBytesEquals( bytes, chars ) ) {
            throw new IllegalArgumentException("Expected " + chars + " but got " + ASCII.decode(ByteBuffer.wrap(bytes)));
        }
    }

    private boolean asciiBytesEquals(byte[] bytes, String chars) {
        return Arrays.equals( bytes, chars.getBytes(ASCII) );
    }

    private byte[] readBytes(final DataInputStream stream, final int length)
            throws IOException {
        byte[] buffer = newArrayFor( length );
        stream.readFully(buffer);
        return buffer;
    }

    private byte[] newArrayFor(int length) {
        return new byte[length];
    }



}
