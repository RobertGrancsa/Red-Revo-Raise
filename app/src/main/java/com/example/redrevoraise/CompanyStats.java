package com.example.redrevoraise;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CompanyStats extends AppCompatActivity {
    private TextView companyTicker;
    private TextView companyRegion;
    private TextView currentPrice;
    private TextView priceDifference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        Bundle bundle = this.getIntent().getExtras();
        Company company = null;

        if (bundle != null) {
            company = (Company) bundle.getSerializable("company");
        }
        String difference = getIntent().getStringExtra("difference");
        String percentage = getIntent().getStringExtra("percentage");

//        company = getIntent().getParcelableExtra("company");

        companyTicker = findViewById(R.id.companyName);
        companyRegion = findViewById(R.id.companyRegionScreen);
        currentPrice = findViewById(R.id.currentPriceScreen);
        priceDifference = findViewById(R.id.priceDifferenceScreen);

//        currentPrice = findViewById();

        companyTicker.setText(company.getTicker());
        companyRegion.setText(company.getRegion());
        currentPrice.setText(company.getRegularMarketPrice());
        priceDifference.setText(new StringBuilder().append(difference).append(" (").append(percentage).append("%)").toString());

        if (Float.parseFloat(difference) < 0) {
            priceDifference.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        } else {
            priceDifference.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green)));
        }
    }
}
