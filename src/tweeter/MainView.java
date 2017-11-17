package tweeter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import database.Database;

public class MainView implements ActionListener {
	private JButton newBtn;
	private JPanel contentPanel;
	private Slide slide;
	private GridBagConstraints gc = new GridBagConstraints();
	private Database db;
	private int btnCounter;
	private JLabel imageLabel = new JLabel();
	private ImageIcon icon = new ImageIcon("null");
	private JTextArea textArea = new JTextArea("null");
	
	
	public JPanel mainPanel;
	public Tweet tweet;
	public TweetPanelRecording recordingPanel;
	
	public MainView() throws SQLException {
		buildPanel();
	}
	
	private void buildPanel() throws SQLException {
		recordingPanel = new TweetPanelRecording(callback, this);
		db = recordingPanel.getDatabase();
		
		// Create Panel		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		
		// Panel for Content/ ScrollPane and ImageLabel
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		contentPanel.setPreferredSize(new Dimension(750, 450));
		contentPanel.setBackground(Color.WHITE);
		
		// Components for Panel
		slide = new Slide(db, this);
		
		newBtn = new JButton("neu");
		newBtn.addActionListener(this);
		
		//TextArea - text for each tweet
		String header = db.getTweetHeader();
		if(header == null) {
			header = "Noch keine Einträge vorhanden";
		}
		String text = db.getTweetText();
		if(text == null) {
			text = "\n\nNoch keine Einträge vorhanden";
		}
		textArea.setText(header);
		textArea.append("\n\n");
		textArea.append(text);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setVisible(true);
		textArea.setBorder(BorderFactory.createLineBorder(Color.WHITE));		
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(textArea.getBorder());
		
		//Image for the active Tweet
		if(db.getPrimaryKey() != 0) {
			showInView();
		}
		
		//Grid for mainPanel
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.fill = GridBagConstraints.BOTH;
		mainPanel.add(slide.getPanel(), gc);
		
		//Grid for contentPanel
		gc.weightx = 3;
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 12, 0, 0);
		contentPanel.add(scrollPane, gc);
		
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridx = 1;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		contentPanel.add(imageLabel, gc);
		
		gc.weightx = 1;
		gc.weighty = 3;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.CENTER;
		gc.fill = GridBagConstraints.BOTH;
		mainPanel.add(contentPanel, gc);
		
		gc.weightx = 0;
		gc.weighty = 0;
		gc.gridx = 0;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.EAST;
		gc.fill = GridBagConstraints.NONE;
		mainPanel.add(newBtn, gc);
		
		mainPanel.setBackground(Color.WHITE);
		setPanel(mainPanel);
	}
	
	public void showInView() throws SQLException {
		btnCounter = slide.getI();
		String imgPath = slide.getImage(btnCounter);
		icon = new ImageIcon(imgPath);
		icon = formatImage();
		imageLabel.setIcon(icon);
		imageLabel.setPreferredSize(new Dimension(180, 250));
		
		String textTweetComplete = slide.getTweetText(btnCounter);
		textArea.setText(textTweetComplete);
	}
	
	public void showInView(boolean state) throws SQLException {
		btnCounter = slide.getI();
		--btnCounter;
		slide.setI(btnCounter);
		String imgPath = slide.getImage(btnCounter);
		icon = new ImageIcon(imgPath);
		icon = formatImage();
		imageLabel.setIcon(icon);
		imageLabel.setPreferredSize(new Dimension(180, 250));
		
		String textTweetComplete = slide.getTweetText(btnCounter);
		textArea.setText(textTweetComplete);
	}
	
	public void removeFromSlide(int index) throws SQLException {
		slide.removeFromSlide(index);
		showInView(true);
	}
	
	
	//Formatiere das Bild auf 250 x 180
	public ImageIcon formatImage() {
		Image image = icon.getImage();
		Image newimg = image.getScaledInstance(250, 180, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		return icon;
	}
	
	//set this.Panel
	private void setPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
	
	//get this.Panel
	public JPanel getPanel() {
		return mainPanel;
	}
	
	public int getBtnCounter() {
		return btnCounter;
	}
	
	public Database getDatabase() {
		return db;
	}
	
	//sets the visible for the components on the JPanel
	public void setVisible(boolean state) {
		newBtn.setVisible(state);
		contentPanel.setVisible(state);
		slide.getPanel().setVisible(state);
		MainFrame.reset();
	}
	
	//ActionListener
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		if(clicked == newBtn) {
			gc.weightx = 1;
			gc.weighty = 1;
			gc.gridx = 0;
			gc.gridy = 0;
			gc.fill = GridBagConstraints.BOTH;
			gc.anchor = GridBagConstraints.CENTER;
			mainPanel.add(recordingPanel.getPanel(), gc);
			recordingPanel.setVisible(true);
			setVisible(false);
		}
		
	}
	
	Runnable callback = new Runnable(){
	    @Override
	    public void run()
	    {
	    	setVisible(true);
	    	
	    }
	};
}
