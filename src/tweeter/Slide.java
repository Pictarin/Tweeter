package tweeter;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.Database;

public class Slide implements ActionListener{
	
	private static JPanel panel;
	private JButton btnL;
	private static JButton btnR;
	private int i = 1;
	private int counter = 0;
	private static int tweetsInDatabase = 0;	
	private static GridBagConstraints gc = new GridBagConstraints();
	private ArrayList<String> imagesFromTweets = new ArrayList<>();
	private ArrayList<String> textFromTweet = new ArrayList<>();
	private static ArrayList <String> imagePathArray = new ArrayList<>();
	private static ArrayList <JLabel> imageLabels = new ArrayList<>();
	
	public static JLabel imageLabel;
	public TweetPanelRecording tweetPanel;
	private static Database db;
	private Object mainView;
	private int position = 1;
	
	public Slide(Database database, Object mainView) throws SQLException {
		this.mainView = mainView;
		db = database;
		createPanel();
	}
	
	private void createPanel() throws SQLException {
		
		tweetsInDatabase = db.readDB();
		setI(tweetsInDatabase);
		
		//panel for content on the Slide
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBackground(Color.WHITE);
		//panel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
		
		//Buttons with Image as arrow
		
		ImageIcon leftIcon = new ImageIcon("bilder/menuImage/linkerPfeil.jpg");
		Image leftImg = leftIcon.getImage();
		Image newLeftImg = leftImg.getScaledInstance(35, 25, java.awt.Image.SCALE_SMOOTH);
		leftIcon = new ImageIcon(newLeftImg);
		btnL = new JButton(leftIcon);
		btnL.addActionListener(this);
		btnL.setBackground(Color.WHITE);
		btnL.setBorder(BorderFactory.createEmptyBorder());
		
		ImageIcon rightIcon = new ImageIcon("bilder/menuImage/rechterPfeil.jpg");
		Image rightImg = rightIcon.getImage();
		Image newRightImg = rightImg.getScaledInstance(35, 25, java.awt.Image.SCALE_SMOOTH);
		rightIcon = new ImageIcon(newRightImg);
		btnR = new JButton(rightIcon);
		btnR.addActionListener(this);
		btnR.setBackground(Color.WHITE);
		btnR.setBorder(BorderFactory.createEmptyBorder());
		
		//Bei Start soll geschaut werden welche Tweets bereits vorhanden sind
		//Bilder werden dann angezeigt
		//Icons for SlideBar
		
		createSlideIcons();
		
		setI(0);	
		
		//Grid for content on the Panel
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = new Insets(0, 0, 0, 5);
		panel.add(btnL, gc);

		gc.gridx = 6;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = new Insets(0, 5, 0, 0);
		panel.add(btnR, gc);
	}
	
	public void resetSlide() {
		panel.revalidate();
	}
	
	public void createSlideIcons() throws SQLException {
		imagesFromTweets = db.getTweetImage();
		int gridPosition = 3;
		for(i = 0; i < imagesFromTweets.size(); i++) {
			String path = imagesFromTweets.get(i);
			ImageIcon image = new ImageIcon(path);
			image = formatImage(path);
			//Bestehende Elemente aus der DB auf dem Grid anordnen bei Start der Application
			if(counter <= 5) {
				imageLabel = new JLabel(image);
				imageLabel.setVisible(true);
				if(i == 0) {
					gc.gridx = gridPosition;
					gc.gridy = 0;
					gc.anchor = GridBagConstraints.NORTH;
					gc.insets = new Insets(0, 5, 0, 5);
					panel.add(imageLabel, gc);
					imageLabels.add(imageLabel);
					counter++;
				}else if(i == 1 || i == 2){
					gc.gridx = ++gridPosition;
					gc.gridy = 0;
					gc.anchor = GridBagConstraints.NORTH;
					gc.insets = new Insets(0, 5, 0, 5);
					panel.add(imageLabel, gc);
					imageLabels.add(imageLabel);
					counter++;	
				}else if(i == 3) {
					gc.gridx = 1;
					gc.gridy = 0;
					gc.anchor = GridBagConstraints.NORTH;
					gc.insets = new Insets(0, 5, 0, 5);
					panel.add(imageLabel, gc);
					imageLabels.add(imageLabel);
					counter++;	
				}else if(i == 4) {
					gc.gridx = 2;
					gc.gridy = 0;
					gc.anchor = GridBagConstraints.NORTH;
					gc.insets = new Insets(0, 5, 0, 5);
					panel.add(imageLabel, gc);
					imageLabels.add(imageLabel);
					counter++;	
					
				}
			} else {
				imageLabel = new JLabel(image);			//in ein Array garnicht erst anordnen erst bei Index abfrage
				imageLabels.add(imageLabel);			//im Array auf grid setzen
				counter++;								//Array beginnt bei 0 grid bei 1 und imageID bei 1
			}
		}
	}
	
private void visibleInSlide() throws SQLException {	
		//gridx = 0 besetzt! btnLinks
		//gridx = 6 besetzt! btnRechts
		//gridx = 1 - 5 frei!
	
	}
	
