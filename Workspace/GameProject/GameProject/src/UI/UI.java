package UI;

import java.awt.Font;
import java.util.*;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public class UI {

	private ArrayList<Button> buttonList;
	private ArrayList<Menu> menuList;
	private TrueTypeFont font, fontSmall;
	private Font awtFont;
	private Font awtFontSmall;
	private Font awtFontBig;
	private TrueTypeFont fontBig;
	
	public UI() {
		buttonList = new ArrayList<Button>();
		menuList = new ArrayList<UI.Menu>();
		awtFont = new Font("Times New Roman", Font.BOLD, 20);
		font = new TrueTypeFont(awtFont, false);
		awtFontSmall = new Font("Times New Roman", Font.BOLD, 15);
		fontSmall = new TrueTypeFont(awtFontSmall, false);
		awtFontBig = new Font("Times New Roman", Font.BOLD, 50);
		fontBig = new TrueTypeFont(awtFontBig, false);
	}

	public void drawString(int x, int y, String text) {
		font.drawString(x, y, text);
	}
	public void drawStringSmall(int x, int y, String text) {
		fontSmall.drawString(x, y, text);
	}
	public void drawStringBig(int x, int y, String text) {
		fontBig.drawString(x, y, text);
	}
	public void addButton(String name, String textureName, int x, int y) {
		buttonList.add(new Button(name, QuickLoad(textureName), x, y));
	}
	public void addButton(String name, String textureName, int x, int y, int width, int height) {
		buttonList.add(new Button(name, QuickLoad(textureName), x, y, width, height));
	}
	public void removeButton(String name) {
		int i=0;
		while(i<buttonList.size()) {
			if(buttonList.get(i).getName().equals(name))	{
				buttonList.remove(i);
				continue;
			}
			else {
				i++;
			}
			
		}
	}
	public boolean isButtonClicked(String buttonName) {
		Button b = getButton(buttonName);
		float mouseY = HEIGHT - Mouse.getY() - 1;
		if (Mouse.getX() > b.getX() && Mouse.getX() < b.getX() + b.getWidth() && mouseY > b.getY()
				&& mouseY < b.getY() + b.getHeight()) {
			return true;
		}
		return false;

	}

	public Button getButton(String buttonName) {
		for (Button b : buttonList) {
			if (b.getName().equals(buttonName)) {
				return b;
			}
		}
		return null;
	}

	public void createMenu(String name, int x, int y, int width, int height, int optionswidth, int optionheight) {
		menuList.add(new Menu(name, x, y, width, height, optionswidth, optionheight));
	}

	public Menu getMenu(String name) {
		for (Menu m : menuList) {
			if (name.equals(m.getName())) {
				return m;
			}
		}
		return null;
	}

	public void draw() {
		for (Button b : buttonList) {
			DrawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
		}
		for (Menu m : menuList) {
			m.draw();
		}
	}

	public class Menu {
		private ArrayList<Button> menuButtons;
		private int x, y, width, height, buttonAmount, optionswidth, optionheight, padding;
		String name;

		public Menu(String name, int x, int y, int width, int height, int optionswidth, int optionheight) {
			this.name = name;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.buttonAmount = 0;
			this.menuButtons = new ArrayList<Button>();
			this.optionswidth = optionswidth;
			this.optionheight = optionheight;
			this.padding = (width - (optionswidth * TILE_SIZE)) / (optionswidth + 1);
		}

		public void addButton(Button b) {
			setButton(b);
		}
		
		public void quickAdd(String name, String buttonTextureName) {
			Button b = new Button(name, QuickLoad(buttonTextureName), 0, 0);
			setButton(b);
		}
		public void quickAdd(String name, String buttonTextureName, int width, int height) {
			Button b = new Button(name, QuickLoad(buttonTextureName), 0, 0, width, height);
			setButton(b);
		}
		
		public void quickAdd(String name, String buttonTextureName, int width, int height, int TileSize) {
			Button b = new Button(name, QuickLoad(buttonTextureName), 0, 0, width, height);
			setButton(b,TileSize);
		}
		
		private void setButton(Button b) {
			if (optionswidth != 0)
				b.setY(y + (buttonAmount / optionswidth* 2 ) * TILE_SIZE);
			b.setX(x + (buttonAmount %2) * (padding + TILE_SIZE) + padding);
			buttonAmount++;
			menuButtons.add(b);
		}
		private void setButton(Button b, int TileSize) {
			if (optionswidth != 0)
				b.setY(y + (buttonAmount / optionswidth* 2 ) * TileSize);
			b.setX(x + (buttonAmount %2) * (padding + TileSize) + padding);
			buttonAmount++;
			menuButtons.add(b);
		}

		public boolean isButtonClicked(String buttonName) {
			Button b = getButton(buttonName);
			float mouseY = HEIGHT - Mouse.getY() - 1;
			if (Mouse.getX() > b.getX() && Mouse.getX() < b.getX() + b.getWidth() && mouseY > b.getY()
					&& mouseY < b.getY() + b.getHeight()) {
				return true;
			}
			return false;

		}

		public Button getButton(String buttonName) {
			for (Button b : menuButtons) {
				if (b.getName().equals(buttonName)) {
					return b;
				}
			}
			return null;
		}

		public void draw() {
			for (Button b : menuButtons)
				DrawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
		}

		public String getName() {
			return name;
		}
		
	}
}
