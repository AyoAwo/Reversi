
public class ReversiMain
{
	IModel model;
	IView view;
	IController controller;

	ReversiMain()
	{
		model = new GameModel();
		
		view = new GUIView(); 
		
		controller = new ReversiController();

		// Initialise everything...
		model.initialise(8, 8, view, controller);
		controller.initialise(model, view);
		view.initialise(model, controller);
		
		// Now start the game - set up the board
		controller.startup();
	}
	
	public static void main(String[] args)
	{
		new ReversiMain();
	}
}
