// in package org.game
package org.game;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class GeluidSpeler {
    private Clip clip;

    public void speel(String bestandsNaam, boolean herhalen) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }

        try {
            URL geluidURL = getClass().getResource("/music/" + bestandsNaam);
            if (geluidURL == null) {
                System.err.println("Geluidsbestand niet gevonden: " + bestandsNaam);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(geluidURL);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            if (herhalen) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}
