/**
 * Main method to initialize chess board object and GUI
 * 
 * @author Liang Xia
 *  @version 1.0
 * @since   02-10-2017 
 */
public class ChessTester {
	public static void main(String args[]) {
		BoardUtils board = new BoardUtils();
		ChessBoard chessboard = new ChessBoard(board);
		new Gui(chessboard);
	}
}
