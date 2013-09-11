//GAUT Adventure Alpha 0.4
/**Aurthors:
**Almroth Ulrik
**Backlund Linus
**Lundgren Alexander
**/
/*Changelog:
* +Menu
* +Faster Rendering
* +Different hero skin
* +bioms
*/


package game1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JFrame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main extends JFrame implements Runnable {
    
    public int width = 1280, height = 720;
    
    public boolean j = false;
    
    Random r = new Random();
    
    Font font = new Font("Arial", Font.BOLD, 20);
    
    public int tilesize = 32;
    public int worldsize = 32;
    public int biomsize = 32;
    
    public int x = width/2-(tilesize*biomsize*worldsize/2 + tilesize*biomsize/2), y = height/2-(tilesize*biomsize*worldsize/2 + tilesize*biomsize/2);
    public int xdir = 0, ydir = 0;
    
    public int lennyt = 0;
    public int lennyx = r.nextInt(width);
    public int lennyy = r.nextInt(height);
    public int lennyc = r.nextInt((int)Math.pow(255.0, 3.0));
    public int dogerandom;
    public String dogestr = "OMG";
    
    public int watert = 0;
    public int wateri = 0;
    public boolean waterb = false;
    
    public boolean start = false;
    public int startt = 0;
    public int startfade = 0x00;
    
    public boolean menu = true;
    public int menux = height/2;
    public int menuxdir = 0;
    public Color[] menuc = {Color.RED, Color.RED, Color.RED};
    public boolean options = false;
    public Color character = Color.RED;
    public String[] menus = {"Back", "Options", "Quit", "Red", "Blue", "Back"};
    public int menusi = 0;
    
    public Color[][][][] color = new Color[biomsize][biomsize][worldsize][worldsize];
    
    public int biom[][] = new int[worldsize][worldsize];
    public int[][][][] tilefile = new int[biomsize][biomsize][worldsize][worldsize];
    
    private boolean btile = true;
    
    private BufferedImage hero;
    private BufferedImage dogeR;
    private BufferedImage dogeL;
    private BufferedImage doge;
    private BufferedImage grass; 
    private BufferedImage[][][][] tile = new BufferedImage[biomsize][biomsize][worldsize][worldsize];
    private BufferedImage stone;
    private BufferedImage sand;
    private BufferedImage water[] = new BufferedImage[4];
    
    private Image dbImage;
    private Graphics dbg;
    
    @Override
    public void run() {
        try {
            System.out.println("Run");
            while(true) {
		if (start)start();
		else move();
                Thread.sleep(1);
            }
        } catch(Exception e) {System.out.println(e);}
    }
    
    public void start() {
	startt++;
	if (startt>=25) {
	    startt = 0;
	    startfade++;
	    if (startfade>=0xFF) start = false;
	}
    }
    
    public void move() {

        watert++;
        if (watert >= 105) {wateri++; watert = 0;}
        if (wateri >= 4) wateri = 0;
        
	menux+=menuxdir;

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
            //font = new Font("Arial", Font.BOLD, r.nextInt(300)+1);
        }
    }
    
    public void menuxdir(int menuxdir) {
	this.menuxdir = menuxdir;
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
          hero = ImageIO.read(new File("C:\\Users\\Ulrik\\Desktop\\ADVENTURE\\hero.png"));
          doge = ImageIO.read(new File("C:\\Users\\Ulrik\\Desktop\\ADVENTURE\\Doge.png"));
          dogeR = ImageIO.read(new File("C:\\Users\\Ulrik\\Desktop\\ADVENTURE\\Doge.png"));
          dogeL = ImageIO.read(new File("C:\\Users\\Ulrik\\Desktop\\ADVENTURE\\Doge2.png"));
          grass = ImageIO.read(new File("C:\\Users\\Ulrik\\Desktop\\ADVENTURE\\grass.png"));
          stone = ImageIO.read(new File("C:\\Users\\Ulrik\\Desktop\\ADVENTURE\\stone.png"));
          sand = ImageIO.read(new File("C:\\Users\\Ulrik\\Desktop\\ADVENTURE\\sand.png"));
          water[0] = ImageIO.read(new File("C:\\Users\\Ulrik\\Desktop\\ADVENTURE\\water0.png"));
          water[1] = ImageIO.read(new File("C:\\Users\\Ulrik\\Desktop\\ADVENTURE\\water1.png"));
          water[2] = ImageIO.read(new File("C:\\Users\\Ulrik\\Desktop\\ADVENTURE\\water2.png"));
          water[3] = ImageIO.read(new File("C:\\Users\\Ulrik\\Desktop\\ADVENTURE\\water3.png"));
       } catch (IOException e) {
            System.out.println("couldn't load Image: " + e);
       }
        
        addKeyListener(new Key());
        setTitle("Adventure 0.0.3 GAUT");
        setSize(width, height);
        setResizable(false);
        setBackground(Color.BLACK);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    @Override
    public void paint(Graphics g) { 
       dbImage = createImage(getWidth(), getHeight());
        dbg = dbImage.getGraphics();
	if (start)paintStart(dbg);
	else {
	    if (menu) paintMenu(dbg);
	    else {
		paintWorld(dbg);
		paintComponent(dbg);
	    }
	}
        g.drawImage(dbImage, 0, 0, this);
    }
    
    public void paintStart(Graphics g) {

        g.setColor(Color.decode("0x"+Integer.toHexString(startfade)+"0000"));
        g.setFont(font);
        g.drawString("GAUT", width/2, height/2);

	repaint();
    } 
    
    public void paintMenu(Graphics g) {

	g.setColor(Color.GREEN);
	g.setFont(font);
	Rectangle r1 = new Rectangle(tilesize, menux, tilesize, tilesize);
	Rectangle r2 = new Rectangle(tilesize, height/2-2*tilesize, tilesize, tilesize);
	Rectangle r3 = new Rectangle(tilesize, height/2, tilesize, tilesize);
	Rectangle r4 = new Rectangle(tilesize, height/2+2*tilesize, tilesize, tilesize);
	g.fillRect(r1.x, r1.y, r1.width, r1.height);
	g.setColor(menuc[0]);
	g.drawString(menus[0+menusi*3], 2*tilesize, height/2-2*tilesize+tilesize);
	g.setColor(menuc[1]);
	g.drawString(menus[1+menusi*3], 2*tilesize, height/2+tilesize);
	g.setColor(menuc[2]);
	g.drawString(menus[2+menusi*3], 2*tilesize, height/2+2*tilesize+tilesize);
	if (r1.intersects(r2)) {
	    menuc[0] = Color.GREEN;
	    if (j && options) {character = Color.RED; options = false; menu = false; menusi = 0; j = false; if (btile) {g.setColor(Color.BLUE); g.drawString("Loading Terrains...", width/2, height/2); g.setColor(Color.RED);}}
	    if (j && !options) {menu = false; j = false;}
	} else menuc[0] = Color.RED;
	if (r1.intersects(r3)) {
	    menuc[1] = Color.GREEN;
	    if (j && options) {character = Color.BLUE; options = false; menu = false; menusi = 0; j = false; if (btile) {g.setColor(Color.BLUE); g.drawString("Loading Terrains...", width/2, height/2); g.setColor(Color.RED);}}
	    if (j && !options) {options=true; menusi = 1; j = false;}
	} else menuc[1] = Color.RED;
	if (r1.intersects(r4)) {
	    menuc[2] = Color.GREEN;
	    if (j && options) {options = false; menusi = 0; j = false;}
	    if (j && !options) {System.exit(0);}
	} else menuc[2] = Color.RED;

	repaint();
    }
    
