package com.alabamaor.hometest.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alabamaor.hometest.model.CountriesService;
import com.alabamaor.hometest.model.CountryModel;
import com.alabamaor.hometest.model.ErrorMsg;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SelectedViewModel extends ViewModel {


    public MutableLiveData<List<CountryModel>> selectedCountriesList = new MutableLiveData<>();
    public MutableLiveData<Boolean> selectedIsLoading = new MutableLiveData<>();
    public MutableLiveData<ErrorMsg> selectedMsgError = new MutableLiveData<>();


    private CountriesService countriesService = CountriesService.getInstance();
    private CompositeDisposable disposable = new CompositeDisposable();


    public void initWithSelected(List<String> codes) {
        refreshSelected(codes);
    }


    public void refreshSelected(List<String> codes) {
        fetchCountriesSelected(codes);
    }


    private void fetchCountriesSelected(List<String> codes) {

        selectedIsLoading.setValue(true);
        if ((codes.size() == 0)) {
            selectedMsgError.setValue(ErrorMsg.NO_DATA);
            selectedIsLoading.setValue(false);
        }
        else {
            disposable.add(
                    countriesService.getCountriesByCodes(codes)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableSingleObserver<List<CountryModel>>() {
                                @Override
                                public void onSuccess(List<CountryModel> countryModels) {
                                    selectedIsLoading.setValue(false);

                                    selectedCountriesList.setValue(countryModels);
                                    selectedMsgError.setValue(ErrorMsg.NO_ERROR);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    selectedIsLoading.setValue(false);
                                    selectedMsgError.setValue(ErrorMsg.NO_CONNECTION);
                                }
                            }));

        }

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null)
        disposable.clear();
    }
}
