package ch.ledcom.janki.model.json;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data @AllArgsConstructor
public class Field {
    private String name;
    private boolean rtl;
    private boolean sticky;
    private List<String> media;
    private Integer ord;
    private String font;
    private Integer size;
}
