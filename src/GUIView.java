
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUIView implements IView {
	
	IModel model;
	IController controller;
	JFrame P1Frame;
	JFrame P2Frame;
	JLabel P1Message = new JLabel();
	JLabel P2Message = new JLabel();
	JPanel P1Board;
	JPanel P2Board;
	
	
	public static void main(String[] args) {
	}

	@Override
	public void initialise(IModel model, IController controller) {
		this.model = model;
		this.controller = controller;
		
		P1Frame = new JFrame("Reversi - black player");
		P2Frame = new JFrame("Reversi - white player");
		P1Board = new GUIViewBoard(1, model, controller);
		P2Board = new GUIViewBoard(2, model, controller);
		
		for(int i=0;i<2;i++) {
			JFrame frame = (i == 0) ? P1Frame : P2Frame;
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(((i == 0) ? P1Message : P2Message), BorderLayout.NORTH); 
			frame.add(((i == 0) ? P1Board : P2Board), BorderLayout.CENTER);
			JPanel buttonsPanel = new JPanel();
			frame.add(buttonsPanel, BorderLayout.SOUTH);
			buttonsPanel.setLayout(new GridLayout(2,1));
			JButton AI = new JButton((i == 0) ? "Greedy AI (play black)" : "Greedy AI (play white)");
			int player = i+1;
			AI.addActionListener(e -> controller.doAutomatedMove(player));
			buttonsPanel.add(AI);
			JButton Restart = new JButton("Restart");
			Restart.addActionListener(e->controller.startup());
			buttonsPanel.add(Restart);	
			frame.pack();
			frame.setVisible(true);
		}
		
        P1Frame.setLocationRelativeTo(null);
        P2Frame.setLocation(P1Frame.getX() + P1Frame.getWidth(), P1Frame.getY());
	}

	@Override
	public void refreshView() {		
		/*Calling repaint() on any parent component will cause it to recursively repaint all
		components within it, so this will repaint all the squares on the board.
		*/
		P1Board.repaint();
		P2Board.repaint();
	}

	@Override
	public void feedbackToUser(int player, String message) {
		if(player==1) {
			P1Message.setText(message);
		}
		else if(player==2) {
			P2Message.setText(message);
		}
	}
}

