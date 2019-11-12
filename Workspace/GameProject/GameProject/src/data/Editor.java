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
	private Menu buttonMap;
	private Texture menuBackGround;
	private boolean back_menu;
	private String mapName = "Map";
	private static int mapIndex = 0;

	public Editor() {
		this.grid = LoadMap(mapName+Integer.toString(mapIndex));
		this.index = 0;
		this.menuBackGround = QuickLoad("menu_background");
		this.types = new TileType[6];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.types[3] = TileType.DirtStart;
		this.types[4] = TileType.DirtEnd;
		this.types[5] = TileType.GrassNot;
		this.back_menu = false;
		setupUI();
	}

	private void setupUI() {
		editorUI = new UI();
		editorUI.addButton("Grass", "grass", 830, 20, 50, 50);
		editorUI.addButton("GrassNot", "grassNot", 930, 20, 50, 50);
		editorUI.addButton("Dirt", "dirt", 830, 80, 50, 50);
		editorUI.addButton("Water", "water", 930, 80, 50, 50);
		editorUI.addButton("DirtStart", "dirtStart", 830, 140, 50, 50);
		editorUI.addButton("DirtEnd", "dirtEnd", 930, 140, 50, 50);
		
		editorUI.createMenu("Map", 810, 380, 100, 100, 2, 0);
		buttonMap = editorUI.getMenu("Map");
		buttonMap.quickAdd("backMap", "back", 96, 96,96);
		buttonMap.quickAdd("nextMap", "next", 96, 96,96);
		
		editorUI.addButton("MapAndWave", "MapWave", 805, 320, 350, 60);
		
		editorUI.addButton("Clear", "clear",820, 460, 78,78);	
		editorUI.addButton("Save", "save",920, 460, 70,70);		
		
		editorUI.addButton("BackMenu", "HomeMenu", 820, 540, 300, 50);		// button back menu

	}

	public void update() {

		draw();
		if (Mouse.next()) {
			boolean mouuseClicked = Mouse.isButtonDown(0);
			int x = Mouse.getX();
			if (mouuseClicked) {
				if (editorUI.isButtonClicked("Grass")) // Bat su kien khi click vao button
					index = 0;
				else if (editorUI.isButtonClicked("Dirt"))
					index = 1;
				else if (editorUI.isButtonClicked("Water"))
					index = 2;
				else if (editorUI.isButtonClicked("DirtStart"))
					index = 3;
				else if (editorUI.isButtonClicked("DirtEnd"))
					index = 4;
				else if (editorUI.isButtonClicked("GrassNot"))
					index = 5;
				else if(editorUI.isButtonClicked("Save")) {
					SaveMap(mapName+Integer.toString(mapIndex), grid);
				}
				else if(editorUI.isButtonClicked("Clear")) {
					grid = new TileGrid();
				}
				else if (editorUI.isButtonClicked("BackMenu"))
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
				mapIndex +=1;
				if(mapIndex>9)mapIndex = 9;	
				this.grid = LoadMap(mapName+Integer.toString(mapIndex));
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
				mapIndex -=1;
				if(mapIndex<0)mapIndex = 0;
				this.grid = LoadMap(mapName+Integer.toString(mapIndex));
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

	public boolean getBackMenu() {
		return back_menu;
	}

	public void setBackMenu(boolean back_menu) {
		this.back_menu = back_menu;
	}
}