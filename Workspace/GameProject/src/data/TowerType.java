package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public enum TowerType {
	
	CanonnRed(new Texture[] {QuickLoad("cannonBase"), QuickLoad("cannonGun")},10),
	CannonBlue(new Texture[] {QuickLoad("cannonBaseBlue"), QuickLoad("cannonGunBlue")},30);
	
	Texture []textures;
	int damage;
	
	TowerType(Texture [] textures, int damage) {
		this.textures=textures;
		this.damage = damage;
	}
	
}
