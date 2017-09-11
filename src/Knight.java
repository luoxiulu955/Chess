import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Concrete class of Knight
 * 
 * @author Liang Xia
 *  @version 1.0
 * @since   02-10-2017 
 */
public class Knight extends Piece{
	
	//rule of moves
	private final static int[][] rules = {{1, 2}, {1, -2}, {-1, 2}, {-1, -2},
										 {2, 1}, {2, -1}, {-2, 1}, {-2, -1}};
	/**
	 * Initialize Knight object and call to super()
	 * @param id	id of piece
	 * @param position	current position of piece
	 * @param alive	status of piece
	 * @param color	color of piece
	 */
	public Knight(int id, int position, boolean alive, PColor color) {
		super(id, position, alive, color, PType.KNIGHT);
	}

	/**
	 * Get the valid moves of knight
	 */
	@Override
	public List<Integer> getValidMoves(int[]box, List<Piece> pieces,
									   Map<Integer, Integer> positions) {
		List<Integer> validMoves = new ArrayList<>();
		int x = position / 8;
		int y = position % 8;
		int p;
		int pieceId;
		for (int [] r : rules) {
			if (x + r[0] >= 0 && x + r[0] <= 7 &&
				y + r[1] >= 0 && y + r[1] <= 7) {
				p = (x + r[0]) * 8 + y + r[1];
				if (box[p] == 1) {
					pieceId = positions.get(p);
					if (pieces.get(pieceId).getColor() != getColor()) {
						validMoves.add(p);
					}
				} else {
					validMoves.add(p);
				}
			}
		}
		return validMoves;
	}

}
