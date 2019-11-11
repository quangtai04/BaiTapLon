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

public class Game {

	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	private UI gameUI;
	private Texture menuBackGround;
	private Enemy[] enemyTypes;
	private Enemy[] enemisBoss;
	private boolean back_menu = false, replay = false;
	private int waveNumber = 1, start;
	private int indexMap;
	private int soLuongQuan = 3;
	private float thoiGian = 3.15f;
	private boolean nextMap = false, priviousMap = false, startGame = false, saveGame = false, drawGrid = true,
			replayAnswer = false;

	public Game(TileGrid grid, int indexMap) {
		this.grid = grid;
		enemyTypes = new Enemy[3];
		enemisBoss = new Enemy[1];
		enemyTypes[0] = new EnemyAlien(grid.getXYStart().x, grid.getXYStart().y, grid);
		enemyTypes[1] = new EnemyUFO(grid.getXYStart().x, grid.getXYStart().y, grid);
		enemyTypes[2] = new EnemyPlane(grid.getXYStart().x, grid.getXYStart().y, grid);
		enemisBoss[0] = new EnemyBoss(grid.getXYStart().x, grid.getXYStart().y, grid);
		waveManager = new WaveManager(enemyTypes, (int) thoiGian, soLuongQuan);
		player = new Player(grid, waveManager);
		player.setup();
		this.menuBackGround = QuickLoad("menu_background_2");
		this.indexMap = indexMap;
		setupUI();
	}

	private void setupUI() {
		gameUI = new UI();
		gameUI.addButton("TowerNormal", "TowerNormalFull", 815, 50, 50, 50);
		gameUI.addButton("TowerSniper", "TowerSniperFull", 875, 50, 50, 50);
		gameUI.addButton("TowerMachine", "TowerMachineFull", 935, 50, 50, 50);

		gameUI.addButton("MapAndWave", "MapWave", 805, 150, 350, 60);
		gameUI.addButton("Lives", "Lives", 805, 210, 100, 40);
		gameUI.addButton("Cash", "Cash", 905, 210, 100, 40);

		gameUI.addButton("Start", "start", 820, 280, 80, 60);
		gameUI.addButton("Pause", "pause", 910, 280, 80, 60);

		gameUI.addButton("BackMap", "back", 820, 420, 70, 70);
		gameUI.addButton("NextMap", "next", 920, 420, 70, 70);

		gameUI.addButton("SaveGame", "save", 820, 495, 60, 60);
		gameUI.addButton("Replay", "replay", 880, 495, 120, 60);

		gameUI.addButton("Menu", "HomeMenu", 820, 560, 300, 50);
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
				if (gameUI.isButtonClicked("TowerNormal")) // Bat su kien khi click vao button
					player.pickTower(new TowerSpecies(TowerType.TowerNormal, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				if (gameUI.isButtonClicked("TowerSniper")) // Bat su kien khi click vao button
					player.pickTower(new TowerSpecies(TowerType.TowerSniper, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				if (gameUI.isButtonClicked("TowerMachine")) // Bat su kien khi click vao button
					player.pickTower(new TowerSpecies(TowerType.TowerMachine, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				if (gameUI.isButtonClicked("Menu")) {
					back_menu = true;
				}
				if (gameUI.isButtonClicked("Start")) {
					Clock.setMultiplier(1);
					start = 1;
					startGame = true;
				} else if (gameUI.isButtonClicked("Pause") && startGame == true) {
					Clock.setMultiplier(0);
					startGame = false;
				}
				if (gameUI.isButtonClicked("NextMap") && waveNumber == 1) {
					nextMap = true;
				}
				if (gameUI.isButtonClicked("BackMap") && waveNumber == 1) {
					priviousMap = true;
				}
				if (gameUI.isButtonClicked("SaveGame")) {
					saveGame = true;
				}
				if (gameUI.isButtonClicked("Replay")) {
					replayAnswer = true;
				}
			}
		}
	}

	public void update() {
		DrawQuadTex(menuBackGround, 800, 0, 200, 600);

		grid.draw();
		if (start == 0) {
			Clock.setMultiplier(0);
		}
		if (waveManager.isComplete() == false) {
			waveManager.update();
		} else {
			waveNumber++;
			if (waveNumber == 5) {
				waveManager = new WaveManager(enemisBoss, 1, 1);
			} else if (waveNumber == 10) {
				waveManager = new WaveManager(enemisBoss, 2, 3);
			} else {
				soLuongQuan = soLuongQuan + 2;
				thoiGian -= 0.15;
				System.out.println(thoiGian);
				waveManager = new WaveManager(enemyTypes, (int) thoiGian, soLuongQuan);
			}
			Clock.setMultiplier(0);
			player.setWaveManager(waveManager);
		}

		player.update();
		updateUI();

		if (GameLose() || GameWin()) {
			if (GameLose()) {
				PlayGameLose();
			}
			if (GameWin()) {
				PlayGameWin();
			}
		}
		if (replayAnswer) {
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

	}

	public void PlayGameLose() {
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

	public void PlayGameWin() {
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

	public boolean GameLose() {
		if (player.getGameLose())
			return true;
		return false;
	}

	public boolean GameWin() {
		if (waveNumber > 3) // player.livesCount
			return true;
		return false;
	}

	public boolean getBackMenu() {
		return back_menu;
	}

	public void setBackMenu(boolean back_menu) {
		this.back_menu = back_menu;
	}

	public boolean getNextMap() {
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
		Clock.setMultiplier(1);
	}

}
