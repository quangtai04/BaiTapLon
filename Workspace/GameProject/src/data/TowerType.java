package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public enum TowerType {
	
	CanonnRed(QuickLoad("cannonBase"),10);
	
	Texture texture;
	int damage;
	
	TowerType(Texture texture, int damage) {
		this.texture=texture;
		this.damage = damage;
	}
	
}
