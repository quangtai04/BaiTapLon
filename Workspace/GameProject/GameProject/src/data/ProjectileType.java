package data;

import static helpers.Artist.*;
import org.newdawn.slick.opengl.Texture;

public enum ProjectileType {
	CannonBall(QuickLoad("bullet"),10,600),
	Iceball(QuickLoad("projectileIceball"),6,450);
	
	Texture texture;
	int damage;
	float speed;

	ProjectileType(Texture texture, int damage, int speed) {
		this.texture = texture;
		this.damage = damage;
		this.speed = speed;
		
	}
}
