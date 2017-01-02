package ch.ledcom.janki;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.google.common.io.ByteStreams;
import lombok.Data;
import org.springframework.core.io.Resource;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.google.common.base.Charsets.UTF_8;

public class Deck {

    private final JsonFactory jf;
    private final List<NamedResource> resources = new ArrayList<>();

    public Deck(JsonFactory jf) {
        this.jf = jf;
    }

    public void addMedia(@Nonnull String name, Resource resource) {
        resources.add(new NamedResource(name, resource));
    }

    public void dump(OutputStream out) throws IOException {
        ZipOutputStream zip = new ZipOutputStream(out, UTF_8);
        dumpDatabase(zip);
        dumpResources(zip);
        dumpMedia(zip);
        zip.finish();
    }

    private void dumpDatabase(ZipOutputStream zip) throws IOException {
        zip.putNextEntry(new ZipEntry("collection.anki2"));
        // TODO dump database
        zip.closeEntry();
    }

    private void dumpMedia(ZipOutputStream zip) throws IOException {
        zip.putNextEntry(new ZipEntry("media"));
        try (JsonGenerator generator = jf.createGenerator(zip)) {
            int i = 0;
            // TODO format does not match what is expected
            for (NamedResource namedResource : resources) {
                generator.writeStartObject();
                generator.writeNumberField("number", i++);
                generator.writeObjectField("name", namedResource.getName());
                generator.writeEndObject();
            }
        }
        zip.closeEntry();
    }

    private void dumpResources(ZipOutputStream zip) throws IOException {
        int i = 0;
        for (NamedResource namedResource : resources) {
            try (InputStream in = namedResource.getResource().getInputStream()) {
                zip.putNextEntry(new ZipEntry(Integer.toString(i++)));
                ByteStreams.copy(in, zip);
                zip.closeEntry();
            }
        }
    }

    @Data
    private class NamedResource {
        private final String name;
        private final Resource resource;

        public NamedResource(String name, Resource resource) {
            this.name = name;
            this.resource = resource;
        }
    }
}
