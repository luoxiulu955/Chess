import java.util.List;
import java.util.Map;

/**
 * Tread object that store the possible moves of each piece in the shared
 * data container
 * 
 * @author Liang Xia
 *  @version 1.0
 * @since   02-26-2017 
 */
public class PieceThread implements Runnable{
	
	private Thread t;
	private String threadName;
	private SharedData sd;
	private Piece piece;
	private int[] box;
	private List<Piece> pieces;
	private Map<Integer, Integer> positions;

	/**
	 * Constructor that initialized shared data container and related board and pieces information
	 * @param sd	shared data
	 * @param piece	current piece
	 * @param box	board
	 * @param pieces	list of pieces
	 * @param positions	map of position and piece id
	 */
	public PieceThread(SharedData sd, Piece piece, int[] box, 
					   List<Piece> pieces, Map<Integer, Integer> positions) {
		this.sd = sd;
		threadName = "Piece-" + piece.getId();
		this.piece = piece;
		this.box = box;
		this.pieces = pieces;
		this.positions = positions;
	}

	/**
	 * Start thread to execute run() method
	 */
	public void start() {
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
	
	/**
	 * Wait thread until it is stopped
	 */
	public void join() {
		if (t != null) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Store the valid and possible moves into shared data container
	 */
	@Override
	public void run() {
		try {
		List<Integer> moves = piece.getValidMoves(box, pieces, positions);
		sd.updateMoves(piece.getId(), piece.getPosition(), box, moves);
		} catch(Exception e) {
		}
	}

}
