package tweeter;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TweetPanelRecording implements ActionListener {
	private JPanel panel;
	private JTextArea header;
	private JTextArea description;
	private JButton uploadImage;
	private ImageIcon icon = new ImageIcon();
	private JLabel iconLabel;
	private JButton save;
	private MainView mv;
	
	public Tweet tweet;
	public ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
	
	
	public TweetPanelRecording() {
		buildPanel();
	}
	
	private void buildPanel() {
		//create Panel
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		panel.setBackground(Color.WHITE);
		panel.setVisible(true);
		
		//create Content for Panel
		header = new JTextArea("Überschrift eingeben");
		//header.setPreferredSize(new Dimension(800, 50));
		header.setBorder(BorderFactory.createEtchedBorder());
		description = new JTextArea("BESCHREIBUNG DES ARTIKELS");
		
		description.setBorder(BorderFactory.createEtchedBorder());
		description.setLineWrap(true);
		description.setVisible(true);
		
		
		JScrollPane scrollPane = new JScrollPane(description);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		//load the Image
		ImageIcon icon = new ImageIcon("bilder/testbild.jpg");
		Image image = icon.getImage();
		Image newimg = image.getScaledInstance(250, 180, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		
		iconLabel = new JLabel(icon);
		uploadImage = new JButton("UPLOAD");
		uploadImage.setPreferredSize(new Dimension(120, 20));
		
		//Save the recording
		save = new JButton("Speichern");
		save.addActionListener(this);
		
		//Grid for Content on Panel
		
		gc.weightx = 1;
		gc.weighty = 0;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.fill = GridBagConstraints.LINE_END;
		panel.add(header, gc);
		
		gc.weightx = 1;
		gc.weighty = 0;
		gc.gridx = 2;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.CENTER;
		gc.ipadx = 0;
		gc.fill = GridBagConstraints.NONE;
		panel.add(iconLabel, gc);
		
		gc.weightx = 0;
		gc.weighty = 1;
		gc.gridx = 2;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.CENTER;
		gc.ipady = 0;
		gc.insets = new Insets(250, 0, 0, 0);
		panel.add(uploadImage, gc);
		
		gc.weightx = 3;
		gc.weighty = 3;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(20, 0, 0, 0);
		panel.add(scrollPane, gc);
		
		gc.weightx = 0;
		gc.weighty = 0;
		gc.gridx = 2;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.EAST;
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(0, 0, 0, 0);
		panel.add(save, gc);
	}
	
	
	public JPanel getPanel() {
		return panel;
	}

	public ArrayList<Tweet> getList() {
		return tweetList;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		mv = new MainView();
		if(clicked == save) {
			int i = tweetList.size();
			tweetList.add(new Tweet(description.getText(), icon.toString(), ++i));
			panel.setVisible(false);
			mv.setVisible(true);
		}	
	}
}
