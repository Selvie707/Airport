package com.example.airport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.airport.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {

    private ActivityAddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        binding.btnAddData.setOnClickListener(v -> {
            String name = binding.etAddName.getText().toString();
            String city = binding.etAddCity.getText().toString();
            String address = binding.etAddAddress.getText().toString();

            if (name.trim().isEmpty()) {
                binding.etAddName.setError(getString(R.string.errorEmptyInput));
            }
            if (city.trim().isEmpty()) {
                binding.etAddCity.setError(getString(R.string.errorEmptyInput));
            }
            if (address.trim().isEmpty()) {
                binding.etAddAddress.setError(getString(R.string.errorEmptyInput));
            }
            else {
                MyDBHelper mdb = new MyDBHelper(AddActivity.this);
                long execution = mdb.AddAirport(name, city, address);

                if (execution == -1) {
                    showToast(getString(R.string.failedAddData));
                    binding.etAddName.requestFocus();
                }
                else {
                    showToast(getString(R.string.successedAddData));
                    finish();
                }
            }
        });

        binding.ivBack.setOnClickListener(v -> {
            startActivity(new Intent(AddActivity.this, MainActivity.class));
            finish();
        });
    }

    private void showToast(String message) {
        Toast.makeText(AddActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}