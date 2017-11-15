package tweeter;

import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainFrame {
	private static JFrame frame;
	
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
		JMenuItem tweetDelete = new JMenuItem("Löschen");
		data.add(tweetEdit);
		data.add(tweetDelete);		
		menuBar.add(data);
		
		System.out.println("Frame erstellt");
		
		MainView mainView = new MainView();
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
}
