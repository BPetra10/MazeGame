package maze.javafx.controller;

import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import maze.state.BallState;
import maze.state.Direction;
import maze.state.Table;
import org.tinylog.Logger;
import utility.javafx.Stopwatch;

import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

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

    @FXML
    private GridPane gameBoard;
    private Table table = new Table();
    private StackPane[][] table_fields = new StackPane[7][7];

    private BallState ballState = new BallState();

    @FXML
    private Label TimeLabel;
    private Stopwatch stopwatch = new Stopwatch();
    private Instant startTime;

    @FXML
    private Label stepsLabel;
    private IntegerProperty stepCount = new SimpleIntegerProperty();

    private BooleanProperty isSolved = new SimpleBooleanProperty();

    /**
     * This method will initialize the game and set the default state.
     * */
    @FXML
    public void initialize()
    {
        stepsLabel.textProperty().bind(stepCount.asString());
        TimeLabel.textProperty().bind(stopwatch.hhmmssProperty());

        populateGrid();
        finCirc();

        startTime = Instant.now();
        if (stopwatch.getStatus() == Animation.Status.PAUSED) {
            stopwatch.reset();
        }
        stopwatch.start();

        registerKeyEventHandler();

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

    private void finCirc()
    {
        addCircle(1,4);

        Text finish = new Text("CÉL");
        finish.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));
        table_fields[5][2].getChildren().add(finish);
    }

    private void addCircle(int i, int j)
    {
        Circle ball = new Circle(20, 20, 20);
        ball.setFill(Color.BLUE);
        table_fields[i][j].getChildren().add(ball);
    }


    private void timedRemove(int i, int j)
    {
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask(){
            @Override
            public void run() {
                Platform.runLater(() -> {
                    table_fields[i][j].getChildren().clear();
                });
                myTimer.cancel();
            }
        }, 200);
    }

    private void performMove(Direction direction) {
        int [] balldirection;

        if (ballState.checkMove(direction))
        {
            stepCount.set(stepCount.get() + 1);
            while (ballState.checkMove(direction))
            {
                balldirection = ballState.move(direction);
                Logger.info("Move: {}", direction);
                Logger.trace("New state of the ball: row: {} col: {} ",balldirection[2],balldirection[3]);
                addCircle(balldirection[2],balldirection[3]);
                timedRemove(balldirection[0],balldirection[1]);
                if (ballState.isGoal())
                {
                    isSolved.set(true);
                }
            }
        }else
        {
            Logger.warn("Invalid move: {}", direction);
        }
    }

    private void registerKeyEventHandler() {
        final KeyCombination restartKeyCombination = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
        final KeyCombination quitKeyCombination = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN);
        final KeyCombination giveUpKeyCombination = new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN);
        Platform.runLater(() -> gameBoard.getScene().setOnKeyPressed(
                keyEvent -> {
                    if (restartKeyCombination.match(keyEvent)) {
                        Logger.debug("Restarting game...");
                        stopwatch.stop();
                    } else if (quitKeyCombination.match(keyEvent)) {
                        Logger.debug("Exiting...");
                        Platform.exit();
                    }else if(giveUpKeyCombination.match(keyEvent)){
                        Logger.debug("Giving up the game.");
                    } else if (keyEvent.getCode() == KeyCode.UP) {
                        Logger.debug("Up arrow pressed");
                        performMove(Direction.UP);
                    } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                        Logger.debug("Right arrow pressed");
                        performMove(Direction.RIGHT);
                    } else if (keyEvent.getCode() == KeyCode.DOWN) {
                        Logger.debug("Down arrow pressed");
                        performMove(Direction.DOWN);
                    } else if (keyEvent.getCode() == KeyCode.LEFT) {
                        Logger.debug("Left arrow pressed");
                        performMove(Direction.LEFT);
                    }
                }
        ));
    }

}
