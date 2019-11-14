package data;

public class SmallerEnemy extends Enemy {

	public SmallerEnemy(int tileX, int tileY, TileGrid grid) {	// di nhanh, mau it
		super(tileX, tileY, grid);
		this.setTexture("SmallerEnemy");
		this.setHealth(55);
		this.setSpeed(120);
		
	}
}
