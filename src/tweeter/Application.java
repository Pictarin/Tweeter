package tweeter;

import database.Database;

public class Application {
	private static MainFrame frame;
	private static Database db;
	
	public static void main(String[] args) {
		//db = new Database();
		frame = new MainFrame();
	}
}
