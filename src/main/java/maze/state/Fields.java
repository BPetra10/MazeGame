package maze.state;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * This class will help to make our Table, and checking for where is the ball and finish cell in it.
 * */
@Getter
@Setter
@AllArgsConstructor
public class Fields {
    private boolean top;
    private boolean bottom;
    private boolean left;
    private boolean right;
    private boolean ball;
    private boolean finish;
}

