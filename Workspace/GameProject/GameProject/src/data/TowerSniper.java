package data;

import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;

import java.awt.Window.Type;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.opengl.Texture;

public class TowerSniper extends Tower {

	public TowerSniper(Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		super(startTile, enemies);
		super.setType(TowerType.TowerSniper);
		super.setTextures(TowerType.TowerSniper.textures);
		super.setCost(TowerType.TowerSniper.cost);
		super.setDamage(TowerType.TowerSniper.damage);
		super.setRange(TowerType.TowerSniper.range);
		super.setFiringSpeed(TowerType.TowerSniper.firingSpeed);
	}

	@Override
	public void shoot(Enemy target) {
		super.projectiles.add(
				new Projectileball(super.type.projectileType, super.target, super.getX(), super.getY(), 20, 20));
		super.target.reduceHiddenHealth(super.type.projectileType.damage);
	}
}
