package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.DrawQuadTexRot;
import static helpers.Clock.Delta;
import static helpers.Artist.*;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Tower implements Entity {
	private float x, y, timeSinceLastShot, firingSpeed, angle;
	private int width, height, damage, range, cost;
	public Enemy target;
	private Texture[] textures;
	private CopyOnWriteArrayList<Enemy> enemies;
	private boolean targeted;
	public ArrayList<Projectile> projectiles;
	public TowerType type;

	public Tower(Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		this.type = TowerType.TowerNormal;
		this.textures = TowerType.TowerNormal.textures;
		this.damage = TowerType.TowerNormal.damage;
		this.range = TowerType.TowerNormal.range;
		this.cost = TowerType.TowerNormal.cost;
		this.firingSpeed = TowerType.TowerNormal.firingSpeed;
		
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = startTile.getWidth();
		this.height = startTile.getHeight();
		this.enemies = enemies;
		this.targeted = false;
		this.timeSinceLastShot = 0f;
		this.projectiles = new ArrayList<Projectile>();
		this.firingSpeed = type.firingSpeed;
		this.angle = 0f;
	}

	private Enemy acquireTarget() {
		Enemy closest = null;
		for (Enemy e : enemies) {
			if (isInRange(e) &&  e.getHiddenHealth() > 0) {
				closest = e;
			}
		}
		//If an enemy exists and is returned, targeted == true
		if (closest != null)
			targeted = true;
		return closest;
	}

	private boolean isInRange(Enemy e) {					// Kiem tra khoang cach
		float xDistance = Math.abs(e.getX() - x);
		float yDistance = Math.abs(e.getY() - y);
		if (xDistance < range && yDistance < range)
			return true;
		return false;
	}

	private float calculateAngle() {
		double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
		return (float) Math.toDegrees(angleTemp) - 90;
	}

	//Abstract method for 'shoot', must be overriden in subclasses
	public abstract void shoot(Enemy target) ;

	public void updateEnemyList(CopyOnWriteArrayList<Enemy> newList) {
		this.enemies = newList;
	}

	public void update() {
		if ( !targeted || target.getHiddenHealth() <= 0) {
			target = acquireTarget();
		}
		else if (targeted && target.getHiddenHealth() > 0){
			angle = calculateAngle();
			if (timeSinceLastShot > firingSpeed) {
				shoot(target);
				timeSinceLastShot = 0;
			}
			
		}

		if (target == null || target.isAlive() == false || (target != null && isInRange(target) == false))
			targeted = false;

		timeSinceLastShot += Delta();

		for (Projectile p : projectiles) {
			p.update();
		}

		draw();

	}

	public void draw() {
		DrawQuadTex(textures[0], x, y, width, height);

		if (textures.length > 1)
			for (int i = 1; i < textures.length; i++)
				DrawQuadTexRot(textures[i], x, y, width, height, angle);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Enemy getTarget() {
		return target;
	}
	public int getCost()	{
		return cost;
	}
	public TowerType getTowerType() {
		return this.type;
	}
	public void setType(TowerType type) {
		this.type = type;
	}

	public float getFiringSpeed() {
		return firingSpeed;
	}

	public void setFiringSpeed(float firingSpeed) {
		this.firingSpeed = firingSpeed;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public Texture[] getTextures() {
		return textures;
	}

	public void setTextures(Texture[] textures) {
		this.textures = textures;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}
}
