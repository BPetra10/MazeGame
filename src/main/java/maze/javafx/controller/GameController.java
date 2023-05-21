package maze.javafx.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.tinylog.Logger;

public class GameController {
    @FXML
    private Label messageLabel;
    private String playerName;

    /**
     * Setting playerName variable value and "adding to" messageLabel.
     * @param playerName is the actual player name.
     * */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * This method will initialize the game and set the default state.
     * */
    @FXML
    public void initialize()
    {
        Platform.runLater(() ->messageLabel.setText(String.format("Good luck, %s!", playerName)));
        Logger.info("Starting game");
    }
}
