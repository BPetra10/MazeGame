package maze.javafx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import maze.results.GameResult;
import maze.results.HighScore;
import maze.results.ScoreReadWrite;
import org.tinylog.Logger;
import utility.javafx.ControllerHelper;

import javax.inject.Inject;
import java.io.IOException;
import java.time.Duration;

/**
 * Handling scores, and saving them. Restarting the game.
 * */
public class HighScoresController {
    @FXML
    private TableColumn<GameResult, String> player;

    @FXML
    private TableColumn<GameResult, Integer> steps;

    @FXML
    private TableColumn<GameResult, Duration> duration;

    @FXML
    private TableView<GameResult> highScoreTable;

    @Inject
    private FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private void initialize() {
        Logger.debug("Loading high scores...");

        ScoreReadWrite rw = new ScoreReadWrite();
        HighScore hs = rw.getRes();

        player.setCellValueFactory(new PropertyValueFactory<>("player"));
        steps.setCellValueFactory(new PropertyValueFactory<>("steps"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        ObservableList<GameResult> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(hs.getHighScores());
        highScoreTable.setItems(observableResult);
    }

    /**
     * Going back to opening.fxml.
     * @param actionEvent handling Restart button.
     * @throws IOException this will happen, if there is some I/O error while loading FXML.
     * */
    public void handleRestartButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Logger.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        ControllerHelper.loadAndShowFXML(fxmlLoader, "/fxml/opening.fxml", stage);
    }

}
