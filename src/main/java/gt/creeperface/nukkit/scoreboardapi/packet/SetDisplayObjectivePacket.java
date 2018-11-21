package gt.creeperface.nukkit.scoreboardapi.packet;

import cn.nukkit.network.protocol.DataPacket;
import gt.creeperface.nukkit.scoreboardapi.scoreboard.DisplayObjective;
import gt.creeperface.nukkit.scoreboardapi.scoreboard.Objective;

/**
 * @author CreeperFace
 */
public class SetDisplayObjectivePacket extends DataPacket {

    public static final byte NETWORK_ID = 0x6b;

    public DisplayObjective displayObjective;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void encode() {
        reset();
        Objective obj = displayObjective.objective;

        putString(displayObjective.displaySlot.name().toLowerCase());
        putString(obj.name);
        putString(obj.getDisplayName());
        putString(obj.criteria.name);
        putVarInt(displayObjective.sortOrder.ordinal());
    }

    @Override
    public void decode() {

    }
}
