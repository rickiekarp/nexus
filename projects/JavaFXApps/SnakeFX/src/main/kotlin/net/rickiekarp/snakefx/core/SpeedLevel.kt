package net.rickiekarp.snakefx.core;

/**
* Represents the different levels of speed for the game loop. The speed is
* stored as Frames per Second.
*/
public enum SpeedLevel {
	EASY(5),

	MEDIUM(10),

	HARD(15),

	INSANE(30);

	private int fps;

	SpeedLevel(final int fps) {
		this.fps = fps;
	}

	public int getFps() {
		return fps;
	}

}