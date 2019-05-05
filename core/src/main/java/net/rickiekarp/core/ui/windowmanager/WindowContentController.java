package net.rickiekarp.core.ui.windowmanager;

import net.rickiekarp.core.components.button.SidebarButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class WindowContentController {
    private List<SidebarButton> sidebarNodes;
    private HBox titlebarRightButtonBox;
    private VBox sidebarButtonBox;

    WindowContentController() {
        sidebarNodes = new ArrayList<>(3);
    }

    public void addSidebarItem(int position, SidebarButton node) {
        sidebarNodes.add(position, node);
    }

    void addSidebarItem(SidebarButton node) {
        addSidebarItem(sidebarNodes.size(), node);
    }

    /**
     * Removes a SidebarButton item by its given identifier
     * @param identifier SidebarButton identifier
     */
    public void removeSidebarItemByIdentifier(String identifier) {
        for (int i = 0; i < sidebarNodes.size(); i++) {
            if (sidebarNodes.get(i).getIdentifier().equals(identifier)) {
                sidebarNodes.remove(i);
            }
        }
        sidebarButtonBox.getChildren().setAll(sidebarNodes);
    }

    List<SidebarButton> getList() {
        return sidebarNodes;
    }

    HBox getTitlebarButtonBox() {
        return titlebarRightButtonBox;
    }

    VBox getSidebarButtonBox() {
        return sidebarButtonBox;
    }

    void setTitlebarRightButtonBox(HBox titlebar) {
        this.titlebarRightButtonBox = titlebar;
    }

    void setSidebarButtonBox(VBox sidebar) {
        this.sidebarButtonBox = sidebar;
    }
}
