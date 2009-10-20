package org.thiesen.exiftool.tiff;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nonnull;

import org.apache.commons.io.EndianUtils;
import org.thiesen.exiftool.tiff.TiffReader.IntReader;


public class LittleEndianReader implements IntReader {

    @Override
    public short readInt16(final @Nonnull InputStream in) throws IOException {
        return EndianUtils.readSwappedShort(in);
    }

    @Override
    public int readInt32(final @Nonnull InputStream in) throws IOException {
        return EndianUtils.readSwappedInteger(in);
    }

    
    
    
}
