package model.chess;

import java.util.Iterator;

public class Logic {

	private boolean isOneKingStalemate(Board chessBoard, Piece king,
			int type) {
		int nbPiece = 0;
		boolean stalemate = true;
		for (int y = 0; y < chessBoard.getBoardHeight(); y++) {
			for (int x = 0; x < chessBoard.getBoardWidth(); x++) {
				if (chessBoard.getBoardPosition(x, y) == type)
					nbPiece++;
			}
		}
		if (nbPiece == 1) {
			for (int y = king.yPos - 1; y <= king.yPos + 1; y++) {
				for (int x = king.xPos - 1; x <= king.xPos + 1; x++) {
					if (y >= 0 && y < chessBoard.getBoardHeight() && x >= 0
							&& x < chessBoard.getBoardWidth()
							&& chessBoard.getBoardPosition(x, y) != type) {
						if (!isCheck(chessBoard, x, y, type, true)) {
							stalemate = false;
							break;
						}
					}
				}
				if (!stalemate)
					break;
			}
		} else
			stalemate = false;
		return (stalemate);
	}

	public boolean isLimitPieceStalemate(Board chessBoard) {
		if (chessBoard.playerOneQueen != 0 || chessBoard.playerTwoQueen != 0)
			return false;
		else if (chessBoard.playerOneRook != 0 || chessBoard.playerTwoRook != 0)
			return false;
		else if (chessBoard.playerOneKnight > 1
				|| chessBoard.playerTwoKnight > 1)
			return false;
		else if (((chessBoard.playerOneBishopDarkSquare != 0
				|| chessBoard.playerOneBishopLightSquare != 0)
				&& chessBoard.playerOneKnight != 0)
				|| ((chessBoard.playerTwoBishopDarkSquare != 0
						|| chessBoard.playerTwoBishopLightSquare != 0)
						&& chessBoard.playerTwoKnight != 0))
			return false;
		else if ((chessBoard.playerOneBishopDarkSquare != 0
				&& chessBoard.playerOneBishopLightSquare != 0)
				|| (chessBoard.playerTwoBishopDarkSquare != 0
						&& chessBoard.playerTwoBishopLightSquare != 0))
			return false;
		else if (chessBoard.playerOnePawn > 1 || chessBoard.playerTwoPawn > 1)
			return false;
		return true;
	}

	public boolean isStalemate(Board chessBoard, Piece king, int type) {
		if (isOneKingStalemate(chessBoard, king, type)
				|| isLimitPieceStalemate(chessBoard)) {
			chessBoard.stalemate = true;
			return true;
		}
		return false;
	}

	public boolean verticalProtection(Board chessBoard, int xPos, int yPos,
			int type) {
		int y = 0;
		int enemyType = 0;
		if (type == 1)
			enemyType = 2;
		else
			enemyType = 1;
		for (y = yPos - 1; y >= 0; y--) {
			if (chessBoard.getBoardPosition(xPos, y) == type
					&& chessBoard.getPiece(xPos, y).name == "King") {
				for (y = yPos + 1; y < chessBoard.getBoardHeight(); y++) {
					if (chessBoard.getBoardPosition(xPos, y) == type)
						break;
					else if (chessBoard.getBoardPosition(xPos,
							y) == enemyType) {
						if (chessBoard.getPiece(xPos, y).name == "Queen"
								|| chessBoard.getPiece(xPos, y).name == "Rook")
							return true;
						else
							break;
					}
				}
				break;
			} else if (chessBoard.getBoardPosition(xPos, y) != 0)
				break;
		}
		for (y = yPos + 1; y < chessBoard.getBoardHeight(); y++) {
			if (chessBoard.getBoardPosition(xPos, y) == type
					&& chessBoard.getPiece(xPos, y).name == "King") {
				for (y = yPos - 1; y >= 0; y--) {
					if (chessBoard.getBoardPosition(xPos, y) == type)
						break;
					else if (chessBoard.getBoardPosition(xPos,
							y) == enemyType) {
						if (chessBoard.getPiece(xPos, y).name == "Queen"
								|| chessBoard.getPiece(xPos, y).name == "Rook")
							return true;
						else
							break;
					}
				}
				break;
			} else if (chessBoard.getBoardPosition(xPos, y) != 0)
				break;
		}
		return false;
	}

