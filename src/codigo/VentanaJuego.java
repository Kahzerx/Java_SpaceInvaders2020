/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D; 


/**
 *
 * @author Juan
 */
public class VentanaJuego extends javax.swing.JFrame {

    static int SCREEN_WIDTH = 800;
    static int SCREEN_HEIGHT = 600;
    
    int alienRow = 5;
    int alienColumn = 10;
    int counter = 0;
    
    BufferedImage buffer = null;
    
    Timer temp = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO : animation code
            gameLoop();
            
        }
    });
    
    Alien myAlien = new Alien(SCREEN_WIDTH);
    Ship myShip = new Ship();
    Shot myShot = new Shot();
    
    Alien[][] alienList = new Alien[alienRow][alienColumn];
    boolean alienDirection = true;
    
    /**
     * Creates new form VentanaJuego
     */
    public VentanaJuego() {
        initComponents();
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        buffer = (BufferedImage) jPanel1.createImage(SCREEN_WIDTH, SCREEN_HEIGHT);
        buffer.createGraphics();
        
        temp.start();
        
        myShip.posX = SCREEN_WIDTH /2 - myShip.image.getWidth(this)/2;
        myShip.posY = SCREEN_HEIGHT - 100;
        
        for (int i = 0; i < alienRow; i++) {
            for (int j = 0; j < alienColumn; j++) {
                alienList[i][j] = new Alien(SCREEN_WIDTH);
                alienList[i][j].posX = j * (15 + alienList[i][j].image1.getWidth(null));
                alienList[i][j].posY = i * (10 + alienList[i][j].image1.getHeight(null));
            }
        }
        myShot.posY = -2000;
    }
    
    private void drawAlien(Graphics2D g2){
        for (int i = 0; i < alienRow; i++) {
            for (int j = 0; j < alienColumn; j++) {
                alienList[i][j].move(alienDirection);
                if (counter < 50){
                    g2.drawImage(alienList[i][j].image1, alienList[i][j].posX, alienList[i][j].posY, null);
                }
                else if (counter < 100){
                    g2.drawImage(alienList[i][j].image2, alienList[i][j].posX, alienList[i][j].posY, null);
                }
                else{
                    counter = 0;
                }
                if (alienList[i][j].posX == SCREEN_WIDTH - alienList[i][j].image1.getWidth(null) - myAlien.image1.getWidth(null)/3 || alienList [i][j].posX == 0){
                    alienDirection = !alienDirection;
                    for (int k = 0; k < alienRow; k++){
                        for (int m = 0; m < alienColumn; m++){
                            alienList[k][m].posY += alienList[k][m].image1.getHeight(null);
                        }
                    }
                }
            }
        }
    }
    
    private void gameLoop(){
        Graphics2D g2 = (Graphics2D) buffer.getGraphics();
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        counter ++;
        
        drawAlien(g2);
        
        g2.drawImage(myShip.image, myShip.posX, myShip.posY, null);
        g2.drawImage(myShot.image, myShot.posX, myShot.posY, null);
        myShip.move();
        myShot.move();
        collision();
        
        g2 = (Graphics2D) jPanel1.getGraphics();
        g2.drawImage(buffer, 0, 0, null);
    }
    
    private void collision(){
        Rectangle2D.Double alienRect = new Rectangle2D.Double();
        Rectangle2D.Double shotRect = new Rectangle2D.Double();
        shotRect.setFrame(myShot.posX, myShot.posY, myShot.image.getWidth(null), myShot.image.getHeight(null));
        for(int i=0; i<alienRow; i++){
            for (int j=0; j<alienColumn; j++){
                alienRect.setFrame(alienList[i][j].posX, alienList[i][j].posY, alienList[i][j].image1.getWidth(null), alienList[i][j].image1.getHeight(null));
                if (shotRect.intersects(alienRect)){
                    alienList[i][j].posY = 2000;
                    myShot.posY = -2000;
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        switch(evt.getKeyCode()){
            case KeyEvent.VK_LEFT : myShip.setLeftPressed(true); break;
            case KeyEvent.VK_RIGHT : myShip.setRightPressed(true); break;
            case KeyEvent.VK_SPACE : myShot.posX = myShip.posX;myShot.posY = myShip.posY;break;
        }
    }//GEN-LAST:event_formKeyPressed
    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        switch(evt.getKeyCode()){
            case KeyEvent.VK_LEFT : myShip.setLeftPressed(false); break;
            case KeyEvent.VK_RIGHT : myShip.setRightPressed(false); break;

        }
    }//GEN-LAST:event_formKeyReleased


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaJuego().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
