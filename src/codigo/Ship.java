
package codigo;

import java.awt.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author Kahzerx
 */
public class Ship {
    Image image = null;
    public int posX = 0;
    public int posY = 0;
    
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    
    
    public Ship(){
            try{
                image=ImageIO.read(getClass().getResource("/imagenes/nave.png"));
            }
            catch(Exception e){
                System.out.println("Unable to read Ship image");
            }
    }
    
    public void move(){
        if (leftPressed){
            posX--;
        }
        if (rightPressed){
            posX++;
        }      
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
        this.rightPressed = false;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
        this.leftPressed = false;
    }
    
    
}