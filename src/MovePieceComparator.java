import java.util.Comparator;

/**
 *  Comparator of comparing Move object
 * 
 * @author Liang Xia
 *  @version 1.0
 * @since   02-26-2017 
 */
public class MovePieceComparator implements Comparator<Move>{
	
	private int[] box;	//board
	private int color;	//offset value for piece color
	
	/**
	 * Constructor of comparator for comparing Move object based on customized rules
	 * @param box	board
	 * @param color	the color of piece
	 */
	public MovePieceComparator(int[] box, int color) {
		this.box = box;
		this.color = color;
	}
	
	/**
	 * Compare the two pieces' positions based on the customized rules
	 */
	@Override
	public int compare(Move o1, Move o2) {
		int optimumPosition1 = o1.getOptimumMove();
		int optimumPosition2 = o2.getOptimumMove();
		int result;
		
		//check if two Move objects have optimum move or not
		if (optimumPosition1 == -1 || optimumPosition2 == -1) {
			result = optimumPosition2 - optimumPosition1;
			return result;
		}
		
		result = compareCapturePiece(optimumPosition1, optimumPosition2);
		if (result != 0) {
			return result;
		}
		result = compareFurthestToRight(optimumPosition1, optimumPosition2);
		if (result != 0) {
			return result;
		}
		result = compareFurthestAway(optimumPosition1, optimumPosition2);
		if (result != 0) {
			return result;
		}
		result = compareFurthestToLeft(optimumPosition1, optimumPosition2);
		if (result != 0) {
			return result;
		}
		result = comparePiecePosition(o1.getPiecePosition(), o2.getPiecePosition());
		if (result != 0) {
			return result;
		}
		return 0;
	}
	
	/**
	 * Capture an opposing piece is prior, example box[position] = 1 is prior
	 * @param o1	integer
	 * @param o2	integer
	 * @return result of comparison
	 */
	public int compareCapturePiece(Integer o1, Integer o2) {
		return box[o2] - box[o1];
	}
	
	/**
	 * Larger value of y axis is prior
	 * @param o1	integer
	 * @param o2	integer
	 * @return result of comparison
	 */
	public int compareFurthestToRight(Integer o1, Integer o2) {
		int y1, y2;
		y1 = o1 % 8;
		y2 = o2 % 8;
		return color * (y2 - y1);	
	}
	
	/**
	 * Smaller value of x axis is prior
	 * @param o1	integer
	 * @param o2	integer
	 * @return result of comparison
	 */
	public int compareFurthestAway(Integer o1, Integer o2) {
		int x1, x2;
		x1 = o1 / 8;
		x2 = o2 / 8;
		return color * (x1 - x2);
	}
	
	/**
	 * Smaller value of y axis is prior
	 * @param o1	integer
	 * @param o2	integer
	 * @return result of comparison
	 */
	public int compareFurthestToLeft(Integer o1, Integer o2) {
		int y1, y2;
		y1 = o1 % 8;
		y2 = o2 % 8;
		return color * (y1 - y2);	
	}
	
	/**
	 * Smaller value of x axis for current piece position is prior
	 * @param o1	integer
	 * @param o2	integer
	 * @return	result of comparision
	 */
	public int comparePiecePosition(Integer o1, Integer o2) {
		int x1, x2;
		x1 = o1 / 8;
		x2 = o2 / 8;
		return color * (x2 - x1);
	}

}
