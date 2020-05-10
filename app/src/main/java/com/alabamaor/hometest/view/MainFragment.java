package com.alabamaor.hometest.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alabamaor.hometest.R;
import com.alabamaor.hometest.model.CountryModel;
import com.alabamaor.hometest.model.Sort;
import com.alabamaor.hometest.viewModel.MainViewModel;
import com.alabamaor.hometest.viewModel.SharedViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment implements CountryListAdapter.CountryListItem {

    @BindView(R.id.mainCountriesList)
    RecyclerView countryList;
    @BindView(R.id.mainPbLoading)
    ProgressBar loading;
    @BindView(R.id.mainTvErrorMsg)
    TextView tvError;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.sortBy)
    Spinner sortBy;
    @BindView(R.id.sortDesc)
    Spinner sortDesc;


    private SharedViewModel sharedViewModel;
    private MainViewModel mainViewModel;
    private CountryListAdapter countryListAdapter = new CountryListAdapter(new ArrayList<>());
    private MainActivity activity;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i("alabama-->", "onViewCreated");


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i("alabama-->", "onActivityCreated");

        activity = (MainActivity) getActivity();


        ButterKnife.bind(this,activity);

        sharedViewModel = new ViewModelProvider(activity).get(SharedViewModel.class);
        mainViewModel = new ViewModelProvider(activity).get(MainViewModel.class);



        countryList.setAdapter(countryListAdapter);
        countryListAdapter.setCountryListItemListener(this::onCountryListItemSelected);

        refreshLayout.setOnRefreshListener(() -> {
           restart();
            refreshLayout.setRefreshing(false);
        });


        if ( mainViewModel.sortBy.getValue() == null || mainViewModel.sortByAsc.getValue() == null){
            mainViewModel.init();
        }

        sortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mainViewModel.sortBy.setValue(Sort.ByField.values()[position]);

                countryListAdapter.sort(mainViewModel.sortBy.getValue(), mainViewModel.sortByAsc.getValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                countryListAdapter.sort(mainViewModel.sortBy.getValue(), mainViewModel.sortByAsc.getValue());

            }
        });


        sortDesc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mainViewModel.sortByAsc.setValue(Sort.ByAscDesc.values()[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        observeViewModel();
    }

    private void restart(){

        mainViewModel.init();
        sortBy.setSelection(0);
        sortDesc.setSelection(0);

    }

    private void observeViewModel() {

        mainViewModel.mainCountriesList.observe(getViewLifecycleOwner(), countryModels -> {
            if (countryModels != null) {
                countryList.setVisibility(View.VISIBLE);
                countryListAdapter.update(countryModels);
                tvError.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.INVISIBLE);
                countryListAdapter.sort(mainViewModel.sortBy.getValue(),
                        mainViewModel.sortByAsc.getValue());
            }
        });

        mainViewModel.mainIsLoading.observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                if (isLoading) {
                    countryList.setVisibility(View.INVISIBLE);
                    tvError.setVisibility(View.INVISIBLE);
                    loading.setVisibility(View.VISIBLE);
                }
            }
        });


        mainViewModel.mainMsgError.observe(getViewLifecycleOwner(), loadError -> {
            if (loadError != null) {
                if (loadError) {
                    countryList.setVisibility(View.INVISIBLE);
                    tvError.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.INVISIBLE);
                }
            }
        });

        mainViewModel.sortByAsc.observe(getViewLifecycleOwner(), sort -> {
            if (sort != null) {
                countryListAdapter.sort(mainViewModel.sortBy.getValue(), sort);
            }
        });

        mainViewModel.sortBy.observe(getViewLifecycleOwner(), sort -> {
            if (sort != null) {
                countryListAdapter.sort(sort, mainViewModel.sortByAsc.getValue());
            }
        });


    }


    @Override
    public void onDetach() {
        super.onDetach();
        countryListAdapter.setCountryListItemListener(null);

    }

    @Override
    public void onCountryListItemSelected(View v, CountryModel selected) {
        sharedViewModel.countryModelSelected.setValue(selected);
        NavDirections action =
                MainFragmentDirections.actionToSelectFragment();
        Navigation.findNavController(v).navigate(action);

}

}
