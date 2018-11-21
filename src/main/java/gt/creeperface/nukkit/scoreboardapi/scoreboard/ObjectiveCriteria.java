package gt.creeperface.nukkit.scoreboardapi.scoreboard;

/**
 * @author CreeperFace
 */
public class ObjectiveCriteria {

    public String name;

    public boolean readOnly;

    public ObjectiveCriteria(String name, boolean readOnly) {
        this.name = name;
        this.readOnly = readOnly;
    }
}
