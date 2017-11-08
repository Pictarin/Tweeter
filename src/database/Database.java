package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {

	public Database() {
		Connection c = null;
		Statement stmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:tweeter.db");
			System.out.println("Opened database succesfully");
			
			stmt = c.createStatement();
			String sql = "CREATE TABLE TWEETS " + 
							"(ID INT PRIMARY KEY    NOT NULL," +
							"TWEET_NUMBER    INT    NOT NULL," +
							"TWEET_HEADER    TEXT   NOT NULL," +
							"TWEET_TEXT      TEXT   NOT NULL," +
							"TWEET_IMAGE     TEXT   NOT NULL)";
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO TWEETS (ID, TWEET_NUMBER, TWEET_HEADER, TWEET_TEXT, TWEET_IMAGE) " + 
					"VALUES (1, 1, 'ABC', 'ABC', 'ABC');";
			stmt.executeUpdate(sql);
			
			stmt.close();
			c.close();			
		} catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created succesfully");
	}
}