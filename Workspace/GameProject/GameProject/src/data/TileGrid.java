package data;

import static helpers.Artist.TILE_SIZE;

import java.util.ArrayList;

public class TileGrid {
	public Tile[][] map;
	private int tilesWide, tilesHeigh;

	public TileGrid() {
		this.tilesWide = 20;
		this.tilesHeigh = 15;
		map = new Tile[tilesWide][tilesHeigh];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.GrassNot);
			}
		}
	}

	public TileGrid(int[][] newMap) {
		this.tilesWide = newMap[0].length;
		this.tilesHeigh = newMap.length;
		map = new Tile[tilesWide][tilesHeigh];

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				switch (newMap[j][i]) {
				case 0:
					map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Grass);
					break;
				case 1:
					map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Dirt);
					break;
				case 2:
					map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Water);
					break;
				case 3:
					map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.DirtStart);
					break;
				case 4:
					map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.DirtEnd);
					break;
				case 5: 
					map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.GrassNot);
				}

			}
		}
	}

	public void setTile(int xCoord, int yCoord, TileType type) {
		map[xCoord][yCoord] = new Tile(xCoord * TILE_SIZE, yCoord * TILE_SIZE, TILE_SIZE, TILE_SIZE, type);
	}

	public Tile getTile(int xPlace, int yPlace) {
		if (xPlace < tilesWide && yPlace < tilesHeigh && xPlace > -1 && yPlace > -1)
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

	public void draw() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j].draw();
			}
		}
	}

	public ToaDo getXYStart() {
		ToaDo start = new ToaDo();
		for (int i = 0; i < tilesWide; i++)
			for (int j = 0; j < tilesHeigh; j++) {
				if (this.getTile(i, j).getType() == TileType.DirtStart) {
					start.x = i; // x
					start.y = j; // y
					break;
				}
			}
		if (this.getTile(start.x - 1, start.y).getType() == TileType.Dirt) {
			start.x -= 1;
		}
		if (this.getTile(start.x + 1, start.y).getType() == TileType.Dirt) {
			start.x += 1;
		}
		if (this.getTile(start.x, start.y - 1).getType() == TileType.Dirt) {
			start.y -= 1;
		}
		if (this.getTile(start.x, start.y + 1).getType() == TileType.Dirt) {
			start.y += 1;
		}

		return start;
	}

}
