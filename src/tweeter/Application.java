package tweeter;

import java.awt.Component;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * Start Application for Tweeter
 * 
 * Date: 13.11.2017
 * @author Kevin.Adam
 *
 */

public class Application {
	private static MainFrame frame;
	private static Component component;
	
	public static void main(String[] args) throws IOException {
		final File f = new File(System.getProperty("java.io.tmpdir") + "/FileLock.lock");
		if(f.exists()) {
			JOptionPane.showMessageDialog(component,
			    "Anwendung läuft bereits");
			System.exit(0);
		}
		f.createNewFile();
		frame = new MainFrame();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				f.delete();
			}
		}));
	}
}
