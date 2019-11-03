package net.rickiekarp.snakefx.viewmodel;

import net.rickiekarp.snakefx.core.Direction;
import net.rickiekarp.snakefx.core.SpeedLevel;
import javafx.animation.Animation.Status;
import javafx.beans.property.*;

import static net.rickiekarp.snakefx.config.Config.*;

/**
 * This class is the central viewmodel that contains the current state of the
 * applications main properties.
 */
public class ViewModel {

	public final IntegerProperty points = new SimpleIntegerProperty(0);

	public final ObjectProperty<SpeedLevel> speed = new SimpleObjectProperty<>(SpeedLevel.MEDIUM);

	public final BooleanProperty collision = new SimpleBooleanProperty(false);

	public final ObjectProperty<Status> gameloopStatus = new SimpleObjectProperty<>(Status.STOPPED);

	public final IntegerProperty gridSize = new SimpleIntegerProperty(ROW_AND_COLUMN_COUNT.get());

	public final BooleanProperty newHighscoreWindowOpen = new SimpleBooleanProperty(false);

	public final ObjectProperty<Direction> snakeDirection = new SimpleObjectProperty<>(Direction.UP);
}