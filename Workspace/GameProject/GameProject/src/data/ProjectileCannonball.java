package data;

public class ProjectileCannonball extends Projectile{

	public ProjectileCannonball(ProjectileType type, Enemy target, float x, float y, int width, int height) {
		super(type, target, x, y, width, height);
	}
	
	@Override
	public void damage() {
		super.damage();
	}

}
