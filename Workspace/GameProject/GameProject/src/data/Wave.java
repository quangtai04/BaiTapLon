package data;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import helpers.Clock;
import helpers.SimpleAudioPlayer;

import static helpers.SimpleAudioPlayer.*;

import static helpers.Clock.*;
import static helpers.Artist.TILE_SIZE;

public class Wave {
	private float timeSinceLastSpawn, spawnTime;
	private Enemy[] enemyTypes;
	private CopyOnWriteArrayList<Enemy> enemyList;
	private int enemiesPerWave, enemiesSpawned;
	private static int waveNumber = 0;
	private boolean waveCompleted;
	private long lastTotalTime = 0, totalTime = 0;
	private int numberDie = 0;
	private SimpleAudioPlayer playDie = new SimpleAudioPlayer(
			"C:\\Users\\#HarryPotter\\Desktop\\Workspace\\GameProject\\GameProject\\src\\res\\Die.wav");
	private boolean played = false; // Kiem tra xem audio phat lan dau hay khong
	private boolean isAudio = true; // Bat, Tat audio

	public Wave(Enemy[] enemyTypes, float spawTime, int enemiesPerWave) {
		this.spawnTime = spawTime;
		this.enemyTypes = enemyTypes;
		this.enemiesPerWave = enemiesPerWave;
		this.enemiesSpawned = 0;
		this.timeSinceLastSpawn = 0;
		this.enemyList = new CopyOnWriteArrayList<Enemy>();
		this.waveCompleted = false;
		spawn();
		waveNumber = 1;
	}

	public void update() {
		// Assume all enemies are dead, until for loop proves otherwise
		boolean allEnemilesDead = true;
		if (enemiesSpawned < enemiesPerWave) {
			timeSinceLastSpawn += Delta();
			if (timeSinceLastSpawn > spawnTime) {
				spawn();
				waveNumber++;
				timeSinceLastSpawn = 0;
			}
		}
		for (Enemy e : enemyList) {
			if (e.isAlive()) {
				allEnemilesDead = false;
				e.update();
				e.draw();
			} else {
				enemyList.remove(e);
				if (played == false) {
					if(isAudio)
						playDie.play();
					played = true;
				} else {
					if(isAudio)
						playDie.restart();
				}
				lastTotalTime = Clock.getTime();
				numberDie++;
			}
			totalTime = Clock.getTime();
			if (numberDie > 0) {
				if (totalTime - lastTotalTime > 1000) {
					if(isAudio)
						playDie.pause();
					numberDie--;
				}
			}

		}
		if (allEnemilesDead && waveNumber == enemiesPerWave)
			waveCompleted = true;
	}

	public void spawn() {
		int enemyChosen = 0;
		Random random = new Random();
		enemyChosen = random.nextInt(enemyTypes.length);
		enemyList.add(new Enemy(enemyTypes[enemyChosen].getTexture(), enemyTypes[enemyChosen].getStartTile(),
				enemyTypes[enemyChosen].getTileGrid(), TILE_SIZE, TILE_SIZE, enemyTypes[enemyChosen].getSpeed(),
				enemyTypes[enemyChosen].getHealth()));
		enemiesSpawned++;
	}

	public boolean isCompleted() {
		return waveCompleted;
	}

	public CopyOnWriteArrayList<Enemy> getEnemyList() {
		return enemyList;
	}
	public void setIsAudio(boolean isAudio) {
		this.isAudio = isAudio;
	}
	public boolean getIsAudio() {
		return this.isAudio;
	}
}
