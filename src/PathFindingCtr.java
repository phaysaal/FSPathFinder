/*
 * PathFindingCtr.java
 *
 * Created on October 6, 2005, 10:54 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

//package pathfinding;

import javax.microedition.lcdui.*; 
import javax.microedition.lcdui.game.*;

/**
 *
 * @author Guest
 */
public class PathFindingCtr extends GameCanvas implements Runnable{
    //*
    private static final byte SIZE = 4;
    private static byte LEFT = 10;
    private static final byte TOP = 10;
    private static final byte WIDTH = 20;
    private static final byte HEIGHT = 20;
    private static final byte MAXTIME = 50;
    
    private iBox unit[][];
    private byte mode;
    private Traveller traveller;
    private RandomPathGenerator pathOrganizer;
    
    private Thread t;
    private int time;
    //public FrontView fv;
    private int maxTime;
    private int score;
    private byte level;
    private byte direc;
    
    private boolean isGameRunning;
    private boolean isStart;
    private boolean isTiming;
    private boolean isThreadShouldRun = false;
     //*/

    /** The Display of this MIDlet */
    private Display display;
    /** The listener used to report solved events */
    private CommandListener listener;
    
    private FSFPathFinder pf;
    
    
    private Graphics g;

    public PathFindingCtr(FSFPathFinder pf) {
        super(false);
        this.pf = pf;
        //Background Color
        //setBackground(new Color(255, 255, 255));
        direc = 0;
        level = 0;
        maxTime = 100;
        isStart = false;
        
        LEFT = (byte)((this.getWidth() - (WIDTH * SIZE)) / 2);
        //Box creation
        unit = new iBox[WIDTH][HEIGHT];
        for(byte i=0; i<WIDTH; i++)
            for(byte j=0; j<HEIGHT; j++)
                unit[i][j] = new iBox(i, j);
        iBox.setValues(SIZE, TOP, LEFT);      
        mode = 1;    
				
        //Necessary Object Creation
        pathOrganizer = new RandomPathGenerator(WIDTH, HEIGHT, unit);
        traveller = new Traveller(LEFT, TOP, WIDTH, HEIGHT, SIZE, unit);
        
        t = new Thread(this);
        t.start();
        System.out.println("Constructor");
        /////timer = new PFTimer(MAXTIME, 200, 55, this);        
        //Reset();
    }
    
    
    public void run(){
    	int count = 0;
    	try{
    		
    		while(isThreadShouldRun){
    			count++;
    			//System.out.println("Tread is running "+count);
    			Thread.sleep(1000);
    			
			direc = 0;
			if(isTiming)
			time --;

			repaint();
			if(time <= 0){
				isTiming = false;
				threadStop();
				Thread.sleep(300);
				pf.Finish();
			}
    			//System.out.print(","+time);
    		}
    		
    	}
    	catch(Exception e){
    		
    	}
    }
    public void threadStop(){
    	
    	t = null;
    }
    private void ChangeLevel(){
        this.level ++;
        pathOrganizer.setLevel(level);
    }
   
