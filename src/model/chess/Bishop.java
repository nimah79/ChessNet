package model.chess;

import javafx.scene.image.*;

public class Bishop extends Piece {

	private Image image;

	public Bishop(int type, int xPos, int yPos) {
		super(type, xPos, yPos);
		name = "Bishop";
		image = new Image("file:assets/pieces/"
				+ (type == 1 ? "White" : "Black") + "_" + name + ".png");
		imageView.setImage(image);
		imageView.fitHeightProperty();
		imageView.fitWidthProperty();
		imageView.setPreserveRatio(true);
		imageView.setSmooth(true);
		imageView.setCache(true);
	}

	@Override
	public ImageView getImage() {
		return (imageView);
	}

	@Override
	public void SelectPiece(Board chessBoard) {
		int y = this.yPos + 1;
		chessBoard.colorSquare(this.xPos, this.yPos, true);
		if (chessBoard.checkState && !this.isASavior)
			return;
		if (gameLogic.horizontalProtection(chessBoard, this.xPos, this.yPos,
				this.type)
				|| gameLogic.verticalProtection(chessBoard, this.xPos,
						this.yPos, this.type))
			return;
		if (!gameLogic.slashDiagonalProtection(chessBoard, this.xPos, this.yPos,
				this.type)) {
			for (int x = this.xPos + 1; x < chessBoard.getBoardWidth()
					&& y < chessBoard.getBoardHeight(); x++, y++) {
				if (chessBoard.getBoardPosition(x, y) == 0) {
					if (chessBoard.checkState) {
						if (gameLogic.isThisProtecting(chessBoard, x, y,
								this.type))
							chessBoard.colorSquare(x, y, false);
					} else
						chessBoard.colorSquare(x, y, false);
				} else if (chessBoard.getBoardPosition(x, y) == this.type)
					break;
				else {
					if (chessBoard.checkState) {
						if (gameLogic.isThisProtecting(chessBoard, x, y,
								this.type))
							chessBoard.colorSquare(x, y, false);
					} else
						chessBoard.colorSquare(x, y, false);
					break;
				}
			}
			y = this.yPos - 1;
			for (int x = this.xPos - 1; x >= 0 && y >= 0; x--, y--) {
				if (chessBoard.getBoardPosition(x, y) == 0) {
					if (chessBoard.checkState) {
						if (gameLogic.isThisProtecting(chessBoard, x, y,
								this.type))
							chessBoard.colorSquare(x, y, false);
					} else
						chessBoard.colorSquare(x, y, false);
				} else if (chessBoard.getBoardPosition(x, y) == this.type)
					break;
				else {
					if (chessBoard.checkState) {
						if (gameLogic.isThisProtecting(chessBoard, x, y,
								this.type))
							chessBoard.colorSquare(x, y, false);
					} else
						chessBoard.colorSquare(x, y, false);
					break;
				}
			}
		}
		if (!gameLogic.backslashDiagonalProtection(chessBoard, this.xPos,
				this.yPos, this.type)) {
			y = this.yPos + 1;
			for (int x = this.xPos - 1; x >= 0
					&& y < chessBoard.getBoardHeight(); x--, y++) {
				if (chessBoard.getBoardPosition(x, y) == 0) {
					if (chessBoard.checkState) {
						if (gameLogic.isThisProtecting(chessBoard, x, y,
								this.type))
							chessBoard.colorSquare(x, y, false);
					} else
						chessBoard.colorSquare(x, y, false);
				} else if (chessBoard.getBoardPosition(x, y) == this.type)
					break;
				else {
					if (chessBoard.checkState) {
						if (gameLogic.isThisProtecting(chessBoard, x, y,
								this.type))
							chessBoard.colorSquare(x, y, false);
					} else
						chessBoard.colorSquare(x, y, false);
					break;
				}
			}
			y = this.yPos - 1;
			for (int x = this.xPos + 1; x < chessBoard.getBoardWidth()
					&& y >= 0; x++, y--) {
				if (chessBoard.getBoardPosition(x, y) == 0) {
					if (chessBoard.checkState) {
						if (gameLogic.isThisProtecting(chessBoard, x, y,
								this.type))
							chessBoard.colorSquare(x, y, false);
					} else
						chessBoard.colorSquare(x, y, false);
				} else if (chessBoard.getBoardPosition(x, y) == this.type)
					break;
				else {
					if (chessBoard.checkState) {
						if (gameLogic.isThisProtecting(chessBoard, x, y,
								this.type))
							chessBoard.colorSquare(x, y, false);
					} else
						chessBoard.colorSquare(x, y, false);
					break;
				}
			}
		}
	}

}
