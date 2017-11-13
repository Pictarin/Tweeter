package tweeter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import database.Database;

public class TweetPanelRecording implements ActionListener, FocusListener {
	private JPanel panel;
	private JLabel recordingLabel;
	private String headerText = "Überschrift eingeben";
	private JTextField header = new JTextField(headerText);
	private JTextArea description;
	private JButton uploadImage;
	private ImageIcon icon;
	private JLabel iconLabel;
	private JButton save;
	private Runnable callback;
	private Database db = new Database();
	private JScrollPane scrollPane;
	private JFileChooser fileChooser;
	
	public Tweet tweet;	
	
	public TweetPanelRecording(Runnable finishedCallback){
		callback = finishedCallback;
		buildPanel();
		
		// Christian fragen wie man das anders lösen kann
		Runtime.getRuntime().addShutdownHook(new Thread(){
		    @Override
		    public void run()
		    {
		        try {
					db.closeDB();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		    }
		});
		
	}
	
	private void buildPanel() {
		//create Panel
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		panel.setBackground(Color.WHITE);
		panel.setVisible(true);
		
		//create Content for Panel
		
		recordingLabel = new JLabel("   Artikelerfassung   ");
		recordingLabel.setFont(new Font("Serif", Font.BOLD, 20));
		recordingLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.ORANGE));
		recordingLabel.setPreferredSize(new Dimension(800, 100));
		
		header = new JTextField(headerText);
		header.addFocusListener(this);
		header.setBorder(BorderFactory.createEtchedBorder());
		description = new JTextArea("BESCHREIBUNG DES ARTIKELS");
		
		description.setBorder(BorderFactory.createEtchedBorder());
		description.setLineWrap(true);
		description.setVisible(true);
		description.addFocusListener(this);
		
		scrollPane = new JScrollPane(description);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		//load the Image
		iconLabel = new JLabel(icon);
		iconLabel.setMinimumSize(new Dimension(250, 180));
		iconLabel.setMaximumSize(iconLabel.getMinimumSize());
		iconLabel.setBorder(BorderFactory.createEtchedBorder());
		uploadImage = new JButton("UPLOAD");
		uploadImage.setPreferredSize(new Dimension(120, 20));
		uploadImage.addActionListener(this);
		
		//Save the recording
		save = new JButton("Speichern");
		save.addActionListener(this);
		
		//Grid for Content on Panel
		
		gc.weightx = 4;
		gc.weighty = 0;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets = new Insets(0, 0, 10, 0);
		gc.anchor = GridBagConstraints.CENTER;
		panel.add(recordingLabel, gc);
		
		gc.weightx = 0;
		gc.weighty = 0;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(header, gc);
		
		gc.weightx = 1;
		gc.weighty = 0;
		gc.gridx = 2;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.CENTER;
		gc.ipadx = 0;
		gc.fill = GridBagConstraints.NONE;
		panel.add(iconLabel, gc);
		
		gc.weightx = 0;
		gc.weighty = 1;
		gc.gridx = 2;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.CENTER;
		gc.ipady = 0;
		gc.insets = new Insets(250, 0, 0, 0);
		panel.add(uploadImage, gc);
		
		gc.weightx = 3;
		gc.weighty = 3;
		gc.gridx = 0;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(20, 0, 0, 0);
		panel.add(scrollPane, gc);
		
		gc.weightx = 0;
		gc.weighty = 0;
		gc.gridx = 2;
		gc.gridy = 3;
		gc.anchor = GridBagConstraints.EAST;
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(0, 0, 0, 0);
		panel.add(save, gc);
		setVisible(false);
	}
	
	public void setVisible(boolean state) {
		panel.setVisible(state);
		MainFrame.reset();
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	private ImageIcon setIcon(String path) {
		icon = new ImageIcon(path);
		Image image = icon.getImage();
		Image newimg = image.getScaledInstance(250, 180, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		icon.setDescription(path);
		
		return icon;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		if(clicked == save) {
			try {
				new Tweet(db, db.getPrimaryKey() + 1, header.getText(), description.getText(), icon.getDescription());
			} catch (SQLException ex) {
				System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
				System.exit(0);
			}
			callback.run();
			setVisible(false);
			
		}else if(clicked == uploadImage) {
			fileChooser = new JFileChooser();
			FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
			fileChooser.addChoosableFileFilter(fileFilter);
			int result = fileChooser.showSaveDialog(null);
			if(result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				String path = selectedFile.getAbsolutePath();
				iconLabel.setIcon(setIcon(path));
			}
		}
	}
	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource() == header) {
			header.selectAll();
		}else if(e.getSource() == description) {
			description.selectAll();
		}
    }
	
	@Override
	public void focusLost(FocusEvent e) {
		System.out.println("Focus lost");
	}
}
