package org.thiesen.exiftool.gif.blocks;

import javax.annotation.Nonnull;

import org.thiesen.exiftool.gif.blocks.fields.ImageDescriptorPackedField;

public class ImageDescriptor extends Block {

    private byte _imageSeperator;
    private int _leftPosition;
    private int _topPosition;
    private int _width;
    private int _height;
    private ImageDescriptorPackedField _packedField;

    public ImageDescriptor(final byte imageSeperator, final int leftPosition,
            final int topPosition, final int width, final int height,
            final @Nonnull ImageDescriptorPackedField packedField) {
        _imageSeperator = imageSeperator;
        _leftPosition = leftPosition;
        _topPosition = topPosition;
        _width = width;
        _height = height;
        _packedField = packedField;
    }

    @Override
    public String toString() {
        return "Image Descriptor: top: "  + _topPosition + ", left: " + _leftPosition + ", "  + _width + "x" + _height;
    }

}
