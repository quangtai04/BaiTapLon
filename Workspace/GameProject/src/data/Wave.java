package data;

import java.util.ArrayList;
import static helpers.Clock.*;
import static helpers.Artist.*;

public class Wave {
	private float timeSinceLastspawn, spawnTime;
	private Enemy enemyType;
	private ArrayList<Enemy> enemyList;
	private int enemiesPerWave;
	private boolean waveCompleted;

	public Wave(Enemy enemyType, float spawTime, int enemiesPerWave) {
		this.spawnTime = spawTime;
		this.enemyType = enemyType;
		this.enemiesPerWave = enemiesPerWave;
		this.timeSinceLastspawn = 0;
		this.enemyList = new ArrayList<Enemy>();
		this.waveCompleted = false;

		spawn();
	}

	public void update() {
		boolean allEnemilesDead = true;
		if (enemyList.size() < enemiesPerWave) {
			timeSinceLastspawn += Delta();
			if (timeSinceLastspawn > spawnTime) {
				spawn();
				timeSinceLastspawn = 0;
			}
		}
		for (Enemy e : enemyList) {
			if (e.isAlive()) {
				allEnemilesDead = false;
				e.update();
				e.draw();
			}
		}
		if(allEnemilesDead)
			waveCompleted = true;
	}

	public void spawn() {
		enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), enemyType.getTileGrid(), TILE_SIZE, TILE_SIZE,
				enemyType.getSpeed(), enemyType.getHealth()));
	}

	public boolean isComplete() {
		return waveCompleted;
	}
	
	public ArrayList<Enemy> getEnemyList() {
		return enemyList;
	}
}
