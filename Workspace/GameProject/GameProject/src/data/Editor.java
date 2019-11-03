package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.HEIGHT;
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
	private int      index;
	private TileType[] types;
	private UI editorUI;
	private Menu tilePickerMenu;
	private Texture menuBackGround;
	
	public Editor() {
	   this.grid = LoadMap("newMap1");
       this.index = 0;
       this.menuBackGround = QuickLoad("menu_background_2");
       this.types = new TileType[3];
	   this.types[0] = TileType.Grass;
	   this.types[1] = TileType.Dirt;
	   this.types[2] = TileType.Water;
	   setupUI();
	}
	
	private void setupUI() {
		editorUI = new UI();
		editorUI.createMenu("TilePicker", 800, 80,200,600, 2, 0);
		tilePickerMenu = editorUI.getMenu("TilePicker");
		tilePickerMenu.quickAdd("Grass", "grass");
		tilePickerMenu.quickAdd("Dirt", "dirt");
		tilePickerMenu.quickAdd("Water", "water");
	}
	
	public void update() {

		draw();
		if (Mouse.next()) {
			boolean mouuseClicked = Mouse.isButtonDown(0);
			if(mouuseClicked) {
				if (tilePickerMenu.isButtonClicked("Grass"))				// Bat su kien khi click vao button
					index = 0;
				else if(tilePickerMenu.isButtonClicked("Dirt"))
					index = 1;
				else if(tilePickerMenu.isButtonClicked("Water"))
					index = 2;
				else
					setTile();
			}
		}
				
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				moveIndex();
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()) {
				SaveMap("newMap1",grid);
			}
		}
	}
	
	private void draw() {
		DrawQuadTex(menuBackGround, 800, 0, 200, 600);
		grid.draw();
		editorUI.draw();
	}
	
	private void setTile() {
		grid.setTile((int) Math.floor(Mouse.getX() / TILE_SIZE), (int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE),
				types[index]);
	}
	
	//Allows editor to change which TypeTile is selected
	private void moveIndex() {
		index++;
		if (index > types.length - 1) {
			index = 0;
		}
	}
}