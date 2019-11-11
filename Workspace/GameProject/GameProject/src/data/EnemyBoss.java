package data;

public class EnemyBoss extends Enemy {
	public EnemyBoss(int tileX, int tileY, TileGrid grid) {		// di cham, health rat lon
		super(tileX, tileY, grid);
		this.setSpeed(40);
		this.setHealth(400);
	}
}
