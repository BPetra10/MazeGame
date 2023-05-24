package utility.javafx;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Helper class for copying loaded file to score.xml, or copying score.xml to our target file for save.
 * */
public class CopyHelper {
    /**
     * Copy method for loading, and saving files.
     * @param source is the input file.
     * @param target is the output file.
     * */
    public static void Copy(File source, File target){
        try {
            Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}