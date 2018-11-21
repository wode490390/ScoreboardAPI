package gt.creeperface.nukkit.scoreboardapi.packet;

import cn.nukkit.network.protocol.DataPacket;
import gt.creeperface.nukkit.scoreboardapi.packet.data.ScoreInfo;
import java.util.List;

/**
 * @author CreeperFace
 */
public class SetScorePacket extends DataPacket {

    public static final byte NETWORK_ID = 0x6c;

    public Action action;
    public List<ScoreInfo> infos;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void encode() {
        reset();
        putByte((byte) action.ordinal());

        putUnsignedVarInt(infos.size());
        for (ScoreInfo it : infos) {
            putVarLong(it.scoreId);
            putString(it.objective);
            putLInt(it.score);
            putByte((byte) Type.FAKE.ordinal()); //always fake for now
            putString(it.name);
        }
    }

    @Override
    public void decode() {

    }

    public enum Action {
        SET,
        REMOVE;
    }

    public enum Type {
        INVALID,
        PLAYER,
        ENTITY,
        FAKE;
    }
}
