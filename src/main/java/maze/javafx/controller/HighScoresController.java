package maze.javafx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import maze.results.GameResult;
import maze.results.HighScore;
import maze.results.ScoreReadWrite;
import org.tinylog.Logger;

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
}
