package data;

public class EnemyPlane extends Enemy {

	public EnemyPlane(int tileX, int tileY, TileGrid grid) {	// di nhanh, mau it
		super(tileX, tileY, grid);
		this.setTexture("enemyPlane");
		this.setHealth(50);
		this.setSpeed(120);
		
	}
}
