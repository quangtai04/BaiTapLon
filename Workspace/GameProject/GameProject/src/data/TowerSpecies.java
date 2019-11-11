package data;

import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;

import java.awt.Window.Type;
import java.util.concurrent.CopyOnWriteArrayList;

public class TowerSpecies extends Tower {
	private TowerType type;

	public TowerSpecies(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		super(type, startTile, enemies);
	}

	@Override
	public void shoot(Enemy target) {
		super.projectiles.add(
				new ProjectileCannonball(super.type.projectileType, super.target, super.getX(), super.getY(), 20, 20));
		super.target.reduceHiddenHealth(super.type.projectileType.damage);
	}

}
