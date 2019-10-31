package data;

import helpers.Clock;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import static helpers.Artist.*;

import java.util.ArrayList;

public class Player {

	private TileGrid gird;
	private TileType[] types;
	private int index;
	private WaveManager waveManager;
	private ArrayList<TowerCannon> towerList;
	private boolean leftMouseButtonDown;

	public Player(TileGrid gird, WaveManager waveManager) {
		this.gird = gird;
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		index = 1;
		this.waveManager = waveManager;
		this.towerList = new ArrayList<TowerCannon>();
		this.leftMouseButtonDown = false;
	}

	public void setTile() {
		gird.setTile((int) Math.floor(Mouse.getX() / 40), (int) Math.floor((HEIGHT - Mouse.getY() - 1) / 40),
				types[index]);
	}

	public void update() {
		for (TowerCannon t : towerList)
			t.update();

		// Handle Mouse Input
		if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
			towerList.add(new TowerCannon(QuickLoad("cannonBase"),
					gird.GetTile(Mouse.getX() / 40, (HEIGHT - Mouse.getY() - 1) / 40), 10,
					waveManager.getCurrentWave().getEnemyList()));
			// setTile();
		}

		leftMouseButtonDown = Mouse.isButtonDown(0);

		// Handle KeyBoard Input
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				Clock.ChangeMultiplier(0.2f);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
				Clock.ChangeMultiplier(-0.2f);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_T && Keyboard.getEventKeyState()) {
				towerList.add(new TowerCannon(QuickLoad("cannonBase"), gird.GetTile(18, 9), 10,
						waveManager.getCurrentWave().getEnemyList()));
			}
		}
	}

	private void moveIndex() {
		index++;
		if (index > types.length - 1) {
			index = 0;
		}
	}
}
