package data;

import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;

import java.awt.Window.Type;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.opengl.Texture;

public class TowerNormal extends Tower {

	public TowerNormal(Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		super(startTile, enemies);
		super.setType(TowerType.TowerNormal);
		super.setTextures(TowerType.TowerNormal.textures);
		super.setCost(TowerType.TowerNormal.cost);
		super.setDamage(TowerType.TowerNormal.damage);
		super.setRange(TowerType.TowerNormal.range);
		super.setFiringSpeed(TowerType.TowerNormal.firingSpeed);
	}

	@Override
	public void shoot(Enemy target) {
		super.projectiles.add(
				new Projectileball(super.type.projectileType, super.target, super.getX(), super.getY(), 20, 20));
		super.target.reduceHiddenHealth(super.type.projectileType.damage);
	}
}
