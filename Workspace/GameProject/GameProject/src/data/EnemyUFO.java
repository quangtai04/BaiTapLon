package data;
public class EnemyUFO extends Enemy{

	public EnemyUFO(int tileX, int tileY, TileGrid grid) {		// binh thuong
		super(tileX, tileY, grid);
		this.setTexture("ufo");
		this.setSpeed(80);
		this.setHealth(75);
	}
}
