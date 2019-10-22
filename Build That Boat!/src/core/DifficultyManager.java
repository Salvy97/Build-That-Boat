package core;

class DifficultyManager 
{
	public static int getTreesNumber(int difficulty)
	{
		int treesNumber = 4;
		switch(difficulty)
		{
			case 1: treesNumber = 6;
					break;
			case 3: treesNumber = 2;
					break;
		}
		return treesNumber;
	}
	
	public static int getRocksNumber(int difficulty)
	{
		int rocksNumber = 2;
		switch(difficulty)
		{
			case 1: rocksNumber = 4;
					break;
			case 3: rocksNumber = 1;
					break;
		}
		return rocksNumber;
	}
	
	public static int getPlantsNumber(int difficulty)
	{
		int plantsNumber = 10;
		switch (difficulty)
		{
			case 1: plantsNumber = 12;
					break;
			case 3: plantsNumber = 6;
					break;
		}
		return plantsNumber;
	}
	
	public static int getOresNumber(int difficulty)
	{
		int oresNumber = 4;
		switch (difficulty)
		{
			case 1: oresNumber = 6;
					break;
			case 3: oresNumber = 2;
					break;
		}
		return oresNumber;
	}
	
	public static long getSpawnRate(int difficulty)
	{
		int spawnRate = 60;
		switch(difficulty)
		{
			case 1: spawnRate = 120;
					break;
			case 3: spawnRate = 40;
					break;
		}
		return spawnRate * 1000;
	}
}