package com.example.dividendcalc;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");


        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_home) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (id == R.id.menu_about) {
            startActivity(new Intent(this, AboutActivity.class));
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
