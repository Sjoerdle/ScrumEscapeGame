package org.game.rooms;

public class Emojis {

    public static String getEmojiForCharacter(char character) throws Exception {
        switch (character) {
            case 'P':
                return PLAYER;
            case 'M':
                return MONSTER;
            case 'K':
                return KEY;
            case 'D':
                return DOOR;
            case 'L':
                return LOCK;
            case '|':
            case '+':
            case '-':
                return WALL;
            case '#':
                return WALL_CHAIN;
            case 'E':
                return EXIT;
            case '*':
                return MAGIC;
            case 'H':
                return HEALTH;
            case ' ':
                return EMPTY;
            default:
                throw new Exception("Char not implemented: " + character);
        }
    }

    public static final String WALL = "🧱";
    public static final String WALL_CHAIN = "⛓\uFE0F";
    public static final String PLAYER = "🧙‍♂️";
    public static final String MONSTER = "👻";
    public static final String KEY = "🗝️";
    public static final String DOOR = "🚪";
    public static final String MAGIC = "✨";
    public static final String HEALTH = "❤";
    public static final String EXIT = "🪜";
    public static final String LOCK = "🔒";
    public static final String EMPTY = "  "; // "⬜";
}
