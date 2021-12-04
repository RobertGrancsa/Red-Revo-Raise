package com.example.redrevoraise;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public class FirmsRecyclerview {
    private Context mContext;
    private FirmsRecyclerview.CompanyAdapter mCompanyAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Company> company, List<String> keys){
        mContext = context;
        mCompanyAdapter = new FirmsRecyclerview.CompanyAdapter(company, keys);
        mCompanyAdapter.setHasStableIds(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new FirmsRecyclerview.CompanyAdapter(company, keys));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    class CompanyItemView extends RecyclerView.ViewHolder{

        private TextView companyTicker;
        private TextView companyRegion;
        private TextView currentPrice;
        private TextView priceDifference;
        private RelativeLayout backgroundCompany;
        private float priceTodayFloat, priceYesterdayFloat;
        private String key;


        public CompanyItemView(@NonNull ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.firm_layout, parent, false));

            companyTicker = itemView.findViewById(R.id.companyTicker);
            companyRegion = itemView.findViewById(R.id.companyRegion);
            currentPrice = itemView.findViewById(R.id.currentPrice);
            priceDifference = itemView.findViewById(R.id.priceDifference);
            backgroundCompany = itemView.findViewById(R.id.backgroundCompany);
        }
        public void bind(Company company, String key){
            priceTodayFloat = Float.parseFloat(company.getRegularMarketPrice());
            priceYesterdayFloat = Float.parseFloat(company.getRegularMarketYest());

            float diff = priceTodayFloat - priceYesterdayFloat;
            String difference = Float.toString(diff);

            float per = priceTodayFloat / priceYesterdayFloat * 100;
            String percentage = Float.toString(per);

            companyTicker.setText(company.getTicker());
            companyRegion.setText(company.getRegion());
            currentPrice.setText(company.getRegularMarketPrice());
            priceDifference.setText(new StringBuilder().append(difference).append(" (").append(percentage).append("%)").toString());

            if (diff < 0) {
                priceDifference.setTextColor(ColorStateList.valueOf(mContext.getResources().getColor(R.color.colorAccent)));
            } else {
                priceDifference.setTextColor(ColorStateList.valueOf(mContext.getResources().getColor(R.color.green)));
            }


            backgroundCompany.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CompanyStats.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("company", company);
                    intent.putExtras(bundle);
                    intent.putExtra("difference", difference);
                    intent.putExtra("percentage", percentage);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,
                            Pair.create(backgroundCompany, "background"), Pair.create(companyTicker, "ticker"),
                            Pair.create(companyRegion, "region"));
                    mContext.startActivity(intent, options.toBundle());
                }
            });
            this.key = key;
        }
    }

    class CompanyAdapter extends RecyclerView.Adapter<CompanyItemView>{
        private List<Company> mCompanyList;
        private List<String> mKeys;

        public CompanyAdapter(List<Company> mCompanyList, List<String> mKeys) {
            this.mCompanyList = mCompanyList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public CompanyItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CompanyItemView(parent);
        }

        @Override
        public long getItemId(int position) {
            if (position < mCompanyList.size()){
                mCompanyList.get(position).getTicker();
            }
            return RecyclerView.NO_ID;
        }

        @Override
        public void onBindViewHolder(@NonNull CompanyItemView holder, int position) {
            holder.bind(mCompanyList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mCompanyList.size();
        }
    }
}

