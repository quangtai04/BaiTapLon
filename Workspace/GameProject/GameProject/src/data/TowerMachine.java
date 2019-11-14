package data;

import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;

import java.awt.Window.Type;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.opengl.Texture;

public class TowerMachine extends Tower {

	public TowerMachine(Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		super(startTile, enemies);
		super.setType(TowerType.TowerMachine);
		super.setTextures(TowerType.TowerMachine.textures);
		super.setCost(TowerType.TowerMachine.cost);
		super.setDamage(TowerType.TowerMachine.damage);
		super.setRange(TowerType.TowerMachine.range);
		super.setFiringSpeed(TowerType.TowerMachine.firingSpeed);
	}

	@Override
	public void shoot(Enemy target) {
		super.projectiles.add(
				new Projectileball(super.type.projectileType, super.target, super.getX(), super.getY(), 20, 20));
		super.target.reduceHiddenHealth(super.type.projectileType.damage);
	}
}
