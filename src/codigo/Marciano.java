/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author kahzerx
 */
public class Marciano {
    
    public Image image1 = null;
    public Image image2 = null;
    
    private int screenWidth;
    
    public int life = 50;
    
    public Marciano (int _screenWidth){
        screenWidth = _screenWidth;
        try {
            image1 = ImageIO.read(getClass().getResource("/imagenes/marcianito1.png"));
            image2 = ImageIO.read(getClass().getResource("/imagenes/marcianito2.png"));
        } 
        
        catch (Exception e) {
            System.out.println("Unable to read Alien image.");
        }
    }
    
}
