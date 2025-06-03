package org.game.rooms;

public class Emojis {
    public static String getEmojiForCharacter(char character) {
        // Handle all possible characters from the map
        switch (character) {
            case 'P':  // Player
                return PLAYER;
            case 'M':  // Monster
                return MONSTER;
            case 'K':  // Key
                return KEY;
            case 'D':  // Door
                return DOOR;
            case 'L':  // Lock
                return LOCK;
            case '|':  // Wall
            case '+':  // Wall corner
            case '-':  // Wall horizontal
                return WALL;
            case 'E':  // Exit
                return EXIT;
            case '*':  // Magic/Power-up
                return MAGIC;
            case 'H':  // Health potion
                return HEALTH;
            case ' ':  // Empty space
                return EMPTY;
            case '\n': // Newline (shouldn't be rendered)
            case '\r': // Carriage return (shouldn't be rendered)
                return "";
            case '\0': // Null character
                return " ";
            default:
                // For any unexpected character, return a space
                return " ";
        }
    }

    
    // Emoji constants
    public static final String WALL = "🧱";
    public static final String PLAYER = "🧙‍♂️";
    public static final String MONSTER = "👻";
    public static final String KEY = "🗝️";
    public static final String DOOR = "🚪";
    public static final String MAGIC = "✨";
    public static final String HEALTH = "❤";
    public static final String EXIT = "🪜";
    public static final String LOCK = "🔒";
    public static final String EMPTY = "⬜";  // Changed to white square for better visibility
}
