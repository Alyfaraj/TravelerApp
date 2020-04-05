package com.dev.Traveler.ui.enter;

import com.dev.Traveler.data.DataManager;
import com.dev.Traveler.ui.base.BasePresenter;

public class enterPresenter <V extends enterMvpView> extends BasePresenter<V>
        implements enterMvpPresenter {

    public enterPresenter(DataManager dataManager) {
        super(dataManager);
    }


}
