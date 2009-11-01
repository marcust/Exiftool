package org.thiesen.exiftool.gif.blocks;

public class GlobalColorTable extends Block {

    
    
    private final byte[] _bytes;

    public GlobalColorTable(byte[] bytes) {
        _bytes = bytes;
    }

    @Override
    public String toString() {
        return "Global Color Table of size " + _bytes.length;
    }

}
