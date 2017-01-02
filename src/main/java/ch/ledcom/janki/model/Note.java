package ch.ledcom.janki.model;

import javax.persistence.*;

@Entity
@Table(
        name = "notes",
        indexes = {
                @Index(name = "ix_notes_csum", columnList = "csum"),
                @Index(name = "ix_notes_usn", columnList = "usn"),
        }

)
public class Note {
    @Id @Column(nullable = false) private Integer id;
    @Column(nullable = false) private String guid;
    @Column(nullable = false) private Integer mid;
    @Column(nullable = false) private Integer mod;
    @Column(nullable = false) private Integer usn;
    @Column(nullable = false) private String tags;
    @Column(nullable = false) private String flds;
    @Column(nullable = false) private Integer sfld;
    @Column(nullable = false) private Integer csum;
    @Column(nullable = false) private Integer flags;
    @Column(nullable = false) private String data;
}
