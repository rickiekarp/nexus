package net.rickiekarp.snakefx.highscore;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.type.TypeFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * DAO implementation for {@link HighScoreEntry} that is using a JSON for persistence.
 */
public class HighscoreJsonDao implements HighscoreDao {

    private final ObjectMapper mapper;
    private final TypeFactory typeFactory;

    public HighscoreJsonDao() {
        mapper = new ObjectMapper();
        mapper.configure(Feature.INDENT_OUTPUT, true);
        typeFactory = TypeFactory.defaultInstance();
    }

    @Override
    public List<HighScoreEntry> load(String jsonString) {
        if (jsonString != null) {
            try {
                return mapper.readValue(jsonString, typeFactory.constructCollectionType(List.class, HighScoreEntry.class));
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return Collections.emptyList();
    }

}
