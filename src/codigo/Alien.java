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
public class Alien {
    
    public Image image1 = null;
    public Image image2 = null;
    
    public int posX = 0;
    public int posY = 0;
    
    private int screenWidth;
    
    public int life = 50;
    
    public Alien (int _screenWidth){
        screenWidth = _screenWidth;
    }
    
    public void move (boolean direction){
        if (direction){
            posX++;
        }
        else{
            posX--;
        }
    }
    
}
