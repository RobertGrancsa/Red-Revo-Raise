package com.example.redrevoraise;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewWatch;
    private SearchView searchBar;
    private ProgressBar progressIndicator;

    private List<String> keys = new ArrayList<String>();
    private List<Company> companies = new ArrayList<Company>();
    private List<Company> companiesWatch = new ArrayList<Company>();
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPrefs = getSharedPreferences("watchlist", 0);
        index = sharedPrefs.getInt("index", 0);

        progressIndicator = findViewById(R.id.progressBar);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerViewWatch = findViewById(R.id.recyclerViewWatching);
        searchBar = findViewById(R.id.search_bar_home);
        searchBar.setQueryHint("Seach for example \"GOOG\"");

        progressIndicator.setVisibility(View.VISIBLE);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerViewWatch.setNestedScrollingEnabled(false);

        try {
            //Amplify.addPlugin(new AWSApiPlugin()); // UNCOMMENT this line once backend is deployed
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());
            Log.i("Amplify", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("Amplify", "Could not initialize Amplify", error);
        }

        keys.add("key1");
        keys.add("key2");
        keys.add("key3");
        keys.add("key4");
        keys.add("key5");
        keys.add("key6");
        keys.add("key7");
        keys.add("key8");
        keys.add("key9");
        keys.add("key10");

        Company company_1 = new Company();
        company_1.setTicker("TSLA");
        company_1.setPriceToday("1000");
        company_1.setPriceYest("1100");
        company_1.setRegion("US");

        Company company_2 = new Company();
        company_2.setTicker("GOOG");
        company_2.setPriceToday("500");
        company_2.setPriceYest("400");
        company_2.setRegion("US");

        Company company_3 = new Company();
        company_3.setTicker("INTL");
        company_3.setPriceToday("800");
        company_3.setPriceYest("400");
        company_3.setRegion("US");

        Company company_4 = new Company();
        company_4.setTicker("AMZN");
        company_4.setPriceToday("1400");
        company_4.setPriceYest("400");
        company_4.setRegion("US");

        Company company_5 = new Company();
        company_5.setTicker("NVDA");
        company_5.setPriceToday("1000");
        company_5.setPriceYest("400");
        company_5.setRegion("US");

        Company company_6 = new Company();
        company_6.setTicker("CSF");
        company_6.setPriceToday("500");
        company_6.setPriceYest("800");
        company_6.setRegion("US");

        companies.add(company_1);
        companies.add(company_2);
        companies.add(company_3);
        companies.add(company_4);
        companies.add(company_5);
        companies.add(company_6);

        new FirmsRecyclerview().setConfig(mRecyclerViewWatch, MainActivity.this, companiesWatch, keys);
        new FirmsRecyclerview().setConfig(mRecyclerView, MainActivity.this, companies, keys);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                query = query.toLowerCase(Locale.ROOT);
                List<Company> companiestemp = new ArrayList<Company>();
                List<Company> companiestempWatch = new ArrayList<Company>();

                for (int i = 0; i < companies.size(); i++) {
                    String ticker = companies.get(i).getTicker().toLowerCase(Locale.ROOT);
                    if (ticker.contains(query)) {
                        companiestemp.add(companies.get(i));
                    }
                }
                for (int i = 0; i < companiesWatch.size(); i++) {
                    String ticker = companiesWatch.get(i).getTicker().toLowerCase(Locale.ROOT);
                    if (ticker.contains(query)) {
                        companiestempWatch.add(companies.get(i));
                    }
                }

                if (companiestemp.isEmpty() && companiestempWatch.isEmpty()) {
                    Snackbar snackbar = Snackbar
                            .make(mRecyclerView, "No firm named " + query + " found", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {
                    new FirmsRecyclerview().setConfig(mRecyclerView, MainActivity.this, companiestemp, keys);
                    new FirmsRecyclerview().setConfig(mRecyclerViewWatch, MainActivity.this, companiestempWatch, keys);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        progressIndicator.setVisibility(View.GONE);

    }

    private void updateWatching(SharedPreferences sharedPrefs) {
        for (int i = 0; i < index; i++) {
            for (int j = 0; j < companies.size(); j++) {
                String lastIndex = "watcher_" + i;
                String check = sharedPrefs.getString(lastIndex, "");
                if (check.equals(companies.get(j).getTicker())) {
                    companiesWatch.add(companies.get(j));
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_refresh);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new FirmsRecyclerview().setConfig(mRecyclerView, MainActivity.this, companies, keys);
                new FirmsRecyclerview().setConfig(mRecyclerViewWatch, MainActivity.this, companiesWatch, keys);
                searchBar.setQuery("", true);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        companiesWatch.clear();
        SharedPreferences sharedPrefs = getSharedPreferences("watchlist", 0);
        index = sharedPrefs.getInt("index", 0);
        Log.d(TAG, "onResume: " + index);
        updateWatching(sharedPrefs);
        Log.d(TAG, "onResume: Im back bitches");
        super.onResume();
    }
}