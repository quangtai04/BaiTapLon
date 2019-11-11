package data;

public class WaveManager {
	private float timeSinceLastWave, timeBetweenEnemies;
	private int waveNumber, enemiesPerWave;
	private Enemy[] enemyTypes;
	private Wave currentWave;
	
	public WaveManager(Enemy[] enemyTypes, float timeBetweenEnemies, int enemiesPerWave) {
		this.enemyTypes = enemyTypes;
		this.timeBetweenEnemies = timeBetweenEnemies;
		this.enemiesPerWave = enemiesPerWave;
		this.timeSinceLastWave = 0;
		 this.waveNumber = 0;
		 
		 this.currentWave = null;
		 newWave();
	}
	public void update() {
		if(!currentWave.isCompleted()) {
			currentWave.update();
		}
		else {
			newWave();
		}

	}
	public void newWave()	{
		currentWave = new Wave(enemyTypes, timeBetweenEnemies, enemiesPerWave);
		waveNumber++;
		System.out.println("Beginning Wave " + waveNumber);
	}
	public Wave getCurrentWave() {
		return currentWave;
	}
	public int getWaveNumber() {
		return waveNumber;
	}
	public boolean isComplete() {
		return currentWave.isCompleted();
	}
	public void setEnemiesPerWave(int enemiesPerWave) {
		this.enemiesPerWave = enemiesPerWave;
	}
}
