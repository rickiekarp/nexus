package net.rickiekarp.snakefx.core;

import net.rickiekarp.snakefx.view.ViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.FieldSetter;

import java.util.List;

import static org.mockito.Mockito.*;

class SnakeTest {
	private Snake snake;

	private Grid gridMock;

	private static final int X = 4;
	private static final int Y = 2;

	private ViewModel viewModel;

	@BeforeEach
	void setUp() throws NoSuchFieldException {
		gridMock = mock(Grid.class);
		GameLoop gameLoop = mock(GameLoop.class);

		viewModel = new ViewModel();

		snake = new Snake(viewModel, gridMock, gameLoop);
		FieldSetter.setField(snake, snake.getClass().getDeclaredField("x"), X);
		FieldSetter.setField(snake, snake.getClass().getDeclaredField("y"), Y);
	}

//	@Test
//	void testInitialization() {
//		final GameField field = mock(GameField.class);
//
//		when(gridMock.getXY(X, Y)).thenReturn(field);
//
//		snake.init();
//
//		verify(gridMock, times(1)).getXY(X, Y);
//		verify(field, times(1)).changeState(State.HEAD);
//
//		Assertions.assertEquals(getHead(), field);
//
//		// The direction of the snake is UP on start.
//		final Direction direction = currentDirectionFromSnake();
//		Assertions.assertEquals(direction, Direction.UP);
//	}

	@Test
	void testChangeDirection() {
		viewModel.getSnakeDirection().set(Direction.LEFT);
		final Direction direction = nextDirectionFromSnake();
		Assertions.assertEquals(direction, Direction.LEFT);
	}

//	/**
//	 * When the new direction has the same orientation as the old one ( both are
//	 * horizontal or both are vertical) no change of the direction should be
//	 * made.
//	 *
//	 * Otherwise the head of the snake would move directly into the tail.
//	 *
//	 *
//	 * But if the player pressed LEFT and then DOWN faster then the gap between
//	 * two frames, then the Snake would make a 180 degree turnaround. The LEFT
//	 * keypress wouldn't be filtered out because LEFT has another orientation
//	 * then UP and the DOWN keypress wouldn't be filtered out because LEFT
//	 * (which is the "next direction" now) has another orientation then DOWN.
//	 *
//	 * To prevend this we have two variables for the direction: "nextDirection"
//	 * and "currentDirection". When the player likes to change the direction,
//	 * only nextDirection is changed but he test whether the orientation is the
//	 * same is done with the "currentDirection". When the snake moves, the
//	 * "currentDirection" variable gets the value from "nextDirection".
//	 *
//	 */
//	@Test
//	void testChangeDirectionNewHasSameOrientationAsOld() {
//		final GameField head = mock(GameField.class);
//		when(gridMock.getXY(X, Y)).thenReturn(head);
//
//		final GameField newHead = mock(GameField.class);
//		when(newHead.getState()).thenReturn(State.EMPTY);
//		when(gridMock.getFromDirection(head, Direction.LEFT)).thenReturn(newHead);
//
//		// Snake is initialized with currentDirection=UP and nextDirection=UP
//		snake.init();
//
//		viewModel.getSnakeDirection().set(Direction.DOWN);
//
//		// currentDirection and nextDirection is still UP because the
//		// orientation is the same
//		Assertions.assertEquals(nextDirectionFromSnake(), Direction.UP);
//		Assertions.assertEquals(currentDirectionFromSnake(), Direction.UP);
//
//		viewModel.getSnakeDirection().set(Direction.LEFT);
//		// the nextDirection is now changed...
//		Assertions.assertEquals(nextDirectionFromSnake(), Direction.LEFT);
//		// ... the currentDirection is still the old one. It is only changed
//		// when the
//		// snake moves.
//		Assertions.assertEquals(currentDirectionFromSnake(), Direction.UP);
//
//		viewModel.getSnakeDirection().set(Direction.DOWN);
//		// nextDirection is not changed as the currentDirection is still UP and
//		// has the same orientation as DOWN
//		Assertions.assertEquals(nextDirectionFromSnake(), Direction.LEFT);
//		Assertions.assertEquals(currentDirectionFromSnake(), Direction.UP);
//
//		snake.move();
//
//		Assertions.assertEquals(nextDirectionFromSnake(), Direction.LEFT);
//		// now the currentDirection has changed.
//		Assertions.assertEquals(currentDirectionFromSnake(), Direction.LEFT);
//	}

//	@Test
//	void testMove() {
//		final GameField oldHead = mock(GameField.class);
//		when(oldHead.getState()).thenReturn(State.EMPTY);
//		when(gridMock.getXY(X, Y)).thenReturn(oldHead);
//
//		snake.init();
//
//		final GameField newHead = mock(GameField.class);
//		when(newHead.getState()).thenReturn(State.EMPTY);
//		when(gridMock.getFromDirection(oldHead, Direction.UP)).thenReturn(newHead);
//
//		snake.move();
//
//		Assertions.assertEquals(getHead(), newHead);
//
//		verify(oldHead).changeState(State.EMPTY);
//	}

