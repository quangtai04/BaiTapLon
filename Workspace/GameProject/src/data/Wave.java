package data;

import java.util.ArrayList;
import static helpers.Clock.*;

public class Wave {
	private float timeSinceLastSpawn, spawnTime;
	private Enemy enemyType;
	private ArrayList<Enemy> enemyList;
	private int enemiesPerWave;
	private boolean waveCompleted;

	public Wave(Enemy enemyType, float spawTime, int enemiesPerWave) {
		this.spawnTime = spawTime;
		this.enemyType = enemyType;
		this.enemiesPerWave = enemiesPerWave;
		this.timeSinceLastSpawn = 0;
		this.enemyList = new ArrayList<Enemy>();
		this.waveCompleted = false;

		Spawn();
	}

	public void Update() {
		boolean allEnemilesDead = true;
		if (enemyList.size() < enemiesPerWave) {
			timeSinceLastSpawn += Delta();
			if (timeSinceLastSpawn > spawnTime) {
				Spawn();
				timeSinceLastSpawn = 0;
			}
		}
		for (Enemy e : enemyList) {
			if (e.isAlive()) {
				allEnemilesDead = false;
				e.Update();
				e.Draw();
			}
		}
		if(allEnemilesDead)
			waveCompleted = true;
	}

	public void Spawn() {
		enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), enemyType.getTileGrid(), 40, 40,
				enemyType.getSpeed(), enemyType.getHealth()));
	}

	public boolean isComplete() {
		return waveCompleted;
	}
	
	public ArrayList<Enemy> getEnemyList() {
		return enemyList;
	}
}
