import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Concrete class of Pawn
 * 
 * @author Liang Xia
 *  @version 1.0
 * @since   02-10-2017 
 */
public class Pawn extends Piece {

	private int originPosition;	//initialized position
	private final static int[][] RULES = {{-1, 0}, {-1, -1}, {-1, 1}};	//rule of moves
	private int direction;	//offset for different color of piece
	
	/**
	 * Initialize Pawn object and call to super()
	 * @param id	id of piece
	 * @param position	current position of piece
	 * @param alive	status of piece
	 * @param color	color of piece
	 */
	public Pawn(int id, int position, boolean alive, PColor color) {
		super(id, position, alive, color, PType.PAWN);
		originPosition = position;	
		if(color == PColor.WHITE) {
			direction = 1;
		} else {
			direction = -1;
		}
	}

	/**
	 * Get the valid moves of pawn
	 */
	@Override
	public List<Integer> getValidMoves(int[]box, List<Piece> pieces,
								       Map<Integer, Integer> positions) {
		List<Integer> validMoves = new ArrayList<>();
		int x = position / 8;
		int y = position % 8;
		int p;
		int pieceId;
		
		// check move forward by one or two squares
		if (x + direction * RULES[0][0] >= 0 && x + direction * RULES[0][0] <= 7) {
			p = (x + direction * RULES[0][0]) * 8 + y + RULES[0][1];
			if (box[p] == 0) {
				validMoves.add(p);
				
				//if pawn is never moved before, it is able to move 2 squares 
				if (originPosition == position) {
					if(x + direction * 2 * RULES[0][0] >= 0 && 
					   x + direction * 2 * RULES[0][0] <= 7) {
						int q = (x + direction * 2 * RULES[0][0]) * 8 + y + RULES[0][1];
						if (box[q] == 0) {
							validMoves.add(q);
						}
					}
				}
			}
		}
		
		// check if any piece on the positions which can be captured by pawn 
		for (int i = 1; i <= 2; i++) {
			if (x + direction * RULES[i][0] >= 0 && x + direction * RULES[i][0] <= 7 &&
				y + RULES[i][1] >= 0 && y + RULES[i][1] <= 7) {
				p = (x + direction * RULES[i][0]) * 8 + y + RULES[i][1];
				if (box[p] == 1) {
					pieceId = positions.get(p);
					if (pieces.get(pieceId).getColor() != getColor()) {
						validMoves.add(p);
					}
				}
			}
		}
		
		
		return validMoves;
	}

}
