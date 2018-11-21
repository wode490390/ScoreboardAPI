package gt.creeperface.nukkit.scoreboardapi.scoreboard;

import cn.nukkit.Player;
import cn.nukkit.Server;
import gt.creeperface.nukkit.scoreboardapi.packet.RemoveObjectivePacket;
import gt.creeperface.nukkit.scoreboardapi.packet.SetDisplayObjectivePacket;
import gt.creeperface.nukkit.scoreboardapi.packet.SetScorePacket;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.List;

/**
 * @author CreeperFace
 */
public class FakeScoreboard implements Scoreboard {

    private Long2ObjectOpenHashMap<Player> players = new Long2ObjectOpenHashMap<Player>();

    public DisplayObjective objective;

    @Override
    public void update() {
        if (objective.objective.needResend) {
            for (Player p : players.values()) {
                this.despawnFrom(p);
                this.spawnTo(p);
            }
        } else {
            List<SetScorePacket> pks = objective.objective.getChanges();

            if (!pks.isEmpty()) {
                for (SetScorePacket pk : pks) {
                    Server.broadcastPacket(players.values(), pk);
                }
            }
        }

        objective.objective.resetChanges();
    }

    @Override
    public void addPlayer(Player p) {
        players.put(p.getId(), p);
        this.spawnTo(p);
    }

    @Override
    public void removePlayer(Player p) {
        players.remove(p.getId());
        this.despawnFrom(p);
    }

    @Override
    public void spawnTo(Player p) {
        SetDisplayObjectivePacket pk = new SetDisplayObjectivePacket();
        pk.displayObjective = this.objective;
        p.dataPacket(pk);
        this.sendScores(p);
    }

    @Override
    public void despawnFrom(Player p) {
        RemoveObjectivePacket pk = new RemoveObjectivePacket();
        pk.objectiveName = this.objective.objective.name;
        p.dataPacket(pk);
    }

    @Override
    public void sendScores(Player p) {
        SetScorePacket pk = objective.objective.getScorePacket();

        if (pk != null) {
            p.dataPacket(pk);
        }
    }
}
