/*
 * RandomGenerator.java
 *
 * Created on October 10, 2005, 12:16 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

//package pathfinding;
import java.lang.Math;
import java.util.Random;
/**
 *
 * @author Mahmud
 */
public class RandomPathGenerator{
	private static final byte MAXSTRENGTH = 4;
    private byte width;
    private byte height;
    private iBox unit[][];
    private Random rand;
    private byte level;
    
    /** Creates a new instance of RandomGenerator */
    public RandomPathGenerator(byte width, byte height, iBox unit[][]) {
        this.width = width;
        this.height = height;
        this.unit = unit;
        rand = new Random();
        //System.out.println(this.unit.length);
        level = 1;
    }
    
    public void setLevel(byte level){
    	this.level = level;
    }
    public void setFlag(byte i, byte j, byte FLAG){
        if(i < width && j < height && i > -1 && j > -1){
        	unit[j][i].setFlag(FLAG);
        	if(FLAG == iBox.FOOD || FLAG == iBox.BLOCK)
        		unit[j][i].setWeight((byte)(random((byte)MAXSTRENGTH) + 1));
        }
            
    }
    public byte random(byte seed){
    	return (byte)(Math.abs(rand.nextInt()) % seed);
//    	long x = System.currentTimeMillis();
//    	long y = (x / 10000000000L) % 10;
//    	int z;
//    	while((y--) > 0){
//    		z = (int)((x / 1000000000L)%10);
//    		switch(z){
//    			case 0:
//    				if(x < 5000000000L)
//    					x += 5000000000L;
//    				break;
//    			case 1:
//    				x = ((long)Math.floor(((x / 100000) * x))) % 10000000000L; 
//    				break;
//    			case 2:
//    				x = (1001001001L * x) % 10000000000L;
//    				break;
//    			case 3:
//    				if(x < 100000000L)
//    					x += 9814055677L;
//    				else
//    					x = 10000000000L - x;
//    				break;
//    			case 4:
//    				x = 100000*(x%100000) + Math.floor(x/100000);
//    				break;
//    			case 5:
//    				x = (1001001001L * x) % 10000000000L;
//    				break;
//    			case 6:
//    				//...
//    				break;
//    			case 7:
//    				if(x < 100000)
//    					x = x * x + 99999;
//    				else
//    					x = x - 99999;
//    				break;
//    			case 8:
//    				if(x < 1000000000)
//    					x = 10 * x;
//    				break;
//    			case 9:
//    				x = Math.floor(x * (x - 1) / 100000) % 10000000000L;
//    			
//    		}
//    	}
//    	return (byte)(x % seed);
//    	
    }
    public void PathGenerator(){
        byte a = 0, b = 0, q, j=0;
        try{
        
	        do{
	            //Horizontal Path
	            q = random(width);
	            
		    //System.out.println(q);
	            j = b;
	            for(b += q; j < b && j < width; j++)
	                setFlag(a, j, iBox.PATH);            
	            b = (byte)(j - 1);
	            
	            //Vertical Path
	            q = (byte)(random(height) + 2);
	            //System.out.println(q);
	            j = a;
	            for(a += q; j < a && a < height; j++)
	                setFlag(j, b, iBox.PATH);
	            a = (byte)(j - 1);
	            
	            //-Ve Horizontal Path
	            q = random(height);
	            //System.out.println(q);
	            j = b;
	            for( b -= q; j > b && j > -1; j--)
	                setFlag(a, j, iBox.PATH);            
	            b = (byte)(j + 1);
	            
	            //Vertical Path
	            q = (byte)(random(width) + 3);
	            //System.out.println(q);
	            j = a;
	            for(a += q; j < a && j < width; j++)
	                setFlag(j, b, iBox.PATH);
	            a = (byte)(j - 1);
	            
	        }while(a < width - 1);
	        
	        for(j=b; j < width; j++)
	                setFlag(a, j, iBox.PATH);
        }catch(Exception e){
        	System.err.println(""+e+ "--" + a+","+b+","+j);
        }
    }
    public void Reset(){
        InitBoard();
	/*
	for(byte i=0; i<width; i++)
            for(byte j=0; j<height; j++)
                setFlag(i, j, iBox.FREE);
        PathGenerator();
        WallGenerator();
        
        */
	FoodGenerator();
    }
    public void FoodGenerator(){
        byte a = 0, b = 0;
        for(byte i = 0; i < (level + 30); i++){
            a = random(height);
            b = random(width);
            setFlag(a, b, iBox.FOOD); 
                       
        }
    }
    public void WallGenerator(){
        byte a = 0, b = 0, q;
        byte j;
        try{
        
	        for(byte i = 0; i < ((level * 2) + 20); i++){
	        	
	            a = random(height);
	            b = random(width);
	            q = random(height);
	            //System.out.println("" + a + "," + b + "," + q);
	            for(j=a; j<=q; j++)
	                setFlag(j, b, iBox.BLOCK);            
	           	
	            a = random(height);
	            b = random(width);
	            q = random(width);            
	            //System.out.println(q);
	            for(j=b; j<=q; j++)
	                setFlag(a, j, iBox.BLOCK);  
	                      
	        }
	        setFlag((byte)0, (byte)0, iBox.FREE);
	        setFlag((byte)(width - 1), (byte)(height - 1), iBox.FREE);
        }
        catch(Exception e){
        	System.err.println("Error: " + e);
        }
    }
    