    public void finish(){
    	Reset();
    }
    public void Stop(){
    	isThreadShouldRun = false;
    }
    public void Start(){
    	//System.out.println("Start");
    	
    	traveller.setScore(0);
    	pathOrganizer.setLevel((byte)0);
        Reset();
        isThreadShouldRun = true;
        t = new Thread(this); 
        t.start();
        
    }
    public int getScore(){
    	return traveller.getScore();
    }
    public void Next(){
    	//System.out.println("Next");
    	//traveller.setScore(traveller.getScore() - 50);
	traveller.reduce(50);
    	Reset();
    }
    public void Reset(){
        //System.out.println("Reset");
        direc = 0;   
         
        isTiming = false;      
        isGameRunning = false;
	//System.out.println("1>");
        pathOrganizer.Reset();
	//System.out.println("2>");
        ChangeLevel();
	//System.out.println("3>");
        setTime(maxTime);
	//System.out.println("4>");
	traveller.Reset();
	//System.out.println("5>");
        repaint();
	//System.out.println("6>");
        if(!isTiming) {
        	isTiming = true;
        }
        //System.out.println("7>");
    }
    public int getTime(){
	isTiming = false;
    	return time;
    }
    public void setTime(int t){
    	time = t;
	isTiming = true;
    }
    public void Resume(){
        direc = 0;   
          
        isTiming = false;      
        isGameRunning = false;
        //pathOrganizer.Reset();
        //ChangeLevel();
        
		//traveller.Reset();
        repaint();
        if(!isTiming) {
        	isTiming = true;
        	
        }
        System.out.println("Resume");
    }
    public void Move(byte direction){
        //traveller.Move(direction);
        if(isTiming){
        	direc = direction;
        	repaint();
        	
        }
        
    }
/*
    public void ChangeMode(){
        if(mode == 1){
            pathOrganizer = new FixedPathGenerator(WIDTH, HEIGHT, unit);
            mode = 2;
        }
        else{
            pathOrganizer = new RandomPathGenerator(WIDTH, HEIGHT, unit);
            mode = 1;
        }      
    }
*/
    protected void keyRepeated(int keyCode) {
        int action = getGameAction(keyCode);
        System.out.println(action);
        switch (action) {
        case Canvas.LEFT:
        case Canvas.RIGHT:
        case Canvas.UP:
        case Canvas.DOWN:
            keyPressed(keyCode);
	    break;
        default:
            break;
        }
                
    }

   
    protected void keyPressed(int keyCode) {
        //System.out.println(keyCode);
        if(isGameRunning == true){
	        switch(keyCode){
		    case -1:
		    case 50:
	        		Move(Traveller.DIRECTION_UP);
	        		break;
		    case -2:
		    case 56:
	        		Move(Traveller.DIRECTION_DOWN);
	        		break;
	        	case -3:
		    case 52:
	        		Move(Traveller.DIRECTION_LEFT);
	        		break;
	        	case -4:
		    case 54:
	        		Move(Traveller.DIRECTION_RIGHT);
	        		break;
	        	case -5:
		    case 53:
	        		Reset();
	        		break;
		    case 49:
			Move(Traveller.DIRECTION_UP);
			Move(Traveller.DIRECTION_LEFT);
			break;
		    case 51:
			Move(Traveller.DIRECTION_UP);
			Move(Traveller.DIRECTION_RIGHT);
			break;
		    case 55:
			Move(Traveller.DIRECTION_DOWN);
			Move(Traveller.DIRECTION_LEFT);
			break;
		    case 57:
			Move(Traveller.DIRECTION_DOWN);
			Move(Traveller.DIRECTION_RIGHT);
			break;
	        }
	    }
    }
    

    public void paint(Graphics g){ 
        byte i, j;
        //
        g.setFont(Font.getFont(64, 0, 8));
	
		if(isGameRunning == false)	{
			this.g = g;
			g.setColor(0xFFFFFF);
		    g.fillRect(0,  0, this.getWidth(), this.getHeight());
//		    g.setColor(0xFF00EE);
//		    g.drawString("FS Path Finder",LEFT,5,20);
		    //g.setColor(0x00C8C8);
		    //j=0;
		    
		    
		    iBox.setGraphics(g);
		    
		    for(i=0; i<WIDTH; i++)
		        for(j=0; j<HEIGHT; j++)
		            unit[i][j].draw();
		    unit[WIDTH-1][HEIGHT-1].sdraw();
		    traveller.setGraphics(g);
		    
	    	isGameRunning = true;
	    	//System.out.println("*");
	    	
	    	
	    }
	    else if(direc > 0){
		    traveller.erase();
		    traveller.Move(direc);		    
	    }
	    traveller.writeScore(level);
	    traveller.draw(); 
	    
	    
    	g.setColor(0xFFFFFF);
    	g.fillRect(LEFT + (SIZE*WIDTH) - 10 , TOP - 10, 50,10);
    	g.setColor(0xCC0000);
    	if(time < maxTime && time > 0)
    		g.drawString(""+time, LEFT + (SIZE*WIDTH) - 10, TOP - 10, 20);
    	else {
    		//System.out.println("Is Start:  " + isStart);
    		if(time < 1){
    			g.drawString("Level Is Over.", LEFT + (SIZE*WIDTH) - 10, TOP - 10, 20);
    		}
    		//else if(isStart == false){
    			
    		//	g.drawString("Press 'Start'", LEFT + 145, TOP - 20, 20);
    		//	isStart = true;
    		//}
    			
    	}	
	    if(traveller.isFinish())
		    	finish();
		       
        //System.out.println("In Paint: " + g) ;
         g.setColor(0x0000EE);
	g.drawRect(LEFT, TOP, WIDTH*SIZE, HEIGHT*SIZE);
    }
    
}
