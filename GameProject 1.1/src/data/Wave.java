package data;

import java.util.ArrayList;
import static helpers.Clock.*;

public class Wave {
	private float timeSinceLastSpawn, spawnTime;
	private Enemy enemyType;
	private ArrayList<Enemy> enemyList;
	
	public Wave (float spawTime, Enemy enemyType) {
		this.spawnTime = spawTime;
		this.enemyType = enemyType;
		timeSinceLastSpawn = 0;
		enemyList = new ArrayList<Enemy>();
	}

	public void Update()	{
		timeSinceLastSpawn += Delta();
		if(timeSinceLastSpawn > spawnTime) {
			Spawn();
			timeSinceLastSpawn = 0;
		}
		
		for(Enemy e:enemyList)	{
			e.Update();
			e.Draw();
		}
	}
	public void Spawn() {
		enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), 40, 40, enemyType.getSpeed()));
	}
}
