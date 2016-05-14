package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class JustJava extends ActionBarActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_java);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        int price, basePrice;
        basePrice = 5;


        boolean hasWhippedCream = whippedCream();
        boolean hasChocolate = chocolate();

        if(hasChocolate ==true && hasWhippedCream == true)
        {
            basePrice+=3;
        }
        else if(hasChocolate)
        {
            basePrice+=2;
        }
        else if(hasWhippedCream)
        {
            basePrice+=1;
        }

        price = calculatePrice(quantity, basePrice);
        String name = name();
        String priceMessage= createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        // String priceMessage = price + " $ for " + quantity + " cup of coffee. Pay Up \nThank You";

        //Email Snippet

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"Sahil.Arora@emc.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java for Sahil Arora");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displayMessage(priceMessage);






    }

    private String name() {

        EditText nameView = (EditText) findViewById(R.id.name);
        String name = nameView.getText().toString();
        return name;
    }

    private boolean chocolate() {
        CheckBox chkBx = (CheckBox) findViewById(R.id.chocolate_chk_bx);
        return (chkBx.isChecked());

    }

    private boolean whippedCream() {

        CheckBox chkBx = (CheckBox) findViewById(R.id.whipped_cream_chk_bx);
        if(chkBx.isChecked() == true)
            return true;
        else
            return false;
    }

    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name) {

        String priceMessage = "Name: "+name+" \nQuantity: "+ quantity + " \nHas Whipped Cream : "+ hasWhippedCream+ " \nHas Chocolate : "+hasChocolate+ " \nPrice: " + price +" \nThank You.";
        return priceMessage;
    }

    /**
     *
     * @param quantity is the number of coffees ordered
     * @return
     */

    private int calculatePrice(int quantity, int pricePerUnit) {

        return quantity * pricePerUnit ;
    }

    public void increment(View view) {

        if(quantity == 10)
        {
            Toast toast = Toast.makeText(this, "That is too much coffee! ", Toast.LENGTH_LONG);
            toast.show();
        }
        else
        {
            quantity = quantity + 1 ;
        }
        display(quantity);

    }

    public void decrement(View view) {


        if(quantity == 1)
        {
            Toast toast = Toast.makeText(this, "You cannot order less than 1", Toast.LENGTH_LONG);
            toast.show();
        }
        else
        {
            quantity = quantity - 1 ;
        }
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given price on the screen.
     */

    /*private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }*/

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
