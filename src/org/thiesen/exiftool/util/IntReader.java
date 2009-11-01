/*
 * $Id$
 * (c) Copyright 2009 freiheit.com technologies GmbH
 *
 * Created on 01.11.2009 by Marcus Thiesen (marcus@freiheit.com)
 *
 * This file contains unpublished, proprietary trade secret information of
 * freiheit.com technologies GmbH. Use, transcription, duplication and
 * modification are strictly prohibited without prior written consent of
 * freiheit.com technologies GmbH.
 */
package org.thiesen.exiftool.util;

import java.io.IOException;
import java.io.InputStream;

public interface IntReader {
    public long readUnsignedInt32( final InputStream in ) throws IOException;
    public int readUnsignedInt16( final InputStream in ) throws IOException;
}