import java.util.Random;

/**
 * 
 * @author Brady Coles
 *
 */
public class MemoryGame {

	/*
	 * The game board, is arranged board[row][column].
	 */
	private Tile[][] board;
	
	public MemoryGame() {
		generateBoard(5, 5);
	}
	
	
	/**
	 * Creates and populates the board with tiles starting at 0 and going up as
	 * needed. If the height and width parameters make a board with an odd number
	 * of tiles, the width is increased to accommodate an even number of tiles.
	 * @param width The width to make the board, might be increased by 1 to
	 * ensure that the board holds an even number of tiles.
	 * @param height The height of the board.
	 */
	private void generateBoard(int width, int height) {
		// Ensure even number of Tiles
		if (width % 2 != 0 && height % 2 != 0) {
			System.out.printf("Invalid board: %d x %d%n", width, height);
			System.out.printf("Creating board of size %d x %d%n", width + 1, height);
			width ++;
		}
		// Create the Tiles
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
		// Shuffle the Tiles
		shuffleArray = shuffleArray(shuffleArray);
		
		// Create and populate the board with the shuffled Tiles
		board = new Tile[height][width];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = shuffleArray[i * width + j];
			}
		}
	}
	
	/**
	 * Overload method, shuffles an entire array. This should be called when
	 * an entire array should be shuffled.
	 * @param array an array to return a shuffled copy of.
	 * @return A shuffled array of the same elements of the initial array. Note,
	 * the Tile elements are NOT copies, they are references to the same Tile
	 * objects in the original array.
	 */
	private Tile[] shuffleArray(Tile[] array) {
		return shuffleArray(array, 0);
	}
	
	/** 
	 * Recursively shuffles an array of Tile elements.
	 * @param array An array to shuffle.
	 * @param minimum Used for recursion, the elements before minimum are
	 * considered shuffled.
	 * @return
	 */
	private Tile[] shuffleArray(Tile[] array, int minimum) {
		// Base case, only one element left to shuffle.
		if (minimum + 1 >= array.length) {
			return array;
		}
		// Recursive case
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
	
	/**
	 * Displays the board in the console.
	 */
	private void showBoard() {
		// Icon values
		String horizontalSeparator = "-";
		String verticalSeparator = "|";
		String hiddenTile = "*";
		// Get width values for formatting reasons
		int tileWidth = board[0][0].getValue().length();
		int maxColumnNumberWidth = Integer.toString(board[0].length).length();
		int displayedColumnNumberWidth = Integer.max(tileWidth, maxColumnNumberWidth);
		int tileSpacing = displayedColumnNumberWidth + 1 - tileWidth;
		int maxRowNumberWidth = Integer.toString(board.length).length();
		int rowNumberSeparatorWidth = verticalSeparator.length();
		// Column numbers
		String output = repeatString(" ", maxRowNumberWidth + rowNumberSeparatorWidth);
		for (int i = 0; i < board[0].length; i++) {
			output += String.format("%-"+displayedColumnNumberWidth+"d", i+1);
			output += " ";
		}
		output += "\n";
		// Separator
		output += repeatString(" ", maxRowNumberWidth + rowNumberSeparatorWidth);
		output += repeatString(horizontalSeparator, 
				board[0].length * (displayedColumnNumberWidth + 1));
		output += "\n";
		// Rows
		for (int i = 0; i < board.length; i++) {
			output += String.format("%"+maxRowNumberWidth+"d", i+1);
			output += verticalSeparator;
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].isVisible()) {
					output += board[i][j].getValue();
				}
				else {
					output += repeatString(hiddenTile, board[i][j].getValue().length());
				}
				output += repeatString(" ", tileSpacing);
			}
			output += "\n";
		}
		
		// Print to Screen
		System.out.print(output);
	}
	
	/**
	 * Creates a repeated string.
	 * @param s The string to repeat.
	 * @param n The number of times to repeat it, must be positive.
	 * @return The string s repeated n times.
	 */
	private String repeatString(String s, int n) {
		if (n <= 0) {
			return s;
		}
		// Creates a string with s repeated n times.
		return String.format("%0" + n + "d", 0).replace("0",s);
	}
	
	
	public static void main(String[] args) {
		MemoryGame game = new MemoryGame();
		game.showBoard();
	}

}