	public boolean horizontalProtection(Board chessBoard, int xPos,
			int yPos, int type) {
		int x = 0;
		int enemyType = 0;
		if (type == 1)
			enemyType = 2;
		else
			enemyType = 1;
		for (x = xPos - 1; x >= 0; x--) {
			if (chessBoard.getBoardPosition(x, yPos) == type
					&& chessBoard.getPiece(x, yPos).name == "King") {
				for (x = xPos + 1; x < chessBoard.getBoardWidth(); x++) {
					if (chessBoard.getBoardPosition(x, yPos) == type)
						break;
					else if (chessBoard.getBoardPosition(x,
							yPos) == enemyType) {
						if (chessBoard.getPiece(x, yPos).name == "Queen"
								|| chessBoard.getPiece(x, yPos).name == "Rook")
							return true;
						else
							break;
					}
				}
				break;
			} else if (chessBoard.getBoardPosition(x, yPos) != 0)
				break;
		}
		for (x = xPos + 1; x < chessBoard.getBoardWidth(); x++) {
			if (chessBoard.getBoardPosition(x, yPos) == type
					&& chessBoard.getPiece(x, yPos).name == "King") {
				for (x = xPos - 1; x >= 0; x--) {
					if (chessBoard.getBoardPosition(x, yPos) == type)
						break;
					else if (chessBoard.getBoardPosition(x,
							yPos) == enemyType) {
						if (chessBoard.getPiece(x, yPos).name == "Queen"
								|| chessBoard.getPiece(x, yPos).name == "Rook")
							return true;
						else
							break;
					}
				}
				break;
			} else if (chessBoard.getBoardPosition(x, yPos) != 0)
				break;
		}
		return false;
	}

	public boolean slashDiagonalProtection(Board chessBoard, int xPos,
			int yPos, int type) {
		int enemyType = 0;
		if (type == 1)
			enemyType = 2;
		else
			enemyType = 1;
		int y = yPos - 1;
		for (int x = xPos + 1; x < chessBoard.getBoardWidth()
				&& y >= 0; x++, y--) {
			if (chessBoard.getBoardPosition(x, y) == type
					&& chessBoard.getPiece(x, y).name == "King") {
				y = yPos + 1;
				for (x = xPos - 1; x >= 0
						&& y < chessBoard.getBoardHeight(); x--, y++) {
					if (chessBoard.getBoardPosition(x, y) == type)
						break;
					else if (chessBoard.getBoardPosition(x, y) == enemyType) {
						if (chessBoard.getPiece(x, y).name == "Queen"
								|| chessBoard.getPiece(x, y).name == "Bishop")
							return true;
						else
							break;
					}
				}
				break;
			} else if (chessBoard.getBoardPosition(x, y) != 0)
				break;
		}
		y = yPos + 1;
		for (int x = xPos - 1; x >= 0
				&& y < chessBoard.getBoardHeight(); x--, y++) {
			if (chessBoard.getBoardPosition(x, y) == type
					&& chessBoard.getPiece(x, y).name == "King") {
				y = yPos - 1;
				for (x = xPos + 1; x < chessBoard.getBoardWidth()
						&& y >= 0; x++, y--) {
					if (chessBoard.getBoardPosition(x, y) == type)
						break;
					else if (chessBoard.getBoardPosition(x, y) == enemyType) {
						if (chessBoard.getPiece(x, y).name == "Queen"
								|| chessBoard.getPiece(x, y).name == "Bishop")
							return true;
						else
							break;
					}
				}
				break;
			} else if (chessBoard.getBoardPosition(x, y) != 0)
				break;
		}
		return false;
	}

	public boolean backslashDiagonalProtection(Board chessBoard, int xPos,
			int yPos, int type) {
		int enemyType = 0;
		if (type == 1)
			enemyType = 2;
		else
			enemyType = 1;
		int y = yPos - 1;
		for (int x = xPos - 1; x >= 0 && y >= 0; x--, y--) {
			if (chessBoard.getBoardPosition(x, y) == type
					&& chessBoard.getPiece(x, y).name == "King") {
				y = yPos + 1;
				for (x = xPos + 1; x < chessBoard.getBoardWidth()
						&& y < chessBoard.getBoardHeight(); x++, y++) {
					if (chessBoard.getBoardPosition(x, y) == type)
						break;
					else if (chessBoard.getBoardPosition(x, y) == enemyType) {
						if (chessBoard.getPiece(x, y).name == "Queen"
								|| chessBoard.getPiece(x, y).name == "Bishop")
							return true;
						else
							break;
					}
				}
				break;
			} else if (chessBoard.getBoardPosition(x, y) != 0)
				break;
		}
		y = yPos + 1;
		for (int x = xPos + 1; x < chessBoard.getBoardWidth()
				&& y < chessBoard.getBoardHeight(); x++, y++) {
			if (chessBoard.getBoardPosition(x, y) == type
					&& chessBoard.getPiece(x, y).name == "King") {
				y = yPos - 1;
				for (x = xPos - 1; x >= 0 && y >= 0; x--, y--) {
					if (chessBoard.getBoardPosition(x, y) == type)
						break;
					else if (chessBoard.getBoardPosition(x, y) == enemyType) {
						if (chessBoard.getPiece(x, y).name == "Queen"
								|| chessBoard.getPiece(x, y).name == "Bishop")
							return true;
						else
							break;
					}
				}
				break;
			} else if (chessBoard.getBoardPosition(x, y) != 0)
				break;
		}
		return false;
	}

