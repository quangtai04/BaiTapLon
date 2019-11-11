package data;

import static helpers.Artist.*;

import org.newdawn.slick.opengl.Texture;

public enum TowerType {
	CannonRed(new Texture[] { QuickLoad("cannonRed"), QuickLoad("cannonRedGun") },ProjectileType.CannonBall, 15, 300, 2, 25),
	CannonIce(new Texture[] { QuickLoad("cannonIce"), QuickLoad("cannonIceGun") },ProjectileType.Iceball, 30, 300, 2, 25);

	Texture[] textures;
	ProjectileType projectileType;
	int damage, range, cost;
	float firingSpeed;
	
	
	TowerType(Texture[] textures, ProjectileType projectileType, int damage, int range, float firingSpeed, int cost) {
		this.textures = textures;
		this.projectileType = projectileType;
		this.damage = damage;			// hu hai gay ra cho quan dich
		this.range = range;				// pham vi ban
		this.firingSpeed = firingSpeed; //toc do ban
		this.cost = cost;
	}
}
