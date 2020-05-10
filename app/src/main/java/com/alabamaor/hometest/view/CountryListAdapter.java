package com.alabamaor.hometest.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alabamaor.hometest.R;
import com.alabamaor.hometest.model.CountryModel;
import com.alabamaor.hometest.model.Sort;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryViewHolder> {

    private CountryListItem countryListItemListener;

    private List<CountryModel> countries;


    public CountryListAdapter(List<CountryModel> countries) {
        this.countryListItemListener = null;
        this.countries = countries;
    }

    public void update(List<CountryModel> newCountries) {
        this.countries.clear();
        this.countries.addAll(newCountries);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from
                (parent.getContext()).inflate(
                R.layout.country_list_tile, parent,
                false);


        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {

        holder.itemView.setOnClickListener(v -> {
            if (countryListItemListener != null)
                countryListItemListener.onCountryListItemSelected(holder.itemView, countries.get(position));
        });
        holder.bind(countries.get(position));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }


    public void sort(Sort.ByField sortBy, Sort.ByAscDesc sortDesc) {


        switch (sortDesc) {
            case ASC:
                ///^^^
                switch (sortBy) {
                    case NAME:
                        Collections.sort(countries, (o1, o2) -> o1.getName().compareTo(o2.getName()));
                        break;
                    case AREA:
                        Collections.sort(countries, (o1, o2) -> Double.compare(o1.getArea(), o2.getArea()));
                        break;
                }
                break;
            case DESC:
                switch (sortBy) {
                    case NAME:
                        Collections.sort(countries, (o2, o1) -> o1.getName().compareTo(o2.getName()));
                        break;
                    case AREA:
                        Collections.sort(countries, (o2, o1) -> Double.compare(o1.getArea(), o2.getArea()));
                        break;
                }
                break;
        }
        notifyDataSetChanged();
    }

    public CountryListItem getCountryListItemListener() {
        return countryListItemListener;
    }

    public CountryListAdapter setCountryListItemListener(CountryListItem countryListItemListener) {
        this.countryListItemListener = countryListItemListener;
        return this;
    }

    public interface CountryListItem {
        void onCountryListItemSelected(View v, CountryModel selectedCountry);
    }

    class CountryViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.TileTvCountryEnglishName)
        TextView englishName;
        @BindView(R.id.TileTvCountryNativeName)
        TextView nativeName;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(CountryModel countryModel) {
            englishName.setText(countryModel.getName());
            nativeName.setText(countryModel.getNativeName());
        }
    }


}

