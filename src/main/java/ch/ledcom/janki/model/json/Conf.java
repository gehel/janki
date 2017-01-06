package ch.ledcom.janki.model.json;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Conf {
    private Integer nextPos = 0;
    private boolean estTimes = true;
    private List<Integer> activeDecks = new ArrayList<>();
    private String sortType = "noteFld";
    private Integer timeLim = 0;
    private boolean sortBackwards = false;
    private boolean addToCur = true;
    private Integer curDeck = 1;
    private boolean newBury = true;
    private Integer newSpread = 0;
    private boolean dueCounts = true;
    private String curModel;
    private Integer collapseTime = 1200;
}
