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
public class Shot {
    Image image=null;

    public int posX=0;
    public int posY=0;
    
    public Shot(){
        try{
            image=ImageIO.read(getClass().getResource("/imagenes/disparo.png"));
        }
        catch(Exception e){
            System.out.println("Unable to read Shot image");
        }
    }
    public void move(){        
        posY -= 5;
    }
    
    public void posShot(Ship _ship){

        posX=_ship.posX+_ship.image.getWidth(null)/2-image.getWidth(null)/2;
        posY=_ship.posY-image.getHeight(null)/2;
    }
}
