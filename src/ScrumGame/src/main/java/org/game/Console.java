package org.game;

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
}