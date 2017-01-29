package ch.ledcom.janki.model.json;

import lombok.Data;

import java.util.List;

@Data
public class New {
    private Integer perDay;
    private List<Integer> delays;
    private boolean separate;
    private List<Integer> ints;
    private Integer initialFactor;
    private boolean bury;
    private Integer order;
}
