package utility;

// @author Xi Chen, Deep Patel

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Pair;
import songlib.Song;

public class Controller {
		
	class Listener implements ChangeListener<Song>{

		public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue){
			
			showDetail();
			//edit();
			
		}
		
	}
	
	@FXML Button edit;
	@FXML Button detail;
	@FXML Button add;
	@FXML Button delete;
	@FXML GridPane gridpane;
	@FXML
	ListView<Song> songList = new ListView<>();
	Listener listener = new Listener();
	
	ObservableList<Song> obsList;
	ArrayList<Song> songData = new ArrayList<Song>();
	
		public void start(Stage mainStage) throws FileNotFoundException{
			
			songList.setEditable(true);
			mainStage.setTitle("Song Library");
			WriteFile a = new WriteFile();
			a.openFile();
			songData = a.read();
			
			Collections.sort(songData,compareToSong);
			obsList
			=  FXCollections.observableList(songData);
			songList.setItems(obsList);
			mainStage.setOnHiding(new EventHandler<WindowEvent>() {

	            @Override
	            public void handle(WindowEvent event) {
	                Platform.runLater(new Runnable() {

	                    @Override
	                    public void run() {
	                    	WriteFile a = new WriteFile();
	                        a.open();
	                        for(int i = 0; i < songData.size(); i++){
	                        	Song add = songData.get(i);
	                        	a.addSong(add);
	                        	
	                        }
	                        a.close();
	                        System.exit(0);
	                    }
	                });
	            }
	        });
		
		      songList.getSelectionModel().select(0);
		      showDetail();
		      
		     songList
		      .getSelectionModel()
		      .selectedItemProperty()
		      .addListener(
		    		 listener);

		}
		
		Comparator<Song> compareToSong = new Comparator<Song>() {
	        @Override
	        public int compare(Song song1, Song song2) {
	            return song1.getName().compareToIgnoreCase(song2.getName());
	        }
	    };
	  
	    
	    
	    
	    public void functions(ActionEvent e ) {
			Button b = (Button)e.getSource();
			if(b==edit) {
				
				edit();
				
			}else if(b==add){
							
				add();
			
			}else{
				
				delete();
			}

			}
			
			
	    
	   void showDetail(){	
		   if(songList.getSelectionModel().getSelectedIndex()<0){
	    		gridpane.getChildren().clear();
		   }else{
	    	gridpane.getChildren().clear();
	    	gridpane.add(new Label("Name:"), 0, 0);
	    	gridpane.add(new Label(songList.getSelectionModel().getSelectedItem().getName()), 1, 0);
	    	gridpane.add(new Label("Artist:"), 0, 1);
	    	gridpane.add(new Label(songList.getSelectionModel().getSelectedItem().getArtist()), 1, 1);
	    	gridpane.add(new Label("Album:"), 0, 2);
	    	gridpane.add(new Label(songList.getSelectionModel().getSelectedItem().getAlbum()), 1, 2);
	    	gridpane.add(new Label("Year"), 0, 3);
	    	gridpane.add(new Label(songList.getSelectionModel().getSelectedItem().getYear()), 1, 3);
	    }
	   }
	   
	   
	   
	    private void edit(){
	    	
	    	if(songList.getSelectionModel().getSelectedIndex()<0){
	    		Alert alert1 = new Alert(AlertType.ERROR);
	    		alert1.setContentText("No Song to be Edited");
	    		alert1.showAndWait();
	    	}else{
	    	
	    	Dialog dialog = new Dialog();
			dialog.setTitle("Edit Song");
			dialog.setHeaderText("Please Enter Song Information");


			ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
			ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
			
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));

			TextField name = new TextField(songList.getSelectionModel().getSelectedItem().getName());
			name.setPromptText("Song Name");
			TextField artist = new TextField(songList.getSelectionModel().getSelectedItem().getArtist());
			artist.setPromptText("Song Artist");
			TextField album = new TextField(songList.getSelectionModel().getSelectedItem().getAlbum());
			album.setPromptText("Album");
			TextField year = new TextField(songList.getSelectionModel().getSelectedItem().getYear());
			year.setPromptText("Song Year");
 
			grid.add(new Label("Song Name:"), 0, 0);
			grid.add(name, 1, 0);
			grid.add(new Label("Song Artist:"), 0, 1);
			grid.add(artist, 1, 1);
			grid.add(new Label("Song Album:"), 0, 2);
			grid.add(album, 1, 2);
			grid.add(new Label("Song Year:"), 0, 3);
			grid.add(year, 1, 3);

			
			dialog.getDialogPane().setContent(grid);
			Optional<ButtonType> result = dialog.showAndWait();
			if(result.get().equals(cancelButton)){
				return;
			}
			String inputName = name.getText();
			String inputArtist= artist.getText();
			String inputAlbum = album.getText();
			String inputYear = year.getText();
			
			int a = songList.getSelectionModel().getSelectedIndex();
			
			if(inputName.equals("") || inputArtist.equals("") ){
	    		Alert alert1 = new Alert(AlertType.ERROR);
	    		alert1.setContentText("Song Name and Artist cannot be empty");
	    		alert1.showAndWait();
			}
			else{
			
			boolean in = false;
			String n1 = inputName.toLowerCase();
			String a1 = inputArtist.toLowerCase();
			for(int i = 0; i < songData.size(); i++){
				if(i == a){
					
				}
				else{
				String a2 = songData.get(i).artist.toLowerCase();
				String n2 = songData.get(i).name.toLowerCase();
				
				if(a2.equals(a1) && n1.equals(n2)){
					in = true;
				}
				}
			}
				if(in == true){
					Alert alert1 = new Alert(AlertType.ERROR);
					alert1.setTitle("Error");
					alert1.setHeaderText("Duplicate Song");
					alert1.setContentText("You will be returned to the Song Library!");
					alert1.showAndWait();
				}
				else{
			
					songList.getSelectionModel().getSelectedItem().setName(inputName);
					songList.getSelectionModel().getSelectedItem().setArtist(inputArtist);
					songList.getSelectionModel().getSelectedItem().setAlbum(inputAlbum);
					songList.getSelectionModel().getSelectedItem().setYear(inputYear);
					showDetail();

					songList.getSelectionModel().selectedItemProperty().removeListener(listener);
					obsList.sort(compareToSong);
					songList.getSelectionModel().selectedItemProperty().addListener(listener);
			}
			}
			}
	    }
	    
	    
	    
	    private void delete(){
	    	if(songList.getSelectionModel().getSelectedIndex()<0){
	    		Alert alert1 = new Alert(AlertType.ERROR);
	    		alert1.setContentText("No Song to be Deleteed");
	    		alert1.showAndWait();
	    	}else{
	    		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
	    		
				dialog.setTitle("Delete Song");
				dialog.setHeaderText("Are You Sure You Want to Delete This Song??");
	    		
				ButtonType okButton = new ButtonType("YES", ButtonData.OK_DONE);
				ButtonType cancelButton = new ButtonType("NO", ButtonData.CANCEL_CLOSE);
				dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
				Optional<ButtonType> result = dialog.showAndWait();
				
				if(result.get()==okButton){
					if(songList.getSelectionModel().getSelectedIndex()==0){
						obsList.remove(0);
				    	songList.getSelectionModel().select(0);
						
					}else{
	    	obsList.remove(songList.getSelectionModel().getSelectedIndex());
	    	songList.getSelectionModel().select(songList.getSelectionModel().getSelectedIndex()+1);
				}
	    	}
	    	}
	    }
	    
	    
	    	
	    private void add(){
	    	Dialog<ButtonType> dialog = new Dialog<ButtonType>();
			dialog.setTitle("Add Song");
			dialog.setHeaderText("Please Enter Song Information");


			ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
			ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));

			TextField name = new TextField();
			name.setPromptText("Song Name");
			TextField artist = new TextField();
			artist.setPromptText("Song Artist");
			TextField album = new TextField();
			album.setPromptText("Album");
			TextField year = new TextField();
			year.setPromptText("Song Year");

			grid.add(new Label("Song Name:"), 0, 0);
			grid.add(name, 1, 0);
			grid.add(new Label("Song Artist:"), 0, 1);
			grid.add(artist, 1, 1);
			grid.add(new Label("Song Album:"), 0, 2);
			grid.add(album, 1, 2);
			grid.add(new Label("Song Year:"), 0, 3);
			grid.add(year, 1, 3);


			dialog.getDialogPane().setContent(grid);

			// Request focus on the username field by default.
			//Platform.runLater(() -> name.requestFocus());
			
			Optional<ButtonType> result=dialog.showAndWait();
		
			if(result.get()==okButton){
				
			
			String nam = name.getText();
			String art = artist.getText();
			String alb = album.getText();
			String yea = year.getText();
			
			if(nam.equals("") || art.equals("") ){
	    		Alert alert1 = new Alert(AlertType.ERROR);
	    		alert1.setContentText("Please at least Enter Song Name and Artist");
	    		alert1.showAndWait();
			}else{
			Song toAdd = new Song(nam,art,alb,yea);
	    	
			boolean in = false;
		
				for(int i = 0; i < songData.size(); i++){
					String a1 = songData.get(i).artist.toLowerCase();
					String n1 = songData.get(i).name.toLowerCase();
					String a2 = toAdd.artist.toLowerCase();
					String n2 = toAdd.name.toLowerCase();
					
					if(a2.equals(a1) && n1.equals(n2)){
						in = true;
					}
				}
					if(in == true){
						Alert alert1 = new Alert(AlertType.ERROR);
						alert1.setTitle("Error");
						alert1.setHeaderText("Duplicate Song");
						alert1.setContentText("You will be returned to the Song Library!");
						alert1.showAndWait();
					}
					else{
						
						obsList.add(toAdd);
						int e = 0;
						for(int i = 0; i < obsList.size(); i++){
							if(toAdd.equals(obsList.get(i))){
								e = i;
							}
						}
						songList.getSelectionModel().select(e);
						songList.getSelectionModel().selectedItemProperty().removeListener(listener);
						obsList.sort(compareToSong);
						songList.getSelectionModel().selectedItemProperty().addListener(listener);
						
					}
				
			}
			} 
			    	
			}
}

		
		
		
	

