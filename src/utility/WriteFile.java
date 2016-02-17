package utility;

import java.io.*;
import java.lang.*;
import java.util.*;
import utility.Controller;
import songlib.Song;
//@author Xi Chen, Deep Patel
public class WriteFile {
	public ArrayList<Song> songData = new ArrayList<Song>(); 
	private Formatter x;
	public void open(){
		try{
		x = new Formatter("data.txt");
		
		}
		catch(Exception e){
			System.out.println("error");
		}
	}	
	
	public void addSong(Song song){	
	
		x.format("%s%s%s%s",song.name + "-" , song.artist + "-", song.album + "-", song.year + "-");

		
	}
	
	public void close(){
		x.close();
	}
	@SuppressWarnings("resource")
	public void openFile(){
		
		try{
			new Scanner(new File("data.txt"));
		}
		catch(Exception e){
			System.out.println("error");
		}
	}
	
	ArrayList<Song> read() throws FileNotFoundException{
		String token = "";
		String n = "";
		int count = 0;
		ArrayList<Song> songData = new ArrayList<Song>();
	    // for-each loop for calculating heat index of May - October

	    // create Scanner inFile1
	    @SuppressWarnings("resource")
		Scanner inFile1 = new Scanner(new File("data.txt")).useDelimiter("-");

	    // while loop
	    while (inFile1.hasNext()) {
	      // find next line
	      token = inFile1.next();
	      n = n + token + "-";
	      count++;
	      if(count == 4){
	    	  String a[] = new String[n.length()];
	    	  String temp = "";
	    	  int j = 0;
	    	  for(int i = 0; i < n.length(); i++){
	    		if(j == 4){
	    			break;
	    		}
	    		if(n.charAt(i) == '-'){
	    			if(temp.equals("")){
	    				a[j] = "";
	    				temp = "";
	    				j++;
	    			}
	    			else{
	    				a[j] = temp;
	    				temp = "";
	    				j++;
	    			}
	    		}
	    		else{
	    			temp = temp + n.charAt(i);
	    		}
	    	  }
	    	  String name = a[0];
	    	  String art = a[1];
	    	  String alb = a[2];
	    	  String year = a[3];
	    	  Song add = new Song(name,art,alb,year);
	    	  songData.add(add);
	    	  count = 0;
	    	  n = "";
	      }
	    }
	    inFile1.close();
	    
		return songData;
        }
       

	
}	


