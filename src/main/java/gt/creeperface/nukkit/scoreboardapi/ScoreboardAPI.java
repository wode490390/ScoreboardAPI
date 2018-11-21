package gt.creeperface.nukkit.scoreboardapi;

import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import gt.creeperface.nukkit.scoreboardapi.packet.RemoveObjectivePacket;
import gt.creeperface.nukkit.scoreboardapi.packet.SetDisplayObjectivePacket;
import gt.creeperface.nukkit.scoreboardapi.packet.SetScorePacket;

/**
 * @author CreeperFace
 */
public class ScoreboardAPI extends PluginBase implements Listener {

    @Override
    public void onLoad() {
        this.getServer().getNetwork().registerPacket(RemoveObjectivePacket.NETWORK_ID, RemoveObjectivePacket.class);
        this.getServer().getNetwork().registerPacket(SetDisplayObjectivePacket.NETWORK_ID, SetDisplayObjectivePacket.class);
        this.getServer().getNetwork().registerPacket(SetScorePacket.NETWORK_ID, SetScorePacket.class);
    }
}
