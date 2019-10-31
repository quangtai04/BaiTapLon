package data;

import helpers.StateManager;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;

import helpers.Clock;

import static org.lwjgl.opengl.GL11.*;

import static helpers.Artist.*;

public class Boot {
	public Boot() {
		BeginSession();

		//Game game = new Game(map);
		while (!Display.isCloseRequested()) {
			Clock.update();
		//	game.update();
			StateManager.update();
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
	}

	public static void main(String[] args) {
		new Boot();
	}
}
