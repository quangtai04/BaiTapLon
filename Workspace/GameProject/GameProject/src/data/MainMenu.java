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
	private static boolean error = false, clickContinue = false, clickStart = false, checkMusic = true,
			isClickMusic = false, isClickEdit = false;			//error = true khi gameSave = null, false khi gameSave != null;
	
	public MainMenu() {
		background = QuickLoad("mainmenu");
		menuUI = new UI();
		menuUI.addButton("Continue", "continue", WIDTH / 2 - 128, (int) (HEIGHT * 0.3f), 400, 80);
		menuUI.addButton("Play", "playButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.45f), 400, 80);
		menuUI.addButton("Editor", "editorButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.6f), 400, 80);
		menuUI.addButton("Quit", "quitButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.75), 250, 80);
		menuUI.addButton("Information", "information", 50, 540, 300, 50);
		menuUI.addButton("Music", "musicOn", 1080, 30,70,70);
	}

	// Check if a button is clicked by the user, and if so do an action
	private void updateButtons() {
		if (Mouse.next()) {
			if (Mouse.isButtonDown(0)) {
				if (menuUI.isButtonClicked("Continue") && Mouse.getEventButtonState()) {
					clickContinue = true;
					clickStart = false;
					StateManager.setState(GameState.CONTINUE);
				} else if (menuUI.isButtonClicked("Play") && Mouse.getEventButtonState()) {
					clickContinue = false;
					clickStart = true;
					StateManager.setState(GameState.GAME);
				} else if (menuUI.isButtonClicked("Editor") && Mouse.getEventButtonState()) {
					isClickEdit = true;
					StateManager.setState(GameState.EDITOR);
					
				} else if (menuUI.isButtonClicked("Quit"))
					System.exit(0);
				else if (menuUI.isButtonClicked("Information") && Mouse.getEventButtonState()) {
					StateManager.setState(GameState.INFORMATION);
				} else if (menuUI.isButtonClicked("Music") && Mouse.getEventButtonState()) {
					checkMusic = !checkMusic;
					if(checkMusic == false)	{
						menuUI.removeButton("Music");
						menuUI.addButton("Music", "musicOff", 1080, 30,70,70);
					} else {
						menuUI.removeButton("Music");
						menuUI.addButton("Music", "musicOn", 1080, 30,70,70);
					}
					isClickMusic = true;
				}
			}
		}
	}

	public void update() {
		DrawQuadTex(background, 0, 0, 1200, 1030);
		menuUI.draw();
		if (!error)
			updateButtons();
	}

	public void ContinueNull() {
		if (clickContinue) {
			error = true;
			menuUI.addButton("Error", "error", 430, 0);
			menuUI.addButton("OK", "ok", 520, 120);

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

	public void setClickStart(boolean clickStart) {
		this.clickStart = clickStart;
	}

	public boolean getClickContinue() {
		return clickContinue;
	}

	public void setClickContinue(boolean clickContinue) {
		this.clickContinue = clickContinue;
	}

	public void setCheckMusic(boolean checkMusic) {
		this.checkMusic = checkMusic;
	}

	public boolean getCheckMusic() {
		return this.checkMusic;
	}

	public void setIsClickMusic(boolean isClickMusic) {
		this.isClickMusic = isClickMusic;
	}

	public boolean getIsClickMusic() {
		return this.isClickMusic;
	}

	public static boolean isClickEdit() {
		return isClickEdit;
	}

	public static void setClickEdit(boolean isClickEdit) {
		MainMenu.isClickEdit = isClickEdit;
	}
	
}
