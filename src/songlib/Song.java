package songlib;
//@author Xi Chen, Deep Patel
public class Song {

	public String name;
	public String artist;
	public String album;
	public String year;
	
public Song (String songName , String songArtist, String songAlbum , String songYear){
	
	name = songName;
	artist = songArtist;
	album = songAlbum;
	year = songYear;

}

public Song (String songName , String songArtist, String songAlbum ){
	
	name = songName;
	artist = songArtist;
	album = songAlbum;

}

public Song (String songName , String songArtist){
	
	name = songName;
	artist = songArtist;

}

public String getName(){
	
	return name;
}

public void setName(String name){
	
	this.name=name;
	
}

public String getAlbum(){
	
	return album;
}

public void setAlbum(String album){
	
	this.album=album;
	
}

public String getArtist(){
	
	return artist;
}

public void setArtist(String artist){
	
	this.artist=artist;
	
}

public String getYear(){
	
	return year;
}

public void setYear(String year){
	
	this.year=year;
	
}

public String toString(){
	
	return name;
	//return "Song Name: "+name + "\nArtist: " + artist + "\nalbum: " +album + "\nyear: " + year;
}


}


	
