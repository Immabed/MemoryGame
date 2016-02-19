/**
 * A tile object for a memory matching game.
 * @author Brady Coles
 *
 */
public class Tile {

	private char value = ' ';
	private boolean isVisible = false;
	
	public Tile(char value) {
		this.value = value;
	}
	
	public char getValue() {
		return value;
	}
	
	public boolean isVisible() {
		return isVisible;
	}
	
	public void hide() {
		isVisible = false;
	}
	
	public void reveal() {
		isVisible = true;
	}
}
