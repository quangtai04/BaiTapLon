package data;

import static helpers.Artist.QuickLoad;

import java.util.ArrayList;

public class Game {
	
	private TileGrid grid;
	private Player player;
	private WaveManager  waveManager;
	

	
	public Game(int [][]map) {
		grid = new TileGrid(map);
		waveManager = new WaveManager(new Enemy(QuickLoad("ufo"), grid.GetTile(10, 8), grid, 40, 40, 70),2,2);
		player = new Player(grid, waveManager);
	}
	
	public void update() {
		
		grid.Draw();
		waveManager.update();
		player.update();
		
	}
	
}
