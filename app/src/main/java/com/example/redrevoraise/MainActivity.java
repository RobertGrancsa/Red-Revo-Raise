package com.example.redrevoraise;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.CompanyModel;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewWatch;
    private SearchView searchBar;
    private ProgressBar progressIndicator;
    private TextView headerWatchlist;

    private List<String> keys = new ArrayList<String>();
    public final List<CompanyModel> companies = new ArrayList<CompanyModel>();
    private List<CompanyModel> companiesWatch = new ArrayList<CompanyModel>();
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
        headerWatchlist = findViewById(R.id.watchlist_text);
        searchBar.setQueryHint("Seach for example \"GOOG\"");

        progressIndicator.setVisibility(View.VISIBLE);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerViewWatch.setNestedScrollingEnabled(false);

        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
//            Amplify.addPlugin(new AWSCognitoAuthPlugin());
//            Amplify.addPlugin(new AWSS3StoragePlugin());

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }

        Amplify.DataStore.query(
                CompanyModel.class,
                items -> {
                    while (items.hasNext()) {
                        CompanyModel ceva = items.next();
                        Log.d(TAG, "onCreate: " + ceva.getTicker());

                        companies.add(items.next());
                        Log.i("Amplify", "Id " + companies.size());
                    }
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );
        Log.d(TAG, "onCreate: size " + companies.size());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new FirmsRecyclerview().setConfig(mRecyclerViewWatch, MainActivity.this, companiesWatch);
                new FirmsRecyclerview().setConfig(mRecyclerView, MainActivity.this, companies);
                updateWatching(sharedPrefs);
                sortListBy(companies, companiesWatch);
                progressIndicator.setVisibility(View.GONE);
            }
        }, 1000);
        Log.d(TAG, "onCreate: " + companies.size());

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                query = query.toLowerCase(Locale.ROOT);
                List<CompanyModel> companiestemp = new ArrayList<CompanyModel>();
                List<CompanyModel> companiestempWatch = new ArrayList<CompanyModel>();

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
                    new FirmsRecyclerview().setConfig(mRecyclerView, MainActivity.this, companiestemp);
                    new FirmsRecyclerview().setConfig(mRecyclerViewWatch, MainActivity.this, companiestempWatch);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
        if (companiesWatch.isEmpty()) {
            headerWatchlist.setVisibility(View.GONE);
        } else {
            headerWatchlist.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_refresh);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                sortListBy(companies, companiesWatch);
                new FirmsRecyclerview().setConfig(mRecyclerView, MainActivity.this, companies);
                new FirmsRecyclerview().setConfig(mRecyclerViewWatch, MainActivity.this, companiesWatch);
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
        sortListBy(companies, companiesWatch);
        new FirmsRecyclerview().setConfig(mRecyclerView, MainActivity.this, companies);
        new FirmsRecyclerview().setConfig(mRecyclerViewWatch, MainActivity.this, companiesWatch);
        super.onResume();
    }

    private void sortListBy(List<CompanyModel> companies, List<CompanyModel> companiesWatch){
        Spinner spinnerSort = findViewById(R.id.sortBy);
        Spinner spinnerWay = findViewById(R.id.upDown);

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new FirmsRecyclerview().setConfig(mRecyclerView, MainActivity.this, companies);
                new FirmsRecyclerview().setConfig(mRecyclerViewWatch, MainActivity.this, companiesWatch);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });
        spinnerWay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new FirmsRecyclerview().setConfig(mRecyclerView, MainActivity.this, companies);
                new FirmsRecyclerview().setConfig(mRecyclerViewWatch, MainActivity.this, companiesWatch);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        Collections.sort(companies, new Comparator<CompanyModel>(){
            public int compare(CompanyModel obj1, CompanyModel obj2) {
                // ## Ascending order
                Object selectedItem = spinnerSort.getSelectedItem();
                Object selectedItem1 = spinnerWay.getSelectedItem();
                Log.d(ContentValues.TAG, "compare: " + selectedItem1);
                Log.d(ContentValues.TAG, "compare: " + spinnerWay.getItemIdAtPosition(1));
                if (spinnerWay.getItemAtPosition(0).equals(selectedItem1)) {
                    if (spinnerSort.getItemAtPosition(0).equals(selectedItem)) {
                        return obj1.getTicker().compareToIgnoreCase(obj2.getTicker());
                    } else {
                        return Float.valueOf(obj1.getPriceToday()).compareTo(Float.valueOf(obj2.getPriceToday()));
                    }
                } else {
                    if (spinnerSort.getItemAtPosition(0).equals(selectedItem)) {
                        return obj2.getTicker().compareToIgnoreCase(obj1.getTicker());
                    } else {
                        return Float.valueOf(obj2.getPriceToday()).compareTo(Float.valueOf(obj1.getPriceToday()));
                    }
                }

            }
        });
    }
}