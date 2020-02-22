/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author kahzerx
 */
public class Shot {
    Image image=null;

    public int posX=0;
    public int posY=0;
    Clip shotSound;
    

    public void move(){        
        posY -= 5;
    }
    
    public void posShot(Ship _ship){

        posX=_ship.posX+_ship.image.getWidth(null)/2-image.getWidth(null)/2;
        posY=_ship.posY-image.getHeight(null)/2;
    }
    public Shot(){
        try{
            image=ImageIO.read(getClass().getResource("/imagenes/disparo.png"));
            shotSound = AudioSystem.getClip();
            shotSound.open(AudioSystem.getAudioInputStream(getClass().getResource("/sound/laser.wav")));
        }
        catch(IOException e){
            System.out.println(e + "Unable to read Shot image");
        }
        catch(LineUnavailableException | UnsupportedAudioFileException e){
            System.out.println(e + "Unable to initialize shot audio");
        }
    }
}
