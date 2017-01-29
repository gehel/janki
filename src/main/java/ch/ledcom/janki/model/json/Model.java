package ch.ledcom.janki.model.json;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static ch.ledcom.janki.utils.DateUtils.nowAsInt;

@Data
public class Model {
    private List<String> vers;
    private String name;
    private List<String> tags;
    private Integer did;
    private Integer usn;
    private List<Object> req;
    private List<Field> flds;
    private Integer sortf;
    private String latexPre;
    private List<Template> tmpls;
    private String latexPost;
    private Integer type;
    private Integer id;
    private String css;
    private Integer mod;

    public static Model defaultModel() {
        Model model = new Model();
        model.setVers(new ArrayList<>());
        model.setName("Basic");
        model.setTags(new ArrayList<>());
        model.setDid(nowAsInt());
        model.setUsn(-1);
        model.setReq(new ArrayList<>()); // TODO
        model.setFlds(new ArrayList<>());
        model.getFlds().add(new Field("Back", false, false, new ArrayList<>(), 1, "Arial", 12));
        return model;
    }
}