public void paintWorld(Graphics g) {
        
        for (int iy = 0; iy < worldsize; iy++) {
            for (int ix = 0; ix < worldsize; ix++) {
                biom[ix][iy] = r.nextInt(1000);
                for (int jy = 0; jy < biomsize; jy++) {
                    for (int jx = 0; jx < biomsize; jx++) {
                        if (btile) {
                            
                            tilefile[jx][jy][ix][iy] = r.nextInt(1000);
                            if (biom[ix][iy] < 750) {
                                if (tilefile[jx][jy][ix][iy] < 990) tile[jx][jy][ix][iy] = grass;
                                else if (tilefile[jx][jy][ix][iy] < 997) tile[jx][jy][ix][iy] = stone;
                                else if (tilefile[jx][jy][ix][iy] < 1000) tile[jx][jy][ix][iy] = sand;
                            }
                            else if (biom[ix][iy] < 850) {
                                if (tilefile[jx][jy][ix][iy] < 20) tile[jx][jy][ix][iy] = grass;
                                else if (tilefile[jx][jy][ix][iy] < 950) tile[jx][jy][ix][iy] = stone;
                                else if (tilefile[jx][jy][ix][iy] < 1000) tile[jx][jy][ix][iy] = sand;
                            }
                            else if (biom[ix][iy] < 1000) {
                                if (tilefile[jx][jy][ix][iy] < 5) tile[jx][jy][ix][iy] = grass;
                                else if (tilefile[jx][jy][ix][iy] < 25) tile[jx][jy][ix][iy] = stone;
                                else if (tilefile[jx][jy][ix][iy] < 900) tile[jx][jy][ix][iy] = sand;
                                else if (tilefile[jx][jy][ix][iy] < 1000) {tile[jx][jy][ix][iy] = water[0]; waterb = true;}
                            }
                        }
                        if (x+jx*tilesize+ix*biomsize*tilesize > -tilesize && x+jx*tilesize+ix*biomsize*tilesize < width && y+jy*tilesize+iy*biomsize*tilesize > -tilesize && y+jy*tilesize+iy*biomsize*tilesize < height) {
                            if (tile[jx][jy][ix][iy] == water[0]) {g.drawImage(water[wateri], x+jx*tilesize+ix*biomsize*tilesize, y+jy*tilesize+iy*biomsize*tilesize, rootPane); waterb = false; System.out.println(wateri);}
                            else g.drawImage(tile[jx][jy][ix][iy], x+jx*tilesize+ix*biomsize*tilesize, y+jy*tilesize+iy*biomsize*tilesize, rootPane);
                        }
                    }
                }
            }
        }
        btile = false;
        repaint();
    }
    
    public void paintComponent(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(font);
        
        g.drawString("X: "+-(x/tilesize/biomsize)+" Y:" +-(y/tilesize/biomsize), 10, 200);
	g.setColor(character);
        g.drawImage(doge, width/2, height/2, null);
        
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
            if (key == e.VK_W) if (menu) menuxdir(-1); else Ydir(-1);
            if (key == e.VK_A) if (!menu)Xdir(-1);
            if (key == e.VK_S) if (menu) menuxdir(1); else Ydir(1);
            if (key == e.VK_D) if (!menu)Xdir(1);
	    if (key == e.VK_ESCAPE) {menu=!menu; options = false; menusi = 0;}
	    if (key == e.VK_J) j = true;
        }
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == e.VK_W) if (menu) menuxdir(0); else Ydir(0);
            if (key == e.VK_A) Xdir(0);
            if (key == e.VK_S) if (menu) menuxdir(0); else Ydir(0);
            if (key == e.VK_D) Xdir(0);
	    if (key == e.VK_J) j = false;
        }
    }
}
