package PlayScene;

import java.net.URL;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class PlayStart extends Application {
	FXMLLoader fxmlLoader;
	@Override
	public void start(Stage primaryStage)  {
		try {
			URL fxmlUrl = getClass().getResource("Scene.fxml");
			this.fxmlLoader = new FXMLLoader(fxmlUrl);
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);	
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
