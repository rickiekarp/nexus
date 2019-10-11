package net.rickiekarp.botlib.enums;

public enum BotPlatforms implements BotTypeInterface {
    NONE("none"),
    ANDROID("ANDROID"),
    WEB("WEB");

    private final String type;

    BotPlatforms(final String type) {
        this.type = type;
    }

    public String getDisplayableType() {
        return type;
    }
}