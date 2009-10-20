package org.thiesen.exiftool.tiff;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nonnull;

import org.thiesen.exiftool.tiff.TiffReader.IntReader;

public class BigEndianReader implements IntReader {

    @Override
    public int readUnsignedInt16( final @Nonnull InputStream in) throws IOException {
        int ch1 = in.read();
        int ch2 = in.read();
        if ((ch1 | ch2) < 0)
            throw new EOFException();
        return (ch1 << 8) + (ch2 << 0);
    }

    @Override
    public long readUnsignedInt32( final @Nonnull InputStream in) throws IOException {
        int ch1 = in.read();
        int ch2 = in.read();
        int ch3 = in.read();
        int ch4 = in.read();
        if ((ch1 | ch2 | ch3 | ch4) < 0)
            throw new EOFException();
        return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0) & 0xffffffffL;
    }

}
