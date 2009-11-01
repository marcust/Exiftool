package org.thiesen.exiftool.gif.blocks.fields;

public class ImageDescriptorPackedField extends PackedField {

    private final boolean _localColorTableFlag;
    private final boolean _interlaceFlag;
    private final boolean _sortFlag;
    private final byte _localColorTableSize;

    public ImageDescriptorPackedField(byte packedField) {
        _localColorTableFlag = (((packedField >> 7) & 1) > 0);
        _interlaceFlag = (((packedField >> 6) & 1) > 0);
        _sortFlag = (((packedField >> 5) & 1) > 0);
        _localColorTableSize = (byte) (packedField & 7);
    }
    
    
}
