package com.example.airport;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.example.airport.databinding.ActivitySplashScreenBinding;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    private ActivitySplashScreenBinding binding;
    private final int POST_DURATION = 3000;
    private final int PB_DURATION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, POST_DURATION);

        binding.pbLoading.setMax(10);
        ObjectAnimator.ofInt(binding.pbLoading, "progress", 6).setDuration(PB_DURATION).start();
    }
}