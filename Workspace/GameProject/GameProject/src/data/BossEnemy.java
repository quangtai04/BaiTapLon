package data;

public class BossEnemy extends Enemy {
	public BossEnemy(int tileX, int tileY, TileGrid grid) {		// di cham, health rat lon
		super(tileX, tileY, grid);
		this.setTexture("boss");
		this.setSpeed(50);
		this.setHealth(405);
	}
}
