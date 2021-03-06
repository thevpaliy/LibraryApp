package com.vpaliy.mvp.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import com.vpaliy.mvp.App;
import com.vpaliy.mvp.R;
import com.vpaliy.mvp.view.fragment.BookDetailsFragment;
import com.vpaliy.mvp.view.fragment.UserDetailsFragment;
import com.vpaliy.mvp.view.utils.Constant;
import com.vpaliy.mvp.view.utils.Permission;

public class DetailsActivity extends BaseActivity {

    private int actionCode= Constant.USER_DETAILS;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //postpone the transition
        if(Permission.checkForVersion(Build.VERSION_CODES.LOLLIPOP)){
            postponeEnterTransition();
        }

        //
        final boolean hasBeenRecreated=savedInstanceState!=null;
        if(!hasBeenRecreated) {
            savedInstanceState=getIntent().getExtras();
        }
        actionCode=savedInstanceState.getInt(Constant.ACTION);
        if(!hasBeenRecreated) {
            int ID=savedInstanceState.getInt(Constant.ID);
            setUp(ID);
        }

    }

    /**
     * Sets up a fragment that represent the details of previously selected item
     * @param ID the ID of the entity that should be displayed
     */
    private void setUp(int  ID) {
        FragmentManager manager=getSupportFragmentManager();
        switch (actionCode) {
            case Constant.USER_DETAILS:
                manager.beginTransaction()
                        .add(R.id.fragment, UserDetailsFragment.newInstance(ID))
                        .commit();
                break;
            case Constant.BOOK_DETAILS:
                manager.beginTransaction()
                        .add(R.id.fragment, BookDetailsFragment.newInstance(ID))
                        .commit();
                break;
        }
    }

    @Override
    void initializeInjector() {
        App.app().provideAppComponent()
                .inject(this);
    }

    @Override
    void register() {
        eventBus.register(this);
    }

    @Override
    void unregister() {
        eventBus.unregister(this);
    }



}
