package com.anilkumarnishad.breakingnews.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import com.anilkumarnishad.breakingnews.Fragment.EducationFragment;
import com.anilkumarnishad.breakingnews.Fragment.PoliticsFragment;
import com.anilkumarnishad.breakingnews.Fragment.ScienceFragment;
import com.anilkumarnishad.breakingnews.Fragment.SearchViewFragment;
import com.anilkumarnishad.breakingnews.Fragment.SportFragment;
import com.anilkumarnishad.breakingnews.Fragment.TapNewsFragment;
import com.anilkumarnishad.breakingnews.R;
import com.anilkumarnishad.breakingnews.UtilMethods;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.myTablayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tabLayout.getSelectedTabPosition() == 0){
                    UtilMethods.ChangeFragmemt(new TapNewsFragment(), getSupportFragmentManager(), true);
                }else if(tabLayout.getSelectedTabPosition() == 1){
                    UtilMethods.ChangeFragmemt(new SportFragment(), getSupportFragmentManager(), true);
                }else if(tabLayout.getSelectedTabPosition() == 2){
                    UtilMethods.ChangeFragmemt(new EducationFragment(), getSupportFragmentManager(), true);
                }else if(tabLayout.getSelectedTabPosition() == 3){
                    UtilMethods.ChangeFragmemt(new ScienceFragment(), getSupportFragmentManager(), true);
                }else if(tabLayout.getSelectedTabPosition() == 4){
                    UtilMethods.ChangeFragmemt(new PoliticsFragment(), getSupportFragmentManager(), true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setupTabLayout();
    }

    private void setupTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("TopNews"),true);
        tabLayout.addTab(tabLayout.newTab().setText("Sport"));
        tabLayout.addTab(tabLayout.newTab().setText("Education"));
        tabLayout.addTab(tabLayout.newTab().setText("Science"));
        tabLayout.addTab(tabLayout.newTab().setText("Politics"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                LoadFragSearch(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
               // adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_about:
                return true;

            case R.id.action_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Breaking News");
               // shareIntent.putExtra(Intent.EXTRA_TEXT, url);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
                return true;

            case R.id.action_profile:
                Intent intent = new Intent(this,UserProfile.class);
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void LoadFragSearch(String query) {
        Bundle bundle = new Bundle();
        bundle.putString("query",query);
        SearchViewFragment searchViewFragment = new SearchViewFragment();
        searchViewFragment.setArguments(bundle);
        UtilMethods.ChangeFragmemt(searchViewFragment, getSupportFragmentManager(), true);
    }
}