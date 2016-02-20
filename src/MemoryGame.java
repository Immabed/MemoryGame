import java.util.Random;

public class MemoryGame {

	private Tile[][] board;
	
	
	
	private void generateBoard(int width, int height) {
		// Ensure even number of tiles
		if (width % 2 != 0 && height % 2 != 0) {
			System.out.printf("Invalid board: %d x %d", width, height);
			System.out.printf("Creating board of size %d x %d", width + 1, height);
			width ++;
		}
		Tile[] shuffleArray = new Tile[width * height];
		int tileValue = 0;
		int tileWidth = Integer.toString(width * height / 2).length();
		for (int i = 0; i + 1 < shuffleArray.length; i += 2) {
			shuffleArray[i] = new Tile(
					String.format("%0"+tileWidth+"d", tileValue));
			shuffleArray[i+1] = new Tile(
					String.format("%0"+tileWidth+"d", tileValue));
			tileValue++;
		}
		shuffleArray = shuffleArray(shuffleArray);
		
		board = new Tile[width][height];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = shuffleArray[i * height + j];
			}
		}
	}
	
	
	private Tile[] shuffleArray(Tile[] array) {
		return shuffleArray(array, 0);
	}
	
	private Tile[] shuffleArray(Tile[] array, int minimum) {
		if (minimum + 1 >= array.length) {
			return array;
		}
		else {
			Random random = new Random();
			int randomIndex = random.nextInt(array.length - minimum) + minimum;
			Tile[] newArray = new Tile[array.length];
			newArray[0] = array[randomIndex];
			int newArrayIndex = 1;
			for (int i = 0; i < array.length; i++) {
				if (i != randomIndex) {
					newArray[newArrayIndex] = array[i];
					newArrayIndex++;
				}
			}
			return shuffleArray(newArray, minimum + 1);
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
