package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Clock.*;
import static helpers.Artist.*;

public abstract class Projectile implements Entity{		// Xu li dan

	private Texture texture;
	private float x, y, speed,xVelocity,yVelocity;		// Velocity: van toc
	private int damage,width,height;
	private Enemy  target;
    private boolean alive;
    
	public Projectile(ProjectileType type, Enemy target, float x, float y, int width, int height) {
		this.texture = type.texture;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed  = type.speed;
		this.damage = type.damage;
		this.target = target;
		this.alive  = true;
		this.xVelocity = 0f;
		this.yVelocity = 0f;
		calculateDirection();
	}
	private void calculateDirection() {
		float totalAllowedMovement = 1.0f;
		float xDistanceFromTarget = Math.abs(target.getX() - x - TILE_SIZE/4 + TILE_SIZE/2);
		float yDistanceFromTarget = Math.abs(target.getY() - y - TILE_SIZE/4 + TILE_SIZE/2);
		float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
		float xPercentOfMovement = xDistanceFromTarget /totalDistanceFromTarget;
		xVelocity = xPercentOfMovement;
		yVelocity = totalAllowedMovement - xPercentOfMovement;
		//Set direction based on position of target realative to tower
		if(target.getX()<x) {
			xVelocity*=-1;
		}
		if(target.getY()<y) {
			yVelocity*=-1;
		}
	}
	
	// Gay sat thuong cho quan dich
	public void damage() {
		target.damage(damage);
		alive = false;
	}
	public void update() {
		if(alive) {
			calculateDirection();
		  x += xVelocity*speed *Delta();
		  y += yVelocity*speed *Delta(); 
		  if(CheckCollision(x, y, width, height, target.getX(), target.getY(), target.getWidth(), target.getHeight()))	//Kiem tra xem dan da den vi tri cua enemy chua
		     damage();
		  draw();
		}
	}
	
	public void draw() {
		DrawQuadTex(texture, x, y, 20, 20);
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
		// TODO Auto-generated method stub
		
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
	public void setAlive(boolean status) {
		alive = status;
	}
}
