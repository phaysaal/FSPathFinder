/*
 * Traveller.java
 *
 * Created on October 10, 2005, 9:01 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

//package pathfinding;

import javax.microedition.lcdui.*; 

/**
 *
 * @author Mahmud
 */
public class Traveller {
    public static final byte DIRECTION_UP = 1;
    public static final byte DIRECTION_DOWN = 2;
    public static final byte DIRECTION_LEFT = 3;
    public static final byte DIRECTION_RIGHT = 4;
    
    private byte x;
    private byte y;
    private byte width;
    private byte height;
    private byte screenleft;
    private byte screentop;
    private byte size;
    private Graphics g;
    private iBox unit[][];
    
    private int score;
    /** Creates a new instance of Traveller */
    public Traveller(byte screenleft, byte screentop, byte width, byte height, byte size, iBox unit[][]) {
        this.screenleft = screenleft;
        this.screentop = screentop;
        this.width = width;
        this.height = height;
        this.size = size;
        this.unit = unit;
        this.x = 0;
        this.y = 0;
        score = 0;
              
    }
    public void Reset(){
        this.x = 0;
        this.y = 0;
        
        
    }
    public void setGraphics(Graphics g){
    	this.g = g;
    }
    public int getScore(){
        return score;
    }
    public void setScore(int s){
        if(s >= 0)
        	score = s;
        else
        	score = 0;
    }
    public void reduce(int s){
        score -= s;
	if(score < 0)
        	score = 0;    
    }
    public void Move(byte direction){
        byte x = this.x;
        byte y = this.y;
        //erase(g);
        switch(direction){
            case DIRECTION_UP:
                y --;
                break;
            case DIRECTION_DOWN:
                y ++;
                break;
            case DIRECTION_LEFT:
                x --;
                break;
            case DIRECTION_RIGHT:
                x ++;
                break;
        }
        if(x >= 0 && x < width && y >= 0 && y < height){
            if(unit[x][y].isWall() == false){
                this.x = x;
                this.y = y;
            }
            else{
            	score -= (10 - (unit[x][y].getWeight()*2));
            	unit[x][y].looseWeight();
            	
            	if(unit[x][y].isWall() == false){
                	this.x = x;
                	this.y = y;
            	}
            	//else
            		//score -= (10 - (unit[x][y].getWeight()*2)); 
            }
            	
        }
        if(unit[this.x][this.y].isFood() == true){
            score += unit[this.x][this.y].getWeight();
            //writeScore(g);
            //erase(g);
        }
        //draw(g);
    }
    public void writeScore(byte level){
	g.setColor(0xFFFFFF);
        g.fillRect(screenleft, screentop - 10, 160, 10);
        g.setColor(0x0000DD);
        String s = "" + score + "      " + level;
        g.drawString(s, screenleft, screentop - 10, 20);
    }
    
    public void erase(){
        g.setColor(0xE6FFE6);
		g.fillRect(screenleft + (x * size), screentop + (y * size), size , size);
    }
    public boolean isFinish(){
    	return (x == (byte)(width-1) && y == (byte)(height-1));
    }
    public void draw(){
        //g.drawString("" + x + "  " + y, 20, 60);
        int x = this.x * size + screenleft ;
        int y = this.y * size + screentop ;
        int w = (size - 2) / 2;
        int xw = x + w;
        int yw = y + w;
        //g.fillArc(0,0,xw,yw,0,180);
        g.setColor(0xDD0000);
        g.fillRect(x, y, size , size);
        
        //g.setColor(0x77FF77);
        //g.fillRect(x+1, y+1, size -2, size-2);
        //g.setColor(0x003A00);
        //g.fillRect(x+3, y+3, size-6 , size-6);
        //g.fillArc(x, y, size, size,0 ,360);
        //g.fillArc(x + w*2, y + w*2, w*2, w*2, 45, 270);
        //for(int i = 0; i <= w; i++)
        //    g.drawLine(i + x, i + yw, i + xw, i + y);
        //System.out.println("AAA");
    }
}
