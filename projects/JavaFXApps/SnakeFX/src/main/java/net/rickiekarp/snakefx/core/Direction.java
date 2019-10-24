package net.rickiekarp.snakefx.core;

/**
* This enum represents the directions that the snake can go.
*/
public enum Direction {

	UP(false),

	DOWN(false),

	LEFT(true),

	RIGHT(true);

	private boolean horizontal;

	private Direction(final boolean horizontal) {
		this.horizontal = horizontal;
	}

	public boolean hasSameOrientation(final Direction other) {
		if (other == null) {
			return false;
		}
		return (horizontal == other.horizontal);
	}
}
