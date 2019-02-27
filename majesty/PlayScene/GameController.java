package PlayScene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Client.ClientModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameController {
	protected List<String> lazarettCards= new ArrayList<String>();
	private SimpleStringProperty message=new SimpleStringProperty();

	private PlayModel playModel=new PlayModel();
	private static List<String> namePlayer=new ArrayList<String>();
	private static ClientModel model=new ClientModel();
	private int imagePosition;
	private String name= model.getName();
	private int zugNumber=0;
	private int punkte=0;
	private int punkteGegner=0;
	private boolean me=true;
	private List<String> listeLazarett=new ArrayList<>();
	List<Label> verwundbareKarten;
	List<Label> eigeneKarten;

	private List<String> datenListe=new ArrayList<>();
	private int pos;



	@FXML
	private BorderPane root;

	@FXML
	private Label anweisung;

	@FXML
	private ImageView stapelCard1;

	@FXML
	private ImageView stapelCard2;

	@FXML
	private ImageView stapelCard3;

	@FXML
	private ImageView stapelCard4;

	@FXML
	private ImageView stapelCard5;

	@FXML
	private ImageView stapelCard6;

	@FXML
	private ImageView stapel;

	@FXML
	private ImageView muehle;

	@FXML
	private ImageView brauerei;

	@FXML
	private ImageView hexenhaus;

	@FXML
	private ImageView wachturm;

	@FXML
	private ImageView kaserne;

	@FXML
	private ImageView taverne;

	@FXML
	private ImageView schloss;

	@FXML
	private ImageView lazarett;

	@FXML
	private ImageView cardMuellerin;

	@FXML
	private ImageView cardBrauer;

	@FXML
	private ImageView cardHexe;

	@FXML
	private ImageView cardWachen;

	@FXML
	private ImageView cardSoldat;

	@FXML
	private ImageView cardWirt;

	@FXML
	private ImageView cardAdlige;

	@FXML
	private ImageView cardLazarett;

	@FXML
	private Label numberMuellerin1;

	@FXML
	private Label numberBrauer1;

	@FXML
	private Label numberHexe1;

	@FXML
	private Label numberWachen1;

	@FXML
	private Label numberSoldat1;

	@FXML
	private Label numberWirt1;

	@FXML
	private Label numberAdlige1;

	@FXML
	private Label numberLazarett1;

	@FXML
	private Label spieler1;

	@FXML
	private Label punkte1;

	@FXML
	private Label numberMuellerin2;

	@FXML
	private Label numberBrauer2;

	@FXML
	private Label numberHexe2;

	@FXML
	private Label numberWachen2;

	@FXML
	private Label numberSoldat2;

	@FXML
	private Label numberWirt2;

	@FXML
	private Label numberAdlige2;

	@FXML
	private Label numberLazarett2;

	@FXML
	private Label spieler2;

	@FXML
	private Label punkte2;

	@FXML
	void clickStapel(MouseEvent event) {

		ImageView[] imageCards= {cardMuellerin, cardBrauer, cardHexe, cardWachen, cardSoldat, cardWirt, cardAdlige, cardLazarett};
		ImageView[] imageList= {stapelCard1, stapelCard2, stapelCard3, stapelCard4, stapelCard5, stapelCard6};
		playModel.setController(this);
		playModel.setImageCards(imageCards);
		playModel.setImageList(imageList);
		playModel.images();
		for(int i=0; i<imageList.length; i++) {
			Image image= playModel.getCharacters().get(i);	
			imageList[i].setImage(image);
		}
		this.spieler1.setText(namePlayer.get(0));
		this.spieler2.setText(namePlayer.get(1));

		if(this.name.equals(namePlayer.get(0))) {
			anweisung.setText("Willkommen	" + this.name+ "!"+"\nBitte Karte wählen!");	
		}else {
			anweisung.setText( "Willkommen	" + this.name+"!"+ "\n Bitte warten! ");
			for(ImageView iv: imageList) {
				iv.setDisable(true);
			}
			playModel.listen(this.me);

		}
		this.eigeneKarten= new ArrayList<>();
		this.eigeneKarten= new ArrayList<>();
		this.eigeneKarten.add(this.numberMuellerin1);
		this.eigeneKarten.add(this.numberBrauer1);
		this.eigeneKarten.add(this.numberHexe1);
		this.eigeneKarten.add(this.numberWachen1);
		this.eigeneKarten.add(this.numberSoldat1);
		this.eigeneKarten.add(this.numberWirt1);
		this.eigeneKarten.add(this.numberAdlige1);

		this.verwundbareKarten= new ArrayList<>();
		this.verwundbareKarten.add(this.numberMuellerin2);
		this.verwundbareKarten.add(this.numberBrauer2);
		this.verwundbareKarten.add(this.numberHexe2);
		this.verwundbareKarten.add(this.numberWachen2);
		this.verwundbareKarten.add(this.numberSoldat2);
		this.verwundbareKarten.add(this.numberWirt2);
		this.verwundbareKarten.add(this.numberAdlige2);

		stapel.setDisable(true);
	}
	@FXML
	void mouseEnter(MouseEvent event) {
		ImageView stapelCard = (ImageView) event.getSource();
		Image image=stapelCard.getImage();
		for(ImageView iv: playModel.getImageList()) {
			iv.setDisable(true);
		}
		for(int i=0; i<PlayModel.listImages.length; i++) {
			if(image.equals(PlayModel.listImages[i])) {
				playModel.getImageCards()[i].setImage(PlayModel.listImages[i]);
				switch(i) {
				case 0:	cardMuellerin(); break;
				case 1:	cardBauer();	break;
				case 2:	cardHexe();		break;
				case 3:	cardWachen();	break;
				case 4:	cardSoldat();	break;
				case 5:	cardWirt();		break;
				case 6:	cardAdlige();	break;
				}
			}
		}
		for(int j=0; j<playModel.getImageList().length; j++) {
			playModel.getImageList()[j].setDisable(true);
			if(stapelCard.equals(playModel.getImageList()[j])) {
				this.imagePosition=j;
			}
		}
		for(Label l: this.verwundbareKarten) {
			this.datenListe.add(l.getText());
		}
		playModel.messageOut(this.name, this.imagePosition, this.punkte, this.punkteGegner, this.listeLazarett, this.datenListe); 
		playModel.msgIn();
		this.anweisung.setText("Bitte warten!    " + model.getName());

	}
	private void cardMuellerin() {
		this.numberMuellerin1.setText(String.valueOf(Integer.parseInt(this.numberMuellerin1.getText())+1));
		//Integer.parseInt(this.numberMuellerin1.getText())+1));
		this.punkte+=2*(Integer.parseInt(numberMuellerin1.getText()));
	}
	private void cardBauer() {
		this.numberBrauer1.setText(String.valueOf(Integer.parseInt(this.numberBrauer1.getText())+1));
		this.punkte+=2*(Integer.valueOf(numberBrauer1.getText()));
		if(Integer.parseInt(numberMuellerin1.getText())!=0) {
			this.punkte+=2;
		}
		if(Integer.parseInt(numberMuellerin2.getText())!=0) {
			this.punkteGegner+=2;
		}
	}
	private void cardHexe() {
		int cardLazarett= Integer.parseInt(this.numberLazarett1.getText());
		if(cardLazarett!=0) {
			heilen();
		}
		this.numberHexe1.setText(String.valueOf(Integer.parseInt(this.numberHexe1.getText())+1));
		this.punkte+=2*(Integer.parseInt(numberHexe1.getText())+Integer.parseInt(numberBrauer1.getText())+Integer.parseInt(numberMuellerin1.getText()));
	}

	private void cardWachen() {
		this.numberWachen1.setText(String.valueOf(Integer.parseInt(this.numberWachen1.getText())+1));
		this.punkte+=2*(Integer.parseInt(numberWachen1.getText())+Integer.parseInt(numberSoldat1.getText())+Integer.parseInt(numberWirt1.getText()));
	}
	private void cardSoldat() {
		this.numberSoldat1.setText(String.valueOf(Integer.parseInt(this.numberSoldat1.getText())+1));
		this.punkte+=3*(Integer.parseInt(numberSoldat1.getText()));
		int soldate=Integer.parseInt(this.numberSoldat1.getText());
		int wachen=Integer.parseInt(this.numberWachen2.getText());
		if(wachen!=0&&soldate>wachen) {
			int i=0;
			for(int j=0; j<verwundbareKarten.size(); j++) {
				if(i==0&&Integer.parseInt(verwundbareKarten.get(j).getText())!=0) {
					this.pos=j;
					i++;
				}
			}
			switch(this.pos) {
			case 0:	this.listeLazarett.add("muellerin");
			this.numberMuellerin2.setText(String.valueOf(Integer.parseInt(this.numberMuellerin2.getText())-1));
			break;
			case 1:	this.listeLazarett.add("brauer");
			this.numberBrauer2.setText(String.valueOf(Integer.parseInt(this.numberBrauer2.getText())-1));
			break;
			case 2:this.listeLazarett.add("hexe");	
			this.numberHexe2.setText(String.valueOf(Integer.parseInt(this.numberHexe2.getText())-1));
			break;
			case 3:	this.listeLazarett.add("wachen");	
			this.numberWachen2.setText(String.valueOf(Integer.parseInt(this.numberWachen2.getText())-1));
			break;
			case 4:	this.listeLazarett.add("soldat"); 
			this.numberSoldat2.setText(String.valueOf(Integer.parseInt(this.numberSoldat2.getText())-1));
			break;
			case 5:	this.listeLazarett.add("wirt");		
			this.numberWirt2.setText(String.valueOf(Integer.parseInt(this.numberWirt2.getText())-1));
			break;
			case 6:	this.listeLazarett.add("adlige");
			this.numberAdlige2.setText(String.valueOf(Integer.parseInt(this.numberAdlige2.getText())-1));
			break;
			}
			this.numberLazarett2.setText(String.valueOf(Integer.parseInt(this.numberLazarett2.getText())+1));
		}
	}
	private void cardWirt() {
		this.numberWirt1.setText(String.valueOf(Integer.parseInt(this.numberWirt1.getText())+1));
		this.punkte+=4*(Integer.parseInt(numberWirt1.getText()));
		if(Integer.parseInt(numberBrauer1.getText())!=0) {
			this.punkte+=3;
		}
		if(Integer.parseInt(numberBrauer2.getText())!=0) {
			this.punkteGegner+=3;
		}
	}
	private void cardAdlige() {
		this.numberAdlige1.setText(String.valueOf(Integer.parseInt(this.numberAdlige1.getText())+1));
		this.punkte+=5*(Integer.parseInt(numberAdlige1.getText()));
	}

	public static void setPlayers(List<String> players, ClientModel clientModel) {
		model=clientModel;
		for(int i=0; i<players.size(); i++) {
			namePlayer.add(players.get(i));
		}
	}
	public List<String> getPlayers() {
		return namePlayer;
	}
	public void setText(String string) {
		this.anweisung.setText(string);
	}
	public void kartenAktualisieren() {
		for(int i=0; i<playModel.getImageList().length; i++) {
			Image image= playModel.getCharacters().get(i);	
			playModel.getImageList()[i].setImage(image);
		}
	}
	public void datenAktualisieren() {
		Platform.runLater(() -> {
			HashMap<String, Integer> daten=playModel.getDaten();
			for(Map.Entry<String, Integer> e: daten.entrySet()) {
				if(e.getKey().equals(spieler1.getText())) {
					punkte1.setText(String.valueOf(e.getValue()));
				}else if(e.getKey().equals(spieler2.getText())) {
					punkte2.setText(String.valueOf(e.getValue()));
				}
			}
		});
	}
	public void begin() {
		Platform.runLater(()->{
			for(ImageView iv: playModel.getImageList()) {
				iv.setDisable(false);
				anweisung.setText("Wählen Sie eine Karte!   "+ model.getName() );
			}
		});
	}
	public void setMe(boolean b) {

		this.me=b;
	}
	public boolean getMe() {
		return this.me;
	}

	public void ende(String gewinner) {
		Platform.runLater(()->{
			this.anweisung.setText("Gewinner:  " + gewinner);
			this.anweisung.setFont(new Font("Copperplate", 50));
			this.anweisung.setTextFill(Color.web("#e40163")); 
			System.out.println(gewinner);
		});
	}
	public void setCardPosition(String n, int cardPos, List<String> cards) {
		String card=cards.get(cardPos);
		if(this.name.equals(n)) {
			kartenSetzen(card);
		}
	}
	private void kartenSetzen(String card) {
		Platform.runLater(()->{
			switch(card) {
			case "muellerin":			
				this.numberMuellerin2.setText(String.valueOf(Integer.parseInt(this.numberMuellerin2.getText())+1));
				break;
			case "brauer":	
				this.numberBrauer2.setText(String.valueOf(Integer.parseInt(this.numberBrauer2.getText())+1));
				break;
			case "hexe":	
				this.numberHexe2.setText(String.valueOf(Integer.parseInt(this.numberHexe2.getText())+1));
				break;
			case "wachen":	
				this.numberWachen2.setText(String.valueOf(Integer.parseInt(this.numberWachen2.getText())+1));
				break;
			case "soldat":	
				this.numberSoldat2.setText(String.valueOf(Integer.parseInt(this.numberSoldat2.getText())+1));
				break;
			case "wirt":	
				this.numberWirt2.setText(String.valueOf(Integer.parseInt(this.numberWirt2.getText())+1));
				break;
			case "adlige":	
				this.numberAdlige2.setText(String.valueOf(Integer.parseInt(this.numberAdlige2.getText())+1));
				break;
			}
		});		
	}
	private void heilen() {
		String heilKarte=this.listeLazarett.get(this.listeLazarett.size()-1);
		Platform.runLater(()->{
			switch(heilKarte) {
			case "muellerin":			
				this.numberMuellerin1.setText(String.valueOf(Integer.parseInt(this.numberMuellerin1.getText())+1));
				break;
			case "brauer":	
				this.numberBrauer1.setText(String.valueOf(Integer.parseInt(this.numberBrauer1.getText())+1));
				break;
			case "hexe":	
				this.numberHexe1.setText(String.valueOf(Integer.parseInt(this.numberHexe1.getText())+1));
				break;
			case "wachen":	
				this.numberWachen1.setText(String.valueOf(Integer.parseInt(this.numberWachen1.getText())+1));
				break;
			case "soldat":	
				this.numberSoldat1.setText(String.valueOf(Integer.parseInt(this.numberSoldat1.getText())+1));
				break;
			case "wirt":	
				this.numberWirt1.setText(String.valueOf(Integer.parseInt(this.numberWirt1.getText())+1));
				break;
			case "adlige":	
				this.numberAdlige1.setText(String.valueOf(Integer.parseInt(this.numberAdlige1.getText())+1));
				break;
			}
		});
	}
	public void datenListeAktualisieren(List<String> liste) {
		Platform.runLater(()->{
			for(int i=0; i<this.eigeneKarten .size(); i++) {
				eigeneKarten.get(i).setText(liste.get(i));
			}
		});
	}
	public void listeLazarettAktualisieren(List<String> listeLazarett) {
		Platform.runLater(()->{
			this.listeLazarett=listeLazarett;
			this.numberLazarett1.setText(String.valueOf(this.listeLazarett.size()));

		});
	}

}
