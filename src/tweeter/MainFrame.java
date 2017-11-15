package tweeter;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import database.Database;

public class MainFrame implements ActionListener {
	private static JFrame frame;
	private MainView mainView;
	
	public MainFrame() throws SQLException {
		
		// Frame wird gebaut
		frame = new JFrame("Tweeter V. 1.0.0");
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Menueleiste fuer Bearbeiten und Loeschen eines Tweets
		JMenuBar menuBar = new JMenuBar();
		
		JMenu data = new JMenu("Datei");		
		JMenuItem tweetEdit = new JMenuItem("Bearbeiten");
		tweetEdit.addActionListener(this);
		JMenuItem tweetDelete = new JMenuItem("Löschen");
		tweetDelete.addActionListener(this);
		data.add(tweetEdit);
		data.add(tweetDelete);		
		menuBar.add(data);
		
		System.out.println("Frame erstellt");
		
		mainView = new MainView();
		frame.add(menuBar, BorderLayout.NORTH);
		frame.add(mainView.getPanel(), BorderLayout.CENTER);
		reset();
	}
	
	public static JFrame getFrame() {
		return frame;
	}
	
	public static void reset() {
		frame.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem clicked = (JMenuItem)e.getSource();
		if(clicked.getText() == "Bearbeiten") {
			
			System.out.println("Bearbeiten!");
			
		}else if(clicked.getText() == "Löschen") {
			Database db = mainView.getDatabase();
			int index = mainView.getBtnCounter();
			System.out.println(++index);
			try {
				db.deleteTweet(index);
				System.out.println("Löschen!");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
