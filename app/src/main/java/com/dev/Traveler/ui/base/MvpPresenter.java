package com.dev.Traveler.ui.base;

public interface MvpPresenter<V extends MvpView> {
    void onAttach(V MvpView);
}
