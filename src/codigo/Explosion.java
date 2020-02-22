/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.Image;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author kahzerx
 */
public class Explosion {
    Image image1 = null;
    Image image2 = null;

    public int posX = 0;
    public int posY = 0;

    public int lifeTime = 25;
    
    Clip explosionSound;
    
    public Explosion(){
        try{
            explosionSound = AudioSystem.getClip();
            explosionSound.open(AudioSystem.getAudioInputStream(getClass().getResource("/sound/explosion.wav")));
        }
        catch(IOException | LineUnavailableException | UnsupportedAudioFileException e){
            System.err.println(e + "Unable to initialize sound");
        }
    }
}
