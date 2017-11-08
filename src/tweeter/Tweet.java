package tweeter;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Tweet {
	private String text;
	private int numberOfTweets = 0;
	private ImageIcon imageForTweet;
	
	
	// Tweet Constructor
	public Tweet(String text, String pathOfImage, int numberOfTweets) {
		this.text = text;
		imageForTweet = formatImage(pathOfImage);
		setNumberOfTweets(numberOfTweets);
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
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getNumberOfTweets() {
		return numberOfTweets;
	}

	public void setNumberOfTweets(int numberOfTweets) {
		this.numberOfTweets = numberOfTweets;
	}

	public ImageIcon getImageForTweet() {
		return imageForTweet;
	}

	public void setImageForTweet(ImageIcon imageOfTweet) {
		this.imageForTweet = imageOfTweet;
	}
	
}
