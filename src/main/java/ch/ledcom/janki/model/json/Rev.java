package ch.ledcom.janki.model.json;

import lombok.Data;

@Data
public class Rev {
    private Integer perDay;
    private Double fuzz;
    private Integer ivlFct;
    private Integer maxIvl;
    private Double ease4;
    private boolean bury;
    private Integer minSpace;
}
