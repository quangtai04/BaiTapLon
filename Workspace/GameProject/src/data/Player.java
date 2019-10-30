package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import static helpers.Artist.*;

public class Player {

	private TileGrid gird;
	private TileType[] types;
	private int index;

	public Player(TileGrid gird) {
		this.gird = gird;
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		index = 1;
	}

	public void SetTile() {
		gird.setTile((int) Math.floor(Mouse.getX() / 40), (int) Math.floor((HEIGHT - Mouse.getY() - 1) / 40),
				types[index]);
	}

	public void Update() {
		if (Mouse.isButtonDown(0)) {
			SetTile();
		}
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				MoveIndex();
			}
		}
	}

	private void MoveIndex() {
		index++;
		if (index > types.length - 1) {
			index = 0;
		}
	}
}
