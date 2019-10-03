package net.rickiekarp.botlib.enums;

public final class BotType {

    public enum Bot implements BotTypeInterface {

        NONE("none", BotPlatforms.NONE),
        CHROME("chrome", BotPlatforms.WEB),
        FIREFOX("firefox", BotPlatforms.WEB),
        ANDROID("android", BotPlatforms.ANDROID);

        private final String label;
        private final BotPlatforms type;

        Bot(String label, BotPlatforms type) {
            this.label = label;
            this.type = type;
        }

        public String getLabel() {
            return label;
        }

        public BotPlatforms getBotPlatform() {
            return type;
        }


        public String getDisplayableType() {
            return type.getDisplayableType();
        }
    }

    public BotType() {
        super();
    }

    public static void main(String[] args) {
        System.out.println("All bot types");
        for (BotPlatforms type : BotPlatforms.values()) {
            displayType(type);
            System.out.println();
        }
        System.out.println("All bot platforms");
        for (Bot bot : Bot.values()) {
            displayBot(bot);
            System.out.println();
        }
    }

    private static void displayBot(Bot bot) {
        displayType(bot);
        System.out.print(" - ");
        System.out.print(bot.getLabel());
    }

    private static void displayType(BotTypeInterface displayable) {
        System.out.print(displayable.getDisplayableType());
    }
}
