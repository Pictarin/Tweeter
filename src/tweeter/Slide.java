package tweeter;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Slide implements ActionListener{
	
	private JPanel panel;
	private JButton btnL;
	private JButton btnR;
	private int i = 0;
	
	public JLabel redBullLabel;
	public JLabel familDayLabel;
	public TweetPanelRecording tweetPanel;
	public ArrayList <?> list = new ArrayList();
	
	public Slide() {
		createPanel();
	}
	
	private void createPanel() {	
		//panel for content on the Slide
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBackground(Color.WHITE);
		GridBagConstraints gc = new GridBagConstraints();	
		
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
		
//		for(int i = 0; i < tweetPanel.getList().size(); i++) {
//			System.out.println(tweetPanel.getList().size());
//		}
		
		//Icons for SlideBar
		ImageIcon redBullImage = formatImage("bilder/redbull.jpg");
		ImageIcon familDayImage = formatImage("bilder/familyDay.jpg");
		
		redBullLabel = new JLabel(redBullImage);
		familDayLabel = new JLabel(familDayImage);
		
		
		//Grid for content on the Panel
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		panel.add(btnL, gc);
		
		gc.gridx = 1;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.NORTH;
		gc.insets = new Insets(0, 5, 0, 5);
		panel.add(redBullLabel, gc);
		
		gc.gridx = 2;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.NORTH;
		gc.insets = new Insets(0, 5, 0, 5);
		panel.add(familDayLabel, gc);
		
		gc.gridx = 3;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = new Insets(0, 0, 0, 0);
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

	public ImageIcon formatImage(String path) {
		ImageIcon image = new ImageIcon(path);
		Image img = image.getImage();
		Image newimg = img.getScaledInstance(100, 50, java.awt.Image.SCALE_SMOOTH);
		image = new ImageIcon(newimg);
		return image;
	}

	
	//Geplant das sich mithilfe der Pfeil Buttons immer nur 7 Bilder anzeigen lassen mit
	//mit Klick auf rechts bzw links sollen sich die Bilder verschieben
	//sollte 0 oder max erreicht werden benachrichtigung an den User ->  bis dass das 
	//erste bzw letzte Bild erreicht wurde
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		if(clicked == btnL) {
			i = getI();
			if(i <= 0) {
				System.out.println("Erstes Bild!");
			}else {
				setI(--i);
			}
			System.out.println(i);
			System.out.println("-1 -> Schritt nach links!");
		}
		else if(clicked == btnR) {
			i = getI();
			if(i >= 10) {
				System.out.println("Letztes Bild!");
			}else {
				setI(++i);
			}
			System.out.println(i);
			System.out.println("+1 -> Schritt nach rechts!");
		}		
	}
	
	
}
