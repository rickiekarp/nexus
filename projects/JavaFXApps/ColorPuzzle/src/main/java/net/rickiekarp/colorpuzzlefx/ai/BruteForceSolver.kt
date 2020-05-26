package net.rickiekarp.colorpuzzlefx.ai;

import net.rickiekarp.colorpuzzlefx.core.Colors;
import net.rickiekarp.colorpuzzlefx.core.GameLogic;

public class BruteForceSolver implements Solver {
    private GameLogic game;

    private int indexLastColor = 0;

    @Override
    public void setGame(GameLogic game) {
        this.game = game;
    }

    @Override
    public Colors nextStep() {

        final Colors nextColor = Colors.values()[indexLastColor];

        if((indexLastColor +1) < Colors.values().length) {
            indexLastColor++;
        } else {
            indexLastColor = 0;
        }


        return nextColor;
    }
}
