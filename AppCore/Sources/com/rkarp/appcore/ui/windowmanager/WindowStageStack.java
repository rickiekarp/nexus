package com.rkarp.appcore.ui.windowmanager;

import com.rkarp.appcore.debug.LogFileHandler;

import java.util.Stack;

public class WindowStageStack extends Stack<WindowStage> {
    private WindowStack sceneViewStack;

    public WindowStageStack() {
        sceneViewStack = new WindowStack();
    }

    @Override
    public WindowStage push(WindowStage item) {
        addElement(item);
        LogFileHandler.logger.info("Push " + item + " - new size: " + size());
        return item;
    }

    @Override
    public WindowStage pop() {
        LogFileHandler.logger.info("pop -> " + this.peek());
        return super.pop();
    }

    WindowStage pop(String stageIdentifier) {
        for (int i = 0; i < size(); i++) {
            if (get(i).getIdentifier().equals(stageIdentifier)) {
                return pop();
            }
        }
        LogFileHandler.logger.info("Element with identifier " + stageIdentifier + " could not be found!");
        return null;
    }

    public WindowStage getStageByIdentifier(String stageIdentifier) {
        for (WindowStage windowStage : this) {
            if (windowStage.getIdentifier().equals(stageIdentifier)) {
                return windowStage;
            }
        }
        return null;
    }

    public WindowStack getSceneViewStack() {
        return sceneViewStack;
    }
}
