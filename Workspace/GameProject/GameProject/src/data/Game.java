package data;

import static helpers.Artist.*;

import javax.sound.midi.Soundbank;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.Clock;
import helpers.StateManager;

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
	private float thoiGian = 3.15f; // Thoi gian quan linh xuat hien
	private boolean nextMap = false, priviousMap = false, startGame = false, saveGame = false, replayAnswer = false,
			back_menu = false, replay = false, isDestroyCancel = false, GameStarted = false, addX1X2 = false,
			X1Enable = true, isClickBackMenu = false, isAudio = true;
	private int x, y, multiplier = 1;
	private String name = "";

	public Game(TileGrid grid, int indexMap) {
		this.grid = grid;
		enemyTypes = new Enemy[3];
		enemisBoss = new Enemy[1];
		enemyTypes[0] = new TankerEnemy(grid.getXYStart().x, grid.getXYStart().y, grid);
		enemyTypes[1] = new NormalEnemy(grid.getXYStart().x, grid.getXYStart().y, grid);
		enemyTypes[2] = new SmallerEnemy(grid.getXYStart().x, grid.getXYStart().y, grid);
		enemisBoss[0] = new BossEnemy(grid.getXYStart().x, grid.getXYStart().y, grid);

		System.out.println(grid.getXYStart().x + " " + grid.getXYStart().y);

		waveManager = new WaveManager(enemyTypes, (int) thoiGian, soLuongQuan); // tao danh sach enemy
		player = new Player(grid, waveManager);
		player.setup();
		this.menuBackGround = QuickLoad("backGround");
		this.indexMap = indexMap;
		setupUI();
	}

	private void setupUI() {
		gameUI = new UI();
		gameUI.addButton("TowerNormal", "TowerNormalFull", 850, 100, 60, 60); // Button Tower
		gameUI.addButton("TowerSniper", "TowerSniperFull", 940, 100, 60, 60);
		gameUI.addButton("TowerMachine", "TowerMachineFull", 1030, 100, 60, 60);

		gameUI.addButton("Map", "MapWave", 850, 20, 460, 80);
		gameUI.addButton("Lives", "Lives", 830, 210, 150, 60);
		gameUI.addButton("Cash", "Cash", 1010, 210, 150, 60);
		gameUI.addButton("Wave", "MapWave", 900, 280);

//		gameUI.addButton("SaveGame", "save", 820, 495, 60, 60); // Button save Game
//		gameUI.addButton("Replay", "replay", 880, 495, 90, 60); // Button replay game
//		gameUI.addButton("Start", "start_off", 980, 495, 80, 60); // Button Start
//		gameUI.addButton("Pause", "pause_on", 1070, 495, 80, 60); // Button Pause

		gameUI.addButton("Start", "start_off", 820, 495, 80, 60); // Button save Game
		gameUI.addButton("Pause", "pause_on", 900, 495, 80, 60); // Button replay game
		gameUI.addButton("Replay", "replay", 980, 495, 90, 60); // Button Start
		gameUI.addButton("SaveGame", "save", 1070, 495, 60, 60); // Button Pause
		
		gameUI.addButton("BackMap", "back", 880, 350, 120, 120); // Button Back Map
		gameUI.addButton("NextMap", "next", 980, 345, 120, 120); // Button next Map

		gameUI.addButton("Menu", "HomeMenu", 890, 550, 300, 50); // Button back Main menu
	}

	private void updateUI() {
		gameUI.draw();
		gameUI.drawStringSmall(860, 160, "Normal");
		gameUI.drawStringSmall(950, 160, "Sniper");
		gameUI.drawStringSmall(1040, 160, "Machine");

		gameUI.drawStringSmall(870, 180, "$25");
		gameUI.drawStringSmall(960, 180, "$25");
		gameUI.drawStringSmall(1050, 180, "$25");

		gameUI.drawStringBig(920, 30, "MAP  " + (indexMap + 1));
		gameUI.drawString(930, 290, "Wave " + Integer.toString(waveNumber) + " /10");
		gameUI.drawString(890, 220, "" + player.Lives + " / " + player.livesCount);
		gameUI.drawString(1060, 220, "" + player.Cash);

		gameUI.drawStringSmall(0, 0, StateManager.framesInLastSecond + " fps");

		if (Mouse.next() && GameWin() == false && GameLose() == false) {
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
				} else if (isClickBackMenu == false && replay == false && gameUI.isButtonClicked("TowerNormal")
						&& Mouse.getEventButtonState()) {
					player.pickTower(new TowerNormal(grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList()));
					GameStarted = true;
				} else if (isClickBackMenu == false && replay == false && gameUI.isButtonClicked("TowerSniper")
						&& Mouse.getEventButtonState()) {
					player.pickTower(new TowerSniper(grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList()));
					GameStarted = true;
				} else if (isClickBackMenu == false && replay == false && gameUI.isButtonClicked("TowerMachine")
						&& Mouse.getEventButtonState()) {
					player.pickTower(new TowerMachine(grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList()));
					GameStarted = true;
				} else if (gameUI.isButtonClicked("Menu")) { // Back Menu
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
					gameUI.addButton("X1", "X1Enable", 840, 430, 50, 50);
					gameUI.addButton("X2", "X2", 920, 430, 50, 50);
					multiplier = 1;
					if (startGame == true) {
						StartGame();
					}
				} else if (addX1X2 == true && X1Enable == true && gameUI.isButtonClicked("X2")) {
					X1Enable = false;
					gameUI.removeButton("X1Enable");
					gameUI.removeButton("X2");
					gameUI.addButton("X1", "X1", 920, 430, 50, 50);
					gameUI.addButton("X2", "X2Enable", 1000, 430, 50, 50);
					multiplier = 2;
					if (startGame) {
						StartGame();
					}
				}
			}
		}
	}

	public void update() {
		DrawQuadTex(menuBackGround, 800, 0, 360, 1150);
		
		grid.draw();
		if (GameStarted == true) { // Neu game da bat dau thi khong chuyen Map duoc
			gameUI.removeButton("NextMap");
			gameUI.removeButton("BackMap");
			if (addX1X2 == false) {
				gameUI.addButton("X1", "X1Enable", 920, 430, 50, 50);
				gameUI.addButton("X2", "X2", 1000, 430, 50, 50);
				addX1X2 = true;
			}
		}
		if (startGame) {
			gameUI.removeButton("Start");
			gameUI.removeButton("Pause");
			gameUI.addButton("Start", "start_on",820, 495, 80, 60); // Button Start
			gameUI.addButton("Pause", "pause_off", 900, 495, 80, 60); // Button Pause
		} else {
			gameUI.removeButton("Start");
			gameUI.removeButton("Pause");
			gameUI.addButton("Start", "start_off", 820, 495, 80, 60); // Button Start
			gameUI.addButton("Pause", "pause_on", 900, 495, 80, 60); // Button Pause
		}

		updateUI();
		if (isClickBackMenu) {
			BackMenu();
		}
		if (replayAnswer) { // Xu li neu chon Replay
			ReplayGame();
		}
		if (isClickBackMenu == false && replayAnswer == false && GameWin() == false && GameLose() == false) {
			player.update();
		}

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
			waveManager.setIsAudio(isAudio);
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

		if (isDestroyCancel) {
			DestroyTower();
		}
		
	}

	public void PlayGameLose() { // Giao dien Game Over
		Clock.setMultiplier(0);
		gameUI.addButton("GameOver", "lose", 150, 0, 1000, 600);
		gameUI.addButton("ReplayLose", "replay", 275, 400, 140, 50);
		gameUI.addButton("BackMenu", "HomeMenu", 550, 400, 200, 50);

		if (Mouse.isButtonDown(0)) {
			if (gameUI.isButtonClicked("ReplayLose")) {
				setGameReplay(true);
			}
			if (gameUI.isButtonClicked("BackMenu")) {
				setBackMenu(true);
			}
		}
	}

	public void DestroyTower() {
		Tower tower, towerTemp;
		towerTemp = player.getTempTower();
		tower = player.findTower(x, y);
		if (grid.getTile(x, y).getType() == TileType.Grass && tower != null && towerTemp == null) {
			gameUI.drawString(810, 350, player.findTower(x, y).getTowerType().getTowerType());
			gameUI.drawString(810, 370, " ( " + x + "; " + y + " ) ");

		}

		gameUI.addButton("Destroy", "destroy", 980, 350, 70, 50);
		gameUI.addButton("Cancel", "cancel", 1060, 350, 70, 50);

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
		if (player.Lives == player.livesCount)
			gameUI.addButton("GameWin", "victory3start", 150, 0, 1000, 600);
		else if (player.Lives > player.livesCount / 3 * 2)
			gameUI.addButton("GameWin", "victory2start", 150, 0, 1000, 600);
		else
			gameUI.addButton("GameWin", "victory1start", 150, 0, 1000, 600);

		gameUI.addButton("ReplayWin", "replay", 275, 500, 140, 50);
		gameUI.addButton("nextMapWin", "nextMap", 550, 500, 200, 60);

		if (Mouse.isButtonDown(0)) {
			if (gameUI.isButtonClicked("ReplayWin")) {
				setGameReplay(true);
			}
			if (gameUI.isButtonClicked("nextMapWin")) {
				setNextMap(true);
			}
		}
	}

	public void ReplayGame() {
		PauseGame();
		gameUI.addButton("Answer", "answer", 200, 150);
		gameUI.addButton("Yes", "yes", 200, 300, 150, 60);
		gameUI.addButton("No", "no", 500, 300, 150, 60);
		if (Mouse.isButtonDown(0)) {
			if (gameUI.isButtonClicked("Yes")) {
				System.out.println("Replay Game");
				setGameReplay(true);
				replayAnswer = false;
			}
			if (gameUI.isButtonClicked("No")) {
				replayAnswer = false;
				gameUI.removeButton("Answer");
				gameUI.removeButton("Yes");
				gameUI.removeButton("No");
				if (startGame)
					StartGame();
				else {
					PauseGame();
				}
			}
		}
	}

	public void BackMenu() {
		PauseGame();
		gameUI.addButton("Answer", "answerBackMenu", 200, 150);
		gameUI.addButton("Yes", "yes", 200, 300, 150, 60);
		gameUI.addButton("No", "no", 500, 300, 150, 60);
		if (Mouse.isButtonDown(0)) {
			if (gameUI.isButtonClicked("Yes") || gameUI.isButtonClicked("No")) {
				if (gameUI.isButtonClicked("Yes")) {
					back_menu = true;
				}
				if (gameUI.isButtonClicked("No")) {
					if (startGame)
						StartGame();
				}
				isClickBackMenu = false;
				gameUI.removeButton("Answer");
				gameUI.removeButton("Yes");
				gameUI.removeButton("No");
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

	public void setStartedGame(boolean GameStarted) {
		this.GameStarted = GameStarted;
	}

	public void setIsAudio(boolean isAudio) {
		this.isAudio = isAudio;
		this.waveManager.setIsAudio(isAudio);
	}

	public boolean getIsAudio() {
		return this.isAudio;
	}

	public int getIndexMap() {
		return this.indexMap;
	}

}
