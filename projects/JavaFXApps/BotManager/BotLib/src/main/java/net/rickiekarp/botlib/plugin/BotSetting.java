package net.rickiekarp.botlib.plugin;

import javafx.scene.Node;

public class BotSetting {
    private final String name;
    private final String desc;
    private boolean isVisible;
    private Node node;

    public BotSetting(Builder builder) {
        this.name = builder.name;
        this.desc = builder.desc;
        this.isVisible = builder.isShown;
        this.node = builder.node;
    }

    public String getTitle() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Node getNode() {
        return node;
    }

    public static final class Builder {
        private String 			name;
        private String 			desc;
        private boolean 			isShown;
        private Node node;

        public static Builder create() {
            return new Builder();
        }

        public Builder setName(final String s) {
            name = s;
            return this;
        }

        public Builder setDescription(final String s) {
            desc = s;
            return this;
        }

        public Builder setVisible(final boolean b) {
            isShown = b;
            return this;
        }
        public Builder setNode(final Node n) {
            node = n;
            return this;
        }


        public BotSetting build() {
            if (this.node == null) {
                throw new RuntimeException("NetworkAction requires a host");
            }
            return new BotSetting(this);
        }
    }

}
