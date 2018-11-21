package gt.creeperface.nukkit.scoreboardapi.scoreboard;

import gt.creeperface.nukkit.scoreboardapi.packet.SetScorePacket;
import gt.creeperface.nukkit.scoreboardapi.packet.data.ScoreInfo;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CreeperFace
 */
public class Objective {

    public final String name;
    public final ObjectiveCriteria criteria;

    public Objective(String name, ObjectiveCriteria criteria) {
        this.name = name;
        this.criteria = criteria;
    }

    private Long2ObjectOpenHashMap<Score> scores = new Long2ObjectOpenHashMap<Score>();

    private LongOpenHashSet modified = new LongOpenHashSet();
    private LongOpenHashSet renamed = new LongOpenHashSet();

    public boolean needResend = false;

    private SetScorePacket cachedPacket = null;

    private String displayName = "";

    public void setDisplayName(String displayName) {
        if (displayName.equals(this.displayName)) { //nothing changed
            return;
        }
        this.displayName = displayName;
        this.needResend = true;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setScore(long id, String name, int score) {
        Score old = this.scores.get(id);

        Score s = new Score(id, name, score);
        this.scores.put(id, s);
        this.clearCache();

        if (old != null) {
            if (!old.name.equals(name)) {
                this.renamed.add(id);
            } else if (old.value == score) {
                return; //nothing changed
            }
        }

        this.modified.add(id);
    }

    public Score getScore(long id) {
        return this.scores.get(id);
    }

    public void resetScore(long id) {
        Score score = this.scores.remove(id);
        this.renamed.remove(id);

        if (score != null) {
            this.modified.add(id);
        }
    }

    public List<SetScorePacket> getChanges() {
        if (this.modified.isEmpty()) {
            return new ArrayList<SetScorePacket>() {
            };
        }

        List<ScoreInfo> setList = new ArrayList<ScoreInfo>();
        List<ScoreInfo> removeList = new ArrayList<ScoreInfo>();

        for (long id : this.modified) {
            if (this.scores.containsKey(id)) {
                Score score = this.getScore(id);

                ScoreInfo si = new ScoreInfo();
                si.scoreId = id;
                si.objective = this.name;
                si.score = score.value;
                si.name = score.name;
                if (score != null) {
                    setList.add(si);
                }
            } else {
                ScoreInfo si = new ScoreInfo();
                si.scoreId = id;
                si.objective = this.name;
                si.score = 0;
                si.name = "";
                removeList.add(si);
            }
        }

        for (long id : this.renamed) {
            if (this.scores.containsKey(id)) {
                Score score = this.getScore(id);

                if (score != null) {
                    ScoreInfo si = new ScoreInfo();
                    si.scoreId = id;
                    si.objective = this.name;
                    si.score = score.value;
                    si.name = score.name;
                    removeList.add(si);
                }
            }
        }

        List<SetScorePacket> packets = new ArrayList<SetScorePacket>();

        if (!removeList.isEmpty()) {
            SetScorePacket pk = new SetScorePacket();
            pk.action = SetScorePacket.Action.REMOVE;
            pk.infos = removeList;
            packets.add(pk);
        }

        if (!setList.isEmpty()) {
            SetScorePacket pk = new SetScorePacket();
            pk.action = SetScorePacket.Action.SET;
            pk.infos = setList;
            packets.add(pk);
        }

        return packets;
    }

    public SetScorePacket getScorePacket() {
        if (this.cachedPacket != null) {
            return this.cachedPacket;
        }

        if (this.scores.isEmpty()) {
            return null;
        }

        List<ScoreInfo> infos = new ArrayList<ScoreInfo>();

        for (Score score : this.scores.values()) {
            ScoreInfo si = new ScoreInfo();
            si.scoreId = score.id;
            si.objective = this.name;
            si.score = score.value;
            si.name = score.name;
            infos.add(si);
        }

        SetScorePacket pk = new SetScorePacket();
        pk.action = SetScorePacket.Action.SET;
        pk.infos = infos;
        pk.encode();
        pk.isEncoded = true;

        this.cachedPacket = pk;
        return pk;
    }

    public void resetChanges() {
        this.modified.clear();
        this.needResend = false;
    }

    public void clearCache() {
        this.cachedPacket = null;
    }
}
