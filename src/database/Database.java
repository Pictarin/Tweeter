package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
	
	private Connection c;
	private Statement stmt;
	private String sql;
	private ResultSet rs;
	private PreparedStatement preparedStatement;
	
	public Database() {
		c = null;
		stmt = null;
		preparedStatement = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:tweeter.db");
			c.setAutoCommit(false);
			System.out.println("Opened database succesfully");

			stmt = c.createStatement();
				sql = "CREATE TABLE IF NOT EXISTS " + "TWEETS" + 
						"(ID INT PRIMARY KEY    NOT NULL," +
						"TWEET_NUMBER    INT    NOT NULL," +
						"TWEET_HEADER    TEXT   NOT NULL," +
						"TWEET_TEXT      TEXT   NOT NULL," +
						"TWEET_IMAGE     TEXT   NOT NULL)";
			stmt.executeUpdate(sql);
			
			
			System.out.println("Table created succesfully");			
			
			rs = stmt.executeQuery("SELECT * FROM TWEETS ORDER BY ID;");
			
			//UPDATE METHODE - in dem tablemodel tweets wird die TWEET_NUMBER auf 3 gesetzt wo sich die ID 1 befindet
			//String sql = "UPDATE TWEETS set TWEET_NUMBER = 3 where ID=1;";
			//stmt.executeUpdate(sql);
			
			//DELETE METHODE - hier wird der ganze Eintrag aus der DB gelöscht wo sich die ID 2 befindet
			//String sql = "DELETE from TWEETS where ID = 2;";
			//stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		System.out.println("Operation done succesfully");
	}
	
	public void deleteTweet(int index) throws SQLException {
		preparedStatement = c.prepareStatement("DELETE from TWEETS where ID = ?");
		
		preparedStatement.setInt(1, index);		
		int result = preparedStatement.executeUpdate();		
		
		if (result == 1) {
			c.commit();
		}
	}
	
	//Add a new Tweet to the DB
	public void addIntoTweets(int numberOfTweets, String header, String description, String pathOfImage) throws SQLException {
		preparedStatement = c.prepareStatement("INSERT INTO TWEETS (ID, TWEET_NUMBER, TWEET_HEADER, TWEET_TEXT, TWEET_IMAGE)" +
												" VALUES (?, ? , ? , ?, ?)");
		
		preparedStatement.setInt(1, numberOfTweets);
		preparedStatement.setInt(2, numberOfTweets);
		preparedStatement.setString(3, header);
		preparedStatement.setString(4, description);
		preparedStatement.setString(5, pathOfImage);
		int result = preparedStatement.executeUpdate();		
		
		if (result == 1) {
			c.commit();
		}
	}
	
	//gets the PrimaryKey
	public int getPrimaryKey() throws SQLException {
		rs = stmt.executeQuery("SELECT * FROM TWEETS;");
		int id = 0;
		while(rs.next()) {
			id = rs.getInt("id");
		}
		return id;
	}
	
	//Read content in DB
	public int readDB() throws SQLException {
		int i = 0;
		rs = stmt.executeQuery("SELECT * FROM TWEETS;");
		while(rs.next()) {
			i++;
			int id = rs.getInt("id");
			int tweet_number = rs.getInt("tweet_number");
			String tweet_header = rs.getString("tweet_header");
			String tweet_text = rs.getString("tweet_text");
			String tweet_image = rs.getString("tweet_image");

			//Ausgabe zur Überprüfung in der Konsole
//			System.out.println("ID = " + id);
//			System.out.println("TWEET_NUMBER = " + tweet_number);
//			System.out.println("TWEET_HEADER = " + tweet_header);
//			System.out.println("TWEET_TEXT = " + tweet_text);
//			System.out.println("TWEET_IMAGE = " + tweet_image);	
		}
		return i;
	}
	
	//get the TweetNumber
	public int getTweetNumber() throws SQLException {
		rs = stmt.executeQuery("SELECT * FROM TWEETS;");
		while(rs.next()) {
			int tweet_number = rs.getInt("tweet_number");

			return tweet_number;
		}
		return 0;
	}
	
	//get the TweetHeader
	public String getTweetHeader() throws SQLException {
		rs = stmt.executeQuery("SELECT * FROM TWEETS;");
		while(rs.next()) {
			String tweet_header = rs.getString("tweet_header");
			return tweet_header;
		}
		return null;
	}
	
	//get the TweetText
	public String getTweetText() throws SQLException {
		rs = stmt.executeQuery("SELECT * FROM TWEETS;");
		while(rs.next()) {
			String tweet_text = rs.getString("tweet_text");
			
			return tweet_text;
		}
		return null;
	}
	
	public ArrayList<String> getTweetImage() throws SQLException {
		ArrayList<String> array = new ArrayList<String>();
		rs = stmt.executeQuery("SELECT * FROM TWEETS;");
		while(rs.next()) {
			String tweetImage = rs.getString("tweet_image");
			array.add(tweetImage);
		}
		return array;
	}
	
	public ArrayList<String> getTweetComplete() throws SQLException {
		ArrayList<String> array = new ArrayList<String>();
		rs = stmt.executeQuery("SELECT * FROM TWEETS;");
		while(rs.next()) {
			String tweetTextHeader = rs.getString("tweet_header");
			String tweetTextDescription = rs.getString("tweet_text");
			String tweetComplete = tweetTextHeader+ "\n\n" + tweetTextDescription;
			array.add(tweetComplete);
		}
		return array;
	}
	
	//Close DB	
	public void closeDB() throws SQLException {
		rs.close();
		stmt.close();
		c.close();
	}
	
}