package ch.ledcom.janki;

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

import javax.sql.DataSource;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JankiConfiguration.class)
public class AnkiTest {

    @Autowired private CardRepository cardRepository;
    @Autowired private ColRepository colRepository;
    @Autowired private NoteRepository noteRepository;
    @Autowired private DataSource dataSource;

    @Test
    public void test() throws IOException, SQLException {

        JsonFactory jf = new JsonFactory();
        jf.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);

        ObjectMapper objectMapper = new ObjectMapper(jf);

        Anki anki = new Anki(jf, objectMapper, dataSource, cardRepository, colRepository, noteRepository);
        anki.addMedia("bonjour.svg", getResource("bonjour.svg"));
        anki.addCard(1432, 12345);

        File outFile = new File("/tmp/test.apkg");
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(outFile))) {
            anki.dump(out);
        }
    }

    private Resource getResource(String name) {
        return new ClassPathResource("ch/ledcom/janki/" + name);
    }

}
