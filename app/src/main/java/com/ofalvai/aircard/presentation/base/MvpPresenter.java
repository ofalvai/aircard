package com.ofalvai.aircard.presentation.base;

public interface MvpPresenter<V extends MvpView> {

    void attachView(V view);

    void detachView();
}
