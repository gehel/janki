package ch.ledcom.janki;

import ch.ledcom.janki.model.Col;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColRepository extends CrudRepository<Col, Integer> {
}