	public boolean isCheck(Board chessBoard, int xPos, int yPos, int type,
			boolean kingCanCapture) {
		int y = 0;
		int x = 0;
		int enemyType = 0;
		if (type == 1)
			enemyType = 2;
		else
			enemyType = 1;
		for (x = xPos - 1; x >= 0; x--) {
			if (chessBoard.getBoardPosition(x, yPos) == type
					&& chessBoard.getPiece(x, yPos).name != "King")
				break;
			else if (chessBoard.getBoardPosition(x, yPos) == enemyType) {
				if (x == xPos - 1 && chessBoard.getPiece(x, yPos) != null
						&& kingCanCapture
						&& chessBoard.getPiece(x, yPos).name == "King")
					return true;
				else if (chessBoard.getPiece(x, yPos) != null
						&& (chessBoard.getPiece(x, yPos).name == "Queen"
								|| chessBoard.getPiece(x, yPos).name == "Rook"))
					return true;
				else
					break;
			}
		}
		for (x = xPos + 1; x < chessBoard.getBoardWidth(); x++) {
			if (chessBoard.getBoardPosition(x, yPos) == type
					&& chessBoard.getPiece(x, yPos).name != "King")
				break;
			else if (chessBoard.getBoardPosition(x, yPos) == enemyType) {
				if (x == xPos + 1 && chessBoard.getPiece(x, yPos) != null
						&& kingCanCapture
						&& chessBoard.getPiece(x, yPos).name == "King")
					return true;
				else if (chessBoard.getPiece(x, yPos) != null
						&& (chessBoard.getPiece(x, yPos).name == "Queen"
								|| chessBoard.getPiece(x, yPos).name == "Rook"))
					return true;
				else
					break;
			}
		}
		for (y = yPos - 1; y >= 0; y--) {
			if (chessBoard.getBoardPosition(xPos, y) == type
					&& chessBoard.getPiece(xPos, y).name != "King")
				break;
			else if (chessBoard.getBoardPosition(xPos, y) == enemyType) {
				if (y == yPos - 1 && chessBoard.getPiece(xPos, y) != null
						&& kingCanCapture
						&& chessBoard.getPiece(xPos, y).name == "King")
					return true;
				else if (chessBoard.getPiece(xPos, y) != null
						&& (chessBoard.getPiece(xPos, y).name == "Queen"
								|| chessBoard.getPiece(xPos, y).name == "Rook"))
					return true;
				else
					break;
			}
		}
		for (y = yPos + 1; y < chessBoard.getBoardHeight(); y++) {
			if (chessBoard.getBoardPosition(xPos, y) == type
					&& chessBoard.getPiece(xPos, y).name != "King")
				break;
			else if (chessBoard.getBoardPosition(xPos, y) == enemyType) {
				if (y == yPos + 1 && chessBoard.getPiece(xPos, y) != null
						&& kingCanCapture
						&& chessBoard.getPiece(xPos, y).name == "King")
					return true;
				else if (chessBoard.getPiece(xPos, y) != null
						&& (chessBoard.getPiece(xPos, y).name == "Queen"
								|| chessBoard.getPiece(xPos, y).name == "Rook"))
					return true;
				else
					break;
			}
		}
		for (y = yPos - 1, x = xPos - 1; y >= 0 && x >= 0; y--, x--) {
			if (chessBoard.getBoardPosition(x, y) == type
					&& chessBoard.getPiece(x, y).name != "King")
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType) {
				if (y == yPos - 1 && chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null
						&& ((kingCanCapture
								&& chessBoard.getPiece(x, y).name == "King")
								|| (type == 1 && chessBoard.getPiece(x,
										y).name == "Pawn")))
					return true;
				else if (chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null
						&& (chessBoard.getPiece(x, y).name == "Queen"
								|| chessBoard.getPiece(x, y).name == "Bishop"))
					return true;
				else
					break;
			}
		}
		for (y = yPos + 1, x = xPos + 1; y < chessBoard.getBoardHeight()
				&& x < chessBoard.getBoardWidth(); y++, x++) {
			if (chessBoard.getBoardPosition(x, y) == type
					&& chessBoard.getPiece(x, y).name != "King")
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType) {
				if (y == yPos + 1 && chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null
						&& ((kingCanCapture
								&& chessBoard.getPiece(x, y).name == "King")
								|| (type == 2 && chessBoard.getPiece(x,
										y).name == "Pawn")))
					return true;
				else if (chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null
						&& (chessBoard.getPiece(x, y).name == "Queen"
								|| chessBoard.getPiece(x, y).name == "Bishop"))
					return true;
				else
					break;
			}
		}
		for (y = yPos - 1, x = xPos + 1; y >= 0
				&& x < chessBoard.getBoardWidth(); y--, x++) {
			if (chessBoard.getBoardPosition(x, y) == type
					&& chessBoard.getPiece(x, y).name != "King")
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType) {
				if (y == yPos - 1 && chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null
						&& ((kingCanCapture
								&& chessBoard.getPiece(x, y).name == "King")
								|| (type == 1 && chessBoard.getPiece(x,
										y).name == "Pawn")))
					return true;
				else if (chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null
						&& (chessBoard.getPiece(x, y).name == "Queen"
								|| chessBoard.getPiece(x, y).name == "Bishop"))
					return true;
				else
					break;
			}
		}
		for (y = yPos + 1, x = xPos - 1; y < chessBoard.getBoardHeight()
				&& x >= 0; y++, x--) {
			if (chessBoard.getBoardPosition(x, y) == type
					&& chessBoard.getPiece(x, y).name != "King")
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType) {
				if (y == yPos + 1 && chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null
						&& ((kingCanCapture
								&& chessBoard.getPiece(x, y).name == "King")
								|| (type == 2 && chessBoard.getPiece(x,
										y).name == "Pawn")))
					return true;
				else if (chessBoard.getBoardPosition(x, y) != 0
						&& (chessBoard.getPiece(x, y).name == "Queen"
								|| chessBoard.getPiece(x, y).name == "Bishop"))
					return true;
				else
					break;
			}
		}
		for (y = -2; y <= 2; y++) {
			if (y != 0) {
				x = y % 2 == 0 ? 1 : 2;
				if (yPos + y >= 0 && yPos + y < chessBoard.getBoardHeight()
						&& xPos - x >= 0
						&& xPos - x < chessBoard.getBoardWidth()
						&& chessBoard.getBoardPosition(xPos - x,
								yPos + y) != type
						&& chessBoard.getBoardPosition(xPos - x,
								yPos + y) != 0) {
					if (chessBoard.getPiece(xPos - x, yPos + y) != null
							&& chessBoard.getPiece(xPos - x,
									yPos + y).name == "Knight")
						return true;
				}
				if (yPos + y >= 0 && yPos + y < chessBoard.getBoardHeight()
						&& xPos + x >= 0
						&& xPos + x < chessBoard.getBoardWidth()
						&& chessBoard.getBoardPosition(xPos + x,
								yPos + y) != type
						&& chessBoard.getBoardPosition(xPos + x,
								yPos + y) != 0) {
					if (chessBoard.getPiece(xPos + x, yPos + y) != null
							&& chessBoard.getPiece(xPos + x,
									yPos + y).name == "Knight")
						return true;
				}
			}
		}
		return false;
	}

	public void findAllCheckPieces(Board chessBoard, int xPos, int yPos,
			int type) {
		int y = 0;
		int x = 0;
		int enemyType = 0;
		if (type == 1)
			enemyType = 2;
		else
			enemyType = 1;

		for (x = xPos - 1; x >= 0; x--) {
			if (chessBoard.getBoardPosition(x, yPos) == type)
				break;
			else if (chessBoard.getBoardPosition(x, yPos) == enemyType) {
				if (chessBoard.getPiece(x, yPos) != null
						&& (chessBoard.getPiece(x, yPos).name == "Queen"
								|| chessBoard.getPiece(x, yPos).name == "Rook"))
					chessBoard.checkPieces.add(chessBoard.getPiece(x, yPos));
				else
					break;
			}
		}
		for (x = xPos + 1; x < chessBoard.getBoardWidth(); x++) {
			if (chessBoard.getBoardPosition(x, yPos) == type)
				break;
			else if (chessBoard.getBoardPosition(x, yPos) == enemyType) {
				if (chessBoard.getPiece(x, yPos) != null
						&& (chessBoard.getPiece(x, yPos).name == "Queen"
								|| chessBoard.getPiece(x, yPos).name == "Rook"))
					chessBoard.checkPieces.add(chessBoard.getPiece(x, yPos));
				else
					break;
			}
		}
		for (y = yPos - 1; y >= 0; y--) {
			if (chessBoard.getBoardPosition(xPos, y) == type)
				break;
			else if (chessBoard.getBoardPosition(xPos, y) == enemyType) {
				if (chessBoard.getPiece(xPos, y) != null
						&& (chessBoard.getPiece(xPos, y).name == "Queen"
								|| chessBoard.getPiece(xPos, y).name == "Rook"))
					chessBoard.checkPieces.add(chessBoard.getPiece(xPos, y));
				else
					break;
			}
		}
		for (y = yPos + 1; y < chessBoard.getBoardHeight(); y++) {
			if (chessBoard.getBoardPosition(xPos, y) == type)
				break;
			else if (chessBoard.getBoardPosition(xPos, y) == enemyType) {
				if (chessBoard.getPiece(xPos, y) != null
						&& (chessBoard.getPiece(xPos, y).name == "Queen"
								|| chessBoard.getPiece(xPos, y).name == "Rook"))
					chessBoard.checkPieces.add(chessBoard.getPiece(xPos, y));
				else
					break;
			}
		}
		for (y = yPos - 1, x = xPos - 1; y >= 0 && x >= 0; y--, x--) {
			if (chessBoard.getBoardPosition(x, y) == type)
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType) {
				if (y == yPos - 1 && chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null && (type == 1
								&& chessBoard.getPiece(x, y).name == "Pawn"))
					chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
				else if (chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null
						&& (chessBoard.getPiece(x, y).name == "Queen"
								|| chessBoard.getPiece(x, y).name == "Bishop"))
					chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
				else
					break;
			}
		}
		for (y = yPos + 1, x = xPos + 1; y < chessBoard.getBoardHeight()
				&& x < chessBoard.getBoardWidth(); y++, x++) {
			if (chessBoard.getBoardPosition(x, y) == type)
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType) {
				if (y == yPos + 1 && chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null && (type == 2
								&& chessBoard.getPiece(x, y).name == "Pawn"))
					chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
				else if (chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null
						&& (chessBoard.getPiece(x, y).name == "Queen"
								|| chessBoard.getPiece(x, y).name == "Bishop"))
					chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
				else
					break;
			}
		}
		for (y = yPos - 1, x = xPos + 1; y >= 0
				&& x < chessBoard.getBoardWidth(); y--, x++) {
			if (chessBoard.getBoardPosition(x, y) == type)
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType) {
				if (y == yPos - 1 && chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null && (type == 1
								&& chessBoard.getPiece(x, y).name == "Pawn"))
					chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
				else if (chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null
						&& (chessBoard.getPiece(x, y).name == "Queen"
								|| chessBoard.getPiece(x, y).name == "Bishop"))
					chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
				else
					break;
			}
		}
		for (y = yPos + 1, x = xPos - 1; y < chessBoard.getBoardHeight()
				&& x >= 0; y++, x--) {
			if (chessBoard.getBoardPosition(x, y) == type)
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType) {
				if (y == yPos + 1 && chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null && (type == 2
								&& chessBoard.getPiece(x, y).name == "Pawn"))
					chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
				if (chessBoard.getBoardPosition(x, y) != 0
						&& (chessBoard.getPiece(x, y).name == "Queen"
								|| chessBoard.getPiece(x, y).name == "Bishop"))
					chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
				else
					break;
			}
		}
		for (y = -2; y <= 2; y++) {
			if (y != 0) {
				x = y % 2 == 0 ? 1 : 2;
				if (yPos + y >= 0 && yPos + y < chessBoard.getBoardHeight()
						&& xPos - x >= 0
						&& xPos - x < chessBoard.getBoardWidth()
						&& chessBoard.getBoardPosition(xPos - x,
								yPos + y) != type
						&& chessBoard.getBoardPosition(xPos - x,
								yPos + y) != 0) {
					if (chessBoard.getPiece(xPos - x, yPos + y) != null
							&& chessBoard.getPiece(xPos - x,
									yPos + y).name == "Knight")
						chessBoard.checkPieces
								.add(chessBoard.getPiece(xPos - x, yPos + y));
				}
				if (yPos + y >= 0 && yPos + y < chessBoard.getBoardHeight()
						&& xPos + x >= 0
						&& xPos + x < chessBoard.getBoardWidth()
						&& chessBoard.getBoardPosition(xPos + x,
								yPos + y) != type
						&& chessBoard.getBoardPosition(xPos + x,
								yPos + y) != 0) {
					if (chessBoard.getPiece(xPos + x, yPos + y) != null
							&& chessBoard.getPiece(xPos + x,
									yPos + y).name == "Knight")
						chessBoard.checkPieces
								.add(chessBoard.getPiece(xPos + x, yPos + y));
				}
			}
		}
	}

	public void findAllSaviorPieces(Board chessBoard, int xPos, int yPos,
			int type, boolean protect) {
		int y = 0;
		int x = 0;
		int enemyType = 0;
		if (type == 1)
			enemyType = 2;
		else
			enemyType = 1;
		for (x = xPos - 1; x >= 0; x--) {
			if (chessBoard.getBoardPosition(x, yPos) == type)
				break;
			else if (chessBoard.getBoardPosition(x, yPos) == enemyType) {
				if (chessBoard.getPiece(x, yPos) != null
						&& (chessBoard.getPiece(x, yPos).name == "Queen"
								|| chessBoard.getPiece(x, yPos).name == "Rook"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(x, yPos));
				else
					break;
			}
		}
		for (x = xPos + 1; x < chessBoard.getBoardWidth(); x++) {
			if (chessBoard.getBoardPosition(x, yPos) == type)
				break;
			else if (chessBoard.getBoardPosition(x, yPos) == enemyType) {
				if (chessBoard.getPiece(x, yPos) != null
						&& (chessBoard.getPiece(x, yPos).name == "Queen"
								|| chessBoard.getPiece(x, yPos).name == "Rook"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(x, yPos));
				else
					break;
			}
		}
		for (y = yPos - 1; y >= 0; y--) {
			if (chessBoard.getBoardPosition(xPos, y) == type)
				break;
			else if (chessBoard.getBoardPosition(xPos, y) == enemyType) {
				if (enemyType == 2 && protect == true && y == yPos - 1
						&& chessBoard.getPiece(xPos, y) != null
						&& chessBoard.getPiece(xPos, y).name == "Pawn")
					chessBoard.saviorPieces.add(chessBoard.getPiece(xPos, y));
				if (enemyType == 2 && protect == true && y == yPos - 2
						&& chessBoard.getPiece(xPos, y) != null
						&& chessBoard.getPiece(xPos, y).name == "Pawn"
						&& chessBoard.getPiece(xPos, y).isFirstTime())
					chessBoard.saviorPieces.add(chessBoard.getPiece(xPos, y));
				if (chessBoard.getPiece(xPos, y) != null
						&& (chessBoard.getPiece(xPos, y).name == "Queen"
								|| chessBoard.getPiece(xPos, y).name == "Rook"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(xPos, y));
				else
					break;
			}
		}
		for (y = yPos + 1; y < chessBoard.getBoardHeight(); y++) {
			if (chessBoard.getBoardPosition(xPos, y) == type)
				break;
			else if (chessBoard.getBoardPosition(xPos, y) == enemyType) {
				if (enemyType == 1 && protect == true && y == yPos + 1
						&& chessBoard.getPiece(xPos, y) != null
						&& chessBoard.getPiece(xPos, y).name == "Pawn")
					chessBoard.saviorPieces.add(chessBoard.getPiece(xPos, y));
				if (enemyType == 1 && protect == true && y == yPos + 2
						&& chessBoard.getPiece(xPos, y) != null
						&& chessBoard.getPiece(xPos, y).name == "Pawn"
						&& chessBoard.getPiece(xPos, y).isFirstTime())
					chessBoard.saviorPieces.add(chessBoard.getPiece(xPos, y));
				if (chessBoard.getPiece(xPos, y) != null
						&& (chessBoard.getPiece(xPos, y).name == "Queen"
								|| chessBoard.getPiece(xPos, y).name == "Rook"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(xPos, y));
				else
					break;
			}
		}
		for (y = yPos - 1, x = xPos - 1; y >= 0 && x >= 0; y--, x--) {
			if (chessBoard.getBoardPosition(x, y) == type)
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType) {
				if (protect == false && y == yPos - 1
						&& chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null && (type == 1
								&& chessBoard.getPiece(x, y).name == "Pawn"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
				if (chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null
						&& (chessBoard.getPiece(x, y).name == "Queen"
								|| chessBoard.getPiece(x, y).name == "Bishop"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
				else
					break;
			}
		}
		for (y = yPos + 1, x = xPos + 1; y < chessBoard.getBoardHeight()
				&& x < chessBoard.getBoardWidth(); y++, x++) {
			if (chessBoard.getBoardPosition(x, y) == type)
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType) {
				if (protect == false && y == yPos + 1
						&& chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null && (type == 2
								&& chessBoard.getPiece(x, y).name == "Pawn"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
				if (chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null
						&& (chessBoard.getPiece(x, y).name == "Queen"
								|| chessBoard.getPiece(x, y).name == "Bishop"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
				else
					break;
			}
		}
		for (y = yPos - 1, x = xPos + 1; y >= 0
				&& x < chessBoard.getBoardWidth(); y--, x++) {
			if (chessBoard.getBoardPosition(x, y) == type)
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType) {
				if (protect == false && y == yPos - 1
						&& chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null && (type == 1
								&& chessBoard.getPiece(x, y).name == "Pawn"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
				if (chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null
						&& (chessBoard.getPiece(x, y).name == "Queen"
								|| chessBoard.getPiece(x, y).name == "Bishop"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
				else
					break;
			}
		}
		for (y = yPos + 1, x = xPos - 1; y < chessBoard.getBoardHeight()
				&& x >= 0; y++, x--) {
			if (chessBoard.getBoardPosition(x, y) == type)
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType) {
				if (protect == false && y == yPos + 1
						&& chessBoard.getBoardPosition(x, y) != 0
						&& chessBoard.getPiece(x, y) != null && (type == 2
								&& chessBoard.getPiece(x, y).name == "Pawn"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
				if (chessBoard.getBoardPosition(x, y) != 0
						&& (chessBoard.getPiece(x, y).name == "Queen"
								|| chessBoard.getPiece(x, y).name == "Bishop"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
				else
					break;
			}
		}
		for (y = -2; y <= 2; y++) {
			if (y != 0) {
				x = y % 2 == 0 ? 1 : 2;
				if (yPos + y >= 0 && yPos + y < chessBoard.getBoardHeight()
						&& xPos - x >= 0
						&& xPos - x < chessBoard.getBoardWidth()
						&& chessBoard.getBoardPosition(xPos - x,
								yPos + y) != type
						&& chessBoard.getBoardPosition(xPos - x,
								yPos + y) != 0) {
					if (chessBoard.getPiece(xPos - x, yPos + y) != null
							&& chessBoard.getPiece(xPos - x,
									yPos + y).name == "Knight")
						chessBoard.saviorPieces
								.add(chessBoard.getPiece(xPos - x, yPos + y));
				}
				if (yPos + y >= 0 && yPos + y < chessBoard.getBoardHeight()
						&& xPos + x >= 0
						&& xPos + x < chessBoard.getBoardWidth()
						&& chessBoard.getBoardPosition(xPos + x,
								yPos + y) != type
						&& chessBoard.getBoardPosition(xPos + x,
								yPos + y) != 0) {
					if (chessBoard.getPiece(xPos + x, yPos + y) != null
							&& chessBoard.getPiece(xPos + x,
									yPos + y).name == "Knight")
						chessBoard.saviorPieces
								.add(chessBoard.getPiece(xPos + x, yPos + y));
				}
			}
		}
	}

	public boolean isCheckmate(Board chessboard, int xPos, int yPos,
			int type) {
		boolean checkmate = true;
		int x = xPos;
		int y = yPos;
		for (y = yPos - 1; y <= yPos + 1; y++) {
			for (x = xPos - 1; x <= xPos + 1; x++) {
				if (y >= 0 && y < chessboard.getBoardHeight() && x >= 0
						&& x < chessboard.getBoardWidth()
						&& chessboard.getBoardPosition(x, y) != type) {
					if (!isCheck(chessboard, x, y, type, true)) {
						checkmate = false;
						break;
					}
				}
			}
			if (!checkmate)
				break;
		}
		if (chessboard.checkPieces.size() < 2) {
			Piece checkPiece = chessboard.checkPieces.get(0);
			canCapture(chessboard, checkPiece);
			canProtect(chessboard, xPos, yPos, type, checkPiece);
			if (!chessboard.saviorPieces.isEmpty()) {
				for (Iterator<Piece> piece = chessboard.saviorPieces
						.iterator(); piece.hasNext();) {
					Piece item = piece.next();
					item.isASavior = true;
					if (verticalProtection(chessboard, item.xPos, item.yPos,
							item.type)
							|| horizontalProtection(chessboard, item.xPos,
									item.yPos, item.type)
							|| slashDiagonalProtection(chessboard, item.xPos,
									item.yPos, item.type)
							|| backslashDiagonalProtection(chessboard,
									item.xPos, item.yPos, item.type)) {
						item.isASavior = false;
						piece.remove();
					}
				}
			}
			if (!chessboard.saviorPieces.isEmpty())
				checkmate = false;
		}
		return (checkmate);
	}

	public void canCapture(Board chessboard, Piece checkPiece) {
		findAllSaviorPieces(chessboard, checkPiece.xPos, checkPiece.yPos,
				checkPiece.type, false);
	}

	public void canProtect(Board chessboard, int xPos, int yPos, int type,
			Piece checkPiece) {
		if (checkPiece.name == "Knight" || checkPiece.name == "Pawn")
			return;
		if (xPos == checkPiece.xPos && yPos > checkPiece.yPos)
			for (int y = checkPiece.yPos + 1; y < yPos; y++)
				findAllSaviorPieces(chessboard, checkPiece.xPos, y,
						checkPiece.type, true);
		if (xPos == checkPiece.xPos && yPos < checkPiece.yPos)
			for (int y = checkPiece.yPos - 1; y > yPos; y--)
				findAllSaviorPieces(chessboard, checkPiece.xPos, y,
						checkPiece.type, true);
		if (xPos > checkPiece.xPos && yPos == checkPiece.yPos)
			for (int x = checkPiece.xPos + 1; x < xPos; x++)
				findAllSaviorPieces(chessboard, x, checkPiece.yPos,
						checkPiece.type, true);
		if (xPos < checkPiece.xPos && yPos == checkPiece.yPos)
			for (int x = checkPiece.xPos - 1; x > xPos; x--)
				findAllSaviorPieces(chessboard, x, checkPiece.yPos,
						checkPiece.type, true);
		int y = checkPiece.yPos + 1;
		if (xPos > checkPiece.xPos && yPos > checkPiece.yPos)
			for (int x = checkPiece.xPos + 1; x < xPos && y < yPos; x++, y++)
				findAllSaviorPieces(chessboard, x, y, checkPiece.type, true);
		y = checkPiece.yPos - 1;
		if (xPos < checkPiece.xPos && yPos < checkPiece.yPos)
			for (int x = checkPiece.xPos - 1; x > xPos && y > yPos; x--, y--)
				findAllSaviorPieces(chessboard, x, y, checkPiece.type, true);
		y = checkPiece.yPos + 1;
		if (xPos < checkPiece.xPos && yPos > checkPiece.yPos)
			for (int x = checkPiece.xPos - 1; x > xPos && y < yPos; x--, y++)
				findAllSaviorPieces(chessboard, x, y, checkPiece.type, true);
		y = checkPiece.yPos - 1;
		if (xPos > checkPiece.xPos && yPos < checkPiece.yPos)
			for (int x = checkPiece.xPos + 1; x < xPos && y > yPos; x++, y--)
				findAllSaviorPieces(chessboard, x, y, checkPiece.type, true);
	}

	public boolean isThisProtecting(Board chessboard, int xPos, int yPos,
			int type) {
		Piece checkPiece = chessboard.checkPieces.get(0);
		if (chessboard.getKing(type).xPos == checkPiece.xPos
				&& chessboard.getKing(type).yPos > checkPiece.yPos)
			if (xPos == chessboard.getKing(type).xPos
					&& yPos < chessboard.getKing(type).yPos
					&& yPos > checkPiece.yPos)
				return true;
		if (chessboard.getKing(type).xPos == checkPiece.xPos
				&& chessboard.getKing(type).yPos < checkPiece.yPos)
			if (xPos == chessboard.getKing(type).xPos
					&& yPos > chessboard.getKing(type).yPos
					&& yPos < checkPiece.yPos)
				return true;
		if (chessboard.getKing(type).xPos > checkPiece.xPos
				&& chessboard.getKing(type).yPos == checkPiece.yPos)
			if (yPos == chessboard.getKing(type).yPos
					&& xPos < chessboard.getKing(type).xPos
					&& xPos > checkPiece.xPos)
				return true;
		if (chessboard.getKing(type).xPos < checkPiece.xPos
				&& chessboard.getKing(type).yPos == checkPiece.yPos)
			if (yPos == chessboard.getKing(type).yPos
					&& xPos > chessboard.getKing(type).xPos
					&& xPos < checkPiece.xPos)
				return true;
		int y = checkPiece.yPos;
		if (chessboard.getKing(type).xPos > checkPiece.xPos
				&& chessboard.getKing(type).yPos > checkPiece.yPos)
			for (int x = checkPiece.xPos; x < chessboard.getKing(type).xPos
					&& y < chessboard.getKing(type).yPos; x++, y++)
				if (xPos == x && yPos == y)
					return true;
		y = checkPiece.yPos;
		if (chessboard.getKing(type).xPos < checkPiece.xPos
				&& chessboard.getKing(type).yPos < checkPiece.yPos)
			for (int x = checkPiece.xPos; x > chessboard.getKing(type).xPos
					&& y > chessboard.getKing(type).yPos; x--, y--)
				if (xPos == x && yPos == y)
					return true;
		y = checkPiece.yPos;
		if (chessboard.getKing(type).xPos < checkPiece.xPos
				&& chessboard.getKing(type).yPos > checkPiece.yPos)
			for (int x = checkPiece.xPos; x > chessboard.getKing(type).xPos
					&& y < chessboard.getKing(type).yPos; x--, y++)
				if (xPos == x && yPos == y)
					return true;
		y = checkPiece.yPos;
		if (chessboard.getKing(type).xPos > checkPiece.xPos
				&& chessboard.getKing(type).yPos < checkPiece.yPos)
			for (int x = checkPiece.xPos; x < chessboard.getKing(type).xPos
					&& y > chessboard.getKing(type).yPos; x++, y--)
				if (xPos == x && yPos == y)
					return true;
		return false;
	}
}
