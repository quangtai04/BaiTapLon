package data;

public class EnemyAlien extends Enemy {

	public EnemyAlien(int tileX, int tileY, TileGrid grid) {		// di cham, health lon
		super(tileX, tileY, grid);
		this.setSpeed(50);
		this.setHealth(80);
	}
}
