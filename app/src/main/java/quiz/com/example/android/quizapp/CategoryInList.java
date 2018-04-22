package quiz.com.example.android.quizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import quiz.com.example.android.quizapp.Adapters.CategoryListAdapter;
import quiz.com.example.android.quizapp.Data.CategoryDatum;

public class CategoryInList extends AppCompatActivity {
    ArrayList<CategoryDatum> categoryList;
    private ListView l;
    private CategoryListAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private SharedPreferences sp;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorylist);
        final android.support.v4.widget.DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDrawerLayout.closeDrawers();
                        if (menuItem.getItemId() == R.id.nav_logout) {
                            logout();
                        }
                        else if(menuItem.getItemId()==R.id.nav_about){
                            Snackbar.make(drawerLayout,"This is Developed By Siddhant Agarwal", Snackbar.LENGTH_LONG).show();
                        }
                        else if(menuItem.getItemId()==R.id.nav_help){
                            helpDialog();
                        }
                        else if(menuItem.getItemId()==R.id.nav_feedback){
                            Snackbar.make(drawerLayout,"Thanks for Your Feedback", Snackbar.LENGTH_LONG).show();
                        }

                        return true;
                    }
                });
        l = findViewById(R.id.listview);
        categoryList = new ArrayList<>();
        categoryList.add(new CategoryDatum("Computer", R.drawable.computer, R.color.colour_computer));
        categoryList.add(new CategoryDatum("General", R.drawable.general, R.color.colour_general));
        categoryList.add(new CategoryDatum("Invention", R.drawable.invention, R.color.colour_inventions));
        categoryList.add(new CategoryDatum("Create Quiz", R.drawable.createquiz, R.color.colorPrimary));

        adapter = new CategoryListAdapter(getApplicationContext(), categoryList);
        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (position == 3) {
                    Intent i = new Intent(CategoryInList.this, CreateQuiz.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(CategoryInList.this, ComputerQuiz.class);
                    if (position == 0) {
                        i.putExtra("tablename", "Computer");
                    }
                    if (position == 1) {
                        i.putExtra("tablename", "General");
                    }
                    if (position == 2) {
                        i.putExtra("tablename", "Invention");
                    }
                    startActivity(i);
                }
            }
        });
    }

    public void logout() {
        sp = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("logged");
        editor.commit();
        Intent i = new Intent(CategoryInList.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void helpDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Help");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        final TextView input = new TextView(this);
        input.setText("\nThis App is made for playing quiz game.You can choose to play in one of the " +
                "predefined categories or you can create your own category using 'Create Quiz' option." +
                "On creating a new category you can add questions by holding each created category.\n" +
                "\nEnjoy!!");
        input.setLayoutParams(lp);
        input.setPadding(30,5,30,5);
        alertDialog.setView(input);
        alertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }
}
