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
	private static int tweetsInDatabase = 0;	
	private static GridBagConstraints gc = new GridBagConstraints();
	private ArrayList<String> imagesFromTweets = new ArrayList<String>();
	
	public static JLabel imageLabel;
	public TweetPanelRecording tweetPanel;
	private static Database db;
	private Object mainView;
	
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
		imagesFromTweets = db.getTweetImage();
		for(i = 0; i < imagesFromTweets.size(); i++) {
			String path = imagesFromTweets.get(i);
			ImageIcon image = new ImageIcon(path);
			image = formatImage(path);
			//Bestehende Elemente aus der DB auf dem Grid anordnen bei Start der Application
			imageLabel = new JLabel(image);
			int gridPosition = i;
			gc.gridx = ++gridPosition;
			gc.gridy = 0;
			gc.anchor = GridBagConstraints.NORTH;
			gc.insets = new Insets(0, 5, 0, 5);
			panel.add(imageLabel, gc);
		}
		i = 0;
		//Grid for content on the Panel
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = new Insets(0, 0, 0, 5);
		panel.add(btnL, gc);

		int gridPosition = tweetsInDatabase;
		gc.gridx = ++gridPosition;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = new Insets(0, 5, 0, 0);
		panel.add(btnR, gc);
	}
	
	public static void addInToSlide(String path) throws SQLException {
		tweetsInDatabase = db.readDB();
		int gridPosition = tweetsInDatabase;
		String imagePath = path;
		ImageIcon image = formatImage(imagePath);
		
		panel.remove(btnR);
		
		imageLabel = new JLabel(image);
		gc.gridx = gridPosition;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.NORTH;
		gc.insets = new Insets(0, 5, 0, 5);
		panel.add(imageLabel, gc);
		
		gc.gridx = ++gridPosition;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = new Insets(0, 5, 0, 0);
		panel.add(btnR, gc);
	}
	
	private void setI(int i) {
		this.i = i;
	}
	
	public int getI() {
		return i;
	}
	
	public JPanel getPanel() {
		return panel;
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
		String imagePath = imagesFromTweets.get(index);
		return imagePath;
		
	}
	
	//Geplant das sich mithilfe der Pfeil Buttons immer nur 7 Bilder anzeigen lassen mit
	//mit Klick auf rechts bzw links sollen sich die Bilder verschieben
	//sollte 0 oder max erreicht werden benachrichtigung an den User ->  bis dass das 
	//erste bzw letzte Bild erreicht wurde
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
				System.out.println("Erstes Bild!");
			}else {
				setI(--i);
				try {
					((MainView) mainView).showInView();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			System.out.println(i);
			System.out.println("<-");
		}
		
		else if(clicked == btnR) {
			i = getI();
			if(i  == --check) {
				System.out.println("Letztes Bild!");
			}else {
				setI(++i);
				try {
					((MainView) mainView).showInView();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			System.out.println(i);
			System.out.println("->");
		}		
	}
	
	
}
