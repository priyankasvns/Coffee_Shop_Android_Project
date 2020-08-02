package com.example.assignment_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private String[] sizes = {"small", "medium", "large"};
    private Spinner spnSizes;
    boolean tea = false;
    boolean coffee = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Fetching the array of provinces
        String[] provinces = getResources().getStringArray(R.array.provinces);

        //Setting autocomplete textview to a variable autoProvinces
        AutoCompleteTextView autoProvinces;
        TextInputLayout textInputLayout = findViewById(R.id.autoCompleteTextView);
        autoProvinces = (AutoCompleteTextView) textInputLayout.getEditText();

        //Creating array adapter for the auto complete text view and bind the provinces array to the adapter
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, provinces);
        autoProvinces.setAdapter(adapter1);


        //Fetching the date selected by the user and displaying it in the same text view in the format: yyyy/mm/dd
        final DatePickerDialog[] datePicker = new DatePickerDialog[1];
            final EditText edtDate;
        TextInputLayout textInputLayout1 = findViewById(R.id.edtdate);
        edtDate = (EditText) textInputLayout1.getEditText();
        edtDate.setInputType(InputType.TYPE_NULL);
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                datePicker[0] = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int i, int i1, int i2) {
                                i1 += 1;
                                edtDate.setText(i + "/" + i1 + "/" + i2);
                            }
                        }, year, month, day);

                datePicker[0].show();
            }
        });


        //Creating mappings for canvas radio buttons and radio groups.
        RadioGroup rgBev;
        RadioButton radioTea;
        RadioButton radioCoffee;

        rgBev = (RadioGroup) findViewById(R.id.rgBeverage);
        radioTea = (RadioButton) findViewById(R.id.rbTea);
        radioCoffee = (RadioButton) findViewById(R.id.rbCoffee);

        final RadioGroup rgFlavoringTea;
        final RadioGroup rgFlavoringCoffee;

        RadioButton rbTeaLemon, rbTeaMint, rbCoffeeVanilla, rbCoffeeChocolate;
        rbTeaLemon = (RadioButton) findViewById(R.id.rbLemon);
        rbTeaMint = (RadioButton) findViewById(R.id.rbMint);
        rbCoffeeVanilla = (RadioButton) findViewById(R.id.rbVanilla);
        rbCoffeeChocolate = (RadioButton) findViewById(R.id.rbChocolate);

        rgFlavoringTea = (RadioGroup) findViewById(R.id.rgFlavoringsTea);
        rgFlavoringCoffee = (RadioGroup) findViewById(R.id.rgFlavoringsCoffee);

        //Listener Handler method to accept user selection and show/ hide the tea/ coffee related options and images
        rgBev.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton)  group.findViewById(checkedId);

                if(rb.getId() == R.id.rbTea)
                {
                    tea = true;
                    CardView cv_tea = (CardView) findViewById(R.id.cvtea);
                    cv_tea.setVisibility(View.VISIBLE);
                    CardView cv_coffee = (CardView) findViewById(R.id.cvcoffee);
                    cv_coffee.setVisibility(View.INVISIBLE);

                    rgFlavoringTea.setVisibility(View.VISIBLE);
                    rgFlavoringCoffee.setVisibility(View.INVISIBLE);
                }
                else if(rb.getId() == R.id.rbCoffee)
                {
                    coffee = true;
                    CardView cv_coffee = (CardView) findViewById(R.id.cvcoffee);
                    cv_coffee.setVisibility(View.VISIBLE);
                    CardView cv_tea = (CardView) findViewById(R.id.cvtea);
                    cv_tea.setVisibility(View.INVISIBLE);

                    rgFlavoringCoffee.setVisibility(View.VISIBLE);
                    rgFlavoringTea.setVisibility(View.INVISIBLE);
                }
            }
        });

        //Mapping/ Binding sizes to the spinner
        spnSizes = (Spinner)findViewById(R.id.spnsize);
        ArrayAdapter<String> adptSpnSizes = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,sizes);
        spnSizes.setAdapter(adptSpnSizes);

    }

    /*Calculate the final cost and display a toast message of the order details
    having the customer name, the province and the selections made by the user upon clicking the manipulate button*/
    public void manipulateButton(View view) {

        RadioButton rbTeaLemon, rbTeaMint, rbCoffeeVanilla, rbCoffeeChocolate, rbTeaNone, rbCoffeeNone;

        rbTeaNone = (RadioButton) findViewById(R.id.rbNoneTea);
        rbTeaLemon = (RadioButton) findViewById(R.id.rbLemon);
        rbTeaMint = (RadioButton) findViewById(R.id.rbMint);

        rbCoffeeNone = (RadioButton) findViewById(R.id.rbNoneCoffee);
        rbCoffeeVanilla = (RadioButton) findViewById(R.id.rbVanilla);
        rbCoffeeChocolate = (RadioButton) findViewById(R.id.rbChocolate);

        Spinner spinner = (Spinner)findViewById(R.id.spnsize);
        String text = spinner.getSelectedItem().toString();

        CheckBox chkbxMilk, chkbxSugar;
        chkbxMilk = (CheckBox) findViewById(R.id.cbMilk);
        chkbxSugar = (CheckBox) findViewById(R.id.cbSugar);
        Double total = 0.0;
        Double taxValue;


        String selections = "";

        EditText editTextCustName;
        TextInputLayout textInputLayout2 = findViewById(R.id.editTextTextPersonName);
        editTextCustName = (EditText) textInputLayout2.getEditText();
        String custName = editTextCustName.getText().toString();

        AutoCompleteTextView prov;
        TextInputLayout textInputLayout3 = findViewById(R.id.autoCompleteTextView);
        prov = (AutoCompleteTextView) textInputLayout3.getEditText();
        String Prov = prov.getText().toString();

        selections += "For " + custName;
        selections += " from " + Prov;
        selections += " , a " + text;
        if(tea == true)
        {
            selections += " tea, with ";
        }
        else if(coffee == true)
        {
            selections += " coffee, with ";
        }


        if(text == sizes[0])
        {
            total += 1.50;
        }
        else if(text == sizes[1])
        {
            total += 2.50;
        }
        else if(text == sizes[2])
        {
            total += 3.25;
        }
        else {
            total += 0;
        }

        if(chkbxMilk.isChecked())
        {
            total += 1.25;
            selections += " Milk, ";
        }
        if(chkbxSugar.isChecked())
        {
            total += 1.00;
            selections += " Sugar, ";
        }

        if(rbTeaLemon.isChecked())
        {
            total += 0.25;
            selections += " Lemon flavoring, cost: $";
        }

        if(rbTeaMint.isChecked())
        {
            total += 0.50;
            selections += " Mint flavoring, cost: $";
        }

        if(rbCoffeeVanilla.isChecked())
        {
            total += 0.25;
            selections += " Vanilla flavoring, cost: $";
        }

        if(rbCoffeeChocolate.isChecked())
        {
            total += 0.75;
            selections += " Chocolate flavoring, cost: $";
        }

        if(rbCoffeeNone.isChecked())
        {
            selections += " no flavoring, cost: $";
        }

        if(rbTeaNone.isChecked())
        {
            selections += " no flavoring, cost: $";
        }

        taxValue = total * 0.13;
        total += taxValue;

        String strDouble = String.format("%.2f", total);


        selections += String.valueOf(strDouble);

        Toast toast = Toast.makeText(getApplicationContext(), selections, Toast.LENGTH_SHORT );
        toast.show();

    }
}