
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GUIViewBoard extends JPanel {

	private static final long serialVersionUID = 1L;
	IModel reversiModel;
	int player;
	

	public GUIViewBoard(int player, IModel model, IController controller) {
		this.player = player;
		this.reversiModel = model;
		
		int width = model.getBoardWidth();
		int height = model.getBoardHeight();
	
		this.setLayout(new GridLayout(width,height));
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
                int x = (player == 1) ? i : width-1-i;
                int y = (player == 1) ? j : height-1-j;
                Tile square = new Tile(x,y);
                square.addActionListener(e-> controller.squareSelected(player, x, y));
                this.add(square);
            }
		}
	}
	
	public class Tile extends JButton {

		private static final long serialVersionUID = 1L;
		Color pieceColor = null;
		int x;
		int y;
		
		public Tile(int x, int y)
		{
			this.x = x;
			this.y = y;
			setMinimumSize( new Dimension(50, 50) );
			setPreferredSize( new Dimension(50, 50) );
			this.setBorderPainted(false);
		}

		public void setPieceColor()
		{
			switch(reversiModel.getBoardContents(x, y)) {
			case 1:
				this.pieceColor = Color.BLACK;
				break;
			case 2:
				this.pieceColor = Color.WHITE;
				break;
			default:
				this.pieceColor = null;
			}
		}

		protected void paintComponent(Graphics g)
		{
			//super.paintComponent(arg0);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.GREEN);
			g.fillRect(1, 1, getWidth()-2, getHeight()-2);
			
			/* Size of piece based on grid size, is reactive to resizing.
			 * Takes the smaller of width and height so that pieces are always centred and 
			 * smaller than the square they are in */
			int pieceSize = (getWidth() <= getHeight()) ? getWidth()-8 : getHeight()-8; 
			setPieceColor(); //Get the piece colour based on current board contents
			
			if ( pieceColor != null ) {
				g.setColor((pieceColor == Color.WHITE) ? Color.BLACK : Color.WHITE);
				g.fillOval((getWidth()-(pieceSize+4))/2,(getHeight()-(pieceSize+4))/2, pieceSize+4, pieceSize+4); 
				g.setColor(pieceColor);
				g.fillOval((getWidth()-pieceSize)/2,(getHeight()-pieceSize)/2, pieceSize, pieceSize);
				
			}
		}

	}
	
}
