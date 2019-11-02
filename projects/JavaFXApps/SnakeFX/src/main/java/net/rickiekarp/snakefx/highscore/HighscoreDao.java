package net.rickiekarp.snakefx.highscore;

import java.util.List;

/**
 * Interface for Data Access Object implementations to handle the persistence of
 * {@link HighScoreEntry} instances.
 */
public interface HighscoreDao {

	List<HighScoreEntry> load(String jsonString);
}
