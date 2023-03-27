package com.example.loginwithgg;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.loginwithgg.databinding.ActivityHomeBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.net.URI;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {
            binding.textView2.setText(account.getDisplayName());
            binding.textView.setText(account.getEmail());
            binding.textView3.setText(account.getId());
            Uri photoUrl = account.getPhotoUrl();
            if (photoUrl != null){
                Glide.with(this)
                        .load(photoUrl)
                        .into(binding.imageView);
            }
        } else {
            goSignOut();
        }

        binding.button.setOnClickListener(view -> {
            goSignOut();
        });

        binding.btnAllBooks.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, AllBooksActivity.class));
        });

        Utils.getInstance();

        binding.btnAlreadyRead.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, AlreadyReadBooksActivity.class);
            startActivity(intent);
        });

        binding.btnWantToRead.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, WantToReadActivity.class);
            startActivity(intent);
        });

        binding.btnCurrentReading.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, CurrentlyReadingActivity.class);
            startActivity(intent);
        });

        binding.btnFavorite.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, FavoriteActivity.class);
            startActivity(intent);
        });
    }

    private void goSignOut() {
        gsc.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                finish();

            }
        });
    }
}