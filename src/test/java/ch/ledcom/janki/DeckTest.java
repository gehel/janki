package ch.ledcom.janki;

import ch.ledcom.janki.model.JankiConfiguration;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

import static org.sqlite.SQLiteConfig.Encoding.UTF8;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JankiConfiguration.class)
public class DeckTest {

    @Autowired private CardRepository cardRepository;
    @Autowired private ColRepository colRepository;
    @Autowired private NoteRepository noteRepository;

    @Test
    public void test() throws IOException, SQLException {
        SQLiteDataSource dataSource = createDatasource();

        try (Connection connection = dataSource.getConnection()) {
            connection.createStatement().executeUpdate("CREATE TABLE test_table(id PRIMARY KEY )");
        }

        JsonFactory jf = new JsonFactory();
        jf.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);

        ObjectMapper objectMapper = new ObjectMapper(jf);

        Deck deck = new Deck(jf, objectMapper, dataSource, cardRepository, colRepository, noteRepository);
        deck.addMedia("bonjour.svg", getResource("bonjour.svg"));
        File outFile = new File("/tmp/test.apkg");
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(outFile))) {
            deck.dump(out);
        }
    }

    private Resource getResource(String name) {
        return new ClassPathResource("ch/ledcom/janki/" + name);
    }

    private SQLiteDataSource createDatasource() {
        SQLiteConfig config = new SQLiteConfig();
        config.setEncoding(UTF8);
        config.enforceForeignKeys(true);
        SQLiteDataSource dataSource = new SQLiteDataSource(config);
        dataSource.setUrl("jdbc:sqlite::memory:");
        return dataSource;
    }
}
