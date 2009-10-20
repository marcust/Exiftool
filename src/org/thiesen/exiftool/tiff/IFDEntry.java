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


class IFDEntry {
    private final IFDEntryTag _tag;
    private final IFDEntryType _type;
    private final long _numberOfValues;
    private final long _valueOrOffsetInBytes;
    private final int _tagFieldId;
    
    public IFDEntry(int tagFieldIdentifier, int fieldType,
            long numberOfValues, long valueOrOffsetInBytes) {
        super();
        _tagFieldId = tagFieldIdentifier;
        _tag = IFDEntryTag.valueOf( tagFieldIdentifier );
        _type = IFDEntryType.valueOf( fieldType );
        _numberOfValues = numberOfValues;
        _valueOrOffsetInBytes = valueOrOffsetInBytes;
    }
    
    @Override
    public String toString() {
        return "IFDEntry of Type " + _tag + " with type " + _type + " ["+ _tagFieldId +"] (numberOfValues: " + _numberOfValues + ", valueOrOffsetInBytes: " + _valueOrOffsetInBytes + ")";
    }
    
}