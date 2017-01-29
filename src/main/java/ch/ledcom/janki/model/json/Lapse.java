package ch.ledcom.janki.model.json;

import lombok.Data;

import java.util.List;

@Data
public class Lapse {
    private Integer leechFails;
    private Integer minInt;
    private List<Integer> delays;
    private Integer leechAction;
    private Integer mult;
}
