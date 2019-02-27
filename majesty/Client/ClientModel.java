package Client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import CommonClass.Message;
import PlayScene.GameController;
import PlayScene.PlayModel;
import PlayScene.PlayStart;
import javafx.stage.Stage;

public class ClientModel {
	private MainNextTime main=new MainNextTime();
	private Socket socket;
	private PlayStart playStart= new PlayStart();
	private Stage stage= new Stage();
	private List<String> players= new ArrayList<String>();
	private List<String> stapelKarten= new ArrayList<String>();
	private String name;
	public void connect(String n) {
		String ipAdress= "127.0.0.1";
		Integer port=8080; 
		try {
			socket = new Socket(ipAdress, port);
			name=n;
			Message msgOut=new Message();
			msgOut.setMessage("anmelden");
			msgOut.setName(name);
			msgOut.send(socket);
			getMessage();
		}catch(Exception e) {
			e.toString();
		}
	}
	private void getMessage() {
		Message msgIn= (Message) Message.receive(socket);
		String message= msgIn.getMessage();
		switch(message) {
		case "StartSpiel":
			players=msgIn.getPlayers();
			stapelKarten=msgIn.getCards();
			PlayModel.setCards(stapelKarten, ClientModel.this);
			GameController.setPlayers(players, ClientModel.this);
			playStart.start(this.stage);
			break;
		case "Sorry":
			System.out.println("begin");
			main.begin(this.stage);
			break;
		}
	}

	public void disconnect() {
		if(socket!=null) {
			try {
				socket.close();
			}catch(IOException e) {
			}
		}
	}
	public String getName() {
		return this.name;
	}
	public Socket getSocket() {
		return this.socket;
	}
	
}
