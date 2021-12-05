package com.example.redrevoraise;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.amplifyframework.datastore.generated.model.CompanyModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;

public class CompanyStats extends AppCompatActivity {
    private TextView companyTicker;
    private TextView companyRegion;
    private TextView currentPrice;
    private TextView priceDifference;
    private MaterialButton watchlist;
    private View status;
    private ImageView imageSwitcher;
    private ChipGroup chipGroup;
    private boolean isWatching = false;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        SharedPreferences sharedPrefs = getSharedPreferences("watchlist", 0);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        counter = sharedPrefs.getInt("index", 0);

        String tick = getIntent().getStringExtra("ticker");
        String region = getIntent().getStringExtra("region");
        String priceToday = getIntent().getStringExtra("priceToday");
        String priceYest = getIntent().getStringExtra("priceYest");

        CompanyModel company = CompanyModel.builder()
                .ticker(tick)
                .region(region)
                .priceToday(priceToday)
                .priceYest(priceYest)
                .build();

        String difference = getIntent().getStringExtra("difference");
        String percentage = getIntent().getStringExtra("percentage");

        for (int i = 0; i < counter; i++) {
            String lastIndex = "watcher_" + i;
            String check = sharedPrefs.getString(lastIndex, "");
            if (check.equals(company.getTicker())) {
                isWatching = true;
            }
        }

        companyTicker = findViewById(R.id.companyName);
        companyRegion = findViewById(R.id.companyRegionScreen);
        currentPrice = findViewById(R.id.currentPriceScreen);
        priceDifference = findViewById(R.id.priceDifferenceScreen);
        status = findViewById(R.id.status);
        watchlist = findViewById(R.id.watchlist_button);
        imageSwitcher = findViewById(R.id.graphs_flipper);
        chipGroup = findViewById(R.id.chipGroup);

        companyTicker.setText(company.getTicker());
        companyRegion.setText(company.getRegion());
        currentPrice.setText(new StringBuilder().append("$").append(company.getPriceToday()).toString());
        priceDifference.setText(new StringBuilder().append(difference).append(" (").append(percentage).append("%)").toString());


        if (isWatching) {
            watchlist.setText(getString(R.string.watchlist_on));
            watchlist.setIcon(getDrawable(R.drawable.icon_check));
        } else {
            watchlist.setText(getString(R.string.watchlist_off));
            watchlist.setIcon(getDrawable(R.drawable.icon_add));
        }

        CompanyModel finalCompany = company;
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                String photo = finalCompany.getTicker() + "_";
                String ph;
                int tag = chipGroup.getCheckedChipId();
                switch (tag){
                    case R.id.select_1:
                        ph = new StringBuilder().append(photo).append(1).toString();
                        imageSwitcher.setImageDrawable(Drawable.createFromPath("@drawable/"+ph));
                        break;
                    case R.id.select_2:
                        ph = new StringBuilder().append(photo).append(2).toString();
                        imageSwitcher.setImageDrawable(Drawable.createFromPath("@drawable/"+ph));
                        break;
                    case R.id.select_3:
                        ph = new StringBuilder().append(photo).append(3).toString();
                        imageSwitcher.setImageDrawable(Drawable.createFromPath("@drawable/"+ph));
                        break;
                    case R.id.select_4:
                        ph = new StringBuilder().append(photo).append(4).toString();
                        imageSwitcher.setImageDrawable(Drawable.createFromPath("@drawable/"+ph));
                        break;
                    default:
                        break;
                }
            }
        });

        if (Float.parseFloat(difference) < 0) {
            priceDifference.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.bad)));
            status.setBackgroundColor(getResources().getColor(R.color.bad));
        } else {
            priceDifference.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.good)));
            status.setBackgroundColor(getResources().getColor(R.color.good));
        }

        watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isWatching) {
                    watchlist.setText(getString(R.string.watchlist_on));
                    watchlist.setIcon(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.icon_check));
                    Snackbar.make(imageSwitcher, getString(R.string.watchlist_on_confirm), Snackbar.LENGTH_SHORT)
                            .show();
                    isWatching = true;

                    // Adding the string to watchlist
                    String lastIndex = "watcher_" + counter;
                    editor.putString(lastIndex, finalCompany.getTicker());
                    counter++;
                    editor.putInt("index", counter);
                    editor.commit();
                } else {
                    watchlist.setText(getString(R.string.watchlist_off));
                    watchlist.setIcon(getDrawable(R.drawable.icon_add));
                    Snackbar.make(imageSwitcher, getString(R.string.watchlist_off_confirm), Snackbar.LENGTH_SHORT)
                            .show();
                    isWatching = false;
                    for (int i = 0; i < counter; i++) {
                        String lastIndex = "watcher_" + i;
                        String check = sharedPrefs.getString(lastIndex, "");
                        if (check.equals(finalCompany.getTicker())) {
                            // This is so wrong, but it works for now
                            // TODO future me, please make this more efficient, thank you
                            editor.putString(lastIndex, "");
                            editor.commit();
                        }
                    }
                }
            }
        });
    }
}
