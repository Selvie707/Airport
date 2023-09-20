package com.example.airport;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.airport.databinding.ActivityMainBinding;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MyDBHelper myDBHelper;
    private ArrayList<String> alId;
    private ArrayList<String> alName;
    private ArrayList<String> alCity;
    private ArrayList<String> alAddress;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        myDBHelper = new MyDBHelper(MainActivity.this);

        binding.fabMain.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ShowAirportData();
    }

    private void SQLiteToArrayList() {
        Cursor cursor = myDBHelper.ReadAirportData();
        if (cursor.getCount() == 0) {
            showToast(getString(R.string.toastNoData));
        } else {
            while (cursor.moveToNext()) {
                alId.add(cursor.getString(0));
                alName.add(cursor.getString(1));
                alCity.add(cursor.getString(2));
                alAddress.add(cursor.getString(3));
            }
        }
    }

    private void ShowAirportData() {
        alId = new ArrayList<>();
        alName = new ArrayList<>();
        alCity = new ArrayList<>();
        alAddress = new ArrayList<>();

        SQLiteToArrayList();

        AdapterAirport adapterAirport = new AdapterAirport(MainActivity.this, alId, alName, alCity, alAddress);
        binding.rvMain.setAdapter(adapterAirport);
        binding.rvMain.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}