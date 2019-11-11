package data;

import static helpers.Artist.QuickLoad;
import static helpers.Artist.*;
import static helpers.Artist.TILE_SIZE;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.Button;
import UI.UI;
import UI.UI.Menu;
import helpers.Clock;
import helpers.SimpleAudioPlayer;
import helpers.StateManager;

import static helpers.SimpleAudioPlayer.*;
import static helpers.Leveler.*;

public class Game {

	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	private UI gameUI;
	private Texture menuBackGround;
	private Enemy[] enemyTypes;
	private Enemy[] enemisBoss;
	private int waveNumber = 1, start;
	private int indexMap;
	private int soLuongQuan = 3;
	private float thoiGian = 3.15f;
	private boolean nextMap = false, priviousMap = false, startGame = false, saveGame = false, replayAnswer = false,
			back_menu = false, replay = false, isDestroyCancel = false, GameStarted = false, addX1X2 = false,
			X1Enable = true, isClickBackMenu = false;
	private int x, y, multiplier = 1;

	public Game(TileGrid grid, int indexMap) {
		this.grid = LoadMap("Map"+Integer.toString(indexMap));
		enemyTypes = new Enemy[3];
		enemisBoss = new Enemy[1];
		enemyTypes[0] = new TankerEnemy(grid.getXYStart().x, grid.getXYStart().y, grid);
		enemyTypes[1] = new NormalEnemy(grid.getXYStart().x, grid.getXYStart().y, grid);
		enemyTypes[2] = new SmallerEnemy(grid.getXYStart().x, grid.getXYStart().y, grid);
		enemisBoss[0] = new BossEnemy(grid.getXYStart().x, grid.getXYStart().y, grid);
		waveManager = new WaveManager(enemyTypes, (int) thoiGian, soLuongQuan); // tao danh sach enemy
		player = new Player(grid, waveManager);
		player.setup();
		this.menuBackGround = QuickLoad("menu_background_2");
		this.indexMap = indexMap;
		setupUI();
	}

	private void setupUI() {
		gameUI = new UI();
		gameUI.addButton("TowerNormal", "TowerNormalFull", 815, 50, 50, 50); // Button Tower
		gameUI.addButton("TowerSniper", "TowerSniperFull", 875, 50, 50, 50);
		gameUI.addButton("TowerMachine", "TowerMachineFull", 935, 50, 50, 50);

		gameUI.addButton("MapAndWave", "MapWave", 805, 150, 350, 60);
		gameUI.addButton("Lives", "Lives", 805, 210, 100, 40);
		gameUI.addButton("Cash", "Cash", 905, 210, 100, 40);

		gameUI.addButton("Start", "start_off", 820, 280, 80, 60); // Button Start
		gameUI.addButton("Pause", "pause_on", 910, 280, 80, 60); // Button Pause

		gameUI.addButton("BackMap", "back", 820, 420, 70, 70); // Button Back Map
		gameUI.addButton("NextMap", "next", 920, 420, 70, 70); // Button next Map

		gameUI.addButton("SaveGame", "save", 820, 495, 60, 60); // Button save Game
		gameUI.addButton("Replay", "replay", 880, 495, 120, 60); // Button replay game

		gameUI.addButton("Menu", "HomeMenu", 820, 560, 300, 50); // Button back Main menu
	}

