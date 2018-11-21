package gt.creeperface.nukkit.scoreboardapi.scoreboard;

import cn.nukkit.Player;

/**
 * @author CreeperFace
 */
public interface Scoreboard {

    void update();

    void addPlayer(Player p);

    void removePlayer(Player p);

    void spawnTo(Player p);

    void despawnFrom(Player p);

    void sendScores(Player p);
}
