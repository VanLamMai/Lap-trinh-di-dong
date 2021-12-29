package com.example.length;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String[] units = {
            "Hải lý", "Dặm", "Kilometer", "Lý", "Met",
            "Yard", "Foot", "Inches"
    };
    private double[][] ratio = {
            { 1.00000000, 1.15077945, 1.8520000, 20.2537183, 1852.0000, 2025.37183, 6076.11549, 72913.38583},
            { 0.86897624, 1.00000000, 1.6093440, 17.6000000, 1609.3440, 1760.00000, 5280.00000, 63360.00000},
            { 0.53995680, 0.62137119, 1.00000000, 10.9361330, 1000.0000, 1093.61330, 3280.83990, 39370.07874},
            { 0.04937365, 0.05681818, 0.0914400, 1.00000000, 91.4400, 100.00000, 300.00000, 3600.00000},
            { 0.00053996, 0.00062137, 0.0010000, 0.0109361, 1.0000, 1.09361, 3.28084, 39.37008},
            { 0.00049374, 0.00056818, 0.0009144, 0.0100000, 0.9144, 1.00000, 3.00000, 36.00000},
            { 0.00016458, 0.00018939, 0.0003048, 0.0033333, 0.3048, 0.33333, 1.00000, 12.00000},
            { 0.00001371, 0.00001578, 0.0000254, 0.0002778, 0.0254, 0.02778, 0.08333, 1.00000}

    };
    private EditText txtNumber;
    private Spinner spnUnits;
    private TextView []lblResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControl();
    }
    private void initControl()
    {
        txtNumber = this.<EditText>findViewById(R.id.txtNumber);
        spnUnits = this.<Spinner>findViewById(R.id.spnUnit);
        lblResults = new TextView[]{
                findViewById(R.id.lblHaily),
                findViewById(R.id.lblDam),
                findViewById(R.id.lblPhy),
                findViewById(R.id.lblKm),
                findViewById(R.id.lblMet),
                findViewById(R.id.lblYar),
                findViewById(R.id.lblFot),
                findViewById(R.id.lblInch)
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this, R.layout.support_simple_spinner_dropdown_item, units
        );
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spnUnits.setAdapter(adapter);

        spnUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                changeLengthUnit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        txtNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeLengthUnit();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void changeLengthUnit() {
        int rowIdx = spnUnits.getSelectedItemPosition();

        if (rowIdx < 0) rowIdx = 0;

        String input = txtNumber.getText().toString();

        if (input.isEmpty()) input="0";

        double number = Double.valueOf(input);

        for (int i = 0; i < lblResults.length; i++) {
            double temp = number * ratio[rowIdx][i];

            lblResults[i].setText(String.valueOf(temp));
        }
    }
}