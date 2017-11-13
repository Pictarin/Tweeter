package tweeter;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainFrame {
	private static JFrame frame;
	
	public MainFrame() {
		
		// Frame wird gebaut
		frame = new JFrame("Tweeter V. 1.0.0");
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		System.out.println("Frame erstellt");
		
		MainView mainView = new MainView();
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
