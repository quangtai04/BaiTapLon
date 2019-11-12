package data;

public enum TileType {

	Grass("grass", true), GrassNot("grassNot",false), Dirt("dirt", false), Water("water", false), NULL("water", false),DirtStart("dirtStart", false),DirtEnd("dirtEnd", false);

	String textureName;
	boolean buildable;

	TileType(String textureName, boolean buildable) {
		this.textureName = textureName;
		this.buildable = buildable;
	}
}
