/*
 * $Id$
 * (c) Copyright 2009 freiheit.com technologies GmbH
 *
 * Created on 20.10.2009 by Marcus Thiesen (marcus@freiheit.com)
 *
 * This file contains unpublished, proprietary trade secret information of
 * freiheit.com technologies GmbH. Use, transcription, duplication and
 * modification are strictly prohibited without prior written consent of
 * freiheit.com technologies GmbH.
 */
package org.thiesen.exiftool.tiff;

enum IFDEntryType {
    UNKNOWN(-1),
    BYTE(1),
    ASCII(2),
    SHORT(3),
    LONG(4),
    RATIONAL(5),
    SBYTE(6),
    UNDEFINED(7),
    SSHORT(8),
    SLONG(9),
    SRATIONAL(10),
    FLOAT(11),
    DOUBLE(12);

    private final short _id;
    
    private IFDEntryType(final int id) {
        _id = (short)id;
    }
    
    public short getId() {
        return _id;
    }
    
    public static IFDEntryType valueOf( short value ) {
        for ( final IFDEntryType type : values() ) {
            if ( type.getId() == value ) {
                return type;
            }
        }
        
        return UNKNOWN;
    }
    
}