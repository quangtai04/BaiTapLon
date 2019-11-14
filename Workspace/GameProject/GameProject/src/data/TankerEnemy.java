package data;

public class TankerEnemy extends Enemy {

	public TankerEnemy(int tileX, int tileY, TileGrid grid) {		// di cham, health lon
		super(tileX, tileY, grid);
		this.setTexture("TankerEnemy");
		this.setHealth(105);
		this.setSpeed(60);
		
	}
}