	/**
	 * When the snake moves to a field that has the state "FOOD" the snake
	 * should grow by 1 field.
	 */
	@Test
	void testGrow() {
		final GameField field1 = new GameField(0, 3, 10);
		// at the start field1 is the head
		when(gridMock.getXY(X, Y)).thenReturn(field1);

		// field2 is above field1
		final GameField field2 = new GameField(0, 2, 10);
		field2.changeState(State.FOOD);
		when(gridMock.getFromDirection(field1, Direction.UP)).thenReturn(field2);

		// field3 is above field2
		final GameField field3 = new GameField(0, 1, 10);
		when(gridMock.getFromDirection(field2, Direction.UP)).thenReturn(field3);

		snake.init();

		snake.move();

		// the head of the snake is now on field2
		Assertions.assertEquals(getHead(), field2);

		// field1 is now a part of the tail
		Assertions.assertEquals(field1.getState(), State.TAIL);

		// One Point has to be added.
		Assertions.assertEquals(viewModel.getPoints().get(), 1);

		// Now the snake is moving another field forward. This time the new
		// field (field3)
		// is empty.

		snake.move();

		// field3 becomes the new head
		Assertions.assertEquals(getHead(), field3);

		// field2 becomes the tail
		Assertions.assertEquals(field2.getState(), State.TAIL);

		// field1 is now empty
		Assertions.assertEquals(field1.getState(), State.EMPTY);
	}

//	@Test
//	void testCollision() {
//
//		final GameField oldHead = new GameField(0, 0, 4);
//		when(oldHead.getState()).thenReturn(State.EMPTY);
//		when(gridMock.getXY(X, Y)).thenReturn(oldHead);
//
//		snake.init();
//
//		final GameField tail = mock(GameField.class);
//		when(tail.getState()).thenReturn(State.TAIL);
//		when(gridMock.getFromDirection(oldHead, Direction.UP)).thenReturn(tail);
//
//		snake.move();
//
//		Assertions.assertTrue(viewModel.getCollision().get());
//	}

//	/**
//	 * When the newGame method is called, the head must be set to null and the
//	 * tails arraylist has to be reset.
//	 *
//	 * The init method has to be called too.
//	 */
//	@Test
//	void testNewGame() {
//		final GameField head = mock(GameField.class);
//		when(head.getState()).thenReturn(State.EMPTY);
//		when(gridMock.getXY(X, Y)).thenReturn(head);
//
//		final GameField food = mock(GameField.class);
//		when(food.getState()).thenReturn(State.FOOD);
//		when(gridMock.getFromDirection(head, Direction.UP)).thenReturn(food);
//
//		snake.init();
//		snake.move();
//
//		Assertions.assertEquals(getHead(), food);
//		Assertions.assertEquals(1, getTail().size());
//		Assertions.assertTrue(getTail().contains(head));
//
//		snake.newGame();
//
//		// the head is reset and the tail is empty
//		Assertions.assertEquals(getHead(), head);
//		Assertions.assertTrue(getTail().isEmpty());
//	}

	@SuppressWarnings("unchecked")
	private List<GameField> getTail() {
		return snake.getTail();
	}

	private GameField getHead() {
		return snake.getHead();
	}

	private Direction nextDirectionFromSnake() {
		return snake.getNextDirection();
	}

	private Direction currentDirectionFromSnake() {
		return snake.getCurrentDirection();
	}
}
