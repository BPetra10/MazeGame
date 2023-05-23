package maze.results;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * This class has a list which contains {@link GameResult} elements.
 * */
@XmlRootElement(name="highScores")
public class HighScore {
    private ArrayList<GameResult> highScores;

    /**
     * Making an empty instance of highScores list.
     * */
    public HighScore() {
        this.highScores = new ArrayList<GameResult>();
    }

    /**
     * This is a setter for highScores list.
     * @param highScores is a {@link ArrayList} list, which will contains {@link GameResult} elements.
     * */
    @XmlElement(name = "score")
    public void setHighScores(ArrayList<GameResult> highScores) {
        this.highScores = highScores;
    }

    /**
     * Giving back the top 10 score of the list.
     * If there are more than 10 players, we will delete the scores outside the top 10.
     * @return highScores ArrayList containing top 10 scores.
     * */
    public ArrayList<GameResult> getHighScores(){
        highScores.sort(Comparator.comparing(GameResult::getDuration)
                .thenComparing(GameResult::getSteps).thenComparing(GameResult::getPlayer));
        if(highScores.size()>10)
            highScores.subList(10,highScores.size()).clear();
        return highScores;
    }

    /**
     * Adding new {@link GameResult} to highScores list.
     * @param res is a result of a GamResult element.
     * */
    public void addScore(GameResult res){
        highScores.add(res);
    }
}

