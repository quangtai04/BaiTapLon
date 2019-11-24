package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.*;
import static helpers.Artist.QuickLoad;
import static helpers.Leveler.*;
import static helpers.Artist.TILE_SIZE;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import UI.UI.Menu;
import helpers.Clock;
import helpers.SimpleAudioPlayer;

public class Editor {

	private TileGrid grid;
	private int index;
	private TileType[] types;
	private UI editorUI;
	private Menu buttonMap;
	private Texture menuBackGround;
	private boolean back_menu, isSaveMap = true, answerSave = false, isNextMap = false, isBackMap = false,
			isBackMenu = false, isAddMap = false, isDeleteMap = false;
	private String mapName = "Map";
	private int mapIndex = 0, numberMap = 9;
	private boolean isAudio = true;
	private SimpleAudioPlayer clickMouse = new SimpleAudioPlayer("src\\res\\click.wav");
	private long totalTimeLastClick = 0;

	public Editor() {
		this.grid = LoadMap(mapName + Integer.toString(mapIndex));
		this.index = 0;
		this.menuBackGround = QuickLoad("backGround");
		this.types = new TileType[6];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.types[3] = TileType.DirtStart;
		this.types[4] = TileType.DirtEnd;
		this.types[5] = TileType.GrassNot;
		this.back_menu = false;
		this.numberMap = LoadNumberMap() - 1;
		if (numberMap < 0)
			numberMap = 0;
		setupUI();
	}

	private void setupUI() {
		editorUI = new UI();
		editorUI.addButton("Grass", "grass", 830, 20, 50, 50);
		editorUI.addButton("GrassNot", "grassNot", 950, 20, 50, 50);
		editorUI.addButton("Water", "water", 1070, 20, 50, 50);
		editorUI.addButton("Dirt", "dirt", 830, 80, 50, 50);
		editorUI.addButton("DirtStart", "dirtStart", 950, 80, 50, 50);
		editorUI.addButton("DirtEnd", "dirtEnd", 1070, 80, 50, 50);

		editorUI.createMenu("Map", 830, 240, 200, 100, 2, 0);
		buttonMap = editorUI.getMenu("Map");
		buttonMap.quickAdd("backMap", "back", 96, 96, 96);
		buttonMap.quickAdd("nextMap", "next", 96, 96, 96);

		editorUI.addButton("MapAndWave", "MapWave", 850, 150, 450, 80);

		editorUI.addButton("AddMap", "newMap", 890, 330, 300, 60);
		editorUI.addButton("DeleteMap", "deleteMap", 890, 390, 300, 60);

		editorUI.addButton("Clear", "clear", 890, 460, 78, 78);
		editorUI.addButton("Save", "save", 990, 460, 70, 70);

		editorUI.addButton("BackMenu", "HomeMenu", 890, 530, 300, 50); // button back menu

		editorUI.addButton("Selected", "selected", 830, 20, 50, 50);
	}

	public void update() {

		draw();
		PauseClickButton();
		
		if (Mouse.next()) {
			boolean mouuseClicked = Mouse.isButtonDown(0);
			int x = Mouse.getX();
			if (mouuseClicked) {
				PlayClickButton();
				if (editorUI.isButtonClicked("Grass")) {// Bat su kien khi click vao button
					index = 0;
					editorUI.removeButton("Selected");
					editorUI.addButton("Selected", "selected", 830, 20, 50, 50);
				} else if (editorUI.isButtonClicked("Dirt")) {
					index = 1;
					editorUI.removeButton("Selected");
					editorUI.addButton("Selected", "selected", 830, 80, 50, 50);
				} else if (editorUI.isButtonClicked("Water")) {
					index = 2;
					editorUI.removeButton("Selected");
					editorUI.addButton("Selected", "selected", 1070, 20, 50, 50);
				} else if (editorUI.isButtonClicked("DirtStart")) {
					index = 3;
					editorUI.removeButton("Selected");
					editorUI.addButton("Selected", "selected", 950, 80, 50, 50);
				} else if (editorUI.isButtonClicked("DirtEnd")) {
					index = 4;
					editorUI.removeButton("Selected");
					editorUI.addButton("Selected", "selected", 1070, 80, 50, 50);
				} else if (editorUI.isButtonClicked("GrassNot")) {
					index = 5;
					editorUI.removeButton("Selected");
					editorUI.addButton("Selected", "selected", 950, 20, 50, 50);
				} else if (editorUI.isButtonClicked("Save")) {
					SaveMap(mapName + Integer.toString(mapIndex), grid);
					isSaveMap = true;
					if (isAddMap == true) {
						SaveNumberMap(numberMap + 1);
						isAddMap = false;
					}
				} else if (editorUI.isButtonClicked("Clear")) {
					grid = new TileGrid();
					isSaveMap = false;
				} else if (editorUI.isButtonClicked("BackMenu")) {
					isBackMenu = true;
					if (isSaveMap == false) {
						answerSave = true;
					} else
						back_menu = true;
				} else if (buttonMap.isButtonClicked("backMap")) {
					isBackMap = true;
					if (isSaveMap == false) {
						answerSave = true;
					} else {
						mapIndex -= 1;
						if (mapIndex < 0)
							mapIndex = numberMap;
						this.grid = LoadMap(mapName + Integer.toString(mapIndex));
					}

				} else if (buttonMap.isButtonClicked("nextMap")) {
					isNextMap = true;
					if (isSaveMap == false) {
						answerSave = true;
					} else {
						mapIndex += 1;
						if (mapIndex > numberMap)
							mapIndex = 0;
						this.grid = LoadMap(mapName + Integer.toString(mapIndex));
					}
				} else if (editorUI.isButtonClicked("AddMap")) {
					isAddMap = true;
					grid = new TileGrid();
					numberMap++;
					mapIndex = numberMap;
					isSaveMap = false;

				} else if (editorUI.isButtonClicked("DeleteMap")) {
					isDeleteMap = true;
				} else if (x > 0 && x < 800 && index != -1) {
					setTile();
					isSaveMap = false;
				}
			}
		}
		if (Keyboard.next())
			if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
				index = -1;
				editorUI.removeButton("Selected");
			}

