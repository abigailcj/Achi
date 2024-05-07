package achi;

public class GameCell {

	private int whoOwnsMe; // 1=X, -1=O, 0 means no one.

	/** Ownership is to be "no one". */
	public GameCell( ) {
		whoOwnsMe = 0;
	}
	
	// Setters and Getters:
	
	/* Which player has a piece in this cell, if any. 
	 * @returns 1 if X had a piece there, -1 if O has a piece,
	 * 0 if no one has a piece there.
	 */
	public int getWhoOwnsMe( ) {
		return whoOwnsMe;
	}

	/** Standard setter for the owner of the cell. */
	public void setWhoOwnsMe(int whoOwnsMe) {
		this.whoOwnsMe = whoOwnsMe;
	}

	/** Standard "toString" method. Converts integers, as  
	 *  used internally, to X, O, and –.
	 */
	public String toString( ) {
		if ( whoOwnsMe == 1 )
			return "X";
		else if ( whoOwnsMe == -1 )
			return "O";
		else
			return "—";
	}
	
	
}
