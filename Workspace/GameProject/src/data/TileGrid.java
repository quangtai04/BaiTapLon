package data;

import static helpers.Artist.*;

public class TileGrid {
	public Tile[][] map;
	private	int size = 40;
	private int tilesWide, tilesHeigh;
	public TileGrid() {
		map = new Tile[25][15];
		for (int i = 0; i < map.length; i++) {
			for(int j=0; j<map[i].length; j++)	{
				map[i][j] = new Tile(i*size, j*size, size, size, TileType.Grass);
			}
		}
	}
	
	public TileGrid(int [][]newMap) {
		this.tilesWide = newMap[0].length;
		this.tilesHeigh = newMap.length;
		map = new Tile[tilesWide][tilesHeigh];
		
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
		if(xPlace < tilesWide && yPlace<tilesHeigh && xPlace>-1 && yPlace>-1)
			return map[xPlace][yPlace];
		else {
			return new Tile(0, 0, 0, 0, TileType.NULL);
		}
	}
	
	public int getTilesWide() {
		return tilesWide;
	}

	public void setTilesWide(int tilesWide) {
		this.tilesWide = tilesWide;
	}

	public int getTilesHeigh() {
		return tilesHeigh;
	}

	public void setTilesHeigh(int tilesHeigh) {
		this.tilesHeigh = tilesHeigh;
	}

	public void Draw() {
		for(int i=0; i<map.length; i++)	{
			for(int j=0; j<map[i].length; j++)	{
				map[i][j].Draw();
			}
		}
	}
}
