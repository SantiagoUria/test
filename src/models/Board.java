package models;

import java.util.Random;

public class Board {
	private Light[][] lights;
	private boolean completion;
	private int tries;
	private int size;

	public Board(int size) {
		this.size = size;
		lights = new Light[size][size];
		tries = 0;
		initializeBoard();
		completion = isCompleted();
	}

	private void initializeBoard() {
		Random random = new Random();
		for (int i = 0; i < lights.length; i++)
			for (int j = 0; j < lights.length; j++)
				lights[i][j] = new Light(random.nextBoolean());
	}

	public void switchLightValue(int row, int col) {
		hitNeighboursLights(row, col);
	}

	private void hitNeighboursLights(int row, int col) {
		if (validPosition(row, col)) {
			getLight(row, col).hitSwitch();
			if (col - 1 >= 0)
				getLightLeft(row, col).hitSwitch();
			if (col + 1 < lights.length)
				getLightRight(row, col).hitSwitch();
			if (row - 1 >= 0)
				getLightUp(row, col).hitSwitch();
			if (row + 1 < lights.length)
				getLightDown(row, col).hitSwitch();
			tries++;
		} else {
			System.out.println(":( Thats not a valid position. Try again!");
		}

	}

	public String toString() {
		StringBuilder board = new StringBuilder();
		board.append(getDashLine());
		for (Light[] lightRow : lights)
			board.append(rowToString(lightRow));
		board.append(getDashLine());
		board.append("tries: ").append(tries).append("\n");
		board.append(getDashLine());
		return board.toString();
	}

	public boolean isCompleted() {
		checkCompleted();
		return completion;
	}

	private void checkCompleted() {
		boolean ret = true;
		for (Light[] lightRow : lights)
			for (Light light : lightRow)
				if (!light.getState())
					ret = ret && false;
		completion = ret;
	}

	public int getSize() {
		return size;
	}

	public Light[][] getLights() {
		return lights;
	}

	private boolean validPosition(int row, int col) {
		return row >= 0 && row < lights.length && col >= 0 && col < lights.length;
	}

	public Light getLight(int row, int col) {
		return lights[row][col];
	}

	public Light getLightLeft(int row, int col) {
		return lights[row][col - 1];
	}

	public Light getLightRight(int row, int col) {
		return lights[row][col + 1];
	}

	public Light getLightUp(int row, int col) {
		return lights[row - 1][col];
	}

	public Light getLightDown(int row, int col) {
		return lights[row + 1][col];
	}

	private String rowToString(Light[] lightRow) {
		StringBuilder stringBuilder = new StringBuilder("- ");
		for (Light light : lightRow) {
			if (light.getState()) {
				stringBuilder.append(1).append(" ");
			} else {
				stringBuilder.append(0).append(" ");
			}
		}
		stringBuilder.append("-\n");
		return stringBuilder.toString();
	}

	private String getDashLine() {
		StringBuilder dashLine = new StringBuilder("---");
		for (int i = 0; i < lights.length * 2; i++)
			dashLine.append("-");
		dashLine.append("\n");
		return dashLine.toString();
	}

	public int getTries() {
		return tries;
	}

}