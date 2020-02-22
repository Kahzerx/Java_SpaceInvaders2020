
package codigo;

import java.awt.Image;

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
    }
    
    public void move(){
        if (leftPressed && posX > 0){
            posX -= 3;
        }
        if (rightPressed && posX < VentanaJuego.SCREEN_WIDTH - image.getWidth(null)){
            posX += 3;
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