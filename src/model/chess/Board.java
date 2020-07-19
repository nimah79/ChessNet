package model.chess;

import java.util.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board extends Pane {

	private int boardWidth = 8;
	private int boardHeight = 8;
	private int[][] board;

	private Piece[][] pieces;
	private Window[][] windows;

	// pieceName_color_number
	private Rook rook_2_1;
	private Knight knight_2_1;
	private Bishop bishop_2_1;
	private Queen queen_2;
	private King king_2;
	private Bishop bishop_2_2;
	private Knight knight_2_2;
	private Rook rook_2_2;
	private Pawn pawn_2_1;
	private Pawn pawn_2_2;
	private Pawn pawn_2_3;
	private Pawn pawn_2_4;
	private Pawn pawn_2_5;
	private Pawn pawn_2_6;
	private Pawn pawn_2_7;
	private Pawn pawn_2_8;

	private Rook rook_1_1;
	private Knight knight_1_1;
	private Bishop bishop_1_1;
	private Queen queen_1;
	private King king_1;
	private Bishop bishop_1_2;
	private Knight knight_1_2;
	private Rook rook_1_2;
	private Pawn pawn_1_1;
	private Pawn pawn_1_2;
	private Pawn pawn_1_3;
	private Pawn pawn_1_4;
	private Pawn pawn_1_5;
	private Pawn pawn_1_6;
	private Pawn pawn_1_7;
	private Pawn pawn_1_8;

	private Piece selectedPiece = null;

	private Logic gameLogic = new Logic();
	public List<Piece> checkPieces = new ArrayList<Piece>();
	public List<Piece> saviorPieces = new ArrayList<Piece>();
	public int playerOneRook = 2;
	public int playerOneBishopLightSquare = 1;
	public int playerOneBishopDarkSquare = 1;
	public int playerOneKnight = 2;
	public int playerOneQueen = 1;
	public int playerOnePawn = 8;
	public int playerTwoRook = 2;
	public int playerTwoBishopLightSquare = 1;
	public int playerTwoBishopDarkSquare = 1;
	public int playerTwoKnight = 2;
	public int playerTwoQueen = 1;
	public int playerTwoPawn = 8;
	private Alert alert;

	private Rectangle background;
	private double cell_width;
	private double cell_height;
	private int current_player;
	private boolean isBlack = false;
	public boolean checkmate = false;
	public boolean checkState = false;
	public boolean stalemate = false;

	private final int EMPTY = 0;
	private final int PlayerWhite = 1;
	private final int PlayerBlack = 2;

	public Board() {

		background = new Rectangle();
		background.setFill(Color.WHITE);

		getChildren().add(background);
		board = new int[boardWidth][boardHeight];
		pieces = new Piece[boardWidth][boardHeight];
		windows = new Window[boardWidth][boardHeight];
		for (int i = 0; i < 8; i++) {
			if (i % 2 == 0 || i == 0) {
				isBlack = false;
			} else
				isBlack = true;
			for (int j = 0; j < 8; j++) {
				board[i][j] = EMPTY;
				if (isBlack) {
					windows[i][j] = new Window(0); // Black
					isBlack = false;
				} else {
					windows[i][j] = new Window(1); // White
					isBlack = true;
				}
				getChildren().add(windows[i][j]);
				pieces[i][j] = null;
			}
		}
		current_player = PlayerWhite;
		initPiece();
	}

	public void initPiece() {
		rook_2_1 = new Rook(2, 0, 0);
		knight_2_1 = new Knight(2, 1, 0);
		bishop_2_1 = new Bishop(2, 2, 0);
		queen_2 = new Queen(2, 3, 0);
		king_2 = new King(2, 4, 0);
		bishop_2_2 = new Bishop(2, 5, 0);
		knight_2_2 = new Knight(2, 6, 0);
		rook_2_2 = new Rook(2, 7, 0);
		pawn_2_1 = new Pawn(2, 0, 1);
		pawn_2_2 = new Pawn(2, 1, 1);
		pawn_2_3 = new Pawn(2, 2, 1);
		pawn_2_4 = new Pawn(2, 3, 1);
		pawn_2_5 = new Pawn(2, 4, 1);
		pawn_2_6 = new Pawn(2, 5, 1);
		pawn_2_7 = new Pawn(2, 6, 1);
		pawn_2_8 = new Pawn(2, 7, 1);

		// white pieces
		rook_1_1 = new Rook(1, 0, 7);
		knight_1_1 = new Knight(1, 1, 7);
		bishop_1_1 = new Bishop(1, 2, 7);
		queen_1 = new Queen(1, 3, 7);
		king_1 = new King(1, 4, 7);
		bishop_1_2 = new Bishop(1, 5, 7);
		knight_1_2 = new Knight(1, 6, 7);
		rook_1_2 = new Rook(1, 7, 7);
		pawn_1_1 = new Pawn(1, 0, 6);
		pawn_1_2 = new Pawn(1, 1, 6);
		pawn_1_3 = new Pawn(1, 2, 6);
		pawn_1_4 = new Pawn(1, 3, 6);
		pawn_1_5 = new Pawn(1, 4, 6);
		pawn_1_6 = new Pawn(1, 5, 6);
		pawn_1_7 = new Pawn(1, 6, 6);
		pawn_1_8 = new Pawn(1, 7, 6);

		pieces[0][0] = rook_2_1;
		pieces[1][0] = knight_2_1;
		pieces[2][0] = bishop_2_1;
		pieces[3][0] = queen_2;
		pieces[4][0] = king_2;
		pieces[5][0] = bishop_2_2;
		pieces[6][0] = knight_2_2;
		pieces[7][0] = rook_2_2;

		pieces[0][1] = pawn_2_1;
		pieces[1][1] = pawn_2_2;
		pieces[2][1] = pawn_2_3;
		pieces[3][1] = pawn_2_4;
		pieces[4][1] = pawn_2_5;
		pieces[5][1] = pawn_2_6;
		pieces[6][1] = pawn_2_7;
		pieces[7][1] = pawn_2_8;

		for (int y = 2; y < 6; y++) {
			for (int x = 0; x < boardWidth; x++) {
				pieces[x][y] = null;
			}
		}

		pieces[0][6] = pawn_1_1;
		pieces[1][6] = pawn_1_2;
		pieces[2][6] = pawn_1_3;
		pieces[3][6] = pawn_1_4;
		pieces[4][6] = pawn_1_5;
		pieces[5][6] = pawn_1_6;
		pieces[6][6] = pawn_1_7;
		pieces[7][6] = pawn_1_8;

		pieces[0][7] = rook_1_1;
		pieces[1][7] = knight_1_1;
		pieces[2][7] = bishop_1_1;
		pieces[3][7] = queen_1;
		pieces[4][7] = king_1;
		pieces[5][7] = bishop_1_2;
		pieces[6][7] = knight_1_2;
		pieces[7][7] = rook_1_2;

		for (int y = 0; y < boardHeight; y++) {
			for (int x = 0; x < boardWidth; x++) {
				if (y == 0 || y == 1)
					board[x][y] = 2;
				else if (y == 6 || y == 7)
					board[x][y] = 1;
				else
					board[x][y] = 0;
			}
		}

		for (int i = 0; i < 8; i++) {
			getChildren().addAll(pieces[i][0].getImage(),
					pieces[i][1].getImage(), pieces[i][6].getImage(),
					pieces[i][7].getImage());
		}
	}

	// select piece method
	public void selectPiece(final double x, final double y) {
		int indexX = (int) (x / cell_width);
		int indexY = (int) (y / cell_height);

		if (!checkmate && !stalemate) {
			if (windows[indexX][indexY].isHighlighted()) {
				movePiece(x, y);
				unhighlightWindow();
				selectedPiece = null;
			} else {
				// add condition to know if the player is in check
				if (board[indexX][indexY] == current_player) {
					unhighlightWindow();
					pieces[indexX][indexY].SelectPiece(this);
					selectedPiece = pieces[indexX][indexY];
				}
			}
		}
	}

	public void movePiece(final double x, final double y) {
		int indexX = (int) (x / cell_width);
		int indexY = (int) (y / cell_height);
		selectedPiece.MovePiece(this, indexX, indexY);
		selectedPiece.setFirstTime(false);
		// don't forget to change the player
		if (current_player == PlayerWhite) {
			current_player = PlayerBlack;
			checkState = false;
			for (Iterator<Piece> piece = saviorPieces.iterator(); piece
					.hasNext();) {
				Piece item = piece.next();
				item.isASavior = false;
			}
			if (gameLogic.isCheck(this, king_2.xPos, king_2.yPos,
					current_player, true)) {
				checkPieces.clear();
				saviorPieces.clear();
				checkState = true;
				gameLogic.findAllCheckPieces(this, king_2.xPos, king_2.yPos,
						current_player);
				if (gameLogic.isCheckmate(this, king_2.xPos, king_2.yPos,
						current_player)) {
					checkmate = true;
				}
			}
		} else {
			current_player = PlayerWhite;
			checkState = false;
			for (Iterator<Piece> piece = saviorPieces.iterator(); piece
					.hasNext();) {
				Piece item = piece.next();
				item.isASavior = false;
			}
			if (gameLogic.isCheck(this, king_1.xPos, king_1.yPos,
					current_player, true)) {
				checkPieces.clear();
				saviorPieces.clear();
				checkState = true;
				gameLogic.findAllCheckPieces(this, king_1.xPos, king_1.yPos,
						current_player);
				if (gameLogic.isCheckmate(this, king_1.xPos, king_1.yPos,
						current_player)) {
					checkmate = true;
				}
			}
		}
	}

	public void createPromotePiece(Piece piece) {
		Piece promotedPiece;

		alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Promote a piece");
		alert.setHeaderText("You can promote your pawn into another piece");
		alert.setContentText("Choose one of the following piece");

		ButtonType buttonRook = new ButtonType("Rook");
		ButtonType buttonKnight = new ButtonType("Knight");
		ButtonType buttonBishop = new ButtonType("Bishop");
		ButtonType buttonQueen = new ButtonType("Queen");

		alert.getButtonTypes().setAll(buttonRook, buttonKnight, buttonBishop,
				buttonQueen);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonRook) {
			promotedPiece = new Rook(piece.type, piece.xPos, piece.yPos);
			getChildren().remove(piece.getImage());
			getChildren().add(promotedPiece.getImage());
			pieces[piece.xPos][piece.yPos] = promotedPiece;
			if (piece.type == 1)
				playerOneRook++;
			else
				playerTwoRook++;
		} else if (result.get() == buttonKnight) {
			promotedPiece = new Knight(piece.type, piece.xPos, piece.yPos);
			getChildren().remove(piece.getImage());
			getChildren().add(promotedPiece.getImage());
			pieces[piece.xPos][piece.yPos] = promotedPiece;
			if (piece.type == 1)
				playerOneKnight++;
			else
				playerTwoKnight++;
		} else if (result.get() == buttonBishop) {
			promotedPiece = new Bishop(piece.type, piece.xPos, piece.yPos);
			getChildren().remove(piece.getImage());
			getChildren().add(promotedPiece.getImage());
			pieces[piece.xPos][piece.yPos] = promotedPiece;
			if (piece.type == 1) {
				if ((piece.xPos + piece.yPos) % 2 != 0)
					playerOneBishopDarkSquare++;
				else
					playerOneBishopLightSquare++;
			} else {
				if ((piece.xPos + piece.yPos) % 2 == 0)
					playerTwoBishopLightSquare++;
				else
					playerTwoBishopDarkSquare++;
			}
		} else if (result.get() == buttonQueen) {
			promotedPiece = new Queen(piece.type, piece.xPos, piece.yPos);
			getChildren().remove(piece.getImage());
			getChildren().add(promotedPiece.getImage());
			pieces[piece.xPos][piece.yPos] = promotedPiece;
			if (piece.type == 1)
				playerOneQueen++;
			else
				playerTwoQueen++;
		}
	}

	public void colorSquare(int x, int y, boolean selectedPiece) {
		if (selectedPiece)
			windows[x][y].highlightWindow(Color.ORANGE);
		else
			windows[x][y].highlightWindow(Color.GREEN);
	}

	public void unhighlightWindow() {
		for (int y = 0; y < boardHeight; y++) {
			for (int x = 0; x < boardWidth; x++) {
				if (windows[x][y].getRectangle().getStroke() != null)
					windows[x][y].unhighlight();
			}
		}
	}

	// Getter and setter method

	public Piece getKing(int type) {
		if (type == 1)
			return (king_1);
		return (king_2);
	}

	public int getBoardHeight() {
		return (this.boardHeight);
	}

	public int getBoardWidth() {
		return (this.boardWidth);
	}

	public int getBoardPosition(int x, int y) {
		return (this.board[x][y]);
	}

	public void setBoard(int x, int y, int type) {
		this.board[x][y] = type;
	}

	public Piece getPiece(int x, int y) {
		return (pieces[x][y]);
	}

	public void setPiece(int x, int y, Piece piece) {
		this.pieces[x][y] = piece;
	}

}
