/*
 * iBox.java
 *
 * Created on October 6, 2005, 11:21 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

//package pathfinding;

import javax.microedition.lcdui.*; 

/**
 *
 * @author Guest
 */
public class iBox {
    static final byte FREE = 0;
    static final byte BLOCK = 1;
    static final byte PATH = 2;
    static final byte FOOD = 3;
    private byte x;
    private byte y;
    private byte flag;
    private byte weight;
    private static byte size;
    private static byte top;
    private static byte left;
    private static Graphics g;
    
    /** Creates a new instance of iBox */
    
    public iBox(byte x, byte y) {
        this.x = x;
        this.y = y;
                
        flag = 0;
    }
    public static void setGraphics(Graphics gg){
    	g = gg;
    }
    public static void setValues(byte size, byte top, byte left){
    	iBox.size = size;
        iBox.top = top;
        iBox.left = left;
    }
    public boolean isWall(){
        if(this.flag == this.BLOCK)
            return true;
        return false;
    }
    public boolean isFood(){
        if(this.flag == this.FOOD){
            this.flag = this.FREE;
            return true;
        }
        return false;    
    }
    public byte getX(){
        return x;
    }
    public byte getY(){
        return y;
    }
    public void setWeight(byte w){
    	weight = w;
    }
    public byte getWeight(){
    	return weight;
    }
    public void setFlag(byte flag){
        //RESET
	if(flag == this.FREE || flag == this.PATH)
            this.flag = flag;
	//HIGHEST PRIORITY
	else if(flag == this.BLOCK && this.flag != this.PATH)
            this.flag = flag;
	else if(flag == this.FOOD && this.flag != this.BLOCK)
            this.flag = flag;
        //if(flag == this.FOOD && this.flag == this.PATH)
            //return;
	//if(flag == this.BLOCK && this.flag == this.FOOD)
        //    return;
	
    }
    public void looseWeight(){
    	weight--;
    	if(weight == 0)
    		flag = iBox.FREE;
    		
    	draw();
    		
    }
    public void draw(){
        //g.setColor(0xFF8F00);
        g.setColor(0x0000FF);
        if(flag == FREE){
        	//System.out.println("Free");
            //g.drawRect(x * size + left, y * size + top, size, size);
        }            
        else if(flag == BLOCK){
        	switch(weight){
    		case 0:
    			g.setColor(0xFFFFFF );
    			break;
        	case 1:
        		g.setColor(0x7777FF );
        		break;
        	case 2:
        		g.setColor(0x3333FF );
        		break;
        	case 3:
        		g.setColor(0x0000FF );
        		break;
        	case 4:
        		g.setColor(0x0000CC );
        		break;
        }
            g.fillRect(x * size + left, y * size + top, size , size );
        }
        else if(flag == PATH){
            //g.drawRect(x * size + left, y * size + top, size, size);
            //g.drawRect(x * size + left + 1, y * size + top + 1, size - 2, size - 2);
        }  
        else if(flag == FOOD){
            //System.out.println("Food");
            //g.drawRect(x * size + left, y * size + top, size, size);
            g.setColor(0xFF0000 );
            switch(weight){
            	case 1:
            		g.setColor(0x00DD00 );
            		break;
            	case 2:
            		g.setColor(0x00CC00 );
            		break;
            	case 3:
            		g.setColor(0x00BB00 );
            		break;
            	case 4:
            		g.setColor(0x00AA00 );
            		break;
            }
            g.fillRect(x * size + left+1, y * size + top+1, size -2, size-2 );
            //g.fillArc(x * size + left + 1, y * size + top + 1, size - 2, size - 2, 180, 180); 
            //g.fillArc(x * size + left + 2, y * size + top + 2, size - 4, size - 4, 0, 180);
            //g.drawRect(x * size + left + 2, y * size + top + 2, size - 4, size - 4);
        }  
        //System.out.println(left + " " + top);
    }
    public void sdraw(){
        //g.setColor(0xFF8F00);
        g.setColor(0xFF8040);
        flag = FREE;
        g.drawRect(x * size + left, y * size + top, size, size);
	g.drawRect(x * size + left + 2, y * size + top + 2, size - 4, size - 4);
    }
}
