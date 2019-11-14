package data;
public class NormalEnemy extends Enemy{

	public NormalEnemy(int tileX, int tileY, TileGrid grid) {		// binh thuong
		super(tileX, tileY, grid);
		this.setTexture("NormalEnemy");
		this.setHealth(75);
		this.setSpeed(75);
	}
}
