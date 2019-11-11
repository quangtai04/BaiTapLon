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

public class Editor {

	private TileGrid grid;
	private int index;
	private TileType[] types;
	private UI editorUI;
	private Menu tilePickerMenu;
	private Menu backMenu;
	private Menu buttonMap;
	private Texture menuBackGround;
	private boolean back_menu;
	private String mapName = "Map";
	private static int mapIndex = 0;

	public Editor() {
		this.grid = LoadMap(mapName+Integer.toString(mapIndex));
		this.index = 0;
		this.menuBackGround = QuickLoad("menu_background_2");
		this.types = new TileType[5];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.types[3] = TileType.DirtStart;
		this.types[4] = TileType.DirtEnd;
		this.back_menu = false;
		setupUI();
	}

	private void setupUI() {
		editorUI = new UI();
		editorUI.createMenu("TilePicker", 800, 50, 200, 400, 2, 0);
		tilePickerMenu = editorUI.getMenu("TilePicker");
		tilePickerMenu.quickAdd("Grass", "grass", 50, 50, 50);
		tilePickerMenu.quickAdd("Dirt", "dirt", 50,50,50);
		tilePickerMenu.quickAdd("Water", "water", 50,50,50);
		tilePickerMenu.quickAdd("DirtStart", "dirtStart", 50,50,50);
		tilePickerMenu.quickAdd("DirtEnd", "dirtEnd", 50,50,50);
		tilePickerMenu.quickAdd("Save", "save", 70,70,50);

		editorUI.createMenu("Map", 800, 400, 100, 100, 2, 0);
		buttonMap = editorUI.getMenu("Map");
		buttonMap.quickAdd("backMap", "back", 96, 96,96);
		buttonMap.quickAdd("nextMap", "next", 96, 96,96);
		
		editorUI.addButton("MapAndWave", "MapWave", 805, 320, 350, 60);
		
		editorUI.addButton("Menu", "HomeMenu", 820, 510, 300, 50);

	}

	public void update() {

		draw();
		if (Mouse.next()) {
			boolean mouuseClicked = Mouse.isButtonDown(0);
			int x = Mouse.getX();
			int y = Mouse.getY();
			if (mouuseClicked) {
				if (tilePickerMenu.isButtonClicked("Grass")) // Bat su kien khi click vao button
					index = 0;
				else if (tilePickerMenu.isButtonClicked("Dirt"))
					index = 1;
				else if (tilePickerMenu.isButtonClicked("Water"))
					index = 2;
				else if (tilePickerMenu.isButtonClicked("DirtStart"))
					index = 3;
				else if (tilePickerMenu.isButtonClicked("DirtEnd"))
					index = 4;
				else if(tilePickerMenu.isButtonClicked("Save")) {
					SaveMap(mapName+Integer.toString(mapIndex), grid);
				}
				else if (editorUI.isButtonClicked("Menu"))
					back_menu = true;
				else if (buttonMap.isButtonClicked("backMap")) {
					mapIndex -=1;
					if(mapIndex<0)mapIndex = 0;
					this.grid = LoadMap(mapName+Integer.toString(mapIndex));
					
				} else if (buttonMap.isButtonClicked("nextMap")) {
					mapIndex +=1;
					if(mapIndex>9)mapIndex = 9;	
					this.grid = LoadMap(mapName+Integer.toString(mapIndex));
				}

				else if (x > 0 && x < 800)
					setTile();
				System.out.println(x);
			}
		}

		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				moveIndex();
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()) {
				SaveMap(mapName+Integer.toString(mapIndex), grid);
			}
		}
	}

	private void draw() {
		DrawQuadTex(menuBackGround, 800, 0, 200, 600);
		grid.draw();
		editorUI.draw();
		editorUI.drawString(870, 330, "Map: "+ (mapIndex+1));
	}

	private void setTile() {
		grid.setTile((int) Math.floor(Mouse.getX() / TILE_SIZE),
				(int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE), types[index]);
	}

	// Allows editor to change which TypeTile is selected
	private void moveIndex() {
		index++;
		if (index > types.length - 1) {
			index = 0;
		}
	}

	public boolean getBackMenu() {
		return back_menu;
	}

	public void setBackMenu(boolean back_menu) {
		this.back_menu = back_menu;
	}
}