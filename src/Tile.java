/**
 * A tile object for a memory matching game.
 * @author Brady Coles
 *
 */
public class Tile {

	private String value = "";
	private boolean isVisible = false;
	
	/**
	 * Creates a tile with a given value. Starts not visible.
	 * @param value A String value for the tile to hold.
	 */
	public Tile(String value) {
		this.value = value;
	}
	
	/**
	 * Return the value of the tile.
	 * @return The tile's value.
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Returns the visibility of the tile.
	 * @return true if visible, false if not visible.
	 */
	public boolean isVisible() {
		return isVisible;
	}
	
	/**
	 * Sets visibility to false.
	 */
	public void hide() {
		isVisible = false;
	}
	
	/**
	 * Sets visibility to true.
	 */
	public void reveal() {
		isVisible = true;
	}
	
	/**
	 * Checks if the tile value of two tiles are equal.
	 * @param otherTile A tile to check.
	 * @return if the value of both tiles are the same, returns true.
	 */
	public boolean equals(Tile otherTile) {
		if (otherTile == null) {
			return false;
		}
		if (this.value.equals(otherTile.value)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Return a string detailing the visibility and value of the tile.
	 * @return A string detailing the visibility and value of the tile.
	 */
	public String toString() {
		if (isVisible) {
			return String.format("Visible tile with value %s", value);
		}
		else {
			return String.format("Hidden tile with value %s", value);
		}
	}
}
