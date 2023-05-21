package maze.state;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This enum class represents the Direction constants.
 * */
@Getter
@AllArgsConstructor
public enum Direction {

    /** Enum constant for UP direction.*/
    UP(-1, 0),

    /** Enum constant for RIGHT direction.*/
    RIGHT(0, 1),

    /** Enum constant for DOWN direction.*/
    DOWN(1, 0),

    /** Enum constant for LEFT direction.*/
    LEFT(0, -1);

    private final int rowChange;
    private final int colChange;

}
