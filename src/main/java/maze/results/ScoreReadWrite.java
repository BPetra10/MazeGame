package maze.results;
import lombok.Getter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

/**
 * This class is responsible for XML read and write method implementing.
 * */
@Getter
public class ScoreReadWrite {
    private File file = new File("score.xml");

    /**
     * This method will store the game results in XML file.
     * @param res is a {@link GameResult} element, which we want to add to our file.
     * */
    public void addRes(GameResult res){
        HighScore highScore = getRes();
        highScore.addScore(res);
        try {
            JAXBContext context = JAXBContext.newInstance(HighScore.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            OutputStream out = new FileOutputStream(file);

            marshaller.marshal(highScore, file);

            marshaller.marshal(highScore, System.out);
        }
        catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will give back our scores from an XML file and store in a HighScore instance.
     * @return {@link HighScore} instance, which is actually an {@code ArrayList} for scores.
     * */
    public HighScore getRes(){
        HighScore highScore = new HighScore();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(HighScore.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            file.createNewFile();
            InputStream inputStream = new FileInputStream(file);

            if(file.length()!=0)
                highScore = (HighScore) jaxbUnmarshaller.unmarshal(inputStream);

            inputStream.close();
        }catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
        return highScore;
    }
}

