package helpers;
import  data.MainMenu;
import data.TileGrid;
import  data.Editor;
import data.Game;
import static helpers.Leveler.*;
import UI.UI.*;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

public class StateManager {
     public static enum GameState{
    	 MAINMENU,GAME,EDITOR
     }
     public static GameState gameState = GameState.MAINMENU;
     public static MainMenu  mainMenu;
     public static Game      game ;
     public static Editor    editor;
     
     public static long nextSecond = System.currentTimeMillis() + 1000;     
     public static int framesInLastSecond = 0;
     public static int framesInCurrentSecond = 0;
     private static boolean start = true;
     static TileGrid map;
     private static int levelMap = 0;
    	      
     public static void update() {
    	 switch (gameState) {
		    case MAINMENU:
		    	if(mainMenu==null)
		    		mainMenu = new MainMenu();
		    	mainMenu.update();
		    	break;
		    	
		    case GAME:
		    	if(game==null || start)	{
		    		map = LoadMap("Map"+Integer.toString(levelMap));
		    		game = new Game(map, levelMap);
		    		start = false;
		    	}
		    	game.update();
		    	if(game.getBackMenu())	{
		    		gameState = GameState.MAINMENU;
		    		game.setBackMenu(false);
		    		start = true;
		    	}
		    	if(game.getNextMap()) {
		    		game.setNextMap(false);
		    		levelMap++;
		    		if(levelMap>9)	levelMap=9;
		    		start = true;
		    	}
		    	if(game.getPriviousMap()) {
		    		game.setPriviousMap(false);
		    		levelMap--;
		    		if(levelMap<0)	levelMap=0;
		    		start = true;
		    	}
		    	if(game.getGameReplay())	{
		    		game.setGameReplay(false);
		    		start = true;
		    	}
		    	
		    	break;
		    	
		    case EDITOR:
		    	if(editor==null)
		    		editor = new Editor();
		    	editor.update();
		    	if(editor.getBackMenu()==true)	{
		    		gameState = GameState.MAINMENU;
		    		editor.setBackMenu(false);
		    	}
		    	break;

    	 }
    	 
    	 long currentTime = System.currentTimeMillis();
    	 if(currentTime > nextSecond)	{
    		 nextSecond += 1000;
    		 framesInLastSecond = framesInCurrentSecond;
    		 framesInCurrentSecond = 0;
    	 }
    	 framesInCurrentSecond ++;
    	 
   	
     }
     public static void setState(GameState newState) {
    	 gameState = newState;
     }
     }
