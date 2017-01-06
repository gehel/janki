package ch.ledcom.janki.model.json;

import lombok.Data;

import java.util.List;

@Data
public class Field {
    private String name;
    private boolean rtl = false;
    private boolean sticky = false;
    private List<String> media;
    private Integer ord = 0;
    private String font = "Arial";
    private Integer size = 12;
}
