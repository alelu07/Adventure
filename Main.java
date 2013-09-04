package game1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JFrame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Main extends JFrame implements Runnable {
    
    public int width = 1920, height = 1080;
    
    Random r = new Random();
    
    Font font = new Font("Arial", Font.BOLD, 50);
    
    public int x = width/2, y = height/2;
    public int xdir = 0, ydir = 0;
    
    public int tilesize = 32;
    
    public int lennyt = 0;
    public int lennyx = r.nextInt(width);
    public int lennyy = r.nextInt(height);
    public int lennyc = r.nextInt((int)Math.pow(255.0, 3.0));
    public int dogerandom;
    public String dogestr = "OMG";
    
    public int[][] tilefile = new int[10000][10000];
    
    private boolean btile = true;
    
    private BufferedImage dogeR;
    private BufferedImage dogeL;
    private BufferedImage doge;
    private BufferedImage grass; 
    private BufferedImage[][] tile = new BufferedImage[10000][10000];
    private BufferedImage stone;
    private BufferedImage sand;
    
    private Image dbImage;
    private Graphics dbg;
    
    @Override
    public void run() {
        try {
            System.out.println("Run");
            while(true) {
                move();
                Thread.sleep(1);
            }
        } catch(Exception e) {System.out.println(e);}
    }
    
    public void move() {
        x-=xdir;
        y-=ydir;
        if (xdir > 0) doge = dogeR;
        if (xdir < 0) doge = dogeL;
        
        lennyt++;
        if (lennyt >= 500) {
            lennyt = 0;
            lennyx = r.nextInt(width);
            lennyy = r.nextInt(height);
            lennyc = r.nextInt((int)Math.pow(255.0, 3.0));
            dogestr = doges();
            font = new Font("Arial", Font.BOLD, r.nextInt(300)+1);
        }
    }
    
    public void Xdir(int Xdir) {
        xdir = Xdir;
    }
    public void Ydir(int Ydir) {
        ydir = Ydir;
    }
    
    public String doges() {
        dogerandom = r.nextInt(5);
        if (dogerandom == 0) return "OMG";
        else if (dogerandom == 1) return "Such Intence";
        else if (dogerandom == 2) return "Doge";
        else if (dogerandom == 3) return "Le Epic Maymay XD";
        else if (dogerandom == 4) return "WOW";
        else return "";
    }
    
    public Main() {
        
        try {   
          doge = ImageIO.read(new File("C:\\Users\\Ulrik\\Desktop\\ADVENTURE\\Doge.png"));
          dogeR = ImageIO.read(new File("C:\\Users\\Ulrik\\Desktop\\ADVENTURE\\Doge.png"));
          dogeL = ImageIO.read(new File("C:\\Users\\Ulrik\\Desktop\\ADVENTURE\\Doge2.png"));
          grass = ImageIO.read(new File("C:\\Users\\Ulrik\\Desktop\\ADVENTURE\\grass.png"));
          stone = ImageIO.read(new File("C:\\Users\\Ulrik\\Desktop\\ADVENTURE\\stone.png"));
          sand = ImageIO.read(new File("C:\\Users\\Ulrik\\Desktop\\ADVENTURE\\sand.png"));
       } catch (IOException e) {
            System.out.println("couldn't load Image: " + e);
       }
        
        addKeyListener(new Key());
        setTitle("Game 0.0.2 GAUT");
        setSize(width, height);
        setResizable(false);
        setBackground(Color.BLACK);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void paint(Graphics g) { 
       dbImage = createImage(getWidth(), getHeight());
        dbg = dbImage.getGraphics();
        paintWorld(dbg);
        paintComponent(dbg);
        g.drawImage(dbImage, 0, 0, this);
    }
    
    public void paintWorld(Graphics g) {
        
        for (int iy = 0; iy < 1000; iy++) {
            for (int ix = 0; ix < 1000; ix++) {
                if (btile) {
                    tilefile[ix][iy] = r.nextInt(3);
                    if (tilefile[ix][iy] == 0) tile[ix][iy] = grass;
                    if (tilefile[ix][iy] == 1) tile[ix][iy] = stone;
                    if (tilefile[ix][iy] == 2) tile[ix][iy] = sand;
                }
                if (x+ix*tilesize > -tilesize && x+ix*tilesize < width && y+iy*tilesize > -tilesize && y+iy*tilesize < height) {
                    g.drawImage(tile[ix][iy], ix*tilesize+x, iy*tilesize+y, rootPane);
                }
            }
        }
        btile = false;
        repaint();
    }
    
    public void paintComponent(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(font);
        
        //g.fillRect(width/2, height/2, 30, 30);
        g.drawImage(doge, width/2, height/2, null);
        g.setColor(Color.decode("0x"+Integer.toHexString(lennyc)));
        g.drawString(dogestr, lennyx, lennyy);
        
        repaint();
    }
    
    public static void main(String[] args) {
        Main m = new Main();
        Thread t1 = new Thread(m);
        t1.start();
    }
    
    public class Key extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == e.VK_W) Ydir(-1);
            if (key == e.VK_A) Xdir(-1);
            if (key == e.VK_S) Ydir(1);
            if (key == e.VK_D) Xdir(1);
        }
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == e.VK_W) Ydir(0);
            if (key == e.VK_A) Xdir(0);
            if (key == e.VK_S) Ydir(0);
            if (key == e.VK_D) Xdir(0);
        }
    }
}