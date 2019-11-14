package data;

public class Projectileball extends Projectile{

	public Projectileball(ProjectileType type, Enemy target, float x, float y, int width, int height) {
		super(type, target, x, y, width, height);
	}
	
	@Override
	public void damage() {
		super.damage();
	}

}
