package data;

import static helpers.Artist.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import static helpers.Leveler.*;


public class Editor {
	private TileGrid grid;
	private int index;
	private TileType []types;

	public Editor() {
		this.grid = loadMap("newMap1");
		this.index = 0;
		
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
	}

	public void update() {
		grid.draw();
		
		// Handle Mouse Input
		if (Mouse.isButtonDown(0)) {
			 setTile();
		}

		// Handle KeyBoard Input
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				moveIndex();
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()) {
				saveMap("newMap1",grid);
			}
		}
	}

	private void setTile() {
		grid.setTile((int) Math.floor(Mouse.getX() / TILE_SIZE), (int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE),
				types[index]);
	}
	
	private void moveIndex() {
		index++;
		if (index > types.length - 1) {
			index = 0;
		}
	}

}
