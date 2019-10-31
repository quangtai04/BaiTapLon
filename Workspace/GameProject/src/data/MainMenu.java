package data;

import UI.UI;
import helpers.StateManager;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

public class MainMenu {
    private Texture background;
    private UI menuUI;

    public MainMenu() {
        background = QuickLoad("mainmenu");
        menuUI = new UI();
        menuUI.addButton("Play", "play", WIDTH / 2 - 128, (int) (HEIGHT * 0.45f));
        menuUI.addButton("Editor", "editor", WIDTH / 2 - 128, (int) (HEIGHT * 0.6f));
        menuUI.addButton("Quit", "quit", WIDTH / 2 - 128, (int) (HEIGHT * 0.75f));
    }

    private void updateButtons()    {
        if(Mouse.isButtonDown(0))   {
            if(menuUI.isButtonClicked("Play"))
                StateManager.setState(StateManager.GameState.GAME);
            if(menuUI.isButtonClicked("Editor"))
                StateManager.setState(StateManager.GameState.EDITOR);
            if(menuUI.isButtonClicked("Quit"))
                System.exit(0);
        }
    }
    public void update() {
        DrawQuadTex(background, 0, 0, 1280, 640);
        menuUI.draw();
        updateButtons();
    }
}
