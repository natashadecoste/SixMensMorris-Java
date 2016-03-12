package application;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.fxml.*;
import javafx.event.*;
import javafx.scene.effect.*;
import javafx.scene.control.Button;

public class Controller {
	public static int N; // N Men's Morris
	private static boolean gameplay; //state of whether the game is in play or not
	private int gameTile;
	static Model laModel = new Model();
	static View leView = new View();
	
	

	@FXML 
	private String colourChoice;
	@FXML 
	private Label titleScreenLabel = new Label("Red");
	@FXML
	private Label redDiscs = new Label();
	@FXML
	private Label blueDiscs = new Label();


		
	
	public void getPlayerColour(ActionEvent event){
		colourChoice = ((Button)event.getSource()).getText();	//will give us either "Red" or "Blue" BASED ON THE HOMESCREEN BUTTONS ON THE MAIN MENU
		titleScreenLabel.setText(((Button)event.getSource()).getText());
		laModel.assignPlayer(colourChoice);									//THIS IS SO THE MODEL KNOWS WITH BOOLEANS WHICH PLAYER IS WHICH COLOUR
		
	}
	
	public void changeDiscColourBlue(){
			laModel.changePlayerColourBlue();	
			//System.out.println("change to blue");
			titleScreenLabel.setText("Blue");
	}
	
	public void changeDiscColourRed(){
			laModel.changePlayerColourRed();
			//System.out.println("change to red");
			titleScreenLabel.setText("Red");
	}

	public void startNewGame(ActionEvent event){
		//if there is a state in model, call that
		gameplay = true;
		int N = 6;		//so that we can pass the # of mens morris into the VIew
		redDiscs.setText(Integer.toString(N));		//this will show the players how many discs they have left to place
		blueDiscs.setText(Integer.toString(N));
		gameTile = ((N/3)*2)+1;	//array size needed to draw the gameboard for example 6 mens morris requires an array of 5x5
		leView.newGame(gameTile); //array size # of mens morris passed into the view
		laModel.createBoard(N);
		// laModel.showBoards();		//for debugging
		laModel.getfirstPlayer();
		if (laModel.getPlayerColour().equals("Red")){
			changeDiscColourRed();
		}
		else {
			changeDiscColourBlue();
		}

	}
	
	
	public void loadGame(ActionEvent event){
		gameplay = false;
		gameTile = ((6/3)*2)+1;
		laModel.createBoard(N);
		leView.loadGame(gameTile);
	}
	
	//for when we implement the reset buttons
/*
	public void resetSceneLoad(){
		changeDiscColourRed();
		laModel.reset();
		leView.resetLoad();
	}
	
	public void resetSceneGame(){
		changeDiscColourRed();
		laModel.reset();
		leView.resetGame();
	}*/

	public static void inputClick(int x, int y){
		//System.out.println("mouse clicked at circle: "+y+" "+x);			/*JUST A TEST, BUT THE X AND Y WILL LET YOU CHECK THE MODEL'S ARRAY COORDINATES*/
		leView.draw(laModel.getPlayerColour(),x,y);
		laModel.placeDisc(laModel.getPlayerColour(),y, x);
		if(gameplay){
			//if we are playing a game instead of on load screen
			laModel.switchPlayer();
		}
		//laModel.showBoards();
		
	}
	
	public void valid(){
		//call the model class, to see if it is valid
		//if it isnt valid then return a string array of the errors so i can dialog box them as a pop up to the user
		//if it IS valid then just return null
		String [] errors = laModel.invalidPoints();
		if (errors.length == 0){
			//start game
			System.out.println("here");
			
		}
		else{ 
			leView.invalid(errors);

		}
	}

	public void exitGame(ActionEvent event){
		System.exit(0);
	}
	

}