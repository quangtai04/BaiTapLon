package data;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import static helpers.Clock.*;
import static helpers.Artist.TILE_SIZE;
public class Wave {
	private float timeSinceLastSpawn, spawnTime;
	private Enemy[] enemyTypes;
	private CopyOnWriteArrayList<Enemy> enemyList;
	private int enemiesPerWave, enemiesSpawned;
	private boolean waveCompleted;

	public Wave(Enemy[] enemyTypes, float spawTime, int enemiesPerWave) {
		this.spawnTime = spawTime;
		this.enemyTypes = enemyTypes;
		this.enemiesPerWave = enemiesPerWave;
		this.enemiesSpawned = 0;
		this.timeSinceLastSpawn = 0;
		this.enemyList = new CopyOnWriteArrayList<Enemy>();
		this.waveCompleted = false;

		spawn();
	}

	public void update() {
		//Assume all enemies are dead, until for loop proves otherwise 
		boolean allEnemilesDead = true;
		if (enemiesSpawned < enemiesPerWave) {
			timeSinceLastSpawn += Delta();
			if (timeSinceLastSpawn > spawnTime) {
				spawn();
				timeSinceLastSpawn = 0;
			}
		}
		for (Enemy e : enemyList) {
			if (e.isAlive()) {
				allEnemilesDead = false;
				e.update();
				e.draw();
			}	else {
				enemyList.remove(e);
			}
		}
		if(allEnemilesDead)
			waveCompleted = true;
	}

	public void spawn() {
		int enemyChosen = 0;
		Random random = new Random();
		enemyChosen = random.nextInt(enemyTypes.length);
		enemyList.add(new Enemy(enemyTypes[enemyChosen].getTexture(), enemyTypes[enemyChosen].getStartTile(), enemyTypes[enemyChosen].getTileGrid(), TILE_SIZE, TILE_SIZE,
				enemyTypes[enemyChosen].getSpeed(),enemyTypes[enemyChosen].getHealth()));
		enemiesSpawned ++;
	}

	public boolean isCompleted() {
		return waveCompleted;
	}
	public CopyOnWriteArrayList<Enemy> getEnemyList(){
		
		return enemyList;
	}
}
