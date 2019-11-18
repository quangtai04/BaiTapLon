package data;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.Clock;
import helpers.SimpleAudioPlayer;
import helpers.StateManager;
import helpers.StateManager.GameState;

import static helpers.Artist.*;

public class Information {
	private Texture backGround;
	private UI aboutUI;
	private boolean isAudio = true;
	private SimpleAudioPlayer clickMouse = new SimpleAudioPlayer("C:\\Users\\#HarryPotter\\Desktop\\Workspace\\GameProject\\GameProject\\src\\res\\click.wav");
	private long totalTimeLastClick = 0;

	public Information() {
		backGround = QuickLoad("backGroundInformation");
		aboutUI = new UI();
		aboutUI.addButton("BackMenu", "HomeMenu", 20, 20, 300, 50);

		aboutUI.addButton("NormalTower", "TowerNormalFull", 40, 120, 40, 40);
		aboutUI.addButton("SniperTower", "TowerSniperFull", 40, 170, 40, 40);
		aboutUI.addButton("MachineTower", "TowerMachineFull", 40, 220, 40, 40);

		aboutUI.addButton("NormalEnemy", "NormalEnemy", 650, 120, 40, 40);
		aboutUI.addButton("SmallerEnemy", "SmallerEnemy", 650, 170, 40, 40);
		aboutUI.addButton("TankerEnemy", "TankerEnemy", 650, 220, 40, 40);
		aboutUI.addButton("BossEnemy", "boss", 650, 270, 40, 40);
	}

	public void updateButton() {
		WriteInformation();
		if (Mouse.next())
			if (Mouse.isButtonDown(0)) {
				PlayClickButton();
				if (aboutUI.isButtonClicked("BackMenu")) {
					StateManager.setState(GameState.MAINMENU); // Neu click back menu, quay lai Menu
				}
			}
	}

	public void update() {
		DrawQuadTex(backGround, 0, 0, 1300, 1200);
		aboutUI.draw();
		updateButton();
		PauseClickButton();
	}

	public void WriteInformation() {
		aboutUI.drawStringSmall(40, 80, "Tower");
		aboutUI.drawStringSmall(120, 80, "Type");
		aboutUI.drawStringSmall(200, 80, "Shooting Range"); // Sat thuong gay cho quan dich
		aboutUI.drawStringSmall(350, 70, "Time between");
		aboutUI.drawStringSmall(350, 90, "shots");
		aboutUI.drawStringSmall(480, 80, "Money");

		aboutUI.drawStringSmall(120, 125, "Normal");
		aboutUI.drawStringSmall(120, 175, "Sniper");
		aboutUI.drawStringSmall(120, 225, "Machine");

		aboutUI.drawStringSmall(240, 125, "240");
		aboutUI.drawStringSmall(240, 175, "160");
		aboutUI.drawStringSmall(240, 225, "360");

		aboutUI.drawStringSmall(390, 125, "2");
		aboutUI.drawStringSmall(390, 175, "1");
		aboutUI.drawStringSmall(390, 225, "4");

		aboutUI.drawStringSmall(480, 125, "$25");
		aboutUI.drawStringSmall(480, 175, "$25");
		aboutUI.drawStringSmall(480, 225, "$25");

		aboutUI.drawStringSmall(650, 80, "Enemy");
		aboutUI.drawStringSmall(750, 80, "Type");
		aboutUI.drawStringSmall(850, 80, "Health");
		aboutUI.drawStringSmall(950, 80, "Speed");

		aboutUI.drawStringSmall(750, 125, "Normal");
		aboutUI.drawStringSmall(750, 175, "Smaller");
		aboutUI.drawStringSmall(750, 225, "Tanker");
		aboutUI.drawStringSmall(750, 275, "Boss");

		aboutUI.drawStringSmall(850, 125, "75");
		aboutUI.drawStringSmall(850, 177, "55");
		aboutUI.drawStringSmall(850, 225, "105");
		aboutUI.drawStringSmall(850, 275, "405");

		aboutUI.drawStringSmall(950, 125, "90");
		aboutUI.drawStringSmall(950, 175, "120");
		aboutUI.drawStringSmall(950, 225, "60");
		aboutUI.drawStringSmall(950, 275, "50");

		aboutUI.drawStringSmall(120, 280, "Damage to the enemy: 10");

		aboutUI.drawString(140, 400,"When you begin start game, you can next map or previous map. But you started game, you can't.");

		aboutUI.drawString(20, 500, "Program made by: ");
		aboutUI.drawString(50, 530, "PHAM QUANG TAI - 18021110 - K63T");
		aboutUI.drawString(50, 560, "LE DUC THANG - 18021160 - K63T");

	}
	private void PlayClickButton() {
		if(isAudio)	{
			clickMouse.restart();
			totalTimeLastClick = Clock.getTime();
		}
	}
	private void PauseClickButton()	{
		if(Clock.getTime() - totalTimeLastClick > 1000)	{
			clickMouse.pause();
		}
	}

	public boolean isAudio() {
		return isAudio;
	}

	public void setAudio(boolean isAudio) {
		this.isAudio = isAudio;
	}
	
}
