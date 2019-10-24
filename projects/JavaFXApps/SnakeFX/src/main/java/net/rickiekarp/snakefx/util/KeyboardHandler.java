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

	@SuppressWarnings("incomplete-switch")
	@Override
	public void handle(final KeyEvent event) {
		switch (event.getCode()) {
		case UP:
			System.out.println("up");
			viewModel.snakeDirection.set(Direction.UP);
			break;
		case DOWN:
			System.out.println("down");
			viewModel.snakeDirection.set(Direction.DOWN);
			break;
		case LEFT:
			System.out.println("left");
			viewModel.snakeDirection.set(Direction.LEFT);
			break;
		case RIGHT:
			System.out.println("right");
			viewModel.snakeDirection.set(Direction.RIGHT);
			break;
		}
	}
}