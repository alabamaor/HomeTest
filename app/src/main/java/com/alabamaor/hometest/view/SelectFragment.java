package com.alabamaor.hometest.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alabamaor.hometest.R;
import com.alabamaor.hometest.viewModel.SelectedViewModel;
import com.alabamaor.hometest.viewModel.SharedViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectFragment extends Fragment {

    @BindView(R.id.selectedBtnBack)
    AppCompatButton btnBack;

    @BindView(R.id.selectedCountriesList)
    RecyclerView countryList;

    @BindView(R.id.selectedPbLoading)
    ProgressBar loading;

    @BindView(R.id.selectedTvErrorMsg)
    TextView tvError;

    @BindView(R.id.selectSwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;


    @BindView(R.id.selectedTvNativeName)
    TextView nativeName;
    @BindView(R.id.selectedName)
    TextView name;

    private SharedViewModel sharedViewModel;
    private SelectedViewModel selectedViewModel;

    private CountryListAdapter countryListAdapter = new CountryListAdapter(new ArrayList<>());
    private MainActivity activity;


    public SelectFragment() {
        // Required empty public constructor
    }

    public static SelectFragment newInstance() {
        SelectFragment fragment = new SelectFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity)getActivity();

        sharedViewModel = new ViewModelProvider(activity).get(SharedViewModel.class);
        selectedViewModel = new ViewModelProvider(activity).get(SelectedViewModel.class);

        btnBack.setOnClickListener(v -> {
            NavDirections action =
                    SelectFragmentDirections.actionBack();
            Navigation.findNavController(v).navigate(action);

        });
        name.setText(sharedViewModel.countryModelSelected.getValue().getName());
        nativeName.setText(sharedViewModel.countryModelSelected.getValue().getNativeName());
//        selectedViewModel.initWithSelected(sharedViewModel.countryModelSelected.getValue().getBorders());

        countryList.setAdapter(countryListAdapter);

        refreshLayout.setOnRefreshListener(() -> {
            selectedViewModel.initWithSelected(sharedViewModel.countryModelSelected.getValue().getBorders());
            refreshLayout.setRefreshing(false);
        });


        observeViewModel();
    }


    private void observeViewModel() {

        selectedViewModel.selectedCountriesList.observe(getViewLifecycleOwner(), countryModels -> {
            if (countryModels != null) {

                countryList.setVisibility(View.VISIBLE);
                countryListAdapter.update(countryModels);
                tvError.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.INVISIBLE);
                btnBack.setVisibility(View.VISIBLE);


            }
        });

        selectedViewModel.selectedIsLoading.observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                if (isLoading) {
                    countryList.setVisibility(View.INVISIBLE);
                    btnBack.setVisibility(View.INVISIBLE);
                    tvError.setVisibility(View.INVISIBLE);
                    loading.setVisibility(View.VISIBLE);
                }
            }
        });


        selectedViewModel.selectedMsgError.observe(getViewLifecycleOwner(), error -> {
            if (error != null) {

                switch (error) {

                    case NO_CONNECTION:
                    errorDisplay(getResources().getString(R.string.msg_loading_error));
                        break;
                    case NO_DATA:
                        errorDisplay(getResources().getString(R.string.msg_no_data));
                        break;
                    case NO_ERROR:
                        countryList.setVisibility(View.VISIBLE);
                        tvError.setVisibility(View.INVISIBLE);
                        loading.setVisibility(View.INVISIBLE);
                        btnBack.setVisibility(View.VISIBLE);

                        break;
                }
            }
        });


        sharedViewModel.countryModelSelected.observe(getViewLifecycleOwner(), countryModel -> {
            selectedViewModel.initWithSelected(countryModel.getBorders());

        });

    }

    private void errorDisplay(String str){
        countryList.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.INVISIBLE);
        tvError.setText(str);
        tvError.setVisibility(View.VISIBLE);
        btnBack.setVisibility(View.VISIBLE);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
