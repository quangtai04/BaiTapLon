package data;

public class TankerEnemy extends Enemy {

	public TankerEnemy(int tileX, int tileY, TileGrid grid) {		// di cham, health lon
		super(tileX, tileY, grid);
		this.setTexture("TankerEnemy");
		this.setSpeed(50);
		this.setHealth(100);
	}
}
