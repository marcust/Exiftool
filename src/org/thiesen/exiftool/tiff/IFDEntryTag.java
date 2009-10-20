package org.thiesen.exiftool.tiff;

import com.google.common.collect.ImmutableSet;

public enum IFDEntryTag {


    PHOTOMETRIC_INTERPRETATION(262, "PhotometricInterpretation", IFDEntryType.SHORT ),
    COMPRESSION(259, "Compression", IFDEntryType.SHORT ),
    IMAGE_LENGTH(257, "ImageLength",  IFDEntryType.SHORT, IFDEntryType.LONG ),
    IMAGE_WIDTH(256, "ImageWidth",  IFDEntryType.SHORT, IFDEntryType.LONG ),
    RESOLUTION_UNIT(296, "ResolutionUnt",  IFDEntryType.SHORT ),
    NEW_SUBFILE_TYPE(254, "NewSubfileType", IFDEntryType.LONG ),
    BITS_PER_SAMPLE(258, "BitsPerSample", IFDEntryType.SHORT ),
    DOCUMENT_NAME(269, "DocumentName", IFDEntryType.ASCII ),
    IMAGE_DESCRIPTION(270, "ImageDescription", IFDEntryType.ASCII ),
    STRIP_OFFSETS(273, "StripOffsets", IFDEntryType.SHORT, IFDEntryType.LONG ),
    ORIENTATION(274, "Orientation", IFDEntryType.SHORT ),
    SAMPLES_PER_PIXEL(277, "SamplesPerPixel", IFDEntryType.SHORT ),
    ROWS_PER_STRIP(278, "RowsPerStrip", IFDEntryType.SHORT, IFDEntryType.LONG ),
    X_RESOLUTION(282, "XResolution", IFDEntryType.RATIONAL ),
    Y_RESOLUTION(283, "YResolution", IFDEntryType.RATIONAL ),
    PLANAR_CONFIGURATION(284, "PlanarConfiguration", IFDEntryType.SHORT ),
    STRIP_BYTE_COUNTS(279, "StripByteCounts", IFDEntryType.SHORT, IFDEntryType.LONG ),
    MAKE(271, "Make", IFDEntryType.ASCII ),
    MODEL(272, "Model", IFDEntryType.ASCII ),
    SOFTWARE(305, "Software", IFDEntryType.ASCII ),
    COPYRIGHT(33432, "Copyright", IFDEntryType.ASCII ),
    /*
     * Date Time of Image Creation
     * The format is: “YYYY:MM:DD HH:MM:SS”, with hours like those on a 24-hour
     * clock, and one space character between the date and the time. The length of the
     * string, including the terminating NUL, is 20 bytes.
     */
    DATE_TIME(306, "DateTime", IFDEntryType.ASCII ),
    ARTIST(315, "Artist", IFDEntryType.ASCII ),
    EXIF_IFD_POINTER(34665, "ExifIfdPointer", IsDirectory.YES, IFDEntryType.LONG ),
    GPS_INFO_IFD_POINTER(34853, "GPS Info IFD Pointer", IsDirectory.YES, IFDEntryType.LONG ),
    INTEROPERABILITY_IFD_POINTER(40965, "Interoperability IFD Pointer", IsDirectory.YES, IFDEntryType.LONG ),
    IPTC(33723, "IPTC Metadata", IsDirectory.NO, IFDEntryType.UNDEFINED, IFDEntryType.BYTE ),
    XMP(700, "XML packet containing XMP metadata", IsDirectory.NO, IFDEntryType.BYTE ),
    PHOTOSHOP(34377, "Collection of Photoshop 'Image Resource Blocks'", IsDirectory.NO, IFDEntryType.BYTE ),
    EXIF_EXPOSURE_TIME(33434, "Exif ExposureTime", IsDirectory.NO, IFDEntryType.RATIONAL ),
    EXIF_F_NUMBER(33437, "Exif FNumber", IsDirectory.NO, IFDEntryType.RATIONAL ),
    EXIF_DATE_TIME_ORIGINAL(36867, "Exif Date Time Original", IFDEntryType.ASCII ),
    EXIF_DATE_TIME_DIGITIZED(36868, "Exif Date Time Digitized", IFDEntryType.ASCII ),
    EXIF_USER_COMMENTS(37510, "User Comment", IFDEntryType.ASCII ),
    EXIF_FILE_SOURCE(41728, "FileSource", IFDEntryType.ASCII ),
    EXIF_EXPOSURE_PROGRAM(34850, "Exif Exposure Program", IFDEntryType.SHORT ),
    EXIF_ISO_SPEED_RATING(34855, "Exif Iso Speed Rating", IFDEntryType.SHORT ),
    EXIF_VERSION(36864, "Exif Version", IFDEntryType.UNDEFINED ),
    
    ;
    
    private enum IsDirectory { YES, NO }
    
    private final int _tag;
    private final ImmutableSet<IFDEntryType> _supportedTypes;
    private final String _displayName;
    private final IsDirectory _isDirectory;
    
    private IFDEntryTag( final int tag, final String displayName, final IsDirectory isDirectory, final IFDEntryType... supportedTypes ) {
        _tag = tag;
        _displayName = displayName;
        _isDirectory = isDirectory;
        _supportedTypes = ImmutableSet.of(supportedTypes);
    }

    private IFDEntryTag( final int tag, final String displayName, final IFDEntryType... supportedTypes ) {
        this( tag, displayName, IsDirectory.NO, supportedTypes );
    }

    public static IFDEntryTag valueOf( int tagFieldIdentifier ) {
        for ( final IFDEntryTag tag : values() ) {
            if ( tag.getTag() == tagFieldIdentifier ) {
                return tag;
            }
        }
        
        return null;
    
    }

    private int getTag() {
        return _tag;
    }

    public ImmutableSet<IFDEntryType> getSupportedTypes() {
        return _supportedTypes;
    }

    public String getDisplayName() {
        return _displayName;
    }

    public boolean isDirectory() {
        return _isDirectory == IsDirectory.YES;
    }
    
}
