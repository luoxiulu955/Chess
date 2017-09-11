import java.util.List;
import java.util.Map;

/**
 * Stores common information of different types of pieces
 * 
 * @author Liang Xia
 *  @version 2.0
 * @since   02-26-2017 
 */
public abstract class Piece {
	public enum PColor {BLACK, WHITE};
	public enum PType {KING, KNIGHT, PAWN, QUEEN, ROOK, BISHOP};
	protected int position;
	protected boolean alive;
	protected int id;
	protected PColor color;
	protected PType type;
	
	/** 
	 * Piece constructor. Initialize piece with provided information
	 * including id, position, status, color and type
	 * 
	 */
	public Piece(int id, int position, boolean alive, PColor color, PType type){
		this.id = id;
		this.position = position;
		this.alive = alive;
		this.color = color;
		this.type = type;
	}
	
	/**
	 * Get color of piece
	 * @return	color
	 */
	public PColor getColor(){
		return color;
	}
	
	/**
	 * Get type of piece
	 * @return	type
	 */
	public PType getType(){
		return type;
	}
	
	/**
	 * Get current position of piece
	 * @return	current position
	 */
	public int getPosition() {
		return position;
	}
	
	/**
	 * Set current position of piece
	 * @param position current position
	 */
	public void setPosition(int position) {
		if (position < 0 || position > 63) {
			throw new IllegalArgumentException("Invalid position");
		} 
		this.position = position;
	}
	
	/**
	 * Get id of piece
	 * @return	id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Set id of piece
	 * @param id id of piece
	 */
	public void setId(int id) {
		if (id < 0) {
			throw new IllegalArgumentException("Invalid ID");
		} 
		this.id = id;
	}
	
	/**
	 * Get status of piece
	 * @return	status
	 */
	public boolean getStatus() {
		return alive;
	}
	
	/**
	 * Set status of piece
	 * @param alive status of piece
	 */
	public void setStatus(boolean alive) {
		this.alive = alive;
	}
	
	/**
	 * check if the destination which is chosen by player is valid move
	 * @param destination	destination position
	 * @param box	board
	 * @param pieces	list of piece
	 * @param positions	map of position and piece id
	 * @return	result
	 */
	public boolean isValidMove(int destination, int[]box,
			   List<Piece> pieces, Map<Integer, Integer> positions) {
		boolean isValid = false;
		for(Integer move : getValidMoves(box, pieces, positions)) {
			if(move == destination) {
				isValid = true;
			}
		}
		return isValid;
	}
	
	/**
	 * Get list of valid moves based on each type of piece rules
	 * @param box	board
	 * @param pieces	list of pieces
	 * @param positions	map of position and piece id
	 * @return
	 */
	public abstract List<Integer> getValidMoves(int[]box, List<Piece> pieces,
												Map<Integer, Integer> positions);
}
