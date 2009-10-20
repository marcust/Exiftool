package org.thiesen.exiftool.tiff;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nonnull;

import org.apache.commons.io.EndianUtils;
import org.thiesen.exiftool.tiff.TiffReader.IntReader;


public class LittleEndianReader implements IntReader {

    @Override
    public int readUnsignedInt16(final @Nonnull InputStream in) throws IOException {
        return EndianUtils.readSwappedUnsignedShort(in);
    }

    @Override
    public long readUnsignedInt32(final @Nonnull InputStream in) throws IOException {
        return EndianUtils.readSwappedUnsignedInteger(in);
    }

    
    
    
}
