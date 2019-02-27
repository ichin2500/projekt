package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import CommonClass.Message;
public class ServerModel {
	protected List<ClientThread> clients= new ArrayList<>();
	protected List<String> players= new ArrayList<String>();
	protected ServerSocket serverSocket;
	protected List<Socket> sockets= new ArrayList<Socket>();
	private Cards cards= new Cards();
	private HashMap<String, Integer> listDaten=new HashMap<>();
	private List<String> karten=new ArrayList<>();
	Integer portNumber = 8080;
	private Message msgIn;
	private Socket clientSocket;	
	private boolean stop;
	private String gewinner;

	public void startSpiel() {
		try {
			serverSocket=new ServerSocket(portNumber);
			Runnable r= new Runnable() {
				@Override
				public void run() {
					int number=0;
					while(!stop) {
						try {
							clientSocket = serverSocket.accept();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						msgIn =(Message) Message.receive(clientSocket);
						if(number==0&&msgIn.getMessage().equals("anmelden")) {
							sockets.add(clientSocket);
							players.add(msgIn.getName());
						}else if(number==1&&msgIn.getMessage().equals("anmelden")) {
							sockets.add(clientSocket);
							players.add(msgIn.getName());
							for(String player: players) {
								listDaten.put(player, 0);
							}
							karten= cards.cards();
							Message msgOut=new Message();
							msgOut.setCards(karten);
							msgOut.setPlayers(players);
							msgOut.setMessage("StartSpiel");

							for(Socket s: sockets) {
								msgOut.send(s);
								ClientThread c=new ClientThread(ServerModel.this, s);
								clients.add(c);
								c.start();
							}
						} else if(number>=2&&msgIn.getMessage().equals("anmelden")){

							Message msgOut= new Message();
							msgOut.setMessage("Sorry");
							System.out.println("sorry");
							msgOut.send(clientSocket);
							try {
								if(clientSocket!=null) 
									clientSocket.close();
							} catch( Exception e) {
								e.toString();
							}
						}
						number++;
					}
				}
			};
			Thread t=new Thread(r);
			t.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public void stopServer() {

		for(ClientThread c: clients) c.stopClient();
		stop=true;
		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				// Uninteresting
			}
		}
	}
	public void broadcast(Message msg) {
		for(ClientThread c: clients) {
			c.send(msg);
		}
	}
	public HashMap<String, Integer> datenAktualisieren(String name, int punkte, int punkteGegner){
		for(Map.Entry<String, Integer> e: listDaten.entrySet()) {
			if(e.getKey().equals(name)) {
				e.setValue(punkte);
			} else {
				int i=e.getValue();
				e.setValue(i+punkteGegner);
			}
		}
		return listDaten;
	}
	public List<String> getPlayers() {
		return this.players;
	}
	public List<String> getKarten(){
		return this.karten;
	}
	public List<ClientThread> getClients() {
		return this.clients;
	}
	public String GewinnerErmitteln() {
		List<Integer> punkte= new ArrayList<>();
		List<String> spielerNamen= new ArrayList<>();

		for(Map.Entry<String, Integer> e: listDaten.entrySet()) {
			spielerNamen.add(e.getKey());
			punkte.add(e.getValue());
		}
		System.out.println(listDaten);
		System.out.println(punkte);
		System.out.println(spielerNamen);
		if(punkte.get(0)>punkte.get(1)) {
			gewinner= spielerNamen.get(0); 
		} else if(punkte.get(1)>punkte.get(0)){
			gewinner= spielerNamen.get(1);
		}else {
			gewinner= "keiner hat gewonnen!";
		}
		System.out.println(gewinner);

		return gewinner;
	}
}








