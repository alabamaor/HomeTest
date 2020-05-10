package com.alabamaor.hometest.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alabamaor.hometest.model.CountriesService;
import com.alabamaor.hometest.model.CountryModel;
import com.alabamaor.hometest.model.Sort;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    public MutableLiveData<List<CountryModel>> mainCountriesList = new MutableLiveData<>();
    public MutableLiveData<Boolean> mainIsLoading = new MutableLiveData<>();
    public MutableLiveData<Boolean> mainMsgError = new MutableLiveData<>();


    public MutableLiveData<Sort.ByField> sortBy = new MutableLiveData<>();
    public MutableLiveData<Sort.ByAscDesc> sortByAsc = new MutableLiveData<>();


    private CountriesService countriesService = CountriesService.getInstance();
    private CompositeDisposable disposable = new CompositeDisposable();


    public void init() {
        sortBy.setValue(Sort.ByField.NAME);
        sortByAsc.setValue(Sort.ByAscDesc.ASC);
        refresh();
    }



    public void refresh() {
        fetchCountries();
    }

    private void fetchCountries() {

        mainIsLoading.setValue(true);

        disposable.add(

                countriesService.getAllCountries()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<CountryModel>>() {
                            @Override
                            public void onSuccess(List<CountryModel> countryModels) {
                                mainCountriesList.setValue(countryModels);
                                mainIsLoading.setValue(false);
                                mainMsgError.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                mainIsLoading.setValue(false);
                                mainMsgError.setValue(true);
                            }
                        })

        );



    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

}
