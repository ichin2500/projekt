package Server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import CommonClass.Message;

public class ClientThread extends Thread {
	private ServerModel model;
	private Socket socket;
	private String name;
	private HashMap<String, Integer> daten;
	private int position;
	private String n1;
	private String n2;
	private boolean b=false;
	private List<String> datenListe;
	private List<String> listeLazarett;
	public ClientThread(ServerModel serverModel, Socket s) {
		b=true;
		this.model=serverModel;
		this.socket=s;
		Runnable r=new Runnable() {
			public void run() {
				while(b) {
					Message msgIn= Message.receive(s);
					String message=msgIn.getMessage();
					if(msgIn!=null&&message.equals("ZugEnd")) {
						prozessMsgIn(msgIn);
						prozessMsgOut();
					}else if(message.equals("Ende")){
						String gewinner=model.GewinnerErmitteln();
						Message msgOut= new Message();
						msgOut.setMessage("SpielEnde");
						msgOut.setGewinner(gewinner);
						msgOut.setDaten(daten);
						model.broadcast(msgOut);

						b=false;

					}
				}
			}
		};
		Thread t= new Thread(r, "Client");
		t.start();
	}
	private void prozessMsgIn(Message msgIn) {
		this.name=msgIn.getName();
		int punkte= msgIn.getPunkte();
		int punkteGegner= msgIn.getGegnerPunkte();
		this.daten= model.datenAktualisieren(name, punkte, punkteGegner);
		this.position= msgIn.getPosition();
		this.datenListe= msgIn.getDatenListe();
		this.listeLazarett= msgIn.getListeLazarett();
		model.getKarten().remove(this.position);
	}
	protected void prozessMsgOut() {
		n1= model.getPlayers().get(0);
		n2= model.getPlayers().get(1);
		Message msgOut= new Message();
		msgOut.setMessage("Zug");
		msgOut.setDaten(daten);
		msgOut.setCards(model.getKarten());
		msgOut.setCardPosition(this.position);
		msgOut.setListeLazarett(this.listeLazarett);
		msgOut.setDatenListe(this.datenListe);
		if(this.name.equals(n1)) {
			msgOut.setName(n2);
		}else {
			msgOut.setName(n1);
		}
		model.broadcast(msgOut);
	}
	public void send(Message msg) {
		msg.send(socket);
	}
	public void stopClient() {
		try {
			socket.close();
		}catch(IOException e) {

		}
	}

}




