package org.thiesen.exiftool.gif.blocks.fields;


public class LogicalScreenDescriptorPackedField extends PackedField {

    private final boolean _globalColorTableFlag;
    private final byte _colorResolution;
    private final boolean _sortFlag;
    private final byte _globalColorTableSize;
    
    public LogicalScreenDescriptorPackedField(final byte packedField) {
        _globalColorTableFlag = (((packedField >> 7) & 1) > 0);
        _colorResolution = (byte) ((packedField >> 4) & 7);
        _sortFlag = (((packedField >> 5) & 1) > 0);
        _globalColorTableSize = (byte) (packedField & 7);
    }

    public boolean hasGlobalColorTable() {
        return _globalColorTableFlag;
    }

    public int getGlobalColorTableSize() {
        return _globalColorTableSize;
    }
    
    
}
