import java.util.Random;
import java.util.Scanner;

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
	
	/**
	 * Gets a valid location input from the user.
	 * @return An array where the first element is the row and the second element
	 * is the column of the intended tile.
	 */
	private int[] getLocationInput(Scanner keyboard) {
		int[] location = new int[2];
		while (true) {
			System.out.print("Enter the location of the tile you wish to flip <column row>: ");
			if (keyboard.hasNextInt()) {
				location[1] = keyboard.nextInt();
			}
			else {
				System.out.println("Please enter a valid location <column row>");
				keyboard.nextLine();
				continue;
			}
			if (keyboard.hasNextInt()) {
				location[0] = keyboard.nextInt();
			}
			else {
				System.out.println("Please enter a valid location <column row>");
				keyboard.nextLine();
				continue;
			}
			if (!(	  location[0] > 0 && location[0] <= board.length
				  &&  location[1] > 0 && location[1] <= board[0].length)) {
				System.out.printf("You entered an invalid location: %d %d%n",
						location[1], location[0]);
				keyboard.nextLine();
				continue;
			}
			if (board[location[0]-1][location[1]-1].isVisible()) {
				System.out.printf("The tile at %d %d is already visible.%n",
						location[1], location[0]);
				keyboard.nextLine();
				continue;
			}
			keyboard.nextLine();
			break;	
		}
		// correct for 0 indexing of board, but 1 indexing on visual.
		location[0] = location[0] - 1;
		location[1] = location[1] - 1;
		return location;
	}
	
	/**
	 * Checks if all tiles in board are visible.
	 * @return true if all tiles in board are visible. If any tile is hidden, 
	 * returns false.
	 */
	private boolean areAllVisible() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (!board[i][j].isVisible()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void play(int width, int height) {
		generateBoard(width, height);
		int turnCount = 0;
		Scanner keyboard = new Scanner(System.in);
		do {
			// Increment turn counter
			turnCount++;
			// Clear the console
			System.out.print(repeatString("\n", 20));
			// Flip first tile
			System.out.printf("Turn %d: Pick first tile.%n", turnCount);
			showBoard();
			int[] location1 = getLocationInput(keyboard);
			Tile tile1 = board[location1[0]][location1[1]];
			tile1.reveal();
			
			// Clear the console
			System.out.print(repeatString("\n", 20));
			// Flip second tile
			System.out.printf("Turn %d: Pick second tile.%n", turnCount);
			showBoard();
			System.out.printf("You flipped %s at %d %d%n", 
					tile1.getValue(), location1[1] + 1, location1[0] + 1);
			int[] location2 = getLocationInput(keyboard);
			Tile tile2 = board[location2[0]][location2[1]];
			tile2.reveal();
			
			// Clear the console
			System.out.print(repeatString("\n", 20));
			// Show result
			boolean result = false;
			if (tile1.getValue().equals(tile2.getValue())) {
				result = true;
			}
			System.out.printf("Turn %d: %n", turnCount);
			showBoard();
			System.out.printf("You flipped %s at %d %d and %s at %d %d%n", 
					tile1.getValue(), location1[1] + 1, location1[0] + 1,
					tile2.getValue(), location2[1] + 1, location2[0] + 1);
			if (result) {
				System.out.print("Congratulations, your tiles match!");
			}
			else {
				System.out.print("Try again, your tiles don't match.");
				tile1.hide();
				tile2.hide();
			}
			keyboard.nextLine();
			
		} while (!areAllVisible());
		
		// Clear the console
		System.out.print(repeatString("\n", 20));
		// Output Results
		showBoard();
		System.out.printf("You completed the game in %d turns!", turnCount);
	}
	
	
	
	
	public static void main(String[] args) {
		MemoryGame game = new MemoryGame();
		game.play(4, 4);
	}

}
