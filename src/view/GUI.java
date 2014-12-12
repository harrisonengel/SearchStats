/*******************************************************************/
/*   Program Name:     Lab 4    Sorts                             */
/*                                                                 */
/*   Student Name:     Harrison Engel                              */
/*   Semester:         Fall 2014                                   */
/*   Class-Section:    COSC 20803-035                              */
/*   Instructor:       Dr. Comer                                   */
/*******************************************************************/

package view;

import java.awt.EventQueue;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import model.Model;
import control.Controller;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldComparisons;
	private JComboBox<String> comboBoxSearchType, comboBoxSearchKeys;
	private JComboBox<Integer> comboBoxSkip, comboBoxSearch;
	public JButton btnSearch;
	private JLabel lblSearchKeys;

	/**
	 * On launch, the program creates a View(GUI), Model, and Controller and
	 * then connects them together.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
					Controller control = new Controller();
					control.setGUI(frame);
					Model model = new Model();
					control.setModel(model);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {

		/**************** Frame *************************/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 567, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		/**************** JLabels *************************/
		JLabel lblNumber = new JLabel("Number of Keys");
		lblNumber.setBounds(48, 33, 81, 14);
		contentPane.add(lblNumber);

		JLabel lblSortingMethod = new JLabel("Sorting Method");
		lblSortingMethod.setBounds(31, 133, 110, 14);
		contentPane.add(lblSortingMethod);
		
		JLabel lblKeyComparisons = new JLabel("Key Comparisons");
		lblKeyComparisons.setBounds(263, 133, 123, 14);
		contentPane.add(lblKeyComparisons);
		
		JLabel lblSearch = new JLabel("Search: ");
		lblSearch.setBounds(31, 64, 63, 14);
		contentPane.add(lblSearch);
		
		JLabel lblSkipped = new JLabel("Skipped: ");
		lblSkipped.setBounds(31, 89, 63, 14);
		contentPane.add(lblSkipped);
		
		JLabel lblSearchKeys = new JLabel("Search Keys");
		lblSearchKeys.setBounds(269, 33, 117, 14);
		contentPane.add(lblSearchKeys);
		
		/**************** comboBoxes *************************/
		comboBoxSearchType = new JComboBox<String>();
		comboBoxSearchType
				.setModel(new DefaultComboBoxModel<String>(new String[] {
						"Sequential Search", "Binary Search", "Fibonnaci Search",
						"Interpolation Search" }));
		comboBoxSearchType.setBounds(31, 158, 159, 20);
		contentPane.add(comboBoxSearchType);
		
		comboBoxSearchKeys = new JComboBox<String>();
		comboBoxSearchKeys.setModel(new DefaultComboBoxModel<String>(new String[]{
			"Present Keys", "Missing Keys"}));
		comboBoxSearchKeys.setBounds(263, 58, 123, 20);
		contentPane.add(comboBoxSearchKeys);

		comboBoxSearch = new JComboBox<Integer>();
		comboBoxSearch.setModel(new DefaultComboBoxModel<Integer>(new Integer[]{100, 150, 200, 500}));
		comboBoxSearch.setBounds(104, 58, 86, 20);
		contentPane.add(comboBoxSearch);
		

		comboBoxSkip = new JComboBox<Integer>();
		comboBoxSkip.setModel(new DefaultComboBoxModel<Integer>(new Integer[]{0, 100, 50}));
		comboBoxSkip.setBounds(104, 86, 86, 20);
		contentPane.add(comboBoxSkip);
		
		/**************** JTextFields *************************/
		textFieldComparisons = new JTextField();
		textFieldComparisons.setBounds(396, 130, 145, 20);
		contentPane.add(textFieldComparisons);
		textFieldComparisons.setColumns(10);

		btnSearch = new JButton("Search");
		btnSearch.setBounds(322, 157, 121, 23);
		contentPane.add(btnSearch);				
	}

	public String getSearchSelection() {//Returns the kind of search a user wants to test
		return (String) comboBoxSearchType.getSelectedItem();
	}
	
	public String getSearchFile(){//Returns the kind of file to use as search keys
		return (String)comboBoxSearchKeys.getSelectedItem();
	}

	public int getSkippedKeys() {//Returns the number of keys skipped
		return (Integer)comboBoxSkip.getSelectedItem();
	}
	
	public int getSearchedKeys(){//Returns the number of keys searched 
		return (Integer)comboBoxSearch.getSelectedItem();
	}

	public void setComparisons(int i) {//Sets the text field to display the number of comparisons
		this.textFieldComparisons.setText("" + i);
	}
}
