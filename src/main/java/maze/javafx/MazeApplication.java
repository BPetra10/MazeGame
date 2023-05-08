package maze.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.tinylog.Logger;
import utility.javafx.ControllerHelper;

import javax.inject.Inject;

public class MazeApplication extends Application {
    @Inject
    private FXMLLoader fxmlLoader = new FXMLLoader();

    @Override
    public void start(Stage stage) throws Exception
    {
        Logger.info("Starting application.");
        ControllerHelper.loadAndShowFXML(fxmlLoader,"/fxml/opening.fxml",stage);
        stage.setTitle("Maze game");
        stage.setResizable(false);
    }
}