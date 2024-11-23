package maingame;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class music {
Clip clip;
URL soundURL[] = new URL[30];

public music() {
	soundURL[0] = getClass().getResource("/Sounds/benzenbeat (2).wav");
	soundURL[1] = getClass().getResource("/Sounds/Cartoon Bite sound effect.wav");
	soundURL[2] = getClass().getResource("/Sounds/yipee.wav");
	soundURL[3] = getClass().getResource("/Sounds/itemobtained.wav");
	soundURL[4] = getClass().getResource("/Sounds/do not enter.wav");

}
public void setFile(int i) {
	
try {
	AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
	clip = AudioSystem.getClip();
	clip.open(ais);
	
	
}catch(Exception e) {
	
}
}


public void play() {
	clip.start();
}
public void loop() {
	clip.loop(clip.LOOP_CONTINUOUSLY);
}
public void stop() {
	clip.stop();
}
}
