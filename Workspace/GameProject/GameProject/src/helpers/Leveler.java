package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.text.AbstractDocument.BranchElement;

import data.Tile;
import data.TileGrid;
import data.TileType;

public class Leveler {
	public static void SaveMap(String mapName, TileGrid grid) {
		String mapData = "";
		for (int i = 0; i < grid.getTilesWide(); i++) {
			for (int j = 0; j < grid.getTilesHeigh(); j++) {
				mapData += getTileID(grid.getTile(i, j));
			}
		}
		try {
			File file = new File(mapName);
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(mapData);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static TileGrid LoadMap(String mapName) {
		TileGrid grid = new TileGrid();
		try {
			BufferedReader br = new BufferedReader(new FileReader(mapName));
			String data = br.readLine();
			for (int i = 0; i < grid.getTilesWide(); i++) {
				for (int j = 0; j < grid.getTilesHeigh(); j++) {
					grid.setTile(i, j, getTileType(
							data.substring(i * grid.getTilesHeigh() + j, i * grid.getTilesHeigh() + j + 1)));
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return grid;
	}

	public static TileType getTileType(String ID) {
		TileType type = TileType.NULL;
		switch (ID) {
		case "0":
			type = TileType.Grass;
			break;
		case "1":
			type = TileType.Dirt;
			break;
		case "2":
			type = TileType.Water;
			break;
		case "3":
			type = TileType.DirtStart;
			break;
		case "4":
			type = TileType.DirtEnd;
			break;
		case "5":
			type = TileType.GrassNot;
			break;
		case "6":
			type = TileType.NULL;
			break;

		}

		return type;
	}

	public static String getTileID(Tile t) {
		String ID = "E";
		switch (t.getType()) {
		case Grass:
			ID = "0";
			break;
		case Dirt:
			ID = "1";
			break;
		case Water:
			ID = "2";
			break;
		case DirtStart:
			ID = "3";
			break;
		case DirtEnd:
			ID = "4";
			break;
		case GrassNot:
			ID = "5";
			break;
		case NULL:
			ID = "6";
			break;

		}

		return ID;
	}
}
