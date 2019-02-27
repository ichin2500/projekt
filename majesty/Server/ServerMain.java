package Server;
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

public class ServerMain extends Application{

	public static void main(String[] args) {
		System.out.println("start");
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws IOException {
		ServerModel model = new ServerModel();
		ServerView view = new ServerView(primaryStage, model);
		ServerController controller = new ServerController(model, view);
		view.start();
	}
}

