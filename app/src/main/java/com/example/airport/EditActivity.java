package com.example.airport;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.airport.databinding.ActivityEditBinding;

public class EditActivity extends AppCompatActivity {
    private String id;
    private ActivityEditBinding binding;
    public static final String EXTRA_ID = "vId";
    public static final String EXTRA_NAME = "vName";
    public static final String EXTRA_CITY = "vCity";
    public static final String EXTRA_ADDRESS = "vAddress";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        id =  intent.getStringExtra(EXTRA_ID);
        String name = intent.getStringExtra(EXTRA_NAME);
        String city = intent.getStringExtra(EXTRA_CITY);
        String address = intent.getStringExtra(EXTRA_ADDRESS);

        binding.etEditName.setText(name);
        binding.etEditCity.setText(city);
        binding.etEditAddress.setText(address);

        binding.btnEditData.setOnClickListener(v -> {
            String tempName = binding.etEditName.getText().toString();
            String tempCity = binding.etEditCity.getText().toString();
            String tempAddress = binding.etEditAddress.getText().toString();

            if (tempName.trim().isEmpty()) {
                binding.etEditName.setError(getString(R.string.errorEmptyInput));
            }
            else if (tempCity.trim().isEmpty()) {
                binding.etEditCity.setError(getString(R.string.errorEmptyInput));
            }
            else if (tempAddress.trim().isEmpty()) {
                binding.etEditAddress.setError(getString(R.string.errorEmptyInput));
            }
            else {
                MyDBHelper mdb = new MyDBHelper(EditActivity.this);
                long execution = mdb.EditAirport(id, tempName, tempCity, tempAddress);

                if (execution == -1) {
                    showToast(getString(R.string.failedEditData));
                    binding.etEditName.requestFocus();
                }
                else {
                    showToast(getString(R.string.successedEditData));
                    finish();
                }
            }
        });

        binding.ivBack.setOnClickListener(v -> {
            startActivity(new Intent(EditActivity.this, MainActivity.class));
            finish();
        });
    }

    private void showToast(String message) {
        Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}