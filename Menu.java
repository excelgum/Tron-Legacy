//Menu.Java
/*The Menu Class is a Jframe window of the menu and displays the instruction and allows the user to launch either
 *single player or two player game mode
 *
 **/
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*; 
public class Menu extends JFrame implements MouseListener,KeyListener,MouseMotionListener{
	public String game="none";//game keeps track of which game the user choose either Single or Two
	private final int HEIGHT=625;
	private final int WIDTH=1000;
	private Image dbImage;
	private Graphics dbg;
	//below are images loaded to make the menu look attractive
	private Image bgImage=(new ImageIcon("bg.png")).getImage();
    private Image spImage=(new ImageIcon("sp.png")).getImage();
    private Image tpImage=(new ImageIcon("tp.png")).getImage();
    private Image hImage=(new ImageIcon("h.png")).getImage();
    private Image inImage=(new ImageIcon("instruct.png")).getImage();
    private Image iImage=(new ImageIcon("instructions.png")).getImage();
	private int x,y;//Position of the mouse
	private boolean click=false;//turns true when the mouse is clicked
	//Sound is a class that plays music
	private Sound music= new Sound("song.wav");
    public Menu() {
    	super ("Tron Legacy");
    	music.loop();//command used to start the soundtrack
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setSize (WIDTH,HEIGHT);
		setVisible (true);
		addMouseListener(this);
		addMouseMotionListener(this);
    }
    public void paint(Graphics g){
    	//Double Buffer
		if (dbImage==null){
    		dbImage=createImage(WIDTH,HEIGHT);
    		dbg=dbImage.getGraphics();
    	}
    	dbg.drawImage(bgImage,0,0,this);
    	
    	//the if conditions below draws highlighted images if the mouse is ontop of the given text
    	//by looking at the location
    	//if the user clicks the text the string game is changed to indicate which game mode to run
    	if (y>0 && y<90){
    		dbg.drawImage(hImage,0,30,this);
    		if (x<300 && click){
    			game="Single";
    		}
    	}
    	else if (y>90 && y <145){
    		dbg.drawImage(hImage,0,90,this);
    		if (x<300 && click){
    			game="Two";
    		}
    	}
    	else if (y>545 && y <600 && x<300){
    		dbg.drawImage(iImage,0,0,this);
    		dbg.drawImage(hImage,0,550,this);
    	}
    	if (click){
    		click=false;
    	}
    	//draws the images in the appropriate places
    	dbg.drawImage(spImage,0,10,this);
    	dbg.drawImage(tpImage,-10,70,this);
    	
    	dbg.drawImage(inImage,13,542,this);
    	g.drawImage(dbImage,0,0,this);
    }
	//the main class is built into the menu
    public static void main (String [] arguments){
		Menu menu = new Menu ();//creates the menu
		
		//the while loop keeps the menu visible untill the user has choosen a game mode
		while(menu.game.equals("none")){
    		menu.repaint();
    		delay(20);
    	}
    	menu.setVisible(false);
    	//then it creates an object of the corresponding game mode class to begin a match in that game mode
    	if (menu.game.equals("Single")){
    		SinglePlayer frame=new SinglePlayer();
    		while(frame.running){
    			frame.run();
    			delay(20);
    		}
    		frame.repaint();
    	}
    	else{
    		TwoPlayer frame=new TwoPlayer();
    		while(frame.running){
    			frame.run();
    			delay(20);
    		}
    		frame.repaint();
    	}
    }
    public static void delay(long len){
    	try{
    		Thread.sleep(len);
    	}
    	catch(InterruptedException ex){
    		System.out.println(ex);
    	}
    }
    public void mousePressed(MouseEvent e){}
	public void mouseMoved(MouseEvent e){this.x=e.getX();this.y=e.getY();}//updates the x and y coordinate of the mouse when it moves
	public void mouseDragged(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e){//when the mouse is clicked it sets click to be true
		click=true;
	}
	public void mouseReleased(MouseEvent e){}
	public void keyTyped(KeyEvent e){}
	public void keyPressed(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
}