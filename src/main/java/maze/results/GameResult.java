package maze.results;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;

/**
 * Class representing the result of a game played by a player.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GameResult {
    private String player;
    private int steps;
    private long duration;
    @XmlElement
    public void setPlayer(String player) {
        this.player = player;
    }
    @XmlElement
    public void setSteps(int steps) {
        this.steps = steps;
    }
    @XmlElement
    public void setDuration(long duration) {
        this.duration = duration;
    }
}