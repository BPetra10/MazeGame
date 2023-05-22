package maze.javafx.controller;

import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
import javafx.stage.Stage;
import maze.state.BallState;
import maze.state.Direction;
import maze.state.Table;
import org.tinylog.Logger;
import utility.javafx.ControllerHelper;
import utility.javafx.Stopwatch;

import javax.inject.Inject;
import java.io.IOException;
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

    @Inject
    private FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private Button resetButton;
    @FXML
    private Button giveUpButton;

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

        resetGame();
    }

    private void resetGame()
    {
        stepCount.set(0);
        isSolved.set(false);

        startTime = Instant.now();
        if (stopwatch.getStatus() == Animation.Status.PAUSED)
        {
            stopwatch.reset();
        }
        stopwatch.start();

        removeCircle();
        ballState = new BallState();
        finCirc();
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

    private void removeCircle()
    {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                table_fields[i][j].getChildren().clear();
            }
        }
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
                        resetGame();
                    } else if (quitKeyCombination.match(keyEvent)) {
                        Logger.debug("Exiting...");
                        Platform.exit();
                    }else if(giveUpKeyCombination.match(keyEvent)){
                        Logger.debug("Giving up the game.");
                        giveUpButton.fire();
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

    /**
     * Resetting the game to its default state.
     * @param actionEvent handling Reset Button actions.
     * */
    public void handleResetButton(ActionEvent actionEvent)
    {
        Logger.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        Logger.info("Resetting game");
        stopwatch.stop();
        resetGame();
    }

    /**
     * Giving up the game, and navigating to scores.fxml.
     * @param actionEvent handling give up button actions.
     * */
    public void handleGiveUpButton(ActionEvent actionEvent) throws IOException {
        Logger.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        stopwatch.stop();
        Logger.info("The game has has been given up.");
        ControllerHelper.loadAndShowFXML(fxmlLoader,"/fxml/highscores.fxml",
                (Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
    }

}
