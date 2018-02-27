package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * @author      Mariel Backman Lastname <backman.mariel@gmail.com>
 * @version     1.0
 */

/**
 * This app displays an order form to order coffee
 */

public class MainActivity extends AppCompatActivity {

    /**
     * numberOfCoffees is the amount of coffees ordered
     * extraShots is the amounts of shots of liqueur ordered
     * totalPrice is the price of the order
     */

    int numberOfCoffees = 0;
    int extraShots = 0;

    //Declaring variables to initialize the views on the method onCreate
    private EditText nameTextView;
    private TextView numberOfCoffeesTextView;
    private TextView shotsTextView;
    private CheckBox whippedCreamCheckbox;
    private CheckBox chocolateCheckbox;

    //Declaring variable for the name - to make basic check of the name
    String yourName;

    //Initializing variables to avoid the issue of app resetting the numbers when the cellphone is rotated (from portrait to landscape or vice versa)
    static final String stateNumberOfCoffees = "orderSummaryNumberOfCoffees";
    static final String stateExtraShots = "extraShots";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing the views
        nameTextView = (EditText) findViewById(R.id.your_name);
        numberOfCoffeesTextView = (TextView) findViewById(R.id.quantity_text_view);
        shotsTextView = (TextView) findViewById(R.id.shots_text_view);
        whippedCreamCheckbox = (CheckBox) findViewById(R.id.topping_whipped_cream);
        chocolateCheckbox = (CheckBox) findViewById(R.id.topping_chocolate);

    }

    /*
     * The following 2 methods are used to avoid the issue of the app resetting the numbers when the cellphone is rotated
     * (from portrait to landscape or vice versa)
     */

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        //Save the user's current coffee and shots numbers
        savedInstanceState.putInt(stateNumberOfCoffees, numberOfCoffees);
        savedInstanceState.putInt(stateExtraShots, extraShots);

        //Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {

        //Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        //Restore state members from saved instance
        numberOfCoffees = savedInstanceState.getInt(stateNumberOfCoffees);
        extraShots = savedInstanceState.getInt(stateExtraShots);

        //Now display the numbers
        displayNumberOfCoffees(numberOfCoffees);
        displayExtraShots(extraShots);
    }


    /**
     * This method displays the given quantity value on the screen (numberOfCoffees)
     */
    private void displayNumberOfCoffees(int number) {
        numberOfCoffeesTextView.setText(String.valueOf(number));
    }

    /**
     * For increasing the # of coffees: button +
     */
    public void incrementNumberOfCoffees(View view) {

        //Making sure the number of coffees isn't more than 100
        if (numberOfCoffees >= 100) {

            //Retrieving the relevant string for this error message from strings.xml
            String errorMessageTooManyCoffees = getString(R.string.error_message_too_many_coffees);

            //Show an error message as a toast
            Toast.makeText(this, errorMessageTooManyCoffees, Toast.LENGTH_SHORT).show();
            return;
        }

        //Add 1 cup of coffee
        numberOfCoffees += 1;

        //Now display the actual number
        displayNumberOfCoffees(numberOfCoffees);
    }

    /**
     * For decreasing the # of coffees: button +
     */
    public void decrementNumberOfCoffees(View view) {

        //Making sure the number of coffees isn't less than 1
        if (numberOfCoffees <= 0) {
            //Retrieving the relevant string for this error message from strings.xml
            String errorMessageTooFewCoffees = getString(R.string.error_message_too_few_coffees);

            //Show an error message as a toast
            Toast.makeText(this, errorMessageTooFewCoffees, Toast.LENGTH_SHORT).show();

            //Exit this method early because there's nothing left to do
            return;
        }
        numberOfCoffees -= 1;

        //Now display the actual number
        displayNumberOfCoffees(numberOfCoffees);
    }

    /**
     * This method displays the given quantity value on the screen (shot of liqueur)
     */
    private void displayExtraShots(int number) {

        //Declaring the variable shotsTextView of type TextView and casting the view returned by findViewByID into a TextView
        shotsTextView.setText(String.valueOf(number));
    }

    /**
     * For increasing the # of shots of liqueur: button +
     */
    public void incrementShots(View view) {

        //Max 3 shots permitted
        if (extraShots >= 3) {

            //Retrieving the relevant string for this error message from strings.xml
            String errorMessageTooManyShots = getString(R.string.error_message_too_many_shots);

            //Show an error message as a toast
            Toast.makeText(this, errorMessageTooManyShots, Toast.LENGTH_SHORT).show();
            return;
        }

        //Add 1 shot of liqueur
        extraShots += 1;

        //Now display the actual number
        displayExtraShots(extraShots);
    }

    /**
     * For decreasing the # of shots of liqueur: button -
     */
    public void decrementShots(View view) {

        //Making sure the app doesn't display negative numbers of shots of liqueur.
        if (extraShots == 0) {

            //Retrieving the relevant string for this error message from strings.xml
            String errorMessageTooFewShots = getString(R.string.error_message_too_few_shots);

            //Show an error message as a toast
            Toast.makeText(this, errorMessageTooFewShots, Toast.LENGTH_SHORT).show();
            return;
        }

        //Subtract 1 shot of liqueur
        extraShots -= 1;

        //Now display the actual number
        displayExtraShots(extraShots);
    }

    /**
     * This method calculates the price of the order
     *
     * @param numberOfCoffees is the amount of coffees ordered
     * @param extraShots is the amount of shots of liqueur
     * @param hasWhippedCream is true if the customer checked the "Whipped cream" checkbox
     * @param hasChocolate is true if the customer checked the "Chocolate" checkbox
     *
     * @return the total price of the order
     */

    private int calculatePrice(int numberOfCoffees, int extraShots, boolean hasWhippedCream, boolean hasChocolate) {
        int priceCupCoffee = 5; //Coffee with nothing
        int priceExtraShot = 2; //Extra shots of liqueur
        int totalPrice, priceShots;

       //Adding the price of the whipped cream
       if (hasWhippedCream) {
           priceCupCoffee += 1;
       }

       //Adding the price of the chocolate
       if (hasChocolate) {
           priceCupCoffee += 2;
       }

       //Adding extra shots
       priceShots =  extraShots * priceExtraShot;

       //Calculate the grand total
       totalPrice = (priceCupCoffee + priceShots) * numberOfCoffees;
       return totalPrice;
    }

    /**
     * This method is called when the order button is clicked
     */
    public void submitOrder(View view) {

        //If the user wants whipped cream
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        //If the user wants chocolate
        boolean hasChocolate = chocolateCheckbox.isChecked();

        //Basic check: if the user has entered a name
        yourName = nameTextView.getText().toString();

        if (yourName.isEmpty()) { //if no name is entered

            //Retrieving the relevant string for this error message from strings.xml
            String errorMessageNameEmpty = getString(R.string.error_message_name_empty);

            //Show an error message as a toast
            Toast.makeText(this, errorMessageNameEmpty, Toast.LENGTH_SHORT).show();
            return;
        } else {

            //Name entered - proceed with the order summary
            //Creating an Intent to send the order summary by e-mail
            String orderSummary = createOrderSummary(numberOfCoffees, extraShots, hasWhippedCream, hasChocolate);

            //Retrieving the relevant strings for the e-mail from string.xml
            String emailSubject = getString(R.string.email_subject);

            //Composing the e-mail
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); //Only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
            intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }


    }

    /**
     * This method creates a summary of the order
     *
     * @param numberOfCoffees is the amount of coffees ordered
     * @param extraShots is the amount of shots of liqueur
     * @param hasWhippedCream is true if the customer checked the "Whipped cream" checkbox
     * @param hasChocolate is true if the customer checked the "Chocolate" checkbox
     *
     * @return the order summary in an e-mail on the customer's default mail client
     */
    private String createOrderSummary(int numberOfCoffees, int extraShots, boolean hasWhippedCream, boolean hasChocolate) {

        //Changing the value of the boolean variables from true to Yes, or false to No - retrieving these strings from strings.xml
        String wantWhippedCream, wantChocolate;
        if (hasWhippedCream) {
           wantWhippedCream = getString(R.string.want_topping_yes);
        } else {
           wantWhippedCream = getString(R.string.want_topping_no);
        }

        if (hasChocolate) {
            wantChocolate = getString(R.string.want_topping_yes);
        } else {
            wantChocolate = getString(R.string.want_topping_no);
        }

        //Retrieving the relevant strings for the order summary from strings.xml
        String orderSummaryName = getString(R.string.order_summary_name, yourName);
        String orderSummaryNumberOfCoffees = String.format(getResources().getString(R.string.order_summary_number_coffees), numberOfCoffees);
        String orderSummaryWithWhippedCream = getString(R.string.order_summary_whipped_cream, wantWhippedCream);
        String orderSummaryWithChocolate = getString(R.string.order_summary_chocolate, wantChocolate);
        String orderSummaryExtraShot = String.format(getResources().getString(R.string.order_summary_shots_liqueur), extraShots);
        String orderSummaryTotal = getString(R.string.order_summary_total);
        String orderSummaryThanks = getString(R.string.order_summary_thanks);

        //Retrieving the total price of the order
        int totalPrice = calculatePrice(numberOfCoffees, extraShots, hasWhippedCream, hasChocolate);

        //Now creating the summary
        String orderSummary = orderSummaryName + "\n";
        orderSummary += orderSummaryNumberOfCoffees + "\n";
        orderSummary += orderSummaryWithWhippedCream + "\n";
        orderSummary += orderSummaryWithChocolate + "\n";
        orderSummary += orderSummaryExtraShot + "\n";
        orderSummary += orderSummaryTotal + NumberFormat.getCurrencyInstance().format(totalPrice) + "\n";
        orderSummary += orderSummaryThanks;
        return orderSummary;
    }

}