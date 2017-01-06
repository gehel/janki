package ch.ledcom.janki.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "col")
public class Col {
    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(nullable = false) private Integer id;
    @Column(nullable = false) private Integer crt;
    @Column(nullable = false) private Integer mod;
    @Column(nullable = false) private Integer scm;
    @Column(nullable = false) private Integer ver;
    @Column(nullable = false) private Integer dty;
    @Column(nullable = false) private Integer usn;
    @Column(nullable = false) private Integer ls;
    @Column(nullable = false, columnDefinition = "text") private String conf;
    @Column(nullable = false, columnDefinition = "text") private String models;
    @Column(nullable = false, columnDefinition = "text") private String decks;
    @Column(nullable = false, columnDefinition = "text") private String dconf;
    @Column(nullable = false, columnDefinition = "text") private String tags;
}
