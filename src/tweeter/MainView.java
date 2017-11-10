package tweeter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainView implements ActionListener {
	private JButton newBtn;
	private JPanel contentPanel;
	private JPanel slide;
	private GridBagConstraints gc = new GridBagConstraints();
	
	public JPanel mainPanel;
	public Tweet tweet;
	public TweetPanelRecording recordingPanel;
	
	public MainView() {
		buildPanel();
	}
	
	private void buildPanel() {
		// Create Panel		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		
		// Panel for Content/ ScrollPane and ImageLabel
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		contentPanel.setPreferredSize(new Dimension(750, 450));
		contentPanel.setBackground(Color.WHITE);
		
		// Components for Panel
		slide = new Slide().getPanel();
		
		newBtn = new JButton("neu");
		newBtn.addActionListener(this);
		
		//TextArea - text for each tweet
		JTextArea textArea = new JTextArea("Ich bin eine ScrollBar - Text Area");
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setVisible(true);
		textArea.setBorder(BorderFactory.createLineBorder(Color.WHITE));		
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(textArea.getBorder());
		
		//Image for the active Tweet		
		ImageIcon icon = new ImageIcon("bilder/redbull.jpg");
		Image image = icon.getImage();
		Image newimg = image.getScaledInstance(250, 180, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		JLabel imageLabel = new JLabel(icon);
		imageLabel.setPreferredSize(new Dimension(180, 250));
		
		//Grid for mainPanel
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.fill = GridBagConstraints.BOTH;
		mainPanel.add(slide, gc);
		
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

	private void setPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
	
	public JPanel getPanel() {
		return mainPanel;
	}
	
	public void setVisible(boolean state) {
		newBtn.setVisible(state);
		contentPanel.setVisible(state);
		slide.setVisible(state);
		MainFrame.reset();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		if(clicked == newBtn) {
			recordingPanel = new TweetPanelRecording(callback);
			gc.weightx = 1;
			gc.weighty = 1;
			gc.gridx = 0;
			gc.gridy = 0;
			gc.fill = GridBagConstraints.BOTH;
			gc.anchor = GridBagConstraints.CENTER;
			mainPanel.add(recordingPanel.getPanel(), gc);
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
