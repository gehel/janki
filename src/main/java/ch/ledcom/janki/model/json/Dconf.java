package ch.ledcom.janki.model.json;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Dconf {
    private String name;
    private boolean replayq;
    private Lapse lapse;
    private Rev rev;
    private Integer timer;
    private Integer maxTaken;
    private Integer usn;
    private New _new;
    private Integer mod;
    private Integer id;
    private boolean autoplay;

    public static Dconf defaultDconf() {
        Dconf dconf = new Dconf();
        dconf.setName("Default");
        dconf.setReplayq(true);

        dconf.setLapse(new Lapse());
        dconf.getLapse().setLeechFails(8);
        dconf.getLapse().setMinInt(1);
        dconf.getLapse().setDelays(new ArrayList<>());
        dconf.getLapse().getDelays().add(10);
        dconf.getLapse().setLeechAction(0);
        dconf.getLapse().setMult(0);

        dconf.setRev(new Rev());
        dconf.getRev().setPerDay(100);
        dconf.getRev().setFuzz(0.05);
        dconf.getRev().setIvlFct(1);
        dconf.getRev().setMaxIvl(36500);
        dconf.getRev().setEase4(1.3);
        dconf.getRev().setBury(true);
        dconf.getRev().setMinSpace(1);

        dconf.setTimer(0);
        dconf.setMaxTaken(60);
        dconf.setUsn(0);

        dconf.set_new(new New());
        dconf.get_new().setPerDay(20);
        dconf.get_new().setDelays(new ArrayList<>());
        dconf.get_new().getDelays().add(1);
        dconf.get_new().getDelays().add(10);
        dconf.get_new().setSeparate(true);
        dconf.get_new().setInts(new ArrayList<>());
        dconf.get_new().getInts().add(1);
        dconf.get_new().getInts().add(4);
        dconf.get_new().getInts().add(7);
        dconf.get_new().setInitialFactor(2500);
        dconf.get_new().setBury(true);
        dconf.get_new().setOrder(1);

        dconf.setMod(0);
        dconf.setId(1);
        dconf.setAutoplay(true);

        return dconf;
    }
}