		if (answerSave == true) {
			AnswerSaveMap();
		}
		if (isDeleteMap == true) {
			deleteMap();
		}
		
	}

	private void draw() {
		DrawQuadTex(menuBackGround, 800, 0, 360, 1150);
		grid.draw();
		editorUI.draw();
		editorUI.drawString(920, 170, "Map: " + (mapIndex + 1) + " / " + Integer.toString(numberMap + 1));
	}

	private void setTile() {
		grid.setTile((int) Math.floor(Mouse.getX() / TILE_SIZE),
				(int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE), types[index]);
	}

	public boolean getBackMenu() {
		return back_menu;
	}

	public void setBackMenu(boolean back_menu) {
		this.back_menu = back_menu;
	}

	public void AnswerSaveMap() {
		editorUI.addButton("Answer", "SaveMap", 200, 150);
		editorUI.addButton("Yes", "yes", 200, 300, 150, 60);
		editorUI.addButton("No", "no", 500, 300, 150, 60);
		if (Mouse.isButtonDown(0)) {
			PlayClickButton();
			if (editorUI.isButtonClicked("Yes")) {
				SaveMap(mapName + Integer.toString(mapIndex), grid);
				if (isAddMap == true) {
					isAddMap = false;
					SaveNumberMap(numberMap + 1);
					System.out.println(numberMap);
				}
			} else if (editorUI.isButtonClicked("No")) {
				if (isAddMap == true) {
					isAddMap = false;
					numberMap--;
					if (numberMap < 0)
						numberMap = 0;
				}
			}
			if (editorUI.isButtonClicked("Yes") || editorUI.isButtonClicked("No")) {
				isSaveMap = true;
				editorUI.removeButton("Answer");
				editorUI.removeButton("Yes");
				editorUI.removeButton("No");
				if (isBackMap) {
					mapIndex -= 1;
					if (mapIndex < 0)
						mapIndex = numberMap;
					this.grid = LoadMap(mapName + Integer.toString(mapIndex));
					answerSave = false;
					isBackMap = false;
				} else if (isNextMap) {
					mapIndex += 1;
					if (mapIndex > numberMap)
						mapIndex = 0;
					this.grid = LoadMap(mapName + Integer.toString(mapIndex));
					answerSave = false;
					isNextMap = false;
				}
				if (isBackMenu) {
					back_menu = true;
				}
			}

		}
	}

	public void deleteMap() {
		editorUI.addButton("AnswerDeleteMap", "answerDelete", 200, 150);
		editorUI.addButton("Yes", "yes", 200, 300, 150, 60);
		editorUI.addButton("No", "no", 500, 300, 150, 60);
		if (Mouse.isButtonDown(0)) {
			PlayClickButton();
			if (editorUI.isButtonClicked("Yes")) {
				DeleteMap(mapIndex);
				numberMap--;
				if (numberMap < 0)
					numberMap = 0;
				mapIndex++;
				if (mapIndex > numberMap) {
					mapIndex = 0;
				}
				grid = LoadMap(mapName + Integer.toString(mapIndex));
				isDeleteMap = false;
				isSaveMap = true;
			} else if (editorUI.isButtonClicked("No")) {
				isDeleteMap = false;
			}
			if (editorUI.isButtonClicked("Yes") || editorUI.isButtonClicked("No")) {
				editorUI.removeButton("AnswerDeleteMap");
				editorUI.removeButton("Yes");
				editorUI.removeButton("No");
			}
		}
	}
	private void PlayClickButton() {
		if(isAudio)	{
			clickMouse.restart();
			totalTimeLastClick = Clock.getTime();
		}
	}
	private void PauseClickButton()	{
		if(Clock.getTime() - totalTimeLastClick > 500)	{
			clickMouse.pause();
		}
	}
	public boolean isAudio() {
		return isAudio;
	}

	public void setAudio(boolean isAudio) {
		this.isAudio = isAudio;
	}
}