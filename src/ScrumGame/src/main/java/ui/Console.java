package ui;

import org.jline.reader.*;
import org.jline.terminal.*;

import java.io.IOException;

public class Console {
    private Terminal terminal;

    private LineReader lineReader;

    public Terminal getTerminal() {
        return terminal;
    }

    public LineReader getLineReader() {
        return lineReader;
    }

    public Console()
    {
        try
        {
            terminal = TerminalBuilder.builder()
                    .system(true)
                    .build();

            // Zet de terminal in een non-blocking modus (geen Enter nodig)
            terminal.enterRawMode();

            lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .build();
        } catch (IOException e) {
            System.out.println("Error, unable to create JLine terminal handlers: " + e.getMessage());
            e.printStackTrace(System.out); //Print stackstrace
        }
    }

    public static void clearConsole() {
        try {
            // For Windows
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            // For Unix/Linux/Mac
            else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Fallback: print multiple newlines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}