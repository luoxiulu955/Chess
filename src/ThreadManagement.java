import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread management to operate and manage threads
 * 
 * @author Liang Xia
 *  @version 1.0
 * @since   02-26-2017 
 */
public class ThreadManagement {
	
	private SharedData sd;
	private List<PieceThread> threads;

	/**
	 * construction that initializes shared data, and the list for the threads
	 */
	public ThreadManagement(){
		sd = new SharedData(new ReentrantLock());
		threads = new ArrayList<>();
	}
	
	/**
	 * Create the threads based on the turn
	 * @param stepCounter	total steps
	 * @param box	board
	 * @param pieces	list of pieces
	 * @param positions	map of piece and id
	 */
	public void createThread(int stepCounter, int[] box, List<Piece> pieces,
							 Map<Integer, Integer> positions) {
		int start, end;
		
		if (stepCounter % 2 == 0) {
			start = 0;
			end = 15;
		} else {
			start = 16;
			end = 31;
		}
		
		for(int i = start; i <= end; i++) {
			Piece piece = pieces.get(i);
			if (piece.getStatus()) {
				PieceThread pt = new PieceThread(sd, piece, box, pieces, positions);
				pt.start();
				threads.add(pt);
			}
		}
		
		for(int i = 0; i < threads.size(); i++) {
			threads.get(i).join();
		}
		
		threads.clear();
	}
	
	/**
	 * Get the optimum move
	 * @param box	board
	 * @param color	color
	 * @param pieces	list of pieces
	 * @return	optimum move
	 */
	public List<Integer> getOptimumMove(int[] box, int color, List<Piece> pieces) {
		return sd.getOptimumMove(box, color, pieces);
	}

}
