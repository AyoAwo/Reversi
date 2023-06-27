
public class ReversiController implements IController {
	
	IModel model;
	IView view;

	public ReversiController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialise(IModel model, IView view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void startup() {
		model.setFinished(false);
		model.clear(0);
		model.setBoardContents(3, 3, 1);
		model.setBoardContents(4, 4, 1);
		model.setBoardContents(3, 4, 2);
		model.setBoardContents(4, 3, 2);
		model.setPlayer(1);
		
		this.update();
	}

	@Override
	public void update() {
		int player = model.getPlayer();
		int opp = 3 - player;
		boolean playerValid = false;
		boolean oppValid = false;
		
		for ( int y = 0 ; y < model.getBoardHeight() ; y++ ) {
			for ( int x = 0 ; x < model.getBoardWidth() ; x++ ) {
				if(validMove(x,y,player,false) > 0) {
					playerValid = true; //Notes that current player can make at least 1 move
				}
				//notes if opponent has any valid moves left
				if(validMove(x,y,opp,false) > 0)
					oppValid = true;
			}
		}

		if(!playerValid) {
			if(!oppValid)
				this.gameFinished();
			else {
				model.setPlayer(opp);
				this.update();
			}
			return;
		}
		if(model.hasFinished()) model.setFinished(false);
		view.feedbackToUser(1, (model.getPlayer()==1) ? "Black player - choose where to put your piece" : "Black player - not your turn");
		view.feedbackToUser(2, (model.getPlayer()==2) ? "White player - choose where to put your piece" : "White player - not your turn");
		view.refreshView();
	}

	@Override
	public void squareSelected(int player, int x, int y) {
		
		if (model.hasFinished()){
			gameFinished();
			return;
		}
		if(model.getPlayer() != player)
			view.feedbackToUser(player, "It is not your turn!");
		else if(validMove(x, y, player, false) > 0) {
			validMove(x, y, player, true); //the capture flag dictates if pieces overturned through the function
			model.setBoardContents(x, y, player); //selected piece then placed within model
			model.setPlayer( 3 - model.getPlayer()); //turn is changed to opponent
			this.update(); //calls update function
		}
	}
	
	private int validMove(int x, int y, int player, boolean capture) {
		int moveTotal = 0;
		int count;
		if(model.getBoardContents(x, y) > 0)
			return 0;
		for(int i=-1;i<2;i++)
			for(int j=-1; j<2;j++)
				if((count = checkDirection(x, y, i, j, player, capture))>0)
					moveTotal += count;
		
		return moveTotal;
	}
	
	private int checkDirection(int x, int y, int offsetx, int offsety, int player, boolean capture) {
		int nx = x + offsetx;
		int ny = y + offsety;
		if (nx < 0 || nx >= model.getBoardWidth() || ny < 0 || ny >= model.getBoardHeight() || model.getBoardContents(nx, ny) <= 0)
			return -1;
		else if (model.getBoardContents(nx, ny) == player)
			return 0;
		else {
			int count = checkDirection(nx, ny, offsetx, offsety, player, capture);
			if (count == -1)
				return -1;
			else {
				if(capture)
					model.setBoardContents(nx, ny, player);
				return count + 1;
			}
		}
	}
	
	private void gameFinished() {
		int white = 0;
		int black = 0;
		StringBuilder message = new StringBuilder();
		model.setFinished(true);
		
		for (int y = 0 ; y < model.getBoardHeight() ; y++) {
			for (int x = 0 ; x < model.getBoardWidth() ; x++) {
				if (model.getBoardContents(x, y) == 1)
					black += 1;
				else if (model.getBoardContents(x, y) == 2)
					white += 1;
			}
		}
		
		if(white > black)
			message.append("White won. White "+white+" to Black "+black+". ");
		if(black > white)
			message.append("Black won. Black "+black+" to White "+white+". ");
		if(white == black)
			message.append("Draw. Both players ended with "+black+" pieces. ");
		message.append("Reset game to replay.");
		view.feedbackToUser(1, message.toString());
		view.feedbackToUser(2, message.toString());
		view.refreshView();
	}

	@Override
	public void doAutomatedMove(int player) {
		int x = -1;
		int y = -1;
		int bestScore = 0;
		int count;
		for (int j = 0 ; j < model.getBoardHeight() ; j++) {
			for (int i = 0 ; i < model.getBoardWidth() ; i++) {
				if ((count = validMove(i,j,player, false)) >= bestScore) {
					bestScore = count;		
					x = i;
					y = j;
				}
			}
		}
		this.squareSelected(player, x, y);
	}

	public static void main(String[] args) {
		new ReversiMain();

	}

}
