package net.rickiekarp.snakefx.highscore;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import net.rickiekarp.core.AppContext;
import net.rickiekarp.snakefx.net.SnakeNetworkApi;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.Date;

import static net.rickiekarp.snakefx.config.Config.*;

/**
 * The purpose of the HighscoreManager is to add new highscore entries and to
 * verify that there are only as many entries in the highscore list as defined
 * in {@link net.rickiekarp.snakefx.config.Config#MAX_SCORE_COUNT}.
 */
public class HighscoreManager {

	private final ListProperty<HighScoreEntry> highScoreEntries = new SimpleListProperty<>(
			new ObservableListWrapper<>(new ArrayList<>()));

	public HighscoreManager() {

	}

	public ListProperty<HighScoreEntry> highScoreEntries() {
		return highScoreEntries;
	}

	public void addScore(final String name, final int points) {
        final HighScoreEntry entry = new HighScoreEntry(name, points, new Date());
		Response response = AppContext.Companion.getContext().getNetworkApi().requestResponse(SnakeNetworkApi.Companion.requestAddHighscore(entry));
		if (response.code() == 200) {
			highScoreEntries.add(entry);
			updateList();
		}
	}

	public boolean isNameValid(final String name) {
		if (name == null) {
			return false;
		}
		if (name.isEmpty()) {
			return false;
		}
		if (name.contains(",")) {
			return false;
		}
		return !name.contains(";");
	}

	private void updateList() {
        FXCollections.sort(highScoreEntries);
		for (int i = 0; i < highScoreEntries.size(); i++) {
			if (i < MAX_SCORE_COUNT.get()) {
				highScoreEntries.get(i).setRanking(i + 1);
			} else {
				highScoreEntries.remove(i);
			}
		}
	}
}
