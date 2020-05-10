package com.alabamaor.hometest.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alabamaor.hometest.model.CountryModel;

public class SharedViewModel extends ViewModel {

    public final MutableLiveData<CountryModel> countryModelSelected = new MutableLiveData<>();


}
