import java.util.Collections;
import java.util.List;

/**
 *  Store the piece id and its possible moves in the move object
 * 
 * @author Liang Xia
 *  @version 1.0
 * @since   02-26-2017 
 */
public class Move{

	private int[] box;
	private int id;
	private int piecePosition;
	private int optimumMove = -1;
	private List<Integer> possibleMoves;
	
	/**
	 * constructor of move object
	 * @param id	piece id
	 * @param piecePosition	piece current position
	 * @param box	board
	 * @param possibleMoves	list of possible moves
	 */
	public Move(int id, int piecePosition, int[] box, List<Integer> possibleMoves) {
		this.box = box;
		this.id = id;
		this.piecePosition = piecePosition;
		this.possibleMoves = possibleMoves;		
	}
	
	/**
	 * Get current piece position
	 * @return piece position
	 */
	public int getPiecePosition(){
		return piecePosition;
	}
	
	/**
	 * Get current piece id
	 * @return	current piece id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get list of possible moves
	 * @return	list of possible moves
	 */
	public List<Integer> getPossibleMoves() {
		return possibleMoves;
	}
	
	/**
	 * Calculate the optimum move of the piece
	 */
	public void updateOptimumMove() {
		if (possibleMoves.size() > 0) {
			if (id <= 15) {
				Collections.sort(possibleMoves, new MoveListComparator(box, 1));
			} else {
				Collections.sort(possibleMoves, new MoveListComparator(box, -1));
			}
			optimumMove = possibleMoves.get(0);
		}
	}
	
	/**
	 * Get the optimum move of the piece
	 * @return the optimum move
	 */
	public int getOptimumMove() {
		return optimumMove;
	}
}
