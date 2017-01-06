package ch.ledcom.janki;

import ch.ledcom.janki.model.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends CrudRepository<Note, Integer> {
}
