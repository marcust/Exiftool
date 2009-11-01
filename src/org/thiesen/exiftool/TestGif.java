package org.thiesen.exiftool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.thiesen.exiftool.gif.GifReader;

public class TestGif {


    public static void main(String[] args) throws FileNotFoundException, IOException {
        final GifReader reader = GifReader.read(new FileInputStream(new File("test.gif")));
        
        System.out.println( StringUtils.join(reader.parse(), "\n" ) );
    }

}
