package com.dev.Traveler.ui.base;

import com.dev.Traveler.data.DataManager;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {
    DataManager mDataManager;
    private V mMvpView;
    public BasePresenter(DataManager dataManager)
    {
        mDataManager=dataManager;
    }
        @Override
        public void onAttach(V MvpView) {

        mMvpView=MvpView;

    }
    public V getMvpView(){
        return mMvpView;
    }
    public DataManager getDataManager()
    {
        return mDataManager;
    }
}