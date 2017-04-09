package application;

/*
 * This Controller class populates and adds behaviour to the controls in the windows.
 * 
 * Part of the sample solution to Assignment 5.
 */

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Assignment5Controller {

    @FXML
    private ChoiceBox<String> sizeChoice = new ChoiceBox<>();
    
    @FXML
    private ChoiceBox<String> cheeseChoice = new ChoiceBox<>();
    
    @FXML
    private ChoiceBox<String> pepperoniChoice = new ChoiceBox<>();

    @FXML
    private ChoiceBox<String> hamChoice = new ChoiceBox<>();

    @FXML
    private TextField txtCost = new TextField();
    
    @FXML
    private TextField txtNumToOrder = new TextField();

    @FXML
    private TextField txtLineItemCost = new TextField();

    @FXML
    private Button btnAdd = new Button();

    @FXML
    private TextArea txtFullOrder;

    // Will become the contents of the choice boxes.
    private ObservableList<String> sizeList = FXCollections.observableArrayList("small", "medium", "large");
    private ObservableList<String> cheeseList = FXCollections.observableArrayList("single", "double", "triple");
    private ObservableList<String> pepperoniList = FXCollections.observableArrayList("none", "single", "double", "triple");
    private ObservableList<String> hamList = FXCollections.observableArrayList("none", "single", "double", "triple");

    private Pizza currentPizza;
    private int numToOrder = 1;
	private ArrayList<LineItem> orders = new ArrayList<>();
    
    // Translates topping amount as a string to an integer representation.
	private int translateTopping(String amount) {
    	switch (amount) {
    	case "none" :
    		return 0;
    	case "single" :
    		return 1;
    	case "double" :
    		return 2;
    	}
    	return 3;
    }
    
    // Creates the current pizza object using choice box selections and updates the text boxes
	// showing costs.
	private void updateCost() {
    	String size = sizeChoice.getValue();
    	int cheese = translateTopping(cheeseChoice.getValue());
    	int pepperoni = translateTopping(pepperoniChoice.getValue());
    	int ham = translateTopping(hamChoice.getValue());
    	try {
    		currentPizza = new Pizza(size, cheese, ham, pepperoni);
    	} catch (IllegalPizza ip) {
    		System.err.println("Illegal Pizza - this should not be happening!");
    	}
    	txtCost.setText(String.format("$%.2f", currentPizza.getCost()));
		txtLineItemCost.setText(String.format("$%.2f", numToOrder * currentPizza.getCost()));
    }

    // Loads strings into choice boxes and adds actions to all choice boxes, the quantity
	// text box and the "Add" button.
	@FXML
    void initialize() {
       	sizeChoice.setValue("small");
    	sizeChoice.setItems(sizeList);
       	cheeseChoice.setValue("single");
    	cheeseChoice.setItems(cheeseList);
       	pepperoniChoice.setValue("single");
    	pepperoniChoice.setItems(pepperoniList);
       	hamChoice.setValue("none");
    	hamChoice.setItems(hamList);
    	sizeChoice.valueProperty().addListener((observableValue, oldVal, newVal) ->
    	{
    		updateCost();
    	});
    	cheeseChoice.valueProperty().addListener((observableValue, oldVal, newVal) ->
    	{
    		updateCost();
    	});
    	// Meat choices must limit possible choices so that the sum of meat is less than
    	// four.  This is done by reversing an illegal change made by the user.
    	pepperoniChoice.valueProperty().addListener((observableValue, oldVal, newVal) ->
    	{
    		String ham = hamChoice.getValue();
    		if (newVal.equals("triple"))
    			hamChoice.setValue("none");
    		else if (newVal.equals("double") && (ham.equals("double") || ham.equals("triple")))
    			hamChoice.setValue("single");
    		else if (newVal.equals("single") && ham.equals("triple"))
    			hamChoice.setValue("double");
    		updateCost();
    	});
    	hamChoice.valueProperty().addListener((observableValue, oldVal, newVal) ->
    	{
    		String pepperoni = pepperoniChoice.getValue();
    		if (newVal.equals("triple"))
    			pepperoniChoice.setValue("none");
    		else if (newVal.equals("double") && (pepperoni.equals("double") || pepperoni.equals("triple")))
    			pepperoniChoice.setValue("single");
    		else if (newVal.equals("single") && pepperoni.equals("triple"))
    			pepperoniChoice.setValue("double");
    		updateCost();
    	});
    	// Checks to make sure that the order number is between 1 and 100.  If it is not,
    	// an error message is shown and the order number reverted to the previous value.
    	txtNumToOrder.textProperty().addListener((observableValue, oldVal, newVal) ->
    	{
    		if (newVal.equals("")) {
    			txtLineItemCost.setText("");
    			return;
    		}
    		int orderNum = 1;
    		try {
    			orderNum = Integer.parseInt(newVal);
    		} catch (NumberFormatException e) {
    			txtNumToOrder.setText(oldVal);
    			return;
    		}
    		if (orderNum < 1 || orderNum > 100) {
    	    	Alert info = new Alert(AlertType.ERROR);
    	    	info.setTitle("Error - Illegal Number");
    	    	info.setHeaderText("Illegal Order Number");
    	    	info.setContentText("You must choose between 1 and 100 pizzas!");
    	    	info.showAndWait();
    			txtNumToOrder.setText(oldVal);
    			return;
    		}
    		numToOrder = orderNum;
    		updateCost();
    	});
    	// Adds the current pizza to the ArrayList of LineItem objects and re-displays the
    	// order, along with the total order cost.
    	btnAdd.setOnAction(event ->
    	{
    		if (txtLineItemCost.getText().equals(""))
    			return;
    		LineItem item;
    		float totalCost = 0;
    		String output = "";
    		try {
    			item = new LineItem(numToOrder, currentPizza);
    		} catch (IllegalPizza ip) {
    			System.err.println("Illegal item - should never get here!");
    			return;
    		}
    		orders.add(item);
    		orders.sort(null);
    		for (LineItem order : orders) {
    			totalCost += order.getCost();
    			output += order + "\n";
    		}
    		output += "Total Cost: " + String.format("$%.2f", totalCost);
    		txtFullOrder.setText(output);
    	});
    	updateCost();
     } // end initialize
    
} // end Controller class