	public static void addInToSlide(String path) throws SQLException {
		tweetsInDatabase = db.readDB();
		int gridPosition = tweetsInDatabase;
		String imagePath = path;
		ImageIcon image = formatImage(imagePath);
		
		System.out.println("Größe = " + imagePathArray.size());		
		imagePathArray = db.getTweetImage();
		
		System.out.println("Größe = " + imagePathArray.size());		
		if(imagePathArray.size() == 1) {
			imageLabel = new JLabel(image);
			gc.gridx = 3;
			gc.gridy = 0;
			gc.anchor = GridBagConstraints.NORTH;
			gc.insets = new Insets(0, 5, 0, 5);
			panel.add(imageLabel, gc);
			imageLabels.add(imageLabel);
		}else if(imagePathArray.size() == 2) {
			imageLabel = new JLabel(image);
			gc.gridx = 4;
			gc.gridy = 0;
			gc.anchor = GridBagConstraints.NORTH;
			gc.insets = new Insets(0, 5, 0, 5);
			panel.add(imageLabel, gc);
			imageLabels.add(imageLabel);
			
		}else if(imagePathArray.size() == 3) {
			imageLabel = new JLabel(image);
			gc.gridx = 5;
			gc.gridy = 0;
			gc.anchor = GridBagConstraints.NORTH;
			gc.insets = new Insets(0, 5, 0, 5);
			panel.add(imageLabel, gc);
			imageLabels.add(imageLabel);
			
		}else if(imagePathArray.size() == 4) {
			imageLabel = new JLabel(image);
			gc.gridx = 1;
			gc.gridy = 0;
			gc.anchor = GridBagConstraints.NORTH;
			gc.insets = new Insets(0, 5, 0, 5);
			panel.add(imageLabel, gc);
			imageLabels.add(imageLabel);
			
		}else if(imagePathArray.size() == 5) {
			imageLabel = new JLabel(image);
			gc.gridx = 2;
			gc.gridy = 0;
			gc.anchor = GridBagConstraints.NORTH;
			gc.insets = new Insets(0, 5, 0, 5);
			panel.add(imageLabel, gc);
			imageLabels.add(imageLabel);
			
		}else {		
			imageLabel = new JLabel(image);
			imageLabel.setVisible(false);
			imageLabels.add(imageLabel);
		}
	}
	
	public void removeFromSlide(int index) {	
		imageLabel = imageLabels.get(index);
		panel.remove(imageLabel);

		panel.revalidate();
	}
	
	public static ImageIcon formatImage(String path) {
		ImageIcon image = new ImageIcon(path);
		Image img = image.getImage();
		Image newimg = img.getScaledInstance(100, 50, java.awt.Image.SCALE_SMOOTH);
		image = new ImageIcon(newimg);
		return image;
	}
	
	//Get the image from ArrayList(index)
	public String getImage(int index) throws SQLException {
		imagesFromTweets = db.getTweetImage();
		System.out.println("INDEX : " + index);
		String imagePath = imagesFromTweets.get(index);
		
		return imagePath;
	}
	
	public String getTweetText(int index) throws SQLException {
		textFromTweet = db.getTweetComplete();
		String tweetComplete = textFromTweet.get(index);
		
		return tweetComplete;	
	}
	
	
	public void setI(int i) {
		this.i = i;
	}
	
	public int getI() {
		return i;
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	
	//Wenn i = 0 dann 0 Elemente / erster Eintrag
	//dann muss erster Eintrag auf gridx = 3;
	//i ist IMMER aktueller Eintrag	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		//check for the counter
		int check = 0;
		try {
			check = db.readDB();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if(clicked == btnL) {
			i = getI();
			if(i == 0) {
				setI(--check);
				try {
					((MainView) mainView).showInView();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}else {
				setI(--i);
				try {
					((MainView) mainView).showInView();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			System.out.println("I = " + i + " <-");
		}
		
		else if(clicked == btnR) {
			i = getI();
			if(i  == --check) {
				setI(0);
				try {
					((MainView) mainView).showInView();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}else {
				setI(++i);
				try {
					((MainView) mainView).showInView();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			System.out.println("I = " + i + " ->");
		}		
	}
	
	
}
