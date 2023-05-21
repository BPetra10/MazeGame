package maze.javafx.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import maze.state.Table;
import org.tinylog.Logger;

public class GameController {
    @FXML
    private Label messageLabel;
    private String playerName;
    @FXML
    private GridPane gameBoard;
    private Table table = new Table();
    private StackPane[][] table_fields = new StackPane[7][7];

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
        populateGrid();

        Platform.runLater(() ->messageLabel.setText(String.format("Good luck, %s!", playerName)));
        Logger.info("Starting game");
    }

    private void populateGrid()
    {
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                table_fields[row][col] = fieldMaker(row,col);
                gameBoard.add(table_fields[row][col],col,row);
            }
        }
    }

    private StackPane fieldMaker(int i, int j)
    {
        var field = new StackPane();

        double top = table.getFields()[i][j].isTop() ? 5 : 1;
        double bottom = table.getFields()[i][j].isBottom() ? 5 : 1;
        double left = table.getFields()[i][j].isLeft() ? 5 : 1;
        double right = table.getFields()[i][j].isRight() ? 5 : 1;

        field.setBorder(new Border(new BorderStroke(null,null,null,null,
                BorderStrokeStyle.SOLID,BorderStrokeStyle.SOLID,BorderStrokeStyle.SOLID,BorderStrokeStyle.SOLID,
                null, new BorderWidths(top,right,bottom,left),null)));
        return field;
    }
}