    private void decode(int n, int val){
	byte i;
	int t = 0x80000000;
	for(i = 0; i<width; i++){
	    if((t & val) == 0)
		setFlag((byte)n, i, iBox.FREE); 
	    else
		setFlag((byte)n, i, iBox.BLOCK); 
	    t >>>= 1;
	}
	    
    }
    private void InitBoard(){
	if(level == 9){
	    decode(0, 0xE3E7E000);
	    decode(1, 0xBE3C1000);
	    decode(2, 0xC0401000);
	    decode(3, 0xAF7BF000);
	    decode(4, 0xA94D0000);
	    decode(5, 0xA954F000);
	    decode(6, 0xAD572000);
	    decode(7, 0xA5516000);
	    decode(8, 0xA57EB000);
	    decode(9, 0xA5029000);
	    decode(10, 0xA5FE9000);
	    decode(11, 0xB480B000);
	    decode(12, 0x9CBEA000);
	    decode(13, 0xB0A2B000);
	    decode(14, 0xE7BAD000);
	    decode(15, 0xA40AB000);
	    decode(16, 0x94EB9000);
	    decode(17, 0x87BCF000);
	    decode(18, 0x80401000);
	    decode(19, 0xFFFFD000);
	}
	else if(level == 8){
	    decode(0, 0xFFFFF000);
	    decode(1, 0xA0529000);
	    decode(2, 0xA7529000);
	    decode(3, 0xA552F000);
	    decode(4, 0xBD729000);
	    decode(5, 0xA1129000);
	    decode(6, 0xAF53D000);
	    decode(7, 0xA8789000);
	    decode(8, 0xAB489000);
	    decode(9, 0xFEEFF000);
	    decode(10, 0xA0241000);
	    decode(11, 0xAE27D000);
	    decode(12, 0xAB257000);
	    decode(13, 0xA9E51000);
	    decode(14, 0xA8057000);
	    decode(15, 0xAFF1C000);
	    decode(16, 0xA41D3000);
	    decode(17, 0xABE5D000);
	    decode(18, 0xA0241000);
	    decode(19, 0xFFF7F000);
	}
	else if(level == 7){
	    decode(0, 0x55545000);
decode(1, 0x202A2000);
decode(2, 0x05015000);
decode(3, 0x8AA88000);
decode(4, 0x50454000);
decode(5, 0x8A222000);
decode(6, 0x51051000);
decode(7, 0x8A88A000);
decode(8, 0x50544000);
decode(9, 0x8A202000);
decode(10, 0x11411000);
decode(11, 0xA08A8000);
decode(12, 0x15445000);
decode(13, 0xA20A0000);
decode(14, 0x14115000);
decode(15, 0xA0A88000);
decode(16, 0x14450000);
decode(17, 0xAAA22000);
decode(18, 0x01151000);
decode(19, 0x88888000);
	}
	else if(level == 6){
	    decode(0, 0xFF2AA000);
decode(1, 0x002AA000);
decode(2, 0x7D7EA000);
decode(3, 0x00000000);
decode(4, 0xBFFF6000);
decode(5, 0x80009000);
decode(6, 0x97F94000);
decode(7, 0x94002000);
decode(8, 0x95FF2000);
decode(9, 0x95016000);
decode(10, 0x55754000);
decode(11, 0x555D4000);
decode(12, 0x55414000);
decode(13, 0x557CC000);
decode(14, 0x55020000);
decode(15, 0x55FAE000);
decode(16, 0x940AA000);
decode(17, 0x97FAA000);
decode(18, 0x90012000);
decode(19, 0xCFFC2000);
	}
	else if(level == 5){
	    decode(0, 0xFFFF8000);
	    decode(1, 0x14A0F000);
	    decode(2, 0xD69C9000);
	    decode(3, 0x92979000);
	    decode(4, 0x95505000);
	    decode(5, 0x8FFFF000);
	    decode(6, 0xE8055000);
	    decode(7, 0x28155000);
	    decode(8, 0xE817D000);
	    decode(9, 0x88105000);
	    decode(10, 0xB817F000);
	    decode(11, 0xA8141000);
	    decode(12, 0xA017F000);
	    decode(13, 0xBFD05000);
	    decode(14, 0x8057D000);
	    decode(15, 0xBFD41000);
	    decode(16, 0xA817E000);
	    decode(17, 0xBFD00000);
	    decode(18, 0x801F7000);
	    decode(19, 0x7FE00000);
	}else if(level == 4){
	    decode(0, 0x2AAAA000);
decode(1, 0x00000000);
decode(2, 0x11552000);
decode(3, 0x04004000);
decode(4, 0x0AAAA000);
decode(5, 0x11010000);
decode(6, 0x20822000);
decode(7, 0x44448000);
decode(8, 0x8A692000);
decode(9, 0x11141000);
decode(10, 0x24822000);
decode(11, 0x12444000);
decode(12, 0x0908A000);
decode(13, 0x04910000);
decode(14, 0x02622000);
decode(15, 0x01240000);
decode(16, 0x00882000);
decode(17, 0x00500000);
decode(18, 0x00202000);
decode(19, 0x00000000);
	}else if(level == 3){
	    decode(0, 0x015C0000);
decode(1, 0xFD45E000);
decode(2, 0x01552000);
decode(3, 0xEC102000);
decode(4, 0x01D7E000);
decode(5, 0xF4140000);
decode(6, 0x1275F000);
decode(7, 0x19050000);
decode(8, 0x029D6000);
decode(9, 0x01114000);
decode(10, 0x80266000);
decode(11, 0x4412A000);
decode(12, 0x28282000);
decode(13, 0x1045A000);
decode(14, 0x28822000);
decode(15, 0x41412000);
decode(16, 0x8228A000);
decode(17, 0x04104000);
decode(18, 0x00A12000);
decode(19, 0x10420000);
	}
	else if(level == 2){	    //Eid Mobarak
	    decode(0, 0x70000000);
	    decode(1, 0x00000000);
	    decode(2, 0xFBEFF000);
	    decode(3, 0x420A5000);
	    decode(4, 0xAA695000);
	    decode(5, 0x12A9D000);
	    decode(6, 0x232A5000);
	    decode(7, 0x50495000);
	    decode(8, 0x88245000);
	    decode(9, 0x00000000);
	    decode(10, 0x00000000);
	    decode(11, 0x00000000);
	    decode(12, 0xFEFBF000);
	    decode(13, 0x0A084000);
	    decode(14, 0x7A79F000);
	    decode(15, 0x8A8A5000);
	    decode(16, 0x8A8A5000);
	    decode(17, 0x6A694000);
	    decode(18, 0x1A18C000);
	    decode(19, 0x0A480000);
	}
	else if(level == 1){
	    decode(0, 0x7C000000);
	    decode(1, 0x44E07000);
	    decode(2, 0x50201000);
	    decode(3, 0x5F82D000);
	    decode(4, 0x404A5000);
	    decode(5, 0x7A8B0000);
	    decode(6, 0x04B0A000);
	    decode(7, 0x692A9000);
	    decode(8, 0x1216C000);
	    decode(9, 0x34A02000);
	    decode(10, 0x47979000);
	    decode(11, 0x50605000);
	    decode(12, 0x5EB5D000);
	    decode(13, 0x42820000);
	    decode(14, 0x5AB5E000);
	    decode(15, 0x4B142000);
	    decode(16, 0x444DE000);
	    decode(17, 0x59750000);
	    decode(18, 0x5500F000);
	    decode(19, 0x09FE0000);
	}
	else if(level == 0){
	    
decode(0, 0x00000000);
decode(1, 0xAAAAA000);
decode(2, 0x00104000);
decode(3, 0xAAAAA000);
decode(4, 0x00141000);
decode(5, 0xAAAAA000);
decode(6, 0x55510000);
decode(7, 0xAAAAA000);
decode(8, 0x00004000);
decode(9, 0xAAAA9000);
decode(10, 0x15541000);
decode(11, 0x8AAAA000);
decode(12, 0x55555000);
decode(13, 0x00000000);
decode(14, 0x55555000);
decode(15, 0xA8AAA000);
decode(16, 0x01000000);
decode(17, 0xAAAAA000);
decode(18, 0x00000000);
decode(19, 0xAAAAA000);
	}
	else{
	    System.out.println("LEVEL: " + level);
	    for(byte i=0; i<width; i++)
            for(byte j=0; j<height; j++)
                setFlag(i, j, iBox.FREE);
        PathGenerator();
        WallGenerator();
	}
    }
}
