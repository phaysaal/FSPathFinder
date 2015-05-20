import javax.microedition.lcdui.*; 
import javax.microedition.midlet.MIDlet; 
 
public class FSFPathFinder extends MIDlet implements CommandListener { 
    Display display;
    private PathFindingCtr canvas = null;
    private ScoreMgr sm = null;
    private int time;
    private int scoreID;
    private int score;
    private int dbc = 0;
    private boolean isRunning = false;
    
    private Command nextCommand = 
        new Command("Next Level", Command.ITEM, 21);
    private Command resumeCommand = 
        new Command("Resume", Command.ITEM, 31);
    private Command exitCommand =
	    new Command("Exit", Command.EXIT, 60);
	private Command saveCommand =
	    new Command("Save", Command.ITEM, 70);
	private Command cancelCommand =
	    new Command("Cancel", Command.EXIT, 70);
	private Command menuCommand =
	    new Command("Menu", Command.EXIT, 21);
	private Command updateCommand =
	    new Command("Update Score", Command.ITEM, 60);
	private Command startCommand =
	    new Command("Start", Command.ITEM, 21);
	private Command scoreCommand =
	    new Command("Score", Command.ITEM, 70);
	private Command helpCommand =
	    new Command("Help", Command.ITEM, 70);
	
    private Form frmMenu = null;
    private Form frmHighScore = null;
    private Form frmHelp = null;
    private TextField txtName = null;
    
    public FSFPathFinder() 
    { 
	display = Display.getDisplay(this);
        frmMenu = new Form("FS Path Finder");
	try{
        frmMenu.append(Image.createImage("/title.JPG"));
	}catch(Exception e){
	System.out.println("Not found");}
        frmMenu.addCommand(startCommand);
        frmMenu.addCommand(scoreCommand);
        frmMenu.addCommand(helpCommand);
        frmMenu.addCommand(exitCommand);      
        
        frmMenu.setCommandListener(this);
        
    } 
    
    public void Finish(){
    	canvas.Stop();
    	
    	commandAction(updateCommand, frmMenu);
    	frmMenu.removeCommand(resumeCommand);
    	frmMenu.removeCommand(updateCommand);
    	canvas.Stop();
        canvas = null;
        isRunning = false;
    	
    }
    public void startApp() 
    {	
        display.setCurrent(frmMenu);
    }
    
    public void pauseApp () {} 
   
    public void destroyApp(boolean unconditional) {} 
       
	public void commandAction(Command c, Displayable s) 
	{
        //System.out.println(dbc + ": " + c.getLabel());
        //dbc++;
        if (c == startCommand) 
        {
        	
        	if(canvas != null)
        		canvas.Stop();
        	canvas = null;
        	frmMenu.addCommand(resumeCommand);
        	frmMenu.addCommand(updateCommand);
        	
        	canvas = new PathFindingCtr(this);
        	canvas.addCommand(nextCommand);
        	canvas.addCommand(menuCommand);
        	canvas.setCommandListener(this);
        	display.setCurrent(canvas);
            canvas.Start();
            isRunning = true;
            
        } 
        else if (c == nextCommand ) 
        {
        	canvas.Next();        
        } 
        else if (c == resumeCommand ) 
        {
	    canvas.setTime(time);
	    canvas.Resume();
            display.setCurrent(canvas);
        } 
        else if (c == menuCommand && s == canvas) 
        {
	    time = canvas.getTime();
            display.setCurrent(frmMenu);
        }
        else if (c == menuCommand && s == sm) 
        {
            display.setCurrent(frmMenu);
            sm = null;
        } 
        else if (c == scoreCommand) 
        {
        	sm = new ScoreMgr();
        	sm.addCommand(menuCommand);
        	sm.setCommandListener(this);
        	sm.printScores();
            display.setCurrent(sm);
        }
        else if (c == exitCommand && s == frmMenu) 
        {
            destroyApp(false);
            notifyDestroyed();
        }
        else if (c == updateCommand) 
        {
        	score = canvas.getScore();
        	sm = new ScoreMgr();
        	scoreID = sm.isHighScore(score);
        	txtName = new TextField("Enter Your Name", "", 15, TextField.ANY);
        	
        	frmHighScore = new Form("High Score");
        	if(scoreID == -2){
        		frmHighScore.append("No High Score: " + score + "\n");
        		frmHighScore.append("Try Again... \n");
        	}
        	else{
        		frmHighScore.append("High Score: " + score + "\n");
			
        		frmHighScore.append(txtName);
        		frmHighScore.addCommand(saveCommand);
        	}
        	
        	
        	frmHighScore.addCommand(cancelCommand);
        	frmHighScore.setCommandListener(this);
        	display.setCurrent(frmHighScore);
        	
            
        }
        else if (c == saveCommand) 
        {
        	
        	
            sm.addCommand(menuCommand);
        	sm.setCommandListener(this);
        	
            
    		sm.changeScores(txtName.getString(), score, scoreID);
    		sm.printScores();
            display.setCurrent(sm);
            
            if(isRunning == true){
        		frmMenu.removeCommand(resumeCommand);
    			frmMenu.removeCommand(updateCommand);
    			canvas.Stop();
            	canvas = null;
            	isRunning = false;
        	}
            
        }
        else if (c == cancelCommand) 
        {
            display.setCurrent(frmMenu); 
            frmHighScore = null;
           	sm = null;
        }
	else if (c == helpCommand) 
        {
            frmHelp = new Form("Help");
	    frmHelp.addCommand(menuCommand);
	    frmHelp.setCommandListener(this);
	    frmHelp.append("Use cursor to move Finder to the destiantion Orange Box. Eat food on the way and gain point." + 
			    "Break wall if necessary and loose point. Time is limited...");
	    display.setCurrent(frmHelp); 
            
        }
	else if (c == menuCommand && s == frmHelp) 
        {
            display.setCurrent(frmMenu);
            frmHelp = null;
        } 
	
	
    }

} 

