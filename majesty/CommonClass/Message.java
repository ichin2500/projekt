package CommonClass;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class Message implements Serializable{
	private static final long serialVersionUID = 8540558636874947755L;
	private String name;
	private String message;
	private List<String> players;
	private String gegnerName;
	private int punkte=0;
	private int numberWachen=0;
	private int numberSoldat=0;
	private int imagePosition;
	private String stapelCardId;
	private List<String> stapelKarten= new ArrayList<String>();
	private HashMap<String, Integer> daten;
	private String gewinner;
	private int gegnerPunkte;
	private List<String> listeLazarett;
	private List<String> datenListe;

	public static Message receive(Socket socket) {
		Message msg = null;
		if(socket!=null) {
			ObjectInputStream in = null;
			try {
				in = new ObjectInputStream(socket.getInputStream());
				msg = (Message) in.readObject();

			} catch (IOException e) {
				System.out.println("catch1");
				//e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("catch2");

				//e.printStackTrace();
			}
		}
		return msg;
	}
	public void send(Socket socket) {
		if(socket!=null) {
			try {
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				out.writeObject(this);
				out.flush();
			}catch(IOException e) {
				System.out.println("Exception");
				//e.toString();
			}
		}
	}
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name;
	}
	public void setMessage(String message) {
		this.message=message;
	}
	public String getMessage() {
		return this.message;
	}
	public void setGegner(String gegner) {
		this.gegnerName=gegner;
	}
	public String getGegner() {
		return this.gegnerName;
	}
	public void setPlayers(List<String> players) {
		this.players=players;		
	}
	public List<String> getPlayers(){
		return this.players;
	}
	public String toString() {
		return this.message;
	}
	public void setPunkte(int punkte) {
		this.punkte=punkte;
	}
	public int getPunkte() {
		return this.punkte;
	}
	public void setNumberWachen(int zahlWachen) {
		this.numberWachen= zahlWachen;
	}
	public int getNumberWachen() {
		return this.numberWachen;
	}
	public void setNumberSoldat(int zahlSoldat) {
		this.numberSoldat=zahlSoldat;
	}
	public int getNumberSoldat() {
		return this.numberSoldat;
	}
	public int getPosition() {
		return this.imagePosition;
	}
	public void setId(String id) {

		this.stapelCardId=id;
	}
	public String getId() {
		return this.stapelCardId;
	}
	public List<String> getCards(){
		return this.stapelKarten;
	}
	public void setCards(List<String> cards) {
		this.stapelKarten=cards;
	}
	public void setDaten(HashMap<String, Integer> data){
		this.daten= data;
	}
	public HashMap<String, Integer> getDaten() {
		return this.daten;
	}
	public void setCardPosition(int pos) {

		this.imagePosition=pos;
	}
	public void setGewinner(String g) {

		this.gewinner=g;
	}
	public String getGewinner() {
		return this.gewinner;
	}
	public void setGegnerPunkte(int punkteGegner) {

		this.gegnerPunkte=punkteGegner;
	}
	public int getGegnerPunkte () {
		return this.gegnerPunkte;
	}
	public void setListeLazarett(List<String> liste) {
		this.listeLazarett=liste;
		
	}
	public List<String> getListeLazarett(){
	return this.listeLazarett;
	}
	public void setDatenListe(List<String> datenListe) {

		this.datenListe=datenListe;
	}
	public List<String> getDatenListe(){
		return this.datenListe;
	}
}
