import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * Allows user to progress to move piece based on chess rule.
 * Clicking on a chess piece will highlight the appropriate moves for that chess piece. 
 * Clicking on any of the highlighted squares will move the piece to that square. 
 * Clicking on any non-highlighted square will de-select the piece,
 * and return the board to a state with no highlighted squares.
 * 
 * @author Liang Xia
 *  @version 1.0
 * @since   02-10-2017 
 */
@SuppressWarnings("serial")
public class Gui extends JFrame implements ActionListener{
	
	//define image file name
	private final static String BLACK_BISHOP = "black_bishop.png";
	private final static String BLACK_KING = "black_king.png";
	private final static String BLACK_KNIGHT = "black_knight.png";
	private final static String BLACK_PAWN = "black_pawn.png";
	private final static String BLACK_ROOK = "black_rook.png";
	private final static String BLACK_QUEEN = "black_queen.png";
	private final static String WHITE_BISHOP = "white_bishop.png";
	private final static String WHITE_KING = "white_king.png";
	private final static String WHITE_KNIGHT = "white_knight.png";
	private final static String WHITE_PAWN = "white_pawn.png";
	private final static String WHITE_ROOK = "white_rook.png";
	private final static String WHITE_QUEEN = "white_queen.png";
	
	private JButton[] tiles;				//chess board
	private Container container;			//load all the components
	private JPanel board;					//load chess board
	private JPanel controller;				//display buttons panel and text panel
	private JButton hint;					//hint button
	private ChessBoard chessboard;			//chess board instance
	private List<ImageIcon> imageIcons;		//list of image icons
	private int source = 0;					//the source position
	private int destination = 0;			//the destination position
	private int stepCounter = 0;			//total steps
	private int hintPosition = 0;			//the optimum move position
	private boolean sourceSelected = false;	//check if any piece is selected
	private boolean isHint = false;			//check if hint button is clicked
	private List<Integer> hintMove;			//store the optimum move
	
	/**
	 * Initialize GUI to display chess board, pieces and buttons.
	 * @param chessboard instance of chess board
	 */
	public Gui(ChessBoard chessboard){
		super.setTitle("Chess");
		this.chessboard = chessboard;
		init();
	}
	
