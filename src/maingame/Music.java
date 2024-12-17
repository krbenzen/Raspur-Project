/**
 * Lead Author(s):
 *   @author Benzen Raspur
 *
 * Other contributors:
 *   None
 *
 * References:
 *   - Morelli, R., & Walde, R. (2016). Java, Java, Java: Object-Oriented Problem Solving.
 *     https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *   - Bechtold, S., Brannen, S., Link, J., Merdes, M., Philipp, M., Rancourt, J. D., & Stein, C. (n.d.).
 *     JUnit 5 user guide. JUnit 5.
 *     https://junit.org/junit5/docs/current/user-guide/
 *
 * Version/Date: 12/15/2024
 *
 * Description:
 * This class is responsible for handling background music and sound effects within the game. 
 * It loads audio files from provided URLs and uses a Clip to play, loop, or stop music or sound.
 *
 * ISA: Music IS-A standalone class (no inheritance).
 * HAS-A: Music has-a Clip object to play sounds
 * HAS-A: Music has-a URL array to store sound file locations
 *
 * Learning Outcomes (LOs):
 * LO1. Employ design principles of OOP:
 *    - Encapsulation: All instance variables are private.
 *    - Naming conventions and consistent indentation.
 *    - Class, variable, and method names.
 * LO2. Single and multidimensional arrays:
 *    - Demonstrated by using a single-dimensional array of URLs (sound files).
 * LO3. Construct programs utilizing objects and classes in OOP:
 *    - Music is its own class and creates and uses Clip objects.
 * LO7. Construct programs utilizing exception handling:
 *    - Exception handling is demonstrated by catching exceptions when loading sounds and informing the user.
 */

package maingame;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {
    
    // Private instance variables 
    private Clip audioClip;
    private URL[] soundUrls = new URL[30];
    
    public Music() {
        soundUrls[0] = getClass().getResource("/Sounds/benzenbeat (2).wav");
        soundUrls[1] = getClass().getResource("/Sounds/Cartoon Bite sound effect.wav");
        soundUrls[2] = getClass().getResource("/Sounds/yipee.wav");
        soundUrls[3] = getClass().getResource("/Sounds/itemobtained.wav");
        soundUrls[4] = getClass().getResource("/Sounds/do not enter.wav");
    }
    
    public void setFile(int index) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrls[index]);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioInputStream);
        } catch (Exception e) {
            System.err.println("Error loading audio file at index " + index + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void play() {
        if (audioClip != null) {
            audioClip.start();
        } else {
            System.err.println("Cannot play sound: audio clip is null.");
        }
    }
    
    public void loop() {
        if (audioClip != null) {
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.err.println("Cannot loop sound: audio clip is null.");
        }
    }
    
    public void stop() {
        if (audioClip != null) {
            audioClip.stop();
        } else {
            System.err.println("Cannot stop sound: audio clip is null.");
        }
    }
}
