package maze.state;

/**
 * This class will represent the state of the ball, and its operations (like move, check move etc.).
 * */
public class BallState{
    private Table table = new Table();

    /**
     * It returns whether the maze is solved or not.
     * @return true if the ball is in the goal field.
     */
    public boolean isGoal()
    {
        return table.getFields()[5][2].isBall();
    }

    private boolean canMoveUp(){
        return !table.getFields()[table.getBallRow()][table.getBallCol()].isTop();
    }

    private boolean canMoveDown(){return !table.getFields()[table.getBallRow()][table.getBallCol()].isBottom();}

    private boolean canMoveLeft(){
        return !table.getFields()[table.getBallRow()][table.getBallCol()].isLeft();
    }

    private boolean canMoveRight(){
        return !table.getFields()[table.getBallRow()][table.getBallCol()].isRight();
    }

    /**
     * @param direction a direction to which the ball is intended to be moved.
     * @return whether the ball can be moved to the direction specified.
     */
    public boolean checkMove(Direction direction) {
        return switch (direction) {
            case UP -> canMoveUp();
            case RIGHT -> canMoveRight();
            case DOWN -> canMoveDown();
            case LEFT -> canMoveLeft();
        };
    }

    /**
     * Representation for the move.
     * @param direction a direction to which the ball is intended to be moved.
     * @return int[] which contains the ball previous and actual position.
     * */
    public int[] move(Direction direction){
        int [] prev_actual = new int[4];
        prev_actual[0] = table.getBallRow();
        prev_actual[1] = table.getBallCol();
        table.getFields()[table.getBallRow()][table.getBallCol()].setBall(false);
        int rowChange = direction.getRowChange();
        int colChange = direction.getColChange();
        table.setBallRow(table.getBallRow() + rowChange);
        table.setBallCol(table.getBallCol() + colChange);
        table.getFields()[table.getBallRow()][table.getBallCol()].setBall(true);
        prev_actual[2] = table.getBallRow();
        prev_actual[3] = table.getBallCol();
        return prev_actual;
    }
}

