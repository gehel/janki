package ch.ledcom.janki;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.google.common.io.ByteStreams;
import lombok.Data;
import org.springframework.core.io.Resource;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.ByteStreams.copy;

public class Deck {

    private final JsonFactory jf;
    private final DataSource dataSource;

    private final List<NamedResource> resources = new ArrayList<>();

    public Deck(JsonFactory jf, DataSource dataSource) {
        this.jf = jf;
        this.dataSource = dataSource;
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
        File temp = File.createTempFile("janki", "sqlite");
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
//            statement.setString(1, temp.getCanonicalPath());
//            statement.executeUpdate();
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
