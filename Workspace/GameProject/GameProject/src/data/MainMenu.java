package data;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.Clock;
import helpers.StateManager;
import helpers.StateManager.GameState;

import static helpers.Artist.*;

public class MainMenu {
	private Texture background;
	private UI menuUI;
	private static boolean error = false, clickContinue = false, clickStart = false;

	public MainMenu() {
		background = QuickLoad("mainmenu");
		menuUI = new UI();
		menuUI.addButton("Continue", "continue", WIDTH / 2 - 128, (int) (HEIGHT * 0.3f), 400, 80);
		menuUI.addButton("Play", "playButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.45f), 400, 80);
		menuUI.addButton("Editor", "editorButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.6f), 400, 80);
		menuUI.addButton("Quit", "quitButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.75), 250, 80);
		menuUI.addButton("Information", "information", 50, 540, 300, 50);
	}

	// Check if a button is clicked by the user, and if so do an action
	private void updateButtons() {
		if (Mouse.isButtonDown(0)) {
			if (menuUI.isButtonClicked("Continue")) {
				clickContinue = true;
				clickStart = false;
				StateManager.setState(GameState.CONTINUE);
			}
			if (menuUI.isButtonClicked("Play"))	{
				clickContinue = false;
				clickStart = true;
				StateManager.setState(GameState.GAME);
			}
			if (menuUI.isButtonClicked("Editor"))	{
				StateManager.setState(GameState.EDITOR);
			}
			if (menuUI.isButtonClicked("Quit"))
				System.exit(0);
			if(menuUI.isButtonClicked("Information"))	{
				StateManager.setState(GameState.INFORMATION);
			}
		}
	}

	public void update() {
		DrawQuadTex(background, 0, 0, 1100, 1030);
		menuUI.draw();
		if (!error)
			updateButtons();
	}

	public void ContinueNull() {
		if (clickContinue) {
			error = true;
			menuUI.addButton("Error", "error", 350, 0);
			menuUI.addButton("OK", "ok", 440, 120);

			if (Mouse.isButtonDown(0)) {
				if (menuUI.isButtonClicked("OK")) {
					menuUI.removeButton("Error");
					menuUI.removeButton("OK");
					error = false;
					clickContinue = false;
				}
			}
		}
	}
	public boolean getClickStart() {
		return clickStart;
	}
	public void setClickStart(boolean clickStart)	{
		this.clickStart = clickStart;
	}
	public boolean getClickContinue() {
		return clickContinue;
	}
	public void setClickContinue(boolean clickContinue)	{
		this.clickContinue = clickContinue;
	}
	
}
