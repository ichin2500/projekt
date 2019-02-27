package Server;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class ServerView {
	protected Stage stage;
	private ServerModel model;
	protected static TextArea txtArea= new TextArea();
	Region topSpacer = new Region();
	Button btnStart = new Button("Start");

	public ServerView(Stage stage, ServerModel model) {
		this.stage = stage;
		this.model = model;
		BorderPane pane= new BorderPane();
		pane.setLeft(btnStart);
		pane.setCenter(txtArea);
		btnStart.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
		txtArea.setMaxSize(700,  400);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setTitle("Server");
	}
	protected void start() {
		stage.show();
		stage.setMinWidth(stage.getWidth());
		stage.setMinHeight(stage.getHeight());
	}
	public Stage getStage() {
		return this.stage;
	}
	public void stop() {
		// TODO Auto-generated method stub
		stage.hide();
	}
	public Object updateClients() {
		StringBuffer sb= new StringBuffer();
		for(ClientThread c: model.clients) {
			sb.append(c.toString());
			sb.append("\n");
		}
		this.txtArea.setText(sb.toString());
		return null;
	}
}