package net.rickiekarp.snakefx.core;

import net.rickiekarp.snakefx.view.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static net.rickiekarp.snakefx.settings.Config.*;

/**
 * This class represents the snake.
 */
public class Snake {
    private GameField head;
    private final Grid grid;
    private final int x;
    private final int y;
    private Direction currentDirection;
    private Direction nextDirection;
    private final List<GameField> tail;
    private final ViewModel viewModel;

    /**
     * @param viewModel the viewModel
     * @param grid      the grid on which the snake is created
     * @param gameLoop  the gameloop that is used for the movement of the snake
     */
    public Snake(final ViewModel viewModel, final Grid grid, final GameLoop gameLoop) {
        this.viewModel = viewModel;
        this.grid = grid;
        x = SNAKE_START_X.get();
        y = SNAKE_START_Y.get();

        tail = new ArrayList<>();

        gameLoop.addActions(x -> move());

        viewModel.getSnakeDirection().addListener( (observable, oldDirection, newDirection) -> {
            this.changeDirection(newDirection);
        });
    }

    /**
     * Initalizes the fields of the snake.
     */
    public void init() {
        setHead(grid.getXY(x, y));

        viewModel.getCollision().set(false);

        viewModel.getPoints().set(0);

        currentDirection = Direction.UP;
        nextDirection = Direction.UP;
    }

    /**
     * Change the direction of the snake. The direction is only changed when the new direction has <bold>not</bold> the
     * same orientation as the old one.
     * <p/>
     * For example, when the snake currently has the direction UP and the new direction should be DOWN, nothing will
     * happend because both directions are vertical.
     * <p/>
     * This is to prevent the snake from moving directly into its own tail.
     *
     * @param newDirection
     */
    private void changeDirection(final Direction newDirection) {
        if (!newDirection.hasSameOrientation(currentDirection)) {
            nextDirection = newDirection;
        }
    }

    /**
     * Move the snake by one field.
     */
    void move() {
        currentDirection = nextDirection;

        // prevent snake direction from being different than the current direction
        if (viewModel.getSnakeDirection().get() != currentDirection) {
           viewModel.getSnakeDirection().set(currentDirection);
        }


        final GameField newHead = grid.getFromDirection(head, currentDirection);

        if (Objects.equals(newHead.getState(), State.TAIL)) {
            viewModel.getCollision().set(true);
            return;
        }

        boolean grow = false;
        if (Objects.equals(newHead.getState(), State.FOOD)) {
            grow = true;
        }

        GameField lastField = head;

        for (int i = 0; i < tail.size(); i++) {
            final GameField f = tail.get(i);

            lastField.changeState(State.TAIL);
            tail.set(i, lastField);

            lastField = f;
        }

        if (grow) {
            grow(lastField);
            addPoints();
        } else {
            lastField.changeState(State.EMPTY);
        }

        setHead(newHead);
    }

    public void newGame() {
        tail.clear();
        init();
    }

    private void setHead(final GameField head) {
        this.head = head;
        head.changeState(State.HEAD);
    }

    /**
     * The given field is added to the tail of the snake and gets the state TAIL.
     *
     * @param field
     */
    private void grow(final GameField field) {
        field.changeState(State.TAIL);
        tail.add(field);
    }

    private void addPoints() {
        final int current = viewModel.getPoints().get();
        viewModel.getPoints().set(current + 1);
    }

    public GameField getHead() {
        return head;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public Direction getNextDirection() {
        return nextDirection;
    }

    public List<GameField> getTail() {
        return tail;
    }
}