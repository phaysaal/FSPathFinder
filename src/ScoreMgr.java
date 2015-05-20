
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;

public class ScoreMgr extends Form implements RecordComparator{
	
	
	
    //The RecordStore used for storing the game scores.
    private RecordStore recordStore = null;
    //The player name to use when filtering.
    public static String playerNameFilter = null;
	
	private void sop(int s){
		System.out.println(s);
	}
	private void sop(String s){
		System.out.println(s);
	}
    
	public ScoreMgr(){
		super("Score");
		
		/**
		int j=0;
		
		try{
			
			recordStore = RecordStore.openRecordStore("FSPFScore",true,RecordStore.AUTHMODE_ANY,true);
			recordStore.closeRecordStore();
			recordStore.deleteRecordStore("FSPFScore");
			
			recordStore = null;
			System.out.println("Successfuly deleted");
			//while(j<10){
			//	addRecord(0, "No Name");
			//	j++;
			//}
			//
			
			//printScores();
		}
		catch(Exception e){
			append("ERR: " + e + "\n");
			System.out.println(e);
		}
		//*/
	}
	public void addRecord(int score, String playerName){
		System.out.println("Add Score: " + score);
		int recId;  // returned by addRecord but not used
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(baos);
		try {
		    // Push the score into a byte array.
		    outputStream.writeInt(score);
		    // Then push the player name.
		    outputStream.writeUTF(playerName);
		}
		catch (IOException ioe) {
		    System.out.println(ioe);
		    ioe.printStackTrace();
		}
	
		// Extract the byte array
		byte[] b = baos.toByteArray();
		// Add it to the record store
		try {
		    recId = recordStore.addRecord(b, 0, b.length);
		}
		catch (RecordStoreException rse) {
		    System.out.println(rse);
		    rse.printStackTrace();
		}
	
	}
	public int isHighScore(int score)
	{
		int id = 0;
		int tscore = 0;
		int minScore = 0;
		int minID = 0;
		int c = 0;
		try {
		    recordStore = RecordStore.openRecordStore("FSPFScore",true,RecordStore.AUTHMODE_ANY,true);
		    RecordEnumeration re = recordStore.enumerateRecords(null, this, true);
		    
			sop(score);
			try {
			    while(re.hasNextElement()) {
			    	id = re.nextRecordId();
			    	c++;
			    }
			    	if(c == 0)
				    return -1;
				ByteArrayInputStream bais = new ByteArrayInputStream(recordStore.getRecord(id));
				DataInputStream inputStream = new DataInputStream(bais);
					
				try 
				{
				    tscore = inputStream.readInt();
				    sop("Last score: " + tscore);
				    sop("C: " + c);
				    sop("C: " + c);
				    if(c < 10)
						id = -1;
					else if(tscore > score)
						id = -2;
					sop("ID: " + id);
				}
				catch (EOFException eofe) {
				    System.out.println(eofe);
				    eofe.printStackTrace();
				}
			}
			catch (RecordStoreException rse) {
			    System.out.println(rse);
			    rse.printStackTrace();
			}
			catch (IOException ioe) {
			    System.out.println(ioe);
			    ioe.printStackTrace();
			}
			re.destroy();
			recordStore.closeRecordStore();
		}
		catch (RecordStoreException rse) {
		    System.out.println(rse);
		    rse.printStackTrace();
		}
		
		recordStore = null;
		sop("RETURN ID: " + id);
		return id;
	}
    
    public void changeScores(String name, int score, int id)
    {
    	int iid = 0;
    	sop(id);
		if(score <= 0)
			return;
		try {
		    recordStore = RecordStore.openRecordStore("FSPFScore",true,RecordStore.AUTHMODE_ANY,true);
		    RecordEnumeration re = recordStore.enumerateRecords(null, this, true);
			try {
				while(re.hasNextElement()) 
			    	iid = re.nextRecordId();
			    	
				if(id == -1)
				{
					addRecord(score, name);
					re.destroy();
					recordStore.closeRecordStore();
					recordStore = null;
					return;
				}
				else if(id >= 0 && iid>0)
				{
					recordStore.deleteRecord(iid);
					addRecord(score, name);
				}
				else
					System.out.println(iid);
			}
			catch (RecordStoreException rse) {
			    System.out.println(rse);
			    rse.printStackTrace();
			}
			re.destroy();
			recordStore.closeRecordStore();
		}
		catch (RecordStoreException rse) {
		    System.out.println(rse);
		    rse.printStackTrace();
		}
		
		recordStore = null;
    }
    
	public void printScores()
    {
    	append("Score of FS Path Finder\n");
		try {
			recordStore = RecordStore.openRecordStore("FSPFScore",true,RecordStore.AUTHMODE_ANY,true);
		    RecordEnumeration re = recordStore.enumerateRecords(null, this, true);
			
		    printScoresHelper(re);
		    re.destroy();
		    recordStore.closeRecordStore();
		}
		catch (RecordStoreException rse) {
		    System.out.println(rse);
		    rse.printStackTrace();
		}
		
		recordStore = null;
    }

	private void printScoresHelper(RecordEnumeration re)
    {
    	int i = 1;
    	System.out.println("Score Card");
		try {
		    while(re.hasNextElement()) {
				int id = re.nextRecordId();
				
				ByteArrayInputStream bais = new ByteArrayInputStream(recordStore.getRecord(id));
				DataInputStream inputStream = new DataInputStream(bais);
				try {
				    int score = inputStream.readInt();
				    String playerName = inputStream.readUTF();
				    append(i + ".    " + score + "     " + playerName + "\n");
				}
				catch (EOFException eofe) {
				    System.out.println(eofe);
				    eofe.printStackTrace();
				}
				i++;
		    }
		}
		catch (RecordStoreException rse) {
		    System.out.println(rse);
		    rse.printStackTrace();
		}
		catch (IOException ioe) {
		    System.out.println(ioe);
		    ioe.printStackTrace();
		}
    }

	public int compare(byte[] rec1, byte[] rec2)
    {
		ByteArrayInputStream bais1 = new ByteArrayInputStream(rec1);
		DataInputStream inputStream1 = new DataInputStream(bais1);
		ByteArrayInputStream bais2 = new ByteArrayInputStream(rec2);
		DataInputStream inputStream2 = new DataInputStream(bais2);
		
		int score1 = 0;
		int score2 = 0;
		
		try {
		    score1 = inputStream1.readInt();
		    score2 = inputStream2.readInt();
		}
		catch (EOFException eofe) {
		    System.out.println(eofe);
		    eofe.printStackTrace();
		}
		catch (IOException eofe) {
		    System.out.println(eofe);
		    eofe.printStackTrace();
		}
	
		// Sort desc by score
		if (score1 > score2) {
			//System.out.println(score1 + " > " + score2);
		    return RecordComparator.PRECEDES;
		}
		else if (score1 < score2) {
			//System.out.println(score1 + " < " + score2);
		    return RecordComparator.FOLLOWS;
		}
		else {
		    return RecordComparator.EQUIVALENT;
		}
    }

}
