//SinglePlayer.java
//Anas Ahmed
//This is a class that plays the Tron game in SinglePlayer Mode
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class SinglePlayer extends JFrame implements KeyListener,MouseListener{
	private int x,y;//position of player 1
	private int vx,vy;//velocity of player 1
	private boolean boost=false;//indicates whether the boost is on for player 1
	private int boostAmount=100;//Amount of boost the user has
	
	private final  int HEIGHT=600;
	private final int WIDTH=800;
	private int ax,ay;//Same for the computer player
	private int avx,avy;
	
	private Image dbImage;//Double Buffer graphic stuff
	private Graphics dbg;
	private static boolean[]Keys;
	
	//private Image back = new ImageIcon("Grid.jpg").getImage();
    private int p1score=0;//the scores for the two players
    private int p2score=0;
    
    private HashSet<Point> points = new HashSet<Point>();//contains positions visited of both player 1 and ai
    private HashSet<Point> ppoints = new HashSet<Point>();//only the players positions
    private HashSet<Point> apoints = new HashSet<Point>();//only the ai
    
    private Font font=new Font("Arial",Font.PLAIN,15);
    
    private int time=100;//time that decreases for every 30x that the counter increases
    private int counter=0;//counter 
    public boolean running=true;//status of wheather the game has ended or not
    
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
	
    public SinglePlayer() {//Constructor for the Single Player Game Mode class
    	super("SinglePlayer Mode");
    	addMouseListener(this);
    	addKeyListener(this);
    	setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    	reset();
    	setSize(WIDTH,HEIGHT);
    	Keys=new boolean[2000];
    	setVisible(true);
    }
    
    
    public void move(){//This method deals with manipulating the player by the keyboard keys
    	//Below changes the direction of the user when the certain key is pressed
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
    	
    	boostAmount+=2;//BoostAmount is slowly restored over time and cannot exceed 100
    	if (boostAmount>100){
    		boostAmount=100;
    	}
    	
    	//Below detects the boost and turns it on when if the player has enough boostAmount and the key is pressed
    	//the boost is tweak to allow for a fair game
    	if (boost==false){
    		if (Keys[16] && boostAmount>=50){
    			boostAmount-=10;
    			boost=true;
    		}
    	}
    	else{//when the key is let go the boost stops or if the boost is on boostAmount decreases
    		if (!Keys[16] || boostAmount<=10){
    			boost=false;
    		}
    		boostAmount-=10;
    	}		
    }
    
    public void checkWall(){
    	//this method makes it so if the players hits the border of screen he will be 
    	//teleported to the opposite side.
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
    	
    	
    	if (ax>WIDTH-10){
    		ax=10;
    	}
    	if (ax<10){
    		ax=WIDTH-10;
    	}
    	if (ay<100){
    		ay=HEIGHT-10;
    	}
    	if (ay>HEIGHT-10){
    		ay=100;
    	}
    }
    
  
    
    public void reset(){
    	//when either the ai or user crash it resets the basic information
    	this.points = new HashSet<Point>();
    	this.apoints = new HashSet<Point>();
    	this.ppoints = new HashSet<Point>();
    	x=100;
	    y=350;
	    vx=5;
	    vy=0;
	    
	    ax=700;
	    ay=350;
	    avx=-5;
	    avy=0;
    }
    public boolean ahead(int x,int y,int vx,int vy){
    	//this method is a helper method for the Ai, it takes in the current direction and then checks ahead 3 positions
    	//to see if there is a tron wall ahead, returns true if there is
    	if (vx>0){
    		for (int i=0;i<30;i+=10){
    			if (points.contains(new Point(x+i,y))){
    				return true;
    			}
    		}
    	}
    	if (vx<0){
    		for (int i=0;i<30;i+=10){
    			if (points.contains(new Point(x-i,y))){
    				return true;
    			}
    		}
    	}
    	if (vy>0){
    		for (int i=0;i<30;i+=10){
    			if (points.contains(new Point(x,y+i))){
    				return true;
    			}
    		}
    	}
    	if (vy<0){
    		for (int i=0;i<30;i+=10){
    			if (points.contains(new Point(x,y-i))){
    				return true;
    			}
    		}
    	}
    	return false;
    }
    public void ai(){
    	//this method is used to change the directions of the computer player,
    	//similar to the move method but without keyboard keys
    	int rand=(int)(Math.random()*100)+1;//random # used to change direction of the ai at random times
    	
    	
    	if (avy==0){
    		//the next two if statements choose an arbitary number to change the direction of the ai randomly when that
    		//number is randomply made
    		if (rand==10){
    			avx=0;
    			avy=-5;
    		}
    		if (rand==11){
    			avx=0;
    			avy=5;
    		}
    		
    		//the ai uses the ahead method to check if there is a wall ahead and if there is checks to the right of it
    		//and if there isnt it turns right else it would turn to the left
    		if (ahead(ax,ay,avx,avy)){
    			if (ahead(ax,ay,avx*0,avy+5)){
    				avx=0;
    				avy=-5;
    			}
    			else
    			{
    				avx=0;
    				avy=5;
    			}
    		}
    	}
    	//same code but for when the ai is going horizontally
    	else{
    		if (rand==10){
    			avx=5;
    			avy=0;
    		}
    		if (rand==11){
    			avx=-5;
    			avy=0;
    		}
    		if (ahead(ax,ay,avx,avy)){
    			if (ahead(ax,ay,avx+5,avy*0)){
    				avx=-5;
    				avy=0;
    			}
    			else{
    				avx=5;
    				avy=0;
    			}
    		}
    	}
    	
    }
    
    
    public void collision(){
    	//checks if either the player or ai if on a points already visited
    	//then resets the game and increases the score by 1
    	if (points.contains(new Point(x,y))){reset();p2score+=1;}
    	points.add(new Point(x,y));
    	ppoints.add(new Point(x,y));
    	
    	if (points.contains(new Point(ax,ay))){reset();p1score+=1;}
    	points.add(new Point(ax,ay));
    	apoints.add(new Point(ax,ay));
    }
    
    public void CountDown(){
    	//decreases the time till it hits 0 and the game ends
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
    	//first draws the background made out of lines equals space to form a grid
    	dbg.setColor(new Color(0,0,0));
    	dbg.fillRect(0,0,WIDTH,HEIGHT);
    	dbg.setColor(new Color(25,25,25));
    	for (int i=0;i<WIDTH;i+=10){
    		dbg.drawLine(i,0,i,HEIGHT);
    	}
    	for (int i=0;i<HEIGHT;i+=11){
    		dbg.drawLine(0,i,WIDTH,i);
    	}
    	
    	
    	//goes through every point that has been visited and colors it based on the player
    	dbg.setColor(new Color(0,255,255));
    	for (Point p: ppoints){
    		dbg.fillRect(p.x,p.y,5,5);
    	}
    	dbg.setColor(new Color(255,0,255));
    	for (Point p: apoints){
    		dbg.fillRect(p.x,p.y,5,5);
    	}
    	
    	//Creates the HUD 
    	dbg.setColor(new Color(0,0,0));
    	dbg.fillRect(0,0,WIDTH,100);
    	dbg.setColor(new Color(255,255,255));
    	dbg.setFont(font);
    	dbg.drawString("P1 Score: "+p1score,20,60);
    	dbg.drawString("P2 Score: "+p2score,700,60);
    	//draws a meter indicating the amount of boost the player has
    	dbg.setColor(new Color(255,255,255));
    	dbg.fillRect(0,70,boostAmount*2,20);
    	//draws the text of time remaining
    	dbg.drawString("Time Remaining: "+time,340,60);
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
    			dbg.drawString("Computer Player WINS",300,300);
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
    	ax+=avx;
    	ay+=avy;
    	
    	checkWall();
    	move();
    	ai();
    	collision();
    	repaint();
    	CountDown();
    }
}