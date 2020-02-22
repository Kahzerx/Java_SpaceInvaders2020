/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D; 
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;


/**
 *
 * @author Juan
 */
public class VentanaJuego extends javax.swing.JFrame {

    static int SCREEN_WIDTH = 800;
    static int SCREEN_HEIGHT = 600;
    
    int alienRow = 6;
    int alienColumn = 10;
    int counter = 0;
    int score = 0;
    
    boolean alrShot = false;
    boolean gameOver = false;
    
    BufferedImage buffer = null;
    BufferedImage template = null;
    Image[] images = new Image[30];
    
    Timer temp = new Timer(10, (ActionEvent e) -> {
        //TODO : animation code
        gameLoop();
    });
    
    Alien myAlien = new Alien(SCREEN_WIDTH);
    Ship myShip = new Ship();
    Shot myShot = new Shot();
    ArrayList<Shot> shotList = new ArrayList();
    ArrayList <Explosion> explosionList = new ArrayList();
    
    Alien[][] alienList = new Alien[alienRow][alienColumn];
    boolean alienDirection = true;
    
    /**
     * Creates new form VentanaJuego
     */
    public VentanaJuego() {
        initComponents();
        try {
            template = ImageIO.read(getClass().getResource("/imagenes/invaders2.png"));
        } catch (IOException ex) {
            System.out.println("Unable to read invaders2 image");
        }
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 4; j++){
                images[i*4 + j] = template.getSubimage(j * 64, i * 64, 64, 64).getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            }
        }
        
        images[20] = template.getSubimage(0, 320, 66, 32);
        images[21] = template.getSubimage(66, 320, 64, 32);
        images[23] = template.getSubimage(255, 320, 32, 32);
        images[22] = template.getSubimage(255, 289, 32, 32);
        
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        buffer = (BufferedImage) jPanel1.createImage(SCREEN_WIDTH, SCREEN_HEIGHT);
        buffer.createGraphics();
        
        temp.start();
        
        myShip.image = images[20];
        
        myShip.posX = SCREEN_WIDTH /2 - myShip.image.getWidth(this)/2;
        myShip.posY = SCREEN_HEIGHT - 100;
        
        for (int i = 0; i < alienRow; i++) {
            for (int j = 0; j < alienColumn; j++) {
                alienList[i][j] = new Alien(SCREEN_WIDTH);
                alienList[i][j].image1 = images[2*i];
                alienList[i][j].image2 = images[2*i+1];
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
                if (alienList[i][j].posX == SCREEN_WIDTH - alienList[i][j].image1.getWidth(null) - alienList[i][j].image1.getWidth(null) / 3 || alienList [i][j].posX == 0){
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
    
    private void drawShots (Graphics2D g2){
        Shot auxShot;
        for (int i = 0; i < shotList.size(); i++){
            auxShot = shotList.get(i);
            auxShot.move();
            
            if (auxShot.posY < 0){
                shotList.remove(i);
            }
            else{
                g2.drawImage(auxShot.image, auxShot.posX, auxShot.posY, null);
            }
        }
    }
    
    private void drawExplosions (Graphics2D g2){
        Explosion auxExplosion;
        for (int i=0; i< explosionList.size(); i++){
            auxExplosion = explosionList.get(i);
            auxExplosion.lifeTime --;
            if (auxExplosion.lifeTime > 12){
                g2.drawImage(auxExplosion.image1, auxExplosion.posX, auxExplosion.posY, null);
            }
            else{
                g2.drawImage(auxExplosion.image2, auxExplosion.posX, auxExplosion.posY, null);
            }
            if (auxExplosion.lifeTime <= 0){
                explosionList.remove(i);
            }
        }
    }
    
    private void gameLoop(){
        Graphics2D g2 = (Graphics2D) buffer.getGraphics();
        if (!gameOver){
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            counter ++;

            drawAlien(g2);

            g2.drawImage(myShip.image, myShip.posX, myShip.posY, null);
            drawShots(g2);
            drawExplosions(g2);
            myShip.move();

            collision();

            g2 = (Graphics2D) jPanel1.getGraphics();
            g2.drawImage(buffer, 0, 0, null);
        }
        else{
            end(g2);
        }
    }
    
    private void collision(){
        //marco para el borde del marciano
        Rectangle2D.Double alienRect = new Rectangle2D.Double();
        //marco para el borde del disparo
        Rectangle2D.Double shotRect = new Rectangle2D.Double();
        //marco para el borde de la nave
        
        for (int k = 0; k < shotList.size();k++){
            shotRect.setFrame(shotList.get(k).posX, shotList.get(k).posY, shotList.get(k).image.getWidth(null), shotList.get(k).image.getHeight(null));
            for(int i=0; i<alienRow; i++){
                for (int j=0; j<alienColumn; j++){
                    alienRect.setFrame(alienList[i][j].posX, alienList[i][j].posY, alienList[i][j].image1.getWidth(null), alienList[i][j].image1.getHeight(null));
                    if (shotRect.intersects(alienRect)){
                        Explosion e = new Explosion();
                        e.posX = alienList[i][j].posX;
                        e.posY = alienList[i][j].posY;
                        e.image1 = images[23];
                        e.image2 = images[22];
                        explosionList.add(e);
                        e.explosionSound.start();
                        alienList[i][j].posY = 2000;
                        shotList.remove(k);
                        score ++;
                        if (score >= alienColumn * alienRow) gameOver = true;
                    }

                }
            }
        }
    }
    
    private void end (Graphics2D gameOver){
        try {
            gameOver.setColor(Color.BLACK);
            gameOver.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            Image gameOverImage = ImageIO.read((getClass().getResource("/imagenes/gameOver.png")));
            gameOver.drawImage(gameOverImage, SCREEN_WIDTH/2 - gameOverImage.getWidth(this)/2 , SCREEN_HEIGHT/2 - gameOverImage.getHeight(this), null);
            gameOver = (Graphics2D) jPanel1.getGraphics();
            gameOver.drawImage(buffer, 0, 0, null);
        } catch (IOException e) {
            System.err.println(e + "Unable to load gameOver image");
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
            case KeyEvent.VK_SPACE : 
                if(!alrShot){
                    Shot s = new Shot();
                    s.shotSound.start();
                    s.posShot(myShip);
                    shotList.add(s);
                    alrShot = true;
                }
                break;
        }
    }//GEN-LAST:event_formKeyPressed
    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        switch(evt.getKeyCode()){
            case KeyEvent.VK_LEFT : myShip.setLeftPressed(false); break;
            case KeyEvent.VK_RIGHT : myShip.setRightPressed(false); break;
            case KeyEvent.VK_SPACE : alrShot = false; break;

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
        java.awt.EventQueue.invokeLater(() -> {
            new VentanaJuego().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
