package ch.ledcom.janki.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "graves")
public class Grave {
    @Column(nullable = false) private Integer usn;
    @Column(nullable = false) private Integer oid;
    @Column(nullable = false) private Integer type;
}
