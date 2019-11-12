package data;

public class BossEnemy extends Enemy {
	public BossEnemy(int tileX, int tileY, TileGrid grid) {		// di cham, health rat lon
		super(tileX, tileY, grid);
		this.setTexture("boss");
		this.setSpeed(40);
		this.setHealth(400);
	}
}
