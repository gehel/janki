package ch.ledcom.janki.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
//@Entity
//@Table(name = "graves")
public class Grave {
    @Column(nullable = false) private Integer usn;
    @Column(nullable = false) private Integer oid;
    @Column(nullable = false) private Integer type;
}
