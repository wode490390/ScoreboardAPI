package gt.creeperface.nukkit.scoreboardapi.packet;

import cn.nukkit.network.protocol.DataPacket;

/**
 * @author CreeperFace
 */
public class RemoveObjectivePacket extends DataPacket {

    public static final byte NETWORK_ID = 0x6a;

    public String objectiveName;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void encode() {
        reset();
        putString(objectiveName);
    }

    @Override
    public void decode() {
        objectiveName = getString();
    }
}
