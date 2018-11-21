package gt.creeperface.nukkit.scoreboardapi.scoreboard;

/**
 * @author CreeperFace
 */
public class Score {

    public long id;

    public String name;

    public int value;

    public Score(long id, String name, int value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }
}
