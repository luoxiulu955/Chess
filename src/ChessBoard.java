
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;

/**
 * Load move file and create functionality of moving pieces
 * based on the move file.
 * 
 * @author Liang Xia
 *  @version 2.0
 * @since   02-26-2017 
 */
@SuppressWarnings("serial")
public class ChessBoard extends JFrame{
	private int[] box;							//chess board
	private List<Piece> pieces;					//piece objects
	private Map<Integer, Integer> positions; 	//map of position and piece id
	private ThreadManagement tm;				//thread management

	
	/**
	 * Constructor that initialize thread management
	 */
	public ChessBoard(BoardUtils board) {
		positions = board.getPositions();
		pieces = board.getPieces();
		box = board.getBoard();
		tm = new ThreadManagement();
	}
	
	/**
	 * Get the map of position and piece id
	 * @return	map
	 */
	public Map<Integer, Integer> getPositions() {
		return positions;
	}
	
	/**
	 * create threads to store possible move into shared data container
	 */
	public void createThread(int stepCounter){
		tm.createThread(stepCounter, box, pieces, positions);
	}

	/**
	 * Get piece id and its optimum move
	 * @param color color of piece
	 * @return	list of id and optimum move position
	 */
	public List<Integer> getOptimumMove(int color) {
		return tm.getOptimumMove(box, color, pieces);
	}
	
	/**
	 * Change the piece's position and save the move history in the list.
	 * @param source	the source of position
	 * @param destination	the destination of position
	 * @param record	the move history list
	 */
	public boolean changePosition(int source, int destination) {
		int sourceId = positions.get(source);
		int destinationId = -1;	
		if (box[source] == 1) {
			if (pieces.get(sourceId).isValidMove(destination, box, pieces, positions)) {
				if (box[destination] == 1) {
					destinationId = positions.get(destination);
					pieces.get(destinationId).setStatus(false);
				}
				pieces.get(sourceId).setPosition(destination);
				positions.put(source, -1);
				positions.put(destination, sourceId);
				box[destination] = 1;
				box[source] = 0;
			} else {
				return false;
			}
		} else {
			throw new IllegalArgumentException("No piece is available on this position");
		}
		return true;
	}

	/**
	 * Get the list of the pieces
	 * @return	the list of pieces
	 */
	public List<Piece> getPieces() {
		return pieces;
	}
	
	/**
	 * Get the list of possible moves
	 * @param source the position of piece
	 * @return	list of possible moves
	 */
	public List<Integer> getValidMoves(int source) {
		int pieceId = positions.get(source);
		return pieces.get(pieceId).getValidMoves(box, pieces, positions);
	}
}