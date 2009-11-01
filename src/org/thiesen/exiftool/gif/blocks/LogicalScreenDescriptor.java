package org.thiesen.exiftool.gif.blocks;

import java.io.ByteArrayInputStream;

import org.apache.commons.lang.BitField;
import org.thiesen.exiftool.gif.blocks.fields.LogicalScreenDescriptorPackedField;
import org.thiesen.exiftool.util.IntReader;

public class LogicalScreenDescriptor extends Block {

    private final int _logicalScreenWidth;
    private final int _logicalScreenHeight;
    private final LogicalScreenDescriptorPackedField _packedField;
    private final byte _backgroundColorIndex;
    private final byte _pixelAscpectRation;

    
    public LogicalScreenDescriptor( int logicalScreenWidth,
            int logicalScreenHeight, LogicalScreenDescriptorPackedField packedField,
            byte backgroundColorIndex, byte pixelAspectRatio) {
        _logicalScreenWidth = logicalScreenWidth;
        _logicalScreenHeight = logicalScreenHeight;
        _packedField = packedField;

        _backgroundColorIndex = backgroundColorIndex;
        _pixelAscpectRation = pixelAspectRatio;
    }

    @Override
    public String toString() {
        return _logicalScreenWidth + "x" + _logicalScreenHeight + ( hasGlobalColorTable() ? " with global color table of size " + getGlobalColorTableSize()  : "" );
    }

    public boolean hasGlobalColorTable() {
        return _packedField.hasGlobalColorTable();
    }

    public int getGlobalColorTableSize() {
        return _packedField.getGlobalColorTableSize();
    }

}
