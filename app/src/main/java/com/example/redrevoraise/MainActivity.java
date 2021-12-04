package com.example.redrevoraise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private SearchView searchBar;
    private ProgressBar progressIndicator;

    private List<String> keys = new ArrayList<String>();
    private List<Company> companies = new ArrayList<Company>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressIndicator = findViewById(R.id.progressBar);
        mRecyclerView = findViewById(R.id.recyclerView);

        progressIndicator.setVisibility(View.VISIBLE);


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

        Company company_1 = new Company();
        company_1.setTicker("TSLA");
        company_1.setRegularMarketPrice("1000");
        company_1.setRegularMarketYest("1100");
        company_1.setRegion("US");

        Company company_2 = new Company();
        company_2.setTicker("GOOG");
        company_2.setRegularMarketPrice("500");
        company_2.setRegularMarketYest("400");
        company_2.setRegion("US");

        Company company_3 = new Company();
        company_3.setTicker("GOOG");
        company_3.setRegularMarketPrice("500");
        company_3.setRegularMarketYest("400");
        company_3.setRegion("US");

        Company company_4 = new Company();
        company_4.setTicker("GOOG");
        company_4.setRegularMarketPrice("500");
        company_4.setRegularMarketYest("400");
        company_4.setRegion("US");

        Company company_5 = new Company();
        company_5.setTicker("GOOG");
        company_5.setRegularMarketPrice("500");
        company_5.setRegularMarketYest("400");
        company_5.setRegion("US");

        Company company_6 = new Company();
        company_6.setTicker("GOOG");
        company_6.setRegularMarketPrice("500");
        company_6.setRegularMarketYest("400");
        company_6.setRegion("US");

        companies.add(company_1);
        companies.add(company_2);
        companies.add(company_3);
        companies.add(company_4);
        companies.add(company_5);
        companies.add(company_6);



        new FirmsRecyclerview().setConfig(mRecyclerView, MainActivity.this, companies, keys);
        progressIndicator.setVisibility(View.GONE);

    }
}