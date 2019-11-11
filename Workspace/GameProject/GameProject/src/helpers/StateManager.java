package helpers;

import data.MainMenu;
import data.TileGrid;
import data.Information;
import data.Editor;
import data.Game;
import static helpers.Leveler.*;
import UI.UI.*;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

public class StateManager {
	private static SimpleAudioPlayer simpleAudioPlayer = new SimpleAudioPlayer(
			"C:\\Users\\#HarryPotter\\Desktop\\Workspace\\GameProject\\GameProject\\src\\res\\nhacnen.wav");

	public static enum GameState {
		MAINMENU, CONTINUE, GAME, EDITOR, INFORMATION
	}

	public static GameState gameState = GameState.MAINMENU;
	public static MainMenu mainMenu;
	public static Game game, gameSave = null;
	public static Editor editor;
	public static Information information;

	public static long nextSecond = System.currentTimeMillis() + 1000;
	public static int framesInLastSecond = 0;
	public static int framesInCurrentSecond = 0;
	private static boolean start = true;
	static TileGrid map;
	private static int levelMap = 0;

	public static void update() {
		if(mainMenu == null)													// Xu li music
			simpleAudioPlayer.play();
		else if(mainMenu != null && mainMenu.getCheckMusic() && mainMenu.getIsClickMusic()) {			
			simpleAudioPlayer.continueMusic();
			mainMenu.setIsClickMusic(false);
		}
		else if(mainMenu.getCheckMusic() == false && mainMenu.getIsClickMusic()){
			simpleAudioPlayer.pause();
			mainMenu.setIsClickMusic(false);
		}
		switch (gameState) {
		case MAINMENU:
			if (mainMenu == null)
				mainMenu = new MainMenu();
			mainMenu.update();
			break;
		case CONTINUE:
			if (gameSave == null) {				// Neu gameSave == null , thong bao loi 
				mainMenu.ContinueNull();
			} else {							// Nguoc lai thi chuyen sang Game
				setState(GameState.GAME);
			}
			mainMenu.update();
			break;

		case GAME:
			if (mainMenu.getClickContinue()) {	// Gan game = gameSave (Game luu truoc do)
				game = gameSave;
				mainMenu.setClickContinue(false);	
			}
			if (game == null || mainMenu.getClickStart()) {			// Neu click Start game, bat dau game moi
				levelMap = 0;
				map = LoadMap("Map" + Integer.toString(levelMap));
				game = new Game(map, levelMap);
				mainMenu.setClickStart(false);
			}
			game.update();
			if (game.getBackMenu()) {				// Xu li back Menu
				gameState = GameState.MAINMENU;
				game.setBackMenu(false);
				if (game.getSaveGame()) {
					gameSave.PauseGame();
				}
				mainMenu.setClickContinue(false);
				mainMenu.setClickStart(false);
			}
			if (game.getNextMap()) {
				game.setNextMap(false);
				levelMap++;
				if (levelMap > 9)
					levelMap = 9;
				if(game.GameWin()) {				// Game win
					game = new Game(map, levelMap);
					game.setStartedGame(true);		// Khong cho chuyen man choi
				}
				else {
					game = new Game(map, levelMap);
				}
				
				
			}
			if (game.getPriviousMap()) {
				game.setPriviousMap(false);
				levelMap--;
				if (levelMap < 0)
					levelMap = 0;
				game = new Game(map, levelMap);
			}
			if (game.getGameReplay()) {				// Xu li replay game
				game = new Game(map, levelMap);
			}
			if (game.getSaveGame()) {				// Luu Game
				gameSave = game;
			}

			break;

		case EDITOR:						// Sua Map Game
			if (editor == null)
				editor = new Editor();
			editor.update();
			if (editor.getBackMenu() == true) {
				gameState = GameState.MAINMENU;
				editor.setBackMenu(false);
			}
			break;
		case INFORMATION:					// Thong tin game
			if(information== null)	
				information = new Information();
			information.update();
			break;
			
		}

		long currentTime = System.currentTimeMillis();
		if (currentTime > nextSecond) {
			nextSecond += 1000;
			framesInLastSecond = framesInCurrentSecond;
			framesInCurrentSecond = 0;
		}
		framesInCurrentSecond++;

	}

	public static void setState(GameState newState) {
		gameState = newState;
	}

}
