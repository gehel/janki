package ch.ledcom.janki;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

import static org.sqlite.SQLiteConfig.Encoding.UTF8;

public class DeckTest {

    @Test
    public void test() throws IOException, SQLException {
        SQLiteDataSource dataSource = createDatasource();

        try (Connection connection = dataSource.getConnection()) {
            connection.createStatement().executeUpdate("CREATE TABLE test_table(id PRIMARY KEY )");
        }

        JsonFactory jf = new JsonFactory();
        jf.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);

        Deck deck = new Deck(jf, dataSource);
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
