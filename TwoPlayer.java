//TwoPlayer.java
//Anas Ahmed
//This is a class that plays the tron game in TwoPlayer Mode
//there will be fewer comments in this class because it has pretty much the
//same code as the SinglePlayer class but no ai and all the player1 variables is duplicated
//to allow player 2 to play
//Please refer to the SinglePlayer class for clarification on the code
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class TwoPlayer extends JFrame implements KeyListener,MouseListener{
	private int x,y;
	private int vx,vy;
	private boolean boost=false;
	private int boostAmount=100;
	
	private int x2,y2;
	private int vx2,vy2;
	private boolean boost2=false;
	private int boostAmount2=100;
	
	public final int HEIGHT=600;
	public final int WIDTH=800;
	
	private int p1score=0;
    private int p2score=0;
    
    private HashSet<Point> points = new HashSet<Point>();
    private HashSet<Point> p1points = new HashSet<Point>();
    private HashSet<Point> p2points = new HashSet<Point>();
    
    private Font font=new Font("Arial",Font.PLAIN,15);
    
    private int time=100;
    private int counter=0;
    public boolean running=true;
    
	private Image dbImage;
	private Graphics dbg;
	public static boolean[]Keys;

	public void mousePressed(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void keyTyped(KeyEvent e){}
	public void keyPressed(KeyEvent e){
		Keys[e.getKeyCode()]=true;
	}
	public void keyReleased(KeyEvent e){
		Keys[e.getKeyCode()]=false;
	}
    public TwoPlayer() {
    	super("TwoPlayer Mode");
    	addMouseListener(this);
    	addKeyListener(this);
    	setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    	reset();
    	setSize(WIDTH,HEIGHT);
    	Keys=new boolean[2000];
    	setVisible(true);	
    }
    private void move(){
    	if (vx==0){
    		if (Keys[39]){//Right
    			vy=0;
    			vx=5;
    		}
    		else if (Keys[37]){//Left
    			vy=0;
    			vx=-5;
    		
    		}
    	}
    	if (vy==0){
    		if (Keys[38]){//UP
    			vy=-5;
    			vx=0;
    		}
    		else if (Keys[40]){//Down
    			vy=5;
    			vx=0;
    		}
    	}
    	
    	boostAmount+=2;
    	if (boostAmount>100){
    		boostAmount=100;
    	}
    	
    	
    	if (boost==false){
    		if (Keys[16] && boostAmount>=50){
    			boostAmount-=10;
    			boost=true;
    		}
    	}
    	else{
    		if (!Keys[16] || boostAmount<=10){
    			boost=false;
    		}
    		boostAmount-=10;
    	}		
    }
    private void move2(){
    	if (vx2==0){
    		if (Keys[68]){//Right
    			vy2=0;
    			vx2=5;
    		}
    		else if (Keys[65]){//Left
    			vy2=0;
    			vx2=-5;
    		
    		}
    	}
    	if (vy2==0){
    		if (Keys[87]){//UP
    			vy2=-5;
    			vx2=0;
    		}
    		else if (Keys[83]){//Down
    			vy2=5;
    			vx2=0;
    		}
    	}
    	
    	boostAmount2+=2;
    	if (boostAmount2>100){
    		boostAmount2=100;
    	}
    	
    	
    	if (boost2==false){
    		if (Keys[32] && boostAmount2>=50){
    			boostAmount2-=10;
    			boost2=true;
    		}
    	}
    	else{
    		if (!Keys[32] || boostAmount2<=10){
    			boost2=false;
    		}
    		boostAmount2-=10;
    	}		
    }
    
    public void checkWall(){
    	if (x>WIDTH-10){
    		x=10;
    	}
    	if (x<10){
    		x=WIDTH-10;
    	}
    	if (y<100){
    		y=HEIGHT-10;
    	}
    	if (y>HEIGHT-10){
    		y=100;
    	}
    	
    	
    	if (x2>WIDTH-10){
    		x2=10;
    	}
    	if (x2<10){
    		x2=WIDTH-10;
    	}
    	if (y2<100){
    		y2=HEIGHT-10;
    	}
    	if (y2>HEIGHT-10){
    		y2=100;
    	}
    }
    public void reset(){
    	this.points = new HashSet<Point>();
    	this.p1points = new HashSet<Point>();
    	this.p2points = new HashSet<Point>();
    	x=100;
	    y=350;
	    vx=5;
	    vy=0;
	    
	    x2=700;
	    y2=350;
	    vx2=-5;
	    vy2=0;
    }
    public void collision(){
    	if (points.contains(new Point(x,y))){reset();p2score+=1;}
    	points.add(new Point(x,y));
    	p1points.add(new Point(x,y));
    	
    	if (points.contains(new Point(x2,y2))){reset();p1score+=1;}
    	points.add(new Point(x2,y2));
    	p2points.add(new Point(x2,y2));
    }
    public void CountDown(){
    	counter+=1;
    	if (counter%30==0){
    		time-=1;
    	}
    	if (time==0){
    		running=false;
    	}	
    }
    
    
    
    public void paint(Graphics g){
    	if (dbImage==null){
    		dbImage=createImage(WIDTH,HEIGHT);
    		dbg=dbImage.getGraphics();
    	}
    	
    	dbg.setColor(new Color(0,0,0));
    	dbg.fillRect(0,0,WIDTH,HEIGHT);
    	dbg.setColor(new Color(25,25,25));
    	for (int i=0;i<WIDTH;i+=10){
    		dbg.drawLine(i,0,i,HEIGHT);
    	}
    	for (int i=0;i<HEIGHT;i+=11){
    		dbg.drawLine(0,i,WIDTH,i);
    	}
    	
    	
    	dbg.setColor(new Color(0,255,255));
    	for (Point p: p1points){
    		dbg.fillRect(p.x,p.y,5,5);
    	}
    	dbg.setColor(new Color(255,0,255));
    	for (Point p: p2points){
    		dbg.fillRect(p.x,p.y,5,5);
    	}
    	
    	//Creates the HUD 
    	dbg.setColor(new Color(0,0,0));
    	dbg.fillRect(0,0,WIDTH,100);
    	dbg.setColor(new Color(255,255,255));
    	dbg.setFont(font);
    	dbg.drawString("P1 Score: "+p1score,20,60);
    	dbg.drawString("P2 Score: "+p2score,700,60);
    	//draws a meter indicating the amount of boost each player has
    	dbg.setColor(new Color(255,255,255));
    	dbg.fillRect(0,70,boostAmount*2,20);
    	
    	dbg.setColor(new Color(255,255,255));
    	dbg.fillRect(590,70,boostAmount2*2,20);
    	//draws the text of time remaining
    	dbg.drawString("Time Remaining: "+time,330,60);
    	if (!running){
    		//when the game ends it indicates who has won based on the scores of the players
    		dbg.setColor(new Color(0,0,0));
    		dbg.fillRect(0,0,WIDTH,HEIGHT);
    		dbg.setColor(new Color(255,255,255));
    		if (p1score>p2score){
    			dbg.drawString("GAME OVER",300,250);
    			dbg.drawString("Player 1 WINS",300,300);
    		}
    		else if (p2score>p1score){
    			dbg.drawString("GAME OVER",300,250);
    			dbg.drawString("Player 2 WINS",300,300);
    		}
    		else{
    			dbg.drawString("GAME OVER",300,250);
    			dbg.drawString("Tie Game",300,300);
    		}
    	}
    	g.drawImage(dbImage,0,0,this);
    }
    public void run(){//run is called every loop and calls the required method to keep the game running
    	x+=vx;
    	y+=vy;
    	if (boost==true){
    		x+=vx;
    	    y+=vy;
    	}
    	x2+=vx2;
    	y2+=vy2;
    	if (boost2==true){
    		x2+=vx2;
    	    y2+=vy2;
    	}
    	checkWall();
    	move();
    	move2();
    	collision();
    	repaint();
    	CountDown();
    }
}