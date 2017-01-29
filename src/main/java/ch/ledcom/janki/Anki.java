package ch.ledcom.janki;


import ch.ledcom.janki.model.Card;
import ch.ledcom.janki.model.Col;
import ch.ledcom.janki.model.Note;
import ch.ledcom.janki.model.json.Dconf;
import ch.ledcom.janki.model.json.Deck;
import ch.ledcom.janki.model.json.Model;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.core.io.Resource;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static ch.ledcom.janki.model.json.Conf.defaultConf;
import static ch.ledcom.janki.model.json.Deck.defaultDeck;
import static ch.ledcom.janki.model.json.Model.defaultModel;
import static ch.ledcom.janki.utils.DateUtils.nowAsInt;
import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.ByteStreams.copy;

public class Anki {

    private final JsonFactory jf;
    private final ObjectMapper objectMapper;
    private final DataSource dataSource;
    private final CardRepository cardRepository;
    private final ColRepository colRepository;
    private final NoteRepository noteRepository;
    private final Col baseCollection;

    private final List<NamedResource> resources = new ArrayList<>();

    public Anki(JsonFactory jf, ObjectMapper objectMapper, DataSource dataSource, CardRepository cardRepository, ColRepository colRepository,
                NoteRepository noteRepository) throws JsonProcessingException {
        this.jf = jf;
        this.objectMapper = objectMapper;
        this.dataSource = dataSource;
        this.cardRepository = cardRepository;
        this.colRepository = colRepository;
        this.noteRepository = noteRepository;

        this.baseCollection = initDeck();
    }

    public Col initDeck() throws JsonProcessingException {
        Col col = new Col();
        col.setCrt(nowAsInt());
        col.setMod(nowAsInt());
        col.setScm(nowAsInt());
        col.setVer(11);
        col.setDty(0);
        col.setUsn(0);
        col.setLs(0);
        col.setConf(objectMapper.writeValueAsString(defaultConf()));
        Map<String, Model> models = new HashMap<>();
        models.put("1432", defaultModel());
        col.setModels(objectMapper.writeValueAsString(models));
        Map<String, Deck> decks = new HashMap<>();
        decks.put("12345", defaultDeck());
        col.setDecks(objectMapper.writeValueAsString(decks));
        Map<String, Dconf> dconfs = new HashMap<>();
        dconfs.put("1", Dconf.defaultDconf());
        col.setDconf(objectMapper.writeValueAsString(dconfs));
        col.setTags("{}");
        return colRepository.save(col);
    }

    public void addCard(Integer modelId, Integer deckId) {
        Note note = Note.createNote(modelId, "Bonjour" + Note.SEP + "Hello", "Bonjour");
        Card card = Card.createCard(note, deckId);

        noteRepository.save(note);
        cardRepository.save(card);
    }

    public void addMedia(@Nonnull String name, Resource resource) {
        resources.add(new NamedResource(name, resource));
    }

    public void dump(OutputStream out) throws IOException, SQLException {
        ZipOutputStream zip = new ZipOutputStream(out, UTF_8);
        dumpDatabase(zip);
        dumpResources(zip);
        dumpMedia(zip);
        zip.finish();
    }

    private void dumpDatabase(ZipOutputStream zip) throws IOException, SQLException {
        File temp = File.createTempFile("janki-", ".sqlite");
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate("backup to '" + temp.getAbsolutePath() + "'");
            zip.putNextEntry(new ZipEntry("collection.anki2"));
            try (InputStream in = new BufferedInputStream(new FileInputStream(temp))) {
                copy(in, zip);
            }
            zip.closeEntry();
        } finally {
            temp.delete();
        }

    }

    private void dumpMedia(ZipOutputStream zip) throws IOException {
        zip.putNextEntry(new ZipEntry("media"));
        try (JsonGenerator generator = jf.createGenerator(zip)) {
            generator.writeStartObject();
            int i = 0;
            for (NamedResource namedResource : resources) {
                generator.writeStringField(Integer.toString(i++), namedResource.getName());
            }
            generator.writeEndObject();
            generator.close();
        }
        zip.closeEntry();
    }

    private void dumpResources(ZipOutputStream zip) throws IOException {
        int i = 0;
        for (NamedResource namedResource : resources) {
            try (InputStream in = namedResource.getResource().getInputStream()) {
                zip.putNextEntry(new ZipEntry(Integer.toString(i++)));
                copy(in, zip);
                zip.closeEntry();
            }
        }
    }

    @Data
    private static final class NamedResource {
        private final String name;
        private final Resource resource;

        public NamedResource(String name, Resource resource) {
            this.name = name;
            this.resource = resource;
        }
    }

}
