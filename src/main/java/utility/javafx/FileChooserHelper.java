package utility.javafx;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

/**
 * Provides support for the use of the {@link FileChooser} class.
 */
public class FileChooserHelper {
    /**
     * Instantiates a {@link FileChooser} object, than opens it.
     * Sets the title of the object and adds an {@link FileChooser.ExtensionFilter} for XML documents.
     *
     * @param isOpen whether the document should be opened or saved
     * @param stage  the stage
     * @return the selected file
     */
    public static Optional<File> show(boolean isOpen, Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML documents", "*.xml"));
        if (isOpen) {
            fileChooser.setTitle("Browse an XML file to load the scoreboard");
            return Optional.ofNullable(fileChooser.showOpenDialog(stage));
        } else {
            fileChooser.setTitle("Browse an XML file to save the scoreboard");
            return Optional.ofNullable(fileChooser.showSaveDialog(stage));
        }
    }
}
