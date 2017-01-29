package ch.ledcom.janki.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.concurrent.ThreadLocalRandom;

import static ch.ledcom.janki.utils.DateUtils.nowAsInt;
import static java.util.UUID.randomUUID;

@Data
@Entity
@Table(
        name = "notes",
        indexes = {
                @Index(name = "ix_notes_csum", columnList = "csum"),
                @Index(name = "ix_notes_usn", columnList = "usn"),
        }

)
public class Note {

    public static final char SEP = '\u001f';

    @Id @Column(nullable = false) private Integer id;
    @Column(nullable = false) private String guid;
    /** identifier of the @{@link ch.ledcom.janki.model.json.Model}. */
    @Column(nullable = false) private Integer mid;
    @Column(nullable = false) private Integer mod;
    @Column(nullable = false) private Integer usn;
    @Column(nullable = false) private String tags;
    @Column(nullable = false) private String flds;
    @Column(nullable = false) private String sfld;
    @Column(nullable = false) private Integer csum;
    @Column(nullable = false) private Integer flags;
    @Column(nullable = false) private String data;

    public static Note createNote(Integer modelId, String fields, String sFields) {
        Note note = new Note();
        note.setId(ThreadLocalRandom.current().nextInt());
        note.setGuid(randomUUID().toString());
        note.setMid(modelId);
        note.setMod(nowAsInt());
        note.setUsn(-1);
        note.setTags("");
        note.setFlds(fields);
        note.setSfld(sFields);
        note.setCsum(0); // TODO: actually compute the hash
        note.setFlags(0);
        note.setData("");
        return note;
    }
}
