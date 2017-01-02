package ch.ledcom.janki.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(
        name = "cards",
        indexes = {
                @Index(name = "ix_cards_nid", columnList = "nid"),
                @Index(name = "ix_cards_sched", columnList = "did, queue, due"),
                @Index(name = "ix_cards_usn", columnList = "usn"),
        }
)
public class Card {
    @Id @Column(nullable = false) private Integer id;
    @ManyToOne(optional = false) @JoinColumn(name = "nid") private Note note;
    @Column(nullable = false) private Integer did;
    @Column(nullable = false) private Integer ord;
    @Column(nullable = false) private Integer mod;
    @Column(nullable = false) private Integer usn;
    @Column(nullable = false) private Integer type;
    @Column(nullable = false) private Integer queue;
    @Column(nullable = false) private Integer due;
    @Column(nullable = false) private Integer ivl;
    @Column(nullable = false) private Integer factor;
    @Column(nullable = false) private Integer reps;
    @Column(nullable = false) private Integer lapses;
    @Column(nullable = false) private Integer left;
    @Column(nullable = false) private Integer odue;
    @Column(nullable = false) private Integer odid;
    @Column(nullable = false) private Integer flags;
    @Column(nullable = false, columnDefinition = "text") private String data;
}
