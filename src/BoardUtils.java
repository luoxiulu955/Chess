import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  class of Board utils that initializes the pieces and board
 * 
 * @author Liang Xia
 *  @version 1.0
 * @since   02-26-2017 
 */
public class BoardUtils {
	private int[] box;	//chess board
	private List<Piece> pieces;	//piece objects
	private Map<Integer, Integer> positions; //map of position and piece id
	
	/**
	 * Constructor that initialize piece objects and their positions on the board
	 */
	public BoardUtils(){
		pieces = new ArrayList<Piece>();
		box = new int[64];
		positions = new HashMap<>();
		initBoard();
		initPieces();
	}
	
	/**
	 * Get the board
	 * @return	box
	 */
	public int[] getBoard() {
		return box;
	}
	
	/**
	 * Get the piece list
	 * @return	list of pieces
	 */
	public List<Piece> getPieces() {
		return pieces;
	}
	
	/**
	 * Get the map of position and piece id 
	 * @return	return map
	 */
	public Map<Integer, Integer> getPositions() {
		return positions;
	}
	
	/**
	 * Initialize the array of chess board and mark the positions
	 * which are occupied
	 */
	private void initBoard() {
		for (int i = 0; i < 16; i++) {
			box[i] = 1;
		}
		for (int j = 16; j < 48; j++) {
			box[j] = 0;
			positions.put(j, -1);
		}
		for (int k = 48; k < 64; k++) {
			box[k] = 1;
		}
	}
	
	/**
	 * Initialize the all the pieces 
	 */
	private void initPieces() {
		for (int i = 0; i < 8; i++) {
			pieces.add(i, new Pawn(i, 48+i, true, Piece.PColor.WHITE));
			positions.put(48+i, i);
		}
		pieces.add(8, new Rook(8, 56, true, Piece.PColor.WHITE));
		positions.put(56, 8);
		pieces.add(9, new Knight(9, 57, true, Piece.PColor.WHITE));
		positions.put(57, 9);
		pieces.add(10, new Bishop(10,58, true, Piece.PColor.WHITE));
		positions.put(58, 10);
		pieces.add(11, new Queen(11, 59, true, Piece.PColor.WHITE));
		positions.put(59, 11);
		pieces.add(12, new King(12, 60, true, Piece.PColor.WHITE));
		positions.put(60, 12);
		pieces.add(13, new Bishop(13, 61, true, Piece.PColor.WHITE));
		positions.put(61, 13);
		pieces.add(14, new Knight(14, 62, true, Piece.PColor.WHITE));
		positions.put(62, 14);
		pieces.add(15, new Rook(15, 63, true, Piece.PColor.WHITE));
		positions.put(63, 15);
		
		for (int j = 0; j < 8; j++) {
			pieces.add(16+j, new Pawn(16+j, 8+j, true, Piece.PColor.BLACK));
			positions.put(8+j, 16+j);
		}
		pieces.add(24, new Rook(24, 0, true, Piece.PColor.BLACK));
		positions.put(0, 24);
		pieces.add(25, new Knight(25, 1, true, Piece.PColor.BLACK));
		positions.put(1, 25);
		pieces.add(26, new Bishop(26, 2, true, Piece.PColor.BLACK));
		positions.put(2, 26);
		pieces.add(27, new Queen(27, 3, true, Piece.PColor.BLACK));
		positions.put(3, 27);
		pieces.add(28, new King(28, 4, true, Piece.PColor.BLACK));
		positions.put(4, 28);
		pieces.add(29, new Bishop(29, 5, true, Piece.PColor.BLACK));
		positions.put(5, 29);
		pieces.add(30, new Knight(30, 6, true, Piece.PColor.BLACK));
		positions.put(6, 30);
		pieces.add(31, new Rook(31, 7, true, Piece.PColor.BLACK));
		positions.put(7, 31);
	}
}