	private void updateUI() {
		gameUI.draw();
		gameUI.drawStringSmall(815, 110, "Normal");
		gameUI.drawStringSmall(875, 110, "Sniper");
		gameUI.drawStringSmall(935, 110, "Machine");

		gameUI.drawString(840, 160, "MAP " + (indexMap + 1) + ":  " + waveNumber + " / 10");
		gameUI.drawStringSmall(840, 215, "" + player.Lives + "/" + player.livesCount);
		gameUI.drawStringSmall(940, 215, "" + player.Cash);

		gameUI.drawStringSmall(0, 0, StateManager.framesInLastSecond + " fps");

		if (Mouse.next()) {
			boolean mouuseClicked = Mouse.isButtonDown(0);
			if (mouuseClicked) {
				int MouseX = Mouse.getX() / TILE_SIZE, MouseY = (HEIGHT - Mouse.getY() - 1) / TILE_SIZE;
				if (MouseX < 20 && MouseY < 15) {
					x = MouseX;
					y = MouseY;
					Tile tile = grid.getTile(x, y);
					System.out.println(x + " " + y + " " + tile.getType());
					if (tile.getType() == TileType.Grass && tile.getOcccupied() == true) {
						isDestroyCancel = true;
					}
				} else if (gameUI.isButtonClicked("TowerNormal") && Mouse.getEventButtonState()) {
					player.pickTower(new TowerSpecies(TowerType.TowerNormal, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
					GameStarted = true;
				} else if (gameUI.isButtonClicked("TowerSniper") && Mouse.getEventButtonState()) {
					player.pickTower(new TowerSpecies(TowerType.TowerSniper, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
					GameStarted = true;
				} else if (gameUI.isButtonClicked("TowerMachine") && Mouse.getEventButtonState()) {
					player.pickTower(new TowerSpecies(TowerType.TowerMachine, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
					GameStarted = true;
				} else if (gameUI.isButtonClicked("Menu")) {			//Back Menu
					isClickBackMenu = true;
				} else if (gameUI.isButtonClicked("Start")) {
					StartGame();
					start = 1; // Tro choi bat dau
					startGame = true;
					GameStarted = true;
				} else if (gameUI.isButtonClicked("Pause") && startGame == true) {
					PauseGame();
					startGame = false;
				} else if (GameStarted == false && gameUI.isButtonClicked("NextMap") && waveNumber == 1) {
					nextMap = true;
				} else if (GameStarted == false && gameUI.isButtonClicked("BackMap") && waveNumber == 1) {
					priviousMap = true;
				} else if (gameUI.isButtonClicked("SaveGame")) {
					saveGame = true;
				} else if (gameUI.isButtonClicked("Replay")) {
					replayAnswer = true;
				} else if (addX1X2 == true && X1Enable == false && gameUI.isButtonClicked("X1")) {
					X1Enable = true;
					gameUI.removeButton("X1");
					gameUI.removeButton("X2Enable");
					gameUI.addButton("X1", "X1Enable", 840, 440, 40, 40);
					gameUI.addButton("X2", "X2", 920, 440, 40, 40);
					multiplier = 1;
					if (startGame == true) {
						StartGame();
					}
				} else if (addX1X2 == true && X1Enable == true && gameUI.isButtonClicked("X2")) {
					X1Enable = false;
					gameUI.removeButton("X1Enable");
					gameUI.removeButton("X2");
					gameUI.addButton("X1", "X1", 840, 440, 40, 40);
					gameUI.addButton("X2", "X2Enable", 920, 440, 40, 40);
					multiplier = 2;
					if (startGame) {
						StartGame();
					}
				}
			}
		}
	}

	public void update() {
		DrawQuadTex(menuBackGround, 800, 0, 200, 600);

		grid.draw();

		if (GameStarted == true) {
			gameUI.removeButton("NextMap");
			gameUI.removeButton("BackMap");
			if (addX1X2 == false) {
				gameUI.addButton("X1", "X1Enable", 840, 440, 40, 40);
				gameUI.addButton("X2", "X2", 920, 440, 40, 40);
				addX1X2 = true;
			}
		}
		if (startGame) {
			gameUI.removeButton("Start");
			gameUI.removeButton("Pause");
			gameUI.addButton("Start", "start_on", 820, 280, 80, 60);
			gameUI.addButton("Pause", "pause_off", 910, 280, 80, 60);
		} else {
			gameUI.removeButton("Start");
			gameUI.removeButton("Pause");
			gameUI.addButton("Start", "start_off", 820, 280, 80, 60);
			gameUI.addButton("Pause", "pause_on", 910, 280, 80, 60);
		}

		updateUI();
		player.update();
		if (start == 0) { // Dung tro choi khi bat dau
			Clock.setMultiplier(0);
		}
		if (waveManager.isComplete() == false) {
			waveManager.update(); // Neu waveMange chua hoan thanh thi Update
		} else {
			waveNumber++;
			if (waveNumber == 5) {
				waveManager = new WaveManager(enemisBoss, 1, 1); // Man 5 co 1 Boss Enemy
			} else if (waveNumber == 10) {
				waveManager = new WaveManager(enemisBoss, 2, 3); // Man 10 co 3 Boss Enenmy
			} else {
				soLuongQuan = soLuongQuan + 2; // So luong quan tang theo moi man choi
				thoiGian -= 0.15; // Thoi gian giua 2 quan linh giam dan theo thoi gian
				System.out.println(thoiGian);
				waveManager = new WaveManager(enemyTypes, (int) thoiGian, soLuongQuan);
			}
			Clock.setMultiplier(0); // Dung tro choi khi qua moi level
			startGame = false;
			player.setWaveManager(waveManager);
		}

		if (GameLose() || GameWin()) { // Xu li Game Lose hoac Game Win
			if (GameLose()) {
				PlayGameLose();
			}
			if (GameWin()) {
				PlayGameWin();
			}
		}
		if (replayAnswer) { // Xu li neu chon Replay
			ReplayGame();
		}
		if (isDestroyCancel) {
			DestroyTower();
		}
		if(isClickBackMenu)	{
			BackMenu();
		}
	}

	public void PlayGameLose() { // Giao dien Game Over
		Clock.setMultiplier(0);
		gameUI.addButton("GameOver", "lose", 240, 230, 800, 200);
		gameUI.addButton("Replay", "replay", 275, 400, 140, 50);
		gameUI.addButton("BackMenu", "HomeMenu", 550, 400, 200, 50);

		if (Mouse.isButtonDown(0)) {
			if (gameUI.isButtonClicked("Replay")) {
				setGameReplay(true);
			}
			if (gameUI.isButtonClicked("BackMenu")) {
				setBackMenu(true);
			}
		}
	}
	
	public void DestroyTower()	{
		Tower towerTemp = player.getTemTower();
		Tower tower = player.findTower(x, y);
		if (grid.getTile(x, y).getType() == TileType.Grass && towerTemp == null)
			gameUI.drawString(810, 350,
					player.findTower(x, y).getTowerType().getTowerType() + "(" + x + "; " + y + ")");

		gameUI.addButton("Destroy", "destroy", 820, 380, 70, 50);
		gameUI.addButton("Cancel", "cancel", 920, 380, 70, 50);

		if (tower == null) {
			gameUI.removeButton("Destroy");
			gameUI.removeButton("Cancel");
		}
		if (Mouse.isButtonDown(0) && tower != null) {
			if (gameUI.isButtonClicked("Destroy") && Mouse.getEventButtonState()) {
				player.removeTower(x, y);
				grid.getTile(x, y).setOccupied(false);
				System.out.println("Destroy");
				gameUI.removeButton("Destroy");
				gameUI.removeButton("Cancel");
				isDestroyCancel = false;
			} else if (gameUI.isButtonClicked("Cancel") && Mouse.getEventButtonState()) {
				System.out.println("Cancel");
				gameUI.removeButton("Destroy");
				gameUI.removeButton("Cancel");
				isDestroyCancel = false;
			}
		}
	}

	public void PlayGameWin() { // Giao dien Game Win
		Clock.setMultiplier(0);
		gameUI.addButton("GameWin", "victory", 240, 230, 800, 200);
		gameUI.addButton("Replay", "replay", 275, 400, 140, 50);
		gameUI.addButton("nextMapWin", "nextMap", 550, 400, 200, 50);

		if (Mouse.isButtonDown(0)) {
			if (gameUI.isButtonClicked("Replay")) {
				setGameReplay(true);
			}
			if (gameUI.isButtonClicked("nextMapWin")) {
				setNextMap(true);
			}
		}
	}
	
	public void ReplayGame()	{
		PauseGame();
		gameUI.addButton("Answer", "answer", 200, 150);
		gameUI.addButton("Yes", "yes", 200, 300, 150, 60);
		gameUI.addButton("No", "no", 500, 300, 150, 60);
		if (Mouse.isButtonDown(0)) {
			if (gameUI.isButtonClicked("Yes")) {
				setGameReplay(true);
				replayAnswer = false;
			}
			if (gameUI.isButtonClicked("No")) {
				replayAnswer = false;
				gameUI.removeButton("Answer");
				gameUI.removeButton("Yes");
				gameUI.removeButton("No");
				StartGame();
			}
		}
	}
	
	public void BackMenu()	{
		PauseGame();
		gameUI.addButton("Answer", "answerBackMenu", 200, 150);
		gameUI.addButton("Yes", "yes", 200, 300, 150, 60);
		gameUI.addButton("No", "no", 500, 300, 150, 60);
		if (Mouse.isButtonDown(0)) {
			if (gameUI.isButtonClicked("Yes")) {
				back_menu = true;
			}
			if (gameUI.isButtonClicked("No")) {
				isClickBackMenu = false;
				gameUI.removeButton("Answer");
				gameUI.removeButton("Yes");
				gameUI.removeButton("No");
				if(startGame)	StartGame();
			}
		}
	}

	public boolean GameLose() { // Tra ve gia tri true neu Game Lose, false truong hop con lai
		if (player.getGameLose())
			return true;
		return false;
	}

	public boolean GameWin() { // Tra ve true neu Game Win, false trong truong hopw con lai
		if (waveNumber > player.livesCount) // player.livesCount
			return true;
		return false;
	}

	public boolean getBackMenu() { // Neu Click back menu, tra ve true; false trong truong hop con lai
		return back_menu;
	}

	public void setBackMenu(boolean back_menu) {
		this.back_menu = back_menu;
	}

	public boolean getNextMap() { // = nextMap
		return nextMap;
	}

	public boolean getPriviousMap() {
		return priviousMap;
	}

	public void setNextMap(boolean nextMap) {
		this.nextMap = nextMap;
	}

	public void setPriviousMap(boolean priviousMap) {
		this.priviousMap = priviousMap;
	}

	public boolean getGameReplay() {
		return replay;
	}

	public void setGameReplay(boolean replay) {
		this.replay = replay;
	}

	public boolean getSaveGame() {
		return saveGame;
	}

	public void setSaveGame(boolean setGame) {
		this.saveGame = saveGame;
	}

	public void PauseGame() {
		Clock.setMultiplier(0);
	}

	public void StartGame() {
		Clock.setMultiplier(multiplier);
	}
	
	public boolean getStartedGame() {
		return GameStarted;
	}
	public void setStartedGame(boolean GameStarted)	{
		this.GameStarted = GameStarted;
	}

}
