package data;

import org.lwjgl.opengl.Display;


import helpers.Clock;
import helpers.StateManager;
import static helpers.Artist.BeginSession;

import static org.lwjgl.opengl.GL11.*;

import static helpers.Artist.*;

public class Boot {
	public Boot() {
		//Call static method in Artirt class to initialize OpenGL calls 
		BeginSession();
		
		//Main game loop
		while (!Display.isCloseRequested()) {
			Clock.update();
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
