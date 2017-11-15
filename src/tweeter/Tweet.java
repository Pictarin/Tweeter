package tweeter;

import java.awt.Image;
import java.sql.SQLException;

import javax.swing.ImageIcon;

import database.Database;

public class Tweet {
	private String description;
	private ImageIcon imageForTweet;
	
	// Tweet Constructor
	public Tweet(Database db, int numberOfTweets, String header, String description, String pathOfImage) throws SQLException {
		this.description = description;
		imageForTweet = formatImage(pathOfImage);
		System.out.println("ID = " + numberOfTweets);
		db.addIntoTweets(numberOfTweets, header, description, pathOfImage);
		db.readDB();
	}
	
	// Image Formatter
	private ImageIcon formatImage(String path) {
		ImageIcon image = new ImageIcon(path);
		Image img = image.getImage();
		Image newimg = img.getScaledInstance(100, 50, java.awt.Image.SCALE_SMOOTH);
		image = new ImageIcon(newimg);
		return image;
	}
	
	//Getter and Setter
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ImageIcon getImageForTweet() {
		return imageForTweet;
	}

	public void setImageForTweet(ImageIcon imageOfTweet) {
		this.imageForTweet = imageOfTweet;
	}
	
}
