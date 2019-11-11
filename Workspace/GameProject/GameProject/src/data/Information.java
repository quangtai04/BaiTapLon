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
		aboutUI.addButton("BackMenu", "HomeMenu", 20, 20, 300, 50);
		
		aboutUI.addButton("NormalTower", "TowerNormalFull", 40, 120, 40, 40);
		aboutUI.addButton("SniperTower", "TowerSniperFull", 40, 170, 40, 40);
		aboutUI.addButton("MachineTower", "TowerMachineFull", 40, 220, 40, 40);
		
		aboutUI.addButton("NormalEnemy", "NormalEnemy", 650, 50, 40, 40);
		aboutUI.addButton("SmallerEnemy", "SmallerEnemy", 650, 100, 40, 40);
		aboutUI.addButton("TankerEnemy", "TankerEnemy", 650, 150, 40, 40);
		aboutUI.addButton("BossEnemy", "boss", 650, 200, 40, 40);
	}

	public void updateButton() {
		WriteInformation();
		if (Mouse.next())
			if (Mouse.isButtonDown(0)) {
				if (aboutUI.isButtonClicked("BackMenu")) {
					StateManager.setState(GameState.MAINMENU);			// Neu click back menu, quay lai Menu
				}
			}
	}

	public void update() {
		DrawQuadTex(backGround, 0, 0, 1300, 1200);
		aboutUI.draw();
		updateButton();

	}

	public void WriteInformation() {
		aboutUI.drawStringSmall(40, 80, "Tower");
		aboutUI.drawStringSmall(120, 80, "Type");	
		aboutUI.drawStringSmall(200, 80, "Shooting Range");		// Sat thuong gay cho quan dich
		aboutUI.drawStringSmall(350, 70, "Time between");
		aboutUI.drawStringSmall(350, 90, "shots");
		aboutUI.drawStringSmall(480, 80, "Money");

		aboutUI.drawStringSmall(120, 125, "Normal");
		aboutUI.drawStringSmall(120, 175, "Sniper");
		aboutUI.drawStringSmall(120, 225, "Machine");
		
		aboutUI.drawStringSmall(240, 125, "15");
		aboutUI.drawStringSmall(240, 175, "15");
		aboutUI.drawStringSmall(240, 225, "15");
		
		aboutUI.drawStringSmall(390, 125, "2");
		aboutUI.drawStringSmall(390, 175, "1");
		aboutUI.drawStringSmall(390, 225, "4");
		
		aboutUI.drawStringSmall(480, 125, "$25");
		aboutUI.drawStringSmall(480, 175, "$25");
		aboutUI.drawStringSmall(480, 225, "$25");
		
		aboutUI.drawStringSmall(650, 30, "Enemy");
		aboutUI.drawStringSmall(750, 30, "Type");
		aboutUI.drawStringSmall(850, 30, "Health");
		aboutUI.drawStringSmall(950, 30, "Speed");

		aboutUI.drawStringSmall(750, 60, "Normal");
		aboutUI.drawStringSmall(750, 110, "Smaller");
		aboutUI.drawStringSmall(750, 160, "Tanker");
		aboutUI.drawStringSmall(750, 210, "Boss");
		
		aboutUI.drawStringSmall(850, 60, "50");
		aboutUI.drawStringSmall(850, 110, "75");
		aboutUI.drawStringSmall(850, 160, "100");
		aboutUI.drawStringSmall(850, 210, "400");
		
		aboutUI.drawStringSmall(950, 60, "80");
		aboutUI.drawStringSmall(950, 110, "120");
		aboutUI.drawStringSmall(950, 160, "50");
		aboutUI.drawStringSmall(950, 210, "40");

		aboutUI.drawString(80, 300,
				"When you begin start game, you can next map or privious map. But you started game, you can't. ");

		aboutUI.drawString(20, 500, "Program made by: ");			
		aboutUI.drawString(50, 530, "PHAM QUANG TAI - 18021110 - K63T");
		aboutUI.drawString(50, 560, "LE DUC THANG - 18021160 - K63T");

	}

}
