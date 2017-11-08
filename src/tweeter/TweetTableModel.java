/**
 * Nicht verwendet!
 */
package tweeter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class TweetTableModel {
	private JPanel panel;
	private JTable table;
	public String[] columns = {"Test"};
	public Object[][] rows= {{"Test"}};
	private JScrollPane scrollPane;
	
	public TweetTableModel() {
		buildTable();	
	}
	
	public void buildTable() {
		TableModel model = new DefaultTableModel(rows, columns);
		table = new JTable(model);
		table.setRowHeight(79);
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(750, 130));
		panel = new JPanel();
		panel.add(scrollPane, BorderLayout.CENTER);
		panel.setPreferredSize(scrollPane.getPreferredSize());
		panel.setBackground(Color.WHITE);
	}
	
	public JPanel getPane() {
		System.out.println("Return ScrollPane");
		return panel;
	}
}
