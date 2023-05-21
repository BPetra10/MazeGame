package maze.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;
import utility.javafx.ControllerHelper;

import javax.inject.Inject;
import java.io.IOException;

public class OpeningController {
    @Inject
    private FXMLLoader fxmlLoader = new FXMLLoader();
    @FXML
    private TextField playerNameTextField;
    @FXML
    private Label errorLabel;

    /**
     * This will load the game.fxml, if the player gives a name.
     * @param actionEvent handling Start Button action.
     * @throws IOException this will happen, if there is some I/O error while loading FXML.
     * */
    public void handleStartButton(ActionEvent actionEvent) throws IOException {
        if (playerNameTextField.getText().isEmpty())
        {
            errorLabel.setText("Please enter your name!");
            Logger.warn("Name field is empty.");
        }
        else
        {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            ControllerHelper.loadAndShowFXML(fxmlLoader,"/fxml/game.fxml",stage);
            fxmlLoader.<GameController>getController().setPlayerName(playerNameTextField.getText());
            Logger.info("The user's name is set to {}, loading game scene.", playerNameTextField.getText());
        }
    }
}
