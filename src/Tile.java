/**
 * A tile object for a memory matching game.
 * @author Brady Coles
 *
 */
public class Tile {

	private String value = "";
	private boolean isVisible = false;
	
	public Tile(String value) {
		this.value = value;
	}
	
	public String getValue() {
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
