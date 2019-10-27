package net.rickiekarp.snakefx.util;

import net.rickiekarp.snakefx.core.Direction;
import net.rickiekarp.snakefx.viewmodel.ViewModel;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * This class handles the input of the user.
 */
public class KeyboardHandler implements EventHandler<KeyEvent> {
	private final ViewModel viewModel;

	public KeyboardHandler(final ViewModel viewModel) {
		this.viewModel = viewModel;
	}

	// also add WASD keys to keyboard handler due to https://bugs.openjdk.java.net/browse/JDK-8215545
	@SuppressWarnings("incomplete-switch")
	@Override
	public void handle(final KeyEvent event) {
		switch (event.getCode()) {
			case W:
			case UP:
				viewModel.snakeDirection.set(Direction.UP);
				break;
			case A:
			case LEFT:
				viewModel.snakeDirection.set(Direction.LEFT);
				break;
			case S:
			case DOWN:
				viewModel.snakeDirection.set(Direction.DOWN);
				break;
			case D:
			case RIGHT:
				viewModel.snakeDirection.set(Direction.RIGHT);
				break;
		}
	}
}