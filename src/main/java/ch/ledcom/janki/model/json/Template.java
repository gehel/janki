package ch.ledcom.janki.model.json;

import lombok.Data;

@Data
public class Template {
    private String name;
    private String qfmt;
    private Integer did;
    private String bafmt;
    private String afmt;
    private Integer ord;
    private String bqfmt;
}
