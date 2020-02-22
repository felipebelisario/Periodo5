package freezemonster;

import java.awt.*;

import spriteframework.AbstractBoard;
import spriteframework.MainFrame;

public class FreezeMonsterGame extends MainFrame {


	public FreezeMonsterGame () {
		super("Freeze Monster");
	}
	
	protected  AbstractBoard createBoard() {
		FreezeMonsterBoard board = new FreezeMonsterBoard();
		board.setColor(Color.green);
		return board;
	}


	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {

			new FreezeMonsterGame();
		});
	}

}
