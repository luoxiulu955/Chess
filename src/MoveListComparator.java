import java.util.Comparator;

/**
 *  Comparator of comparing integer
 * 
 * @author Liang Xia
 *  @version 1.0
 * @since   02-26-2017 
 */
public class MoveListComparator implements Comparator<Integer>{

	private int[] box;	//board
	private int color;	//offset value for piece color
	
	/**
	 * Constructor of comparator for comparing list of integer based on customized rules
	 * @param box	board
	 * @param color	the color of piece
	 */
	public MoveListComparator(int[] box, int color) {
		this.box = box;
		this.color = color;
	}
	
	/**
	 * Compare the two pieces' positions based on the customized rules
	 */
	@Override
	public int compare(Integer o1, Integer o2) {
		int result = compareCapturePiece(o1, o2);
		if (result != 0) {
			return result;
		}
		result = compareFurthestToRight(o1, o2);
		if (result != 0) {
			return result;
		}
		result = compareFurthestAway(o1, o2);
		if (result != 0) {
			return result;
		}
		result = compareFurthestToLeft(o1, o2);
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
}
