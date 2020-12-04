package com.example.polaris;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.polaris.HomeAdapter.ArtistAdapter;
import com.example.polaris.HomeAdapter.ArtistHelperClass;
import com.example.polaris.HomeAdapter.CategoriesAdapter;
import com.example.polaris.HomeAdapter.CategoriesHelperClass;
import com.example.polaris.HomeAdapter.TopGenreAdapter;
import com.example.polaris.HomeAdapter.TopGenreHelperClass;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variables
    static final float END_SCALE = 0.7f;
    RecyclerView songsRecycler, categoriesRecycler, artistsRecycler;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4;
    ImageView menuIcon, libraryIcon;
    LinearLayout contentView;
    MenuItem item;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hiding the Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        //Hooks for Dashboard
        songsRecycler = findViewById(R.id.songs_recycler);
        categoriesRecycler = findViewById(R.id.categories_recycler);
        artistsRecycler = findViewById(R.id.artists_recycler);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);
        libraryIcon = findViewById(R.id.your_library);

        //Navigation hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.aboutus);

        //Navigation Drawer Function Call
        navigationDrawer();

        //Recycler view function calls
        songsRecycler();
        categoriesRecycler();
        artistsRecycler();

        //Your Library Page
        libraryIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, LibrarySongs.class);
                startActivity(intent);
            }
        });
    }

    //Navigation Drawer Functions
    private void navigationDrawer() {
        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        animateNavigationDrawer();
    }

    //Animating the Navigation Drawer
    private void animateNavigationDrawer() {
        drawerLayout.setScrimColor(getResources().getColor(R.color.scrimcolor));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //Scale the view based on the current slide effect
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                //Translate the view accounting for the scaled width
                final float x0ffset = drawerView.getWidth() * slideOffset;
                final float x0ffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = x0ffset - x0ffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            //Returning to Home screen from the DashBoard Screen on pressing back
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
    }

    //Actions to be performed on clicking on the Menu Items
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //Get the Menu Item ID
        int id = item.getItemId();
        if (id == R.id.aboutus) {
            Intent intent = new Intent(Dashboard.this, AboutUs.class);
            startActivity(intent);
        } else if (id == R.id.share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Download this Application now from here: https://drive.google.com/drive/folders/1ZOpWbECTU7f2IdAsTS3wlC3JxOoWTL8e?usp=sharing";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } else if (id == R.id.home) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (id == R.id.your_profile) {
            Intent intent = new Intent(Dashboard.this, UserProfile.class);
            intent.putExtras(getIntent().getExtras());
            startActivity(intent);
            overridePendingTransition(R.anim.slide_animation, R.anim.no_animation);
        } else if (id == R.id.logout) {
            Intent intent = new Intent(Dashboard.this, Login.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    //Recycler View Functions
    private void artistsRecycler() {
        artistsRecycler.setHasFixedSize(true);
        artistsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<ArtistHelperClass> artistCollection = new ArrayList<>();
        artistCollection.add(new ArtistHelperClass(R.drawable.alan_walker, "Alan Walker", "English-Norwegian DJ and record producer."));
        artistCollection.add(new ArtistHelperClass(R.drawable.ar_rahman, "A. R. Rahman", "Indian composer, singer, and music producer."));
        artistCollection.add(new ArtistHelperClass(R.drawable.marshmello, "Marshmello", "American electronic music producer and DJ."));
        artistCollection.add(new ArtistHelperClass(R.drawable.arijit_singh, "Arijit Singh", "Indian playback singer."));
        adapter = new ArtistAdapter(artistCollection);
        artistsRecycler.setAdapter(adapter);
    }

    private void categoriesRecycler() {
        categoriesRecycler.setHasFixedSize(true);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //All Gradients
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xffb8d7f5});
        ArrayList<CategoriesHelperClass> categories = new ArrayList<>();
        categories.add(new CategoriesHelperClass(gradient1, R.drawable.workout_icon, "Workout"));
        categories.add(new CategoriesHelperClass(gradient2, R.drawable.party_icon, "Party"));
        categories.add(new CategoriesHelperClass(gradient3, R.drawable.instrumental_icon, "Instrumental"));
        categories.add(new CategoriesHelperClass(gradient4, R.drawable.romance_icon, "Romance"));
        adapter = new CategoriesAdapter(categories);
        categoriesRecycler.setAdapter(adapter);
    }

    private void songsRecycler() {
        songsRecycler.setHasFixedSize(true);
        songsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //All Gradients
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xffb8d7f5});
        ArrayList<TopGenreHelperClass> topGenres = new ArrayList<>();
        topGenres.add(new TopGenreHelperClass(gradient4, R.drawable.bollywood_icon, "Bollywood"));
        topGenres.add(new TopGenreHelperClass(gradient3, R.drawable.edm_icon, "Electronic Dance Music"));
        topGenres.add(new TopGenreHelperClass(gradient2, R.drawable.pop_icon, "Pop Music"));
        topGenres.add(new TopGenreHelperClass(gradient1, R.drawable.rap_icon, "Rap"));
        adapter = new TopGenreAdapter(topGenres);
        songsRecycler.setAdapter(adapter);
    }
}