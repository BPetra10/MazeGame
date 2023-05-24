package maze_tests;

import static org.junit.jupiter.api.Assertions.*;

import maze.state.Direction;
import maze.state.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import maze.state.BallState;
public class BallStateTest {
    private BallState ballState;
    private Table table;
    @BeforeEach
    void setUp() {
        ballState = new BallState();
        table = new Table();
    }

    @Test
    void isGoalFalse(){
        assertFalse(ballState.isGoal());

        assertFalse(table.getFields()[4][4].isFinish());
        assertFalse(table.getFields()[2][2].isFinish());
    }

    @Test
    void isGoal(){
        assertTrue(table.getFields()[5][2].isFinish());
    }
    @Test
    void checkMoveTrue(){
        assertTrue(ballState.checkMove(Direction.LEFT));
        assertTrue(ballState.checkMove(Direction.RIGHT));
        assertTrue(ballState.checkMove(Direction.UP));
        assertTrue(ballState.checkMove(Direction.DOWN));
    }
    @Test
    void moveReturnTrue(){
        int [] expected = {1,4,1,3};
        int [] got = ballState.move(Direction.LEFT);
        assertArrayEquals(expected,got);

        expected = new int[]{1, 3, 2, 3};
        got = ballState.move(Direction.DOWN);
        assertArrayEquals(expected,got);

        expected = new int[]{2, 3, 2, 4};
        got = ballState.move(Direction.RIGHT);
        assertArrayEquals(expected,got);

        expected = new int[]{2, 4, 2, 5};
        got = ballState.move(Direction.RIGHT);
        assertArrayEquals(expected,got);

        expected = new int[]{2, 5, 1, 5};
        got = ballState.move(Direction.UP);
        assertArrayEquals(expected,got);
    }
}

