import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.json.JSONException;
/**
 * 
 * Class to execute the GUI and call matching algorithms
 *
 */
public class TopDishesApp implements ActionListener{

	private JFrame frmTopDishesApp;
	private JTextField textField;
	private JTextField textField_1;
	private JList list_2;
	private JList list;
	Output resultData;
	JScrollPane scrollPane;
	JScrollPane pane;
	HashMap<String,String> restNameToIdMap ;
	ArrayList<String> listOfRest;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TopDishesApp window = new TopDishesApp();
					window.frmTopDishesApp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	 @Override
	    public void actionPerformed(ActionEvent e) {
	        String command = e.getActionCommand();
	        if (command.equals("Exact Match")) {
	            try {
					myMethod("ExactMatch");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }
	        if (command.equals("Partial Match")) {
	            try {
					myMethod("PartialMatch");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }
	        if (command.equals("Substring Match")) {
	            try {
					myMethod("SubstringMatch");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }
	        if (command.equals("Fuzzy Match")) {
	            try {
					myMethod("FuzzyMatch");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }
	    }

	    public void myMethod(String matcher) throws JSONException {
	    	Analysis obj = new Analysis();
	    	resultData = obj.runcompare(matcher);
	    	
	    	Double precision = resultData.getPrecision()*100;
	    	textField_1.setText(precision.toString());
	    	
	    	Double recall = resultData.getRecall()*100;
	    	textField.setText(recall.toString());
	    	
	    	restNameToIdMap = new HashMap<String,String>();
	    	listOfRest = new ArrayList<String>();
	    	 for (Entry<String, TopRated> entry : resultData.getResultMap().entrySet()) {
	             String value=entry.getValue().resdetail.name;
	             listOfRest.add(value);
	             restNameToIdMap.put(value,entry.getKey());
	         }
	    	
	    	list.setListData(listOfRest.toArray()); 
	    	scrollPane.setViewportView(list);
	    	frmTopDishesApp.getContentPane().revalidate();
	    	
	    	
	    }
	/**
	 * Create the application.
	 */
	public TopDishesApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTopDishesApp = new JFrame();
		frmTopDishesApp.setTitle("Top Dishes App");
		frmTopDishesApp.setBounds(100, 100, 666, 465);
		frmTopDishesApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTopDishesApp.getContentPane().setLayout(null);
		
		JButton btnExactMatch = new JButton("Exact Match");
		btnExactMatch.setBounds(38, 34, 117, 29);
		frmTopDishesApp.getContentPane().add(btnExactMatch);
		btnExactMatch.addActionListener(this);
		
		
		JButton btnPartialMatch = new JButton("Partial Match");
		btnPartialMatch.setBounds(167, 34, 117, 29);
		frmTopDishesApp.getContentPane().add(btnPartialMatch);
		btnPartialMatch.addActionListener(this);
		
		JButton btnSubstringMatch = new JButton("Substring Match");
		btnSubstringMatch.setBounds(296, 34, 140, 29);
		frmTopDishesApp.getContentPane().add(btnSubstringMatch);
		btnSubstringMatch.addActionListener(this);
		
		JButton btnFuzzyMatch = new JButton("Fuzzy Match");
		btnFuzzyMatch.setBounds(448, 34, 117, 29);
		frmTopDishesApp.getContentPane().add(btnFuzzyMatch);
		btnFuzzyMatch.addActionListener(this);
		
		JLabel lblResults = new JLabel("Results");
		lblResults.setBounds(48, 75, 61, 16);
		frmTopDishesApp.getContentPane().add(lblResults);
		
		JLabel lblPrecision = new JLabel("Precision");
		lblPrecision.setBounds(58, 103, 61, 16);
		frmTopDishesApp.getContentPane().add(lblPrecision);
		
		textField = new JTextField();
		textField.setBounds(394, 97, 227, 28);
		frmTopDishesApp.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(120, 97, 196, 28);
		frmTopDishesApp.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblRecall = new JLabel("Recall");
		lblRecall.setBounds(345, 103, 61, 16);
		frmTopDishesApp.getContentPane().add(lblRecall);
		
		JLabel lblListOfRestaurants = new JLabel("List of Restaurants");
		lblListOfRestaurants.setBounds(38, 163, 134, 16);
		frmTopDishesApp.getContentPane().add(lblListOfRestaurants);
		
		list= new JList();
		MouseListener mouseListener = new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) {

		           String selectedItem = (String) list.getSelectedValue();
		           String restId =restNameToIdMap.get(selectedItem);
		           ArrayList<String> topDishes = new ArrayList<String>();
		           list_2.setListData( resultData.getResultMap().get(restId).getratedish().toArray()); 
		          frmTopDishesApp.getContentPane().revalidate();
		          
		         }
		    }
		};
		list.addMouseListener(mouseListener);

		JLabel lblTopDishes = new JLabel("Top Dishes");
		lblTopDishes.setBounds(433, 163, 94, 16);
		frmTopDishesApp.getContentPane().add(lblTopDishes);
		
		JLabel lblChooseTheMatching = new JLabel("Choose the Matching Algorithm");
		lblChooseTheMatching.setBounds(225, 6, 211, 16);
		frmTopDishesApp.getContentPane().add(lblChooseTheMatching);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 191, 211, 224);
		frmTopDishesApp.getContentPane().add(scrollPane);
		
		list_2 = new JList();
		list_2.setBounds(315, 191, 327, 224);
		frmTopDishesApp.getContentPane().add(list_2);
		
	}
}
