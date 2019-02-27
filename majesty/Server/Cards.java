package Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cards {

	private ImageView[] imageList;
	protected Image[] listImages;
	private List<String> stapelKarten;


	public List<String> cards() {
		String[] list= { "muellerin", "brauer", "hexe", "wachen", "soldat", "wirt", "adlige"};
		List<String> karten= new ArrayList<String>();

		for(int i=0; i<5; i++) { //StapelKarten 35
			karten.addAll(Arrays.asList(list));	
		}
		Collections.shuffle(karten);
		 stapelKarten=karten;
		 
		 return stapelKarten;

	}

	public ImageView[] getImageList() {
		return this.imageList;
	}


}
