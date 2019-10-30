package data;

import static helpers.Artist.*;

public class TileGrid {
	public Tile[][] map;
	private	int size = 40;
	public TileGrid() {
		map = new Tile[25][17];
		for (int i = 0; i < map.length; i++) {
			for(int j=0; j<map[i].length; j++)	{
				map[i][j] = new Tile(i*size, j*size, size, size, TileType.Grass);
			}
		}
	}
	
	public TileGrid(int [][]newMap) {
		map = new Tile[25][17];
		for(int i=0; i<map.length;i++) {
			for(int j=0; j<map[i].length; j++)	{
				switch(newMap[j][i])	{
					case 0: 
						map[i][j] = new Tile(i*size, j*size, size, size, TileType.Grass);
						break;
					case 1:
						map[i][j] = new Tile(i*size, j*size, size, size, TileType.Dirt);
						break;
					case 2:
						map[i][j] = new Tile(i*size, j*size, size, size, TileType.Water);
						break;
				}
			}
		}
	}
	
	public void setTile(int xCoord, int yCoord, TileType type) {
		map[xCoord][yCoord] = new Tile(xCoord*size, yCoord*size, size, size, type);
	}
	
	public Tile GetTile(int xPlace, int yPlace) {
		return map[xPlace][yPlace];
	}
	
	public void Draw() {
		for(int i=0; i<map.length; i++)	{
			for(int j=0; j<map[i].length; j++)	{
				map[i][j].Draw();
			}
		}
	}
}
