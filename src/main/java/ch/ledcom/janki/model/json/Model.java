package ch.ledcom.janki.model.json;

import lombok.Data;

import java.util.List;

@Data
public class Model {

    private List<String> vers;
    private String name;
    private List<String> tags;
    private Integer did;
    private Integer usn;
    private Object req;
    private List<Field> flds;
    private Integer sortf = 0;
    private String latexPre;
    private List<Template> tmpls;
    private String latexPost;
    private Integer type;
    private Integer id;
    private String css;
    private Integer mod;
}
