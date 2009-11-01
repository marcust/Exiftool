package org.thiesen.exiftool.gif.blocks;

public class Header extends Block {

    private final String _version;
    
    public Header( final String version ) {
        _version = version;
    }
    
    @Override
    public String toString() {
        return "GIF Version " + _version;
    }

}
