package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;
import static helpers.Clock.*;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class TowerCannon {

	private float x, y, timeSinceLastShot, firingSpeed,angle;
	private int width, height, damage,range;
	private Texture baseTexture, cannonTexture;
	private ArrayList<Projectile> projectiles;
    private CopyOnWriteArrayList<Enemy> enemies;
    private Enemy target;
	private boolean targeted;
	public TowerCannon(Texture baseTexture, Tile startTile, int damage,int range,CopyOnWriteArrayList<Enemy> enemies) {
		this.baseTexture = baseTexture;
		this.cannonTexture = QuickLoad("cannonGun");
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = (int) (startTile.getWidth());
		this.height = (int) (startTile.getHeight());
		this.damage = damage;
		this.range  = range;
		this.firingSpeed = 3;
		this.timeSinceLastShot = 0;
		this.projectiles = new ArrayList<Projectile>();
		this.enemies     = enemies;
		this.targeted = false;
		//this.target =      acquireTarget();
	    //this.angle  =  calculateAngle();
		
	}
    private Enemy acquireTarget() {
    	Enemy closest = null;
    	float closestDistance = 10000;
    	for(Enemy e:enemies) {
    		if(isInRange(e)&&findDistance(e)<closestDistance) {
    			closestDistance = findDistance(e);
    			closest         = e;
    		}
    	}
    	if(closest!=null)
    		targeted = true;
    	return closest;
    }
    
    private boolean isInRange(Enemy e) {
    	float xDistance  = Math.abs(e.getX() - x);
    	float yDistance  = Math.abs(e.getY() - y);
    	if(xDistance < range && yDistance < range)
    		return true;
    	return false;
    }
    private float findDistance(Enemy e) {
    	float xDistance  = Math.abs(e.getX() - x);
    	float yDistance  = Math.abs(e.getY() - y);
    	return xDistance + yDistance;
    }
    
    private float calculateAngle() {
    	double angleTemp = Math.atan2(target.getY()-y, target.getX()-x);
    	return (float)Math.toDegrees(angleTemp) - 90;
    }
	private void shoot() {
		timeSinceLastShot = 0;
//		projectiles.add(new ProjectileIceball(QuickLoad("bullet"), target,x + TILE_SIZE/2 - TILE_SIZE/4, y+TILE_SIZE/2 - TILE_SIZE/4,20,20, 900, 10));
	}
	public void updateEnemyList(CopyOnWriteArrayList<Enemy> newList) {
		this.enemies = newList;
	}
	public void update() {
		if(!targeted) {
			target = acquireTarget();
		}
		
		if(target==null||target.isAlive()==false)
			targeted = false;
		
		timeSinceLastShot += Delta();
		if(timeSinceLastShot > firingSpeed)	{
			shoot();
		}
		
		for(Projectile p: projectiles)	{	
			p.update();
		}
		angle  = calculateAngle();
		draw();
			
	}

	public void draw() {
		DrawQuadTex(baseTexture, x, y, width, height);
		DrawQuadTexRot(cannonTexture, x, y, width, height,angle);
	}
}
