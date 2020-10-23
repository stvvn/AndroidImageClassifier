package com.hfad.faceclassifier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    private static final float END_SCALE = 0.7f;
    /************** UI Components **************/
    // Drawer Menu
    DrawerLayout drawerLayout;
    // Navigation View
    NavigationView navigationView;
    // Menu Icon
    ImageView menuIcon;

    // Main Layout
    LinearLayout contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI Components
        drawerLayout   = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);

        navigationDrawer();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();


        }

    }

    /**************
     This function deals with all the actions associated with the Navigation View
     **************/

    private void navigationDrawer() {
        // Navigation Drawer
        navigationView.bringToFront(); // To interact with navigation drawer
        navigationView.setNavigationItemSelectedListener(this); // Make items clickable

        navigationView.setCheckedItem(R.id.nav_home); // Make home selected by default

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check if the drawerLayout is visible
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    // Close the drawer
                    drawerLayout.closeDrawer(GravityCompat.START);

                else
                    // Open the drawer if it's not opened already
                    drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        animateNavigationDrawer();

    }


    /**************
     This function deals with animations of changing color when the user draws the navigation
     drawer.
     **************/

    private void animateNavigationDrawer() {

        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                // contentView for scaling at some value
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }

        });


    }

    /**************
     This function is overridden to ensure that the application close the navigation drawer if it
     is open instead of closing the application.
     **************/
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);

            // If the drawer is not opened, then quit the application when back button is pressed as usual
        else
            super.onBackPressed();
    }

    /**************
     This function is triggered whenever a user clicks one of the item in the
     navigation menu. menuItem indicates the item that was clicked.
     **************/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
                break;
            default:
                Toast.makeText(this, "NEED IMPLEMENTATION", Toast.LENGTH_SHORT).show();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}