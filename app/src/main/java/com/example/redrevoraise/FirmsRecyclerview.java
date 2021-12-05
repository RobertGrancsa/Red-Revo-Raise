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

import com.amplifyframework.datastore.generated.model.CompanyModel;

import java.io.Serializable;
import java.util.List;

public class FirmsRecyclerview {
    private Context mContext;
    private FirmsRecyclerview.CompanyAdapter mCompanyAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<CompanyModel> company){
        mContext = context;
        mCompanyAdapter = new FirmsRecyclerview.CompanyAdapter(company);
        mCompanyAdapter.setHasStableIds(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new FirmsRecyclerview.CompanyAdapter(company));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    class CompanyItemView extends RecyclerView.ViewHolder{

        private TextView companyTicker;
        private TextView companyRegion;
        private TextView currentPrice;
        private TextView priceDifference;
        private RelativeLayout backgroundCompany;
        private float priceTodayFloat, priceYesterdayFloat;
        private View status;


        public CompanyItemView(@NonNull ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.firm_layout, parent, false));

            companyTicker = itemView.findViewById(R.id.companyTicker);
            companyRegion = itemView.findViewById(R.id.companyRegion);
            currentPrice = itemView.findViewById(R.id.currentPrice);
            priceDifference = itemView.findViewById(R.id.priceDifference);
            backgroundCompany = itemView.findViewById(R.id.backgroundCompany);
            status = itemView.findViewById(R.id.status_home);
        }
        public void bind(CompanyModel company){
            priceTodayFloat = Float.parseFloat(company.getPriceToday());
            priceYesterdayFloat = Float.parseFloat(company.getPriceYest());

            float diff = priceTodayFloat - priceYesterdayFloat;
            String difference = String.format("%.2f", diff);

            float per = diff / priceYesterdayFloat * 100;
            String percentage = String.format("%.2f", per);

            companyTicker.setText(company.getTicker());
            companyRegion.setText(company.getRegion());
            currentPrice.setText(new StringBuilder().append("$").append(company.getPriceToday()).toString());
            priceDifference.setText(new StringBuilder().append(difference).append(" (").append(percentage).append("%)").toString());

            if (diff < 0) {
                priceDifference.setTextColor(ColorStateList.valueOf(mContext.getResources().getColor(R.color.bad)));
                status.setBackgroundColor(mContext.getResources().getColor(R.color.bad));
            } else {
                priceDifference.setTextColor(ColorStateList.valueOf(mContext.getResources().getColor(R.color.good)));
                status.setBackgroundColor(mContext.getResources().getColor(R.color.good));
                priceDifference.setText(new StringBuilder().append("+").append(difference).append(" (").append(percentage).append("%)").toString());
            }


            backgroundCompany.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CompanyStats.class);
                    intent.putExtra("ticker", company.getTicker());
                    intent.putExtra("region", company.getRegion());
                    intent.putExtra("priceToday", company.getPriceToday());
                    intent.putExtra("priceYest", company.getPriceYest());
                    intent.putExtra("difference", difference);
                    intent.putExtra("percentage", percentage);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,
                            Pair.create(companyTicker, "ticker"), Pair.create(companyRegion, "region"),
                            Pair.create(currentPrice, "price"), Pair.create(priceDifference, "percentage"));
                    mContext.startActivity(intent, options.toBundle());
                }
            });
        }
    }

    class CompanyAdapter extends RecyclerView.Adapter<CompanyItemView>{
        private List<CompanyModel> mCompanyList;

        public CompanyAdapter(List<CompanyModel> mCompanyList) {
            this.mCompanyList = mCompanyList;
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
            holder.bind(mCompanyList.get(position));
        }

        @Override
        public int getItemCount() {
            return mCompanyList.size();
        }
    }
}

