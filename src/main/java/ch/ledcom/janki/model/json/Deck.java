package ch.ledcom.janki.model.json;

import ch.ledcom.janki.utils.DateUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Deck {

    private String desc;
    private String name;
    private Integer extendRev;
    private Integer usn;
    private boolean collapsed;
    private List<Integer> newToday;
    private List<Integer> timeToday;
    private Integer dyn;
    private Integer extendNew;
    private Integer conf;
    private List<Integer> revToday;
    private List<Integer> lrnToday;
    private Integer id;
    private Integer mod;

    public static Deck defaultDeck() {
        Deck deck = new Deck();
        deck.setDesc("");
        deck.setName("Default");
        deck.setExtendRev(50);
        deck.setUsn(0);
        deck.setCollapsed(false);
        deck.setNewToday(new ArrayList<>());
        deck.getNewToday().add(0);
        deck.getNewToday().add(0);
        deck.setTimeToday(new ArrayList<>());
        deck.getTimeToday().add(0);
        deck.getTimeToday().add(0);
        deck.setDyn(0);
        deck.setExtendNew(10);
        deck.setConf(1);
        deck.setRevToday(new ArrayList<>());
        deck.getRevToday().add(0);
        deck.getRevToday().add(0);
        deck.setLrnToday(new ArrayList<>());
        deck.getLrnToday().add(0);
        deck.getLrnToday().add(0);
        deck.setId(1);
        deck.setMod(DateUtils.nowAsInt());
        return deck;
    }
}
