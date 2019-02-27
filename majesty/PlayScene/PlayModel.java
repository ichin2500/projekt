package PlayScene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Client.ClientModel;
import CommonClass.Message;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayModel {
	private static List<String> stapelCards;
	private static ClientModel model;
	private static HashMap<String, Integer> daten=new HashMap<>();
	private ImageView[] imageList;
	private ImageView[] imageCards;
	protected static Image[] listImages;
	private GameController controller;
	private int cardPosition;
	private String gewinner;
	private int zugNumber;
	private boolean me;
	private int punkte;
	private String name;
	private List<String> listeLazarett=new ArrayList<>();
	private List<String> datenListe=new ArrayList<>();
	public void setImageList(ImageView[] images) {
		this.imageList=images;
	}
	public static void setCards(List<String> cards, ClientModel m) {
		stapelCards=cards;
		model=m;
	}
	public void images() {
		Image muellerin=new Image(getClass().getResourceAsStream("/images/Muellerin.png"));
		Image brauer=new Image(getClass().getResourceAsStream("/images/Brauer.png"));
		Image hexe=new Image(getClass().getResourceAsStream("/images/Hexe.png"));
		Image wachen=new Image(getClass().getResourceAsStream("/images/Wachen.png"));
		Image soldat=new Image(getClass().getResourceAsStream("/images/Soldat.png"));
		Image wirt=new Image(getClass().getResourceAsStream("/images/Wirt.png"));
		Image adelige=new Image(getClass().getResourceAsStream("/images/Adlige.png"));
		Image[] images= new Image[] {muellerin, brauer, hexe, wachen, soldat, wirt, adelige};
		listImages=images;
	}
	public ArrayList<Image> getCharacters() {
		ArrayList<Image> cardImages= new ArrayList<>();
		for(String s: getCards()) {
			if(s.equals("muellerin")) {
				cardImages.add(listImages[0]);
			}else if(s.equals("brauer"))
				cardImages.add(listImages[1]);
			else if(s.equals("hexe"))
				cardImages.add(listImages[2]);
			else if(s.equals("wachen"))
				cardImages.add(listImages[3]);
			else if(s.equals("soldat"))
				cardImages.add(listImages[4]);
			else if(s.equals("wirt"))
				cardImages.add(listImages[5]);
			else
				cardImages.add(listImages[6]);
		}
		return cardImages;
	}
	public List<String> getCards(){
		return stapelCards;
	}
	public ImageView[] getImageList() {
		return this.imageList;
	}
	public void messageOut(String name, int pos, int points, int punkteGegner, List<String> listeLazarett, List<String> datenListe2) {
		Message msgOut=new Message();	
		msgOut.setMessage("ZugEnd");
		msgOut.setName(model.getName());
		msgOut.setCardPosition(pos);
		msgOut.setPunkte(points);
		msgOut.setGegnerPunkte(punkteGegner);
		msgOut.setListeLazarett(listeLazarett);
		msgOut.setDatenListe(datenListe2);
		msgOut.send(model.getSocket());
	}

	public HashMap<String, Integer> getDaten() {
		return daten;
	}
	public void setImageCards(ImageView[] cards) {

		this.imageCards=cards;
	}
	public ImageView[] getImageCards() {
		return this.imageCards;
	}

	public void listen(boolean b) {
		this.me=b;

		Runnable r = new Runnable() {
			@Override
			public void run() {
				while(me) {
					Message msgIn=(Message) Message.receive(model.getSocket());
					if(msgIn!=null&&msgIn.getMessage().equals("Zug")) {
						receiveDaten(msgIn);
						if(msgIn.getName().equals(model.getName())) {
							controller.begin();
							me=false;

						}else {
							System.out.println("other");
						}
					}else {
						System.out.println(" not message PlayModel messageIn");
					}
				}
			}
		};
		Thread t= new Thread(r);
		t.start();
	}
	public void receiveDaten(Message msg) {
		this.cardPosition=msg.getPosition();
		this.name=msg.getName();
		controller.setCardPosition(name, this.cardPosition, stapelCards);
		stapelCards=msg.getCards();
		if(this.name.equals(model.getName())) {
			if(msg.getListeLazarett()!=null) 
				this.listeLazarett=msg.getListeLazarett();
			this.datenListe=msg.getDatenListe();
			controller.datenListeAktualisieren(this.datenListe);
			controller.listeLazarettAktualisieren(this.listeLazarett);
			
		}
		daten=msg.getDaten();

		controller.kartenAktualisieren();
		controller.datenAktualisieren();
	}
	public void setController(GameController gameController) {
		this.controller= gameController;
	}
	public void msgIn() {
		Message msgIn=(Message) Message.receive(model.getSocket());
		if(msgIn!=null&&msgIn.getMessage().equals("Zug")) {
			receiveDaten(msgIn);
			this.me=true;

			wartenMsg(true);
		}else {
			System.out.println(" not message PlayModel messageIn");
		}		
	}
	private void wartenMsg(boolean b) {
		this.me=b;
		Runnable r = new Runnable() {

			@Override
			public void run() {
				while(me) {
					Message msgIn=(Message) Message.receive(model.getSocket());
					if(msgIn!=null&&msgIn.getMessage().equals("Zug")) {
						receiveDaten(msgIn);
						zugNumber++;
						if(msgIn.getName().equals(model.getName())) {
							if(zugNumber<12) {
								controller.begin();
								me=false;
							}else {
								Message msg= new Message();
								msg.setMessage("Ende");
								msg.send(model.getSocket());
								siegerErmitteln();
							}
						}else {
							System.out.println("other");
						}
					}else if(msgIn!=null&&msgIn.getMessage().equals("SpielEnde")) {
						siegerSetzen(msgIn);
					}
				}
			}
		};
		Thread t = new Thread(r);
		t.start();
	}
	protected void siegerErmitteln() {
		Message endMessage= (Message) Message.receive(model.getSocket());
		if(endMessage!=null&& endMessage.getMessage().equals("SpielEnde")) {
			siegerSetzen(endMessage);

		}
	}
	private void siegerSetzen(Message msg) {
		gewinner= msg.getGewinner();
		daten=msg.getDaten();
		controller.ende(gewinner);		
	}
}	


