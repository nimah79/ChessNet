package model.chess;

import javafx.scene.image.*;

public class Knight extends Piece {

	private Image image;

	public Knight(int type, int xPos, int yPos) {
		super(type, xPos, yPos);
		name = "Knight";
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
		int x = 0;
		chessBoard.colorSquare(this.xPos, this.yPos, true);
		if (chessBoard.checkState && !this.isASavior)
			return;
		if (gameLogic.verticalProtection(chessBoard, this.xPos, this.yPos,
				this.type)
				|| gameLogic.horizontalProtection(chessBoard, this.xPos,
						this.yPos, this.type)
				|| gameLogic.slashDiagonalProtection(chessBoard, this.xPos,
						this.yPos, this.type)
				|| gameLogic.backslashDiagonalProtection(chessBoard, this.xPos,
						this.yPos, this.type))
			return;
		for (int y = -2; y <= 2; y++) {
			if (y != 0) {
				x = y % 2 == 0 ? 1 : 2;
				if (this.yPos + y >= 0
						&& this.yPos + y < chessBoard.getBoardHeight()
						&& this.xPos - x >= 0
						&& this.xPos - x < chessBoard.getBoardWidth()
						&& chessBoard.getBoardPosition(this.xPos - x,
								this.yPos + y) != this.type) {
					if (chessBoard.checkState) {
						if (gameLogic.isThisProtecting(chessBoard,
								this.xPos - x, this.yPos + y, this.type))
							chessBoard.colorSquare(this.xPos - x, this.yPos + y,
									false);
					} else
						chessBoard.colorSquare(this.xPos - x, this.yPos + y,
								false);
				}
				if (this.yPos + y >= 0
						&& this.yPos + y < chessBoard.getBoardHeight()
						&& this.xPos + x >= 0
						&& this.xPos + x < chessBoard.getBoardWidth()
						&& chessBoard.getBoardPosition(this.xPos + x,
								this.yPos + y) != this.type) {
					if (chessBoard.checkState) {
						if (gameLogic.isThisProtecting(chessBoard,
								this.xPos + x, this.yPos + y, this.type))
							chessBoard.colorSquare(this.xPos + x, this.yPos + y,
									false);
					} else
						chessBoard.colorSquare(this.xPos + x, this.yPos + y,
								false);
				}
			}
		}
	}

}
