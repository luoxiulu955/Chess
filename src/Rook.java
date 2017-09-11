import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Concrete class of Rook
 * 
 * @author Liang Xia
 *  @version 1.0
 * @since   02-10-2017 
 */
public class Rook extends Piece{

	//rule of moves
	private final static int[][] RULES = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
	
	/**
	 * Initialize Rook object and call to super()
	 * @param id	id of piece
	 * @param position	current position of piece
	 * @param alive	status of piece
	 * @param color	color of piece
	 */
	public Rook(int id, int position, boolean alive, PColor color) {
		super(id, position, alive, color, PType.ROOK);
	}

	/**
	 * Get the valid moves of rook
	 */
	@Override
	public List<Integer> getValidMoves(int[]box, List<Piece> pieces,
									   Map<Integer, Integer> positions) {
		List<Integer> validMoves = new ArrayList<>();
		int x = position / 8;
		int y = position % 8;
		int p;
		int pieceId;
		for (int [] r : RULES) {
			for(int i = 1; !isOutOfBoard(x, y, r, i); i++) {
				p = (x + i * r[0]) * 8 + y + i * r[1];
				if (box[p] == 1) {
					pieceId = positions.get(p);
					if (pieces.get(pieceId).getColor() != getColor()) {
						validMoves.add(p);
					}
					break;
				} else {
					validMoves.add(p);
				}
			}
		}
		return validMoves;
	}
	
	/**
	 * Check if the move is out of the board
	 * @param x	x axis
	 * @param y	y axis
	 * @param r	rule of move
	 * @param n	range
	 * @return	result
	 */
	private boolean isOutOfBoard(int x, int y, int[] r, int n) {
		if (x + n * r[0] >= 0 && x + n * r[0] <= 7 &&
			y + n * r[1] >= 0 && y + n * r[1] <= 7) {
			return false;
		}
		return true;
	}
	
}
