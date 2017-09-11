import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * Shared container to store the pieces' possible moves
 * 
 * @author Liang Xia
 *  @version 1.0
 * @since   02-26-2017 
 */
public class SharedData {
	
	private Lock lock;	//locker of preventing thread safety and shared resource
	List<Move> possibleMoves = new ArrayList<>();
	
	public SharedData(Lock lock) {
		this.lock = lock;
	}
	
	/**
	 * Add piece's possible moves and shared container,
	 * require the lock to avoid race condition
	 * @param id	piece id
	 * @param piecePosition	piece position
	 * @param box	board
	 * @param moves	list of possible moves
	 */
	public void updateMoves(int id, int piecePosition, int[] box, List<Integer> moves) {
		lock.lock();
		try {
			Move move = new Move(id, piecePosition, box, moves);
			move.updateOptimumMove();
			possibleMoves.add(move);
		} catch (Exception e) {
			
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * Get the shared container
	 * @return list of move object
	 */
	public List<Move> getSharedData() {
		return possibleMoves;
	}
	
	/**
	 * Get the optimum move and relative piece id
	 * @param box	board
	 * @param color	color of piece
	 * @param pieces	list of pieces
	 * @return	list that includes piece id and optimum move position
	 */
	public List<Integer> getOptimumMove(int[] box, int color, List<Piece> pieces) {
		Collections.sort(possibleMoves, new MovePieceComparator(box, color));
		List<Integer> result = new ArrayList<>();
		if (possibleMoves != null && possibleMoves.size() > 0){
			Move m = possibleMoves.get(0);
			result.add(m.getId());
			result.add(m.getOptimumMove());
			possibleMoves.clear();
		}
		return result;
	}
	
}
