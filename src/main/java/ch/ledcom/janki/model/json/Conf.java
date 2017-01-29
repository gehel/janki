package ch.ledcom.janki.model.json;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Conf {
    private Integer nextPos ;
    private boolean estTimes;
    private List<Integer> activeDecks;
    private String sortType;
    private Integer timeLim;
    private boolean sortBackwards;
    private boolean addToCur;
    private Integer curDeck;
    private boolean newBury;
    private Integer newSpread;
    private boolean dueCounts;
    private String curModel;
    private Integer collapseTime;

    public static Conf defaultConf() {
        Conf conf = new Conf();
        conf.setNextPos(1);
        conf.setEstTimes(true);
        conf.setActiveDecks(new ArrayList<>());
        conf.getActiveDecks().add(1);
        conf.setSortType("noteFld");
        conf.setTimeLim(0);
        conf.setSortBackwards(false);
        conf.setAddToCur(true);
        conf.setCurDeck(1);
        conf.setNewBury(true);
        conf.setNewSpread(0);
        conf.setDueCounts(true);
        conf.setCurModel("???");
        conf.setCollapseTime(1200);
        return conf;
    }
}
