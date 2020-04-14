package com.fabiolucas.faculdade;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.fabiolucas.faculdade.data.model.Albums;
import com.fabiolucas.faculdade.data.model.DadosJSON;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_albums, R.id.nav_comments, R.id.nav_photos, R.id.nav_posts, R.id.nav_users)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.nav_home) {
                    Toast.makeText(MainActivity.this, "home", Toast.LENGTH_LONG).show();
                }

                if (destination.getId() == R.id.nav_gallery) {
                    Toast.makeText(MainActivity.this, "gallery", Toast.LENGTH_LONG).show();
                }

                if (destination.getId() == R.id.nav_slideshow) {
                    Toast.makeText(MainActivity.this, "slideshow", Toast.LENGTH_LONG).show();
                }

                if (destination.getId() == R.id.nav_albums) {
                    Toast.makeText(MainActivity.this, "albums", Toast.LENGTH_LONG).show();
                }

                if (destination.getId() == R.id.nav_comments) {
                    Toast.makeText(MainActivity.this, "home", Toast.LENGTH_LONG).show();
                }

                if (destination.getId() == R.id.nav_photos) {
                    Toast.makeText(MainActivity.this, "photos", Toast.LENGTH_LONG).show();
                }

                if (destination.getId() == R.id.nav_posts) {
                    Toast.makeText(MainActivity.this, "posts", Toast.LENGTH_LONG).show();
                }

                if (destination.getId() == R.id.nav_todos) {
                    Toast.makeText(MainActivity.this, "todos", Toast.LENGTH_LONG).show();
                }

                if (destination.getId() == R.id.nav_users) {
                    Toast.makeText(MainActivity.this, "users", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