	/**
	 * Initialize Container, Dialog, JPanel and JButtons to create
	 * chess board with green and white color.
	 */
	private void init() {
		imageIcons = new ArrayList<ImageIcon>();
		
		//create container
		container = getContentPane();
		container.setLayout(new FlowLayout());
		
		//create hint button
		hint = new JButton("Hint");
		hint.addActionListener(this);
		
		//create controller panel and add hint button
		controller = new JPanel(new FlowLayout());
		controller.add(hint);
		
		//create board panel
		board = new JPanel();
		board.setLayout(new GridLayout(8, 8));
		board.setPreferredSize(new Dimension(480, 480));
		
		//init chess board and applied color
		tiles = new JButton[64];		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++){
				tiles[i * 8 + j] = new JButton();
				if ((i + j) % 2 == 0) {
					tiles[i * 8 + j].setBackground(Color.WHITE);
					tiles[i * 8 + j].setOpaque(true);
					tiles[i * 8 + j].setBorder(new LineBorder(Color.BLACK));
					
				} else {
					tiles[i * 8 + j].setBackground(Color.GREEN);
					tiles[i * 8 + j].setOpaque(true);
					tiles[i * 8 + j].setBorder(new LineBorder(Color.BLACK));
				}
				board.add(tiles[i * 8 + j]);
				tiles[i * 8 + j].addActionListener(this);
				
			}
		}
		
		//add board panel and controller panel to the container
		container.add(board);
		container.add(controller);
		
		initPieceIcon();
		setVisible(true);
		setResizable(false);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
	
	/**
	 * hightlight the moves
	 * @param list	list of moves
	 */
	public void highlightMoves(List<Integer> list) {
		for(Integer l : list) {
			if (l >= 0) {
				tiles[l].setBackground(Color.ORANGE);
			}
		}
	}
	
	/**
	 * Remove the highlight of moves
	 * @param list	list of moves
	 */
	public void cancelHightlightMoves(List<Integer> list) {
		if (list != null) {
			for(Integer l : list) {
				int i = l / 8;
				int j = l % 8;
				if ((i + j) % 2 == 0) {
					tiles[l].setBackground(Color.WHITE);
				} else {
					tiles[l].setBackground(Color.GREEN);
				}
			}
		}
	}
	
	/**
	 * Click the hint button and highlight the optimum move
	 */
	public void clickHint() {
		chessboard.createThread(stepCounter);
		List<Integer> optimumMove;

		if (stepCounter % 2 == 0) {
			optimumMove = chessboard.getOptimumMove(1);
		} else {
			optimumMove = chessboard.getOptimumMove(-1);
		}
		
		if (sourceSelected) {
			cancelHightlightMoves(chessboard.getValidMoves(source));
		}
		
		//check if optimum Move is available
		if(optimumMove != null && optimumMove.size() > 0) {
			for(Integer position : chessboard.getPositions().keySet()) {
				if (chessboard.getPositions().get(position) == optimumMove.get(0)) {
					source = position;
				}
			}
			hintPosition = optimumMove.get(1);
			hintMove = new ArrayList<Integer>(Arrays.asList(hintPosition));
			
			highlightMoves(hintMove);
			sourceSelected = true;
			isHint = true;
		}
	}
	
	/**
	 * Listen button actions. Select and move the piece to the new position.
	 * Highlight the possible moves of the selected.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == hint) {
			clickHint();
			
		}
		
		for (int i = 0; i < tiles.length; i++) {
			int pieceId;
			if(e.getSource() == tiles[i]) {
				if (!sourceSelected) {
					/*
					 * if no piece is selected yet
					 */
					source = i;
					pieceId = chessboard.getPositions().get(source);
					if (pieceId == -1 || !isCorrectTurn(chessboard.getPieces().get(pieceId).getColor())) {
						continue;
					} else {
						highlightMoves(chessboard.getValidMoves(source));
						sourceSelected = true;
					}
				} else {
					/*
					 * choose a valid destination which is in possible moves or optimum move to move
					 */
					destination = i;
					if (isHint) { 
						cancelHightlightMoves(hintMove);
						isHint = false;
						if (hintPosition == destination) {
							pieceId = chessboard.getPositions().get(source);
							if (chessboard.changePosition(source, destination)) {
								tiles[source].setIcon(null);
								tiles[destination].setIcon(imageIcons.get(pieceId));
								stepCounter++;
							}
						}
					}else{
						cancelHightlightMoves(chessboard.getValidMoves(source));
						if (source != destination) {
							pieceId = chessboard.getPositions().get(source);
							if (chessboard.changePosition(source, destination)) {
								tiles[source].setIcon(null);
								tiles[destination].setIcon(imageIcons.get(pieceId));
								stepCounter++;
							}
						}
					}
					sourceSelected = false;
				}
			}
		}
	}
	
	/**
	 * Check if it is a correct turn
	 * @param color
	 * @return	result
	 */
	private boolean isCorrectTurn(Piece.PColor c) {
		int color;
		if (c == Piece.PColor.WHITE) {
			color = 0;
		} else {
			color = 1;
		}
		if (stepCounter % 2 == color) {
			return true;
		}	
		return false;
	}
	
	/**
	 * Initialize all the pieces' icons before moving pieces
	 */
	public void initPieceIcon() {
		List<Piece> pieces = chessboard.getPieces();
		for (Piece piece : pieces) {
			createNewIcon(piece);
		}
	}
	
	/**
	 * Create piece's icon and applied to specific tile
	 * @param piece the object of piece
	 */
	private void createNewIcon(Piece piece) {
		String color = piece.getColor().toString().toLowerCase();
		String type = piece.getType().toString().toLowerCase();
		int id = piece.getId();
		int position = piece.getPosition();
		try {
			BufferedImage image = ImageIO.read(new File(getImagePath(color, type)));
			ImageIcon imageIcon = new ImageIcon(image);
			imageIcons.add(id, imageIcon);
			tiles[position].setIcon(imageIcon);
		} catch (IOException e) {
			System.out.println("Cannot load img file.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the image file name and full path based on color and type
	 * @param color
	 * @param type
	 * @return	the full path and name of the image file
	 */
	private String getImagePath(String color, String type) {
		String path = null;
		if(color.equals("white")) {
			switch (type) {
				case "bishop":
					path = WHITE_BISHOP;
					break;
				case "king":
					path = WHITE_KING;
					break;
				case "knight":
					path = WHITE_KNIGHT;
					break;
				case "pawn":
					path = WHITE_PAWN;
					break;
				case "queen":
					path = WHITE_QUEEN;
					break;
				case "rook":
					path = WHITE_ROOK;
					break;
				default:
					break;
			}
		} else {
			switch (type) {
				case "bishop":
					path = BLACK_BISHOP;
					break;
				case "king":
					path = BLACK_KING;
					break;
				case "knight":
					path = BLACK_KNIGHT;
					break;
				case "pawn":
					path = BLACK_PAWN;
					break;
				case "queen":
					path = BLACK_QUEEN;
					break;
				case "rook":
					path = BLACK_ROOK;
					break;
				default:
					break;
			}
		}
		
		return path;
	}
}
