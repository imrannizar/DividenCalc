package com.example.dividendcalc;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        EditText investedAmount = findViewById(R.id.investedAmount);
        EditText dividendRate = findViewById(R.id.dividendRate);
        EditText monthsInvested = findViewById(R.id.monthsInvested);
        Button calculateButton = findViewById(R.id.calculateButton);
        TextView monthlyDividend = findViewById(R.id.monthlyDividend);
        TextView totalDividend = findViewById(R.id.totalDividend);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double amount = Double.parseDouble(investedAmount.getText().toString());
                    double rate = Double.parseDouble(dividendRate.getText().toString());
                    int months = Integer.parseInt(monthsInvested.getText().toString());

                    if (amount <= 0) {
                        Toast.makeText(MainActivity.this, "Enter a valid amount", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (rate <= 0) {
                        Toast.makeText(MainActivity.this, "Enter a valid rate", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (months <= 0 || months > 12) {
                        Toast.makeText(MainActivity.this, "Enter 1-12 months", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double monthlyDiv = (rate / 100.0 / 12.0) * amount;
                    double totalDiv = monthlyDiv * months;
                    monthlyDividend.setText(String.format("Monthly Dividend: RM %.2f", monthlyDiv));
                    totalDividend.setText(String.format("Total Dividend: RM %.2f", totalDiv));
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_home) {
            return true;
        } else if (id == R.id.menu_about) {
            startActivity(new Intent(this, AboutActivity.class));
            finish(); // Optional: close MainActivity to prevent stacking
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}