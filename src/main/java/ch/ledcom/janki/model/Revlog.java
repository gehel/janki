package ch.ledcom.janki.model;

import javax.persistence.*;

@Entity
@Table(
        name = "revlog",
        indexes = {
                @Index(name = "ix_revlog_cid", columnList = "cid"),
                @Index(name = "ix_revlog_usn", columnList = "usn"),
        }
)
public class Revlog {
    @Id @Column(nullable = false) private Integer id;
    @ManyToOne(optional = false) @JoinColumn(name = "cid") private Card card;
    @Column(nullable = false) private Integer usn;
    @Column(nullable = false) private Integer ease;
    @Column(nullable = false) private Integer ivl;
    @Column(nullable = false) private Integer lastIvl;
    @Column(nullable = false) private Integer factor;
    @Column(nullable = false) private Integer time;
    @Column(nullable = false) private Integer type;
}
