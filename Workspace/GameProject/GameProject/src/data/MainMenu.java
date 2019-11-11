package data;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

import static helpers.Artist.*;
public class MainMenu {
       private Texture background;
       private UI menuUI;
       public MainMenu() {
    	   background = QuickLoad("mainmenu");
    	   menuUI = new UI();
    	   menuUI.addButton("Play", "playButton", WIDTH/2-128, (int)(HEIGHT*0.40f),400,80);
    	   menuUI.addButton("Editor","editorButton", WIDTH/2-128, (int)(HEIGHT*0.55f),400,80);
    	   menuUI.addButton("Quit", "quitButton", WIDTH/2-128, (int)(HEIGHT*0.7),250,80);
    	   
       }
       //Check if a button is clicked by the user, and if so do an action 
       private void updateButtons() {
    	   if(Mouse.isButtonDown(0)) {
    		   if(menuUI.isButtonClicked("Play"))
    			  StateManager.setState(GameState.GAME);
    		   if(menuUI.isButtonClicked("Editor"))
    			   StateManager.setState(GameState.EDITOR);
    		   if(menuUI.isButtonClicked("Quit"))
    			   System.exit(0);
    	   }
       }
       
       public void update() {
    	   DrawQuadTex(background, 0, 0, 1100, 1030);
    	   menuUI.draw();
    	   updateButtons();
       }
	  
}
