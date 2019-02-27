package Server;



public class ServerController {

	private ServerModel model;
	private ServerView view;

	public ServerController(ServerModel model, ServerView view) {
		this.model = model;
		this.view = view;
		view.btnStart.setOnAction(event -> {
			view.btnStart.setDisable(true);
			model.startSpiel();
		});
		view.getStage().setOnCloseRequest(event->model.stopServer());
	}	
}


