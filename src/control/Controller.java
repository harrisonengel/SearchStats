/*******************************************************************/
/*   Program Name:     Lab 4    Sorts                             */
/*                                                                 */
/*   Student Name:     Harrison Engel                              */
/*   Semester:         Fall 2014                                   */
/*   Class-Section:    COSC 20803-035                              */
/*   Instructor:       Dr. Comer                                   */
/*******************************************************************/

package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import model.Model;
import view.GUI;

public class Controller implements ActionListener {

	private GUI view;
	private Model model;

	public Controller() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			
			String fileName;
			String searchType = view.getSearchSelection();
			String keyFile = view.getSearchFile();
			
			if (keyFile.equalsIgnoreCase("Present Keys"))
					fileName = "PresentKeys.dat";
			else if(keyFile.equalsIgnoreCase("Missing Keys"))
				fileName = "MissingKeys.dat";
			else fileName = "";// Shouldn't be able to get here. Will catch if it somehow does
			
			int searchKeys = view.getSearchedKeys();
			int skipKeys = view.getSkippedKeys();
			int toDisplay;
			
			if (searchType.equalsIgnoreCase("Sequential Search")) {
				toDisplay = model.sequentialSearch(fileName, searchKeys, skipKeys);
			} else if (searchType.equalsIgnoreCase("Binary Search")) {
				toDisplay = model.binarySearch(fileName, searchKeys, skipKeys);
			} else if (searchType.equalsIgnoreCase("Fibonnaci Search")) {
				toDisplay = model.fibonacciSearch(fileName, searchKeys, skipKeys);
			} else {
				toDisplay = model.interpolationSearch(fileName, searchKeys, skipKeys);
			}

			view.setComparisons(toDisplay);
	
		} catch (FileNotFoundException fnfe) {
			System.out.println("OOPS file not found");
		} catch (IOException ioe) {
			System.out.println("OOPS IOException");
		}
	}

	public void setGUI(GUI g) { //Connects control to view
		this.view = g;
		view.btnSearch.addActionListener(this);
	}

	public void setModel(Model m) {//Connects control to model
		this.model = m;
	}


}
