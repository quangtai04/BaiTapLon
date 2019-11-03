package data;
public class EnemyUFO extends Enemy{

	public EnemyUFO(int tileX, int tileY, TileGrid grid) {
		super(tileX, tileY, grid);
		this.setTexture("ufo");
		this.setSpeed(80);
	}
	
}
