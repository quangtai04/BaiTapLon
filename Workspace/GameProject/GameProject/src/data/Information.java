package data;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

import static helpers.Artist.*;

public class Information {
	private Texture backGround;
	private UI aboutUI;
	public Information() {
		backGround = QuickLoad("backGround");
		aboutUI = new UI();
		aboutUI.addButton("BackMenu", "HomeMenu",  20, 20, 300, 50);
		aboutUI.addButton("NormalTowe", "TowerNormalFull", 120, 170, 50, 50);
		aboutUI.addButton("SniperTower", "TowerSniperFull", 120, 240, 50, 50);
		aboutUI.addButton("MachineTowe", "TowerMachineFull", 120, 310, 50, 50);
	}
	public void updateButton()	{
		WriteInformation();
		if(Mouse.isButtonDown(0)) {
			if(aboutUI.isButtonClicked("BackMenu"))	{
				StateManager.setState(GameState.MAINMENU);
			}
		}
	}
	
	public void update()	{
		DrawQuadTex(backGround, 0, 0, 1300, 1200);
		aboutUI.draw();
		updateButton();
		
	}
	
	public void WriteInformation()	{
		aboutUI.drawString(120, 130, "Type");
		aboutUI.drawString(250, 130, "Name");
		aboutUI.drawString(400, 130, "Shooting Range");
		aboutUI.drawString(600, 130, "Time between shots");	
		
		aboutUI.drawString(220, 180, "Tower Normal");
		aboutUI.drawString(440, 180, "Normal");
		aboutUI.drawString(640, 180, "Normal");
		
		aboutUI.drawString(220, 250, "Tower Sniper");
		aboutUI.drawString(440, 250, "Small");
		aboutUI.drawString(640, 250, "Fast");
		
		aboutUI.drawString(220, 320, "Tower Machine");
		aboutUI.drawString(440, 320, "Big");
		aboutUI.drawString(640, 320, "Slow");
		
		aboutUI.drawString(100, 400, "When you begin start game, you can next map or privious map. But you started game, you can't. ");
		
		aboutUI.drawString(20, 500, "Program made by: ");
		aboutUI.drawString(50, 530, "PHAM QUANG TAI - 18021110 - K63T");
		aboutUI.drawString(50, 560, "LE DUC THANG - - K63T");
		
	}

}
