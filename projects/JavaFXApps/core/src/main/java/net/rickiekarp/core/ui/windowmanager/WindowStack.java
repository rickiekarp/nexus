package net.rickiekarp.core.ui.windowmanager;

import net.rickiekarp.core.view.MainScene;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.Stack;

public class WindowStack extends Stack<Node> {
    private Button backButton;

    @Override
    public Node push(Node item) {
        //System.out.println("Push " + item);
        addElement(item);
        MainScene.mainScene.getBorderPane().setCenter(item);
        if (this.size() == 2) {
            initializeBackButton();
            MainScene.mainScene.getWindowScene().getWin().getTitlebarButtonBox().getChildren().add(backButton);
        }
        return item;
    }

    @Override
    public Node pop() {
        //System.out.print("pop -> " + this.peek());
        return super.pop();
    }

    private void initializeBackButton() {
        backButton = new Button("Back");
        backButton.setOnAction(event -> {
            this.pop();
            MainScene.mainScene.getBorderPane().setCenter(super.peek());
            if (this.size() == 1) {
                MainScene.mainScene.getWindowScene().getWin().getTitlebarButtonBox().getChildren().remove(backButton);
                backButton = null;
            }
        });
    }
}
