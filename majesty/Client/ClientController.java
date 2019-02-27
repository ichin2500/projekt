package Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class ClientController {
	private ClientModel model=new ClientModel();
	@FXML
	private BorderPane root;
	@FXML
	private ImageView ivMajesty;
	@FXML
	private static Button news;
	@FXML
	private TextField tfName;
	@FXML
	private Button btnLos;
	@FXML
	private Button btnAnleitung;
	@FXML
	public void anmelden(ActionEvent e) throws Exception {
		String name= tfName.getText();
		btnLos.setDisable(true);
		model.connect(name);
	}
}
