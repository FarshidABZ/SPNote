package com.farshidabz.supernote.view.ui.mainpage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.farshidabz.supernote.R;
import com.farshidabz.supernote.model.UserData;
import com.farshidabz.supernote.presenter.impls.RecoveryAndBackupPresenterImpl;
import com.farshidabz.supernote.presenter.presenter.RecoveryAndBackupPresenter;
import com.farshidabz.supernote.view.ui.mainpage.viewtypes.EmptyStateHolder;
import com.farshidabz.supernote.view.ui.mainpage.viewtypes.EmptyStateItem;
import com.farshidabz.supernote.view.views.mainpage.MainPageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.coderz.coreadapter.CoreAdapter;
import ir.coderz.coreadapter.CoreItem;

/**
 * Created by FarshidAbz.
 * Since 4/12/2017.
 */

public class MainPageActivity extends AppCompatActivity implements MainPageView {
    @BindView(R.id.rvMainPage)
    RecyclerView rvMainPage;

    CoreAdapter coreAdapter;
    List<CoreItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        ButterKnife.bind(this);

        initRvMainPage();
        getUserNotes();
    }

    private void getUserNotes() {
        RecoveryAndBackupPresenter recoveryAndBackupPresenter = new RecoveryAndBackupPresenterImpl(this, this);
        recoveryAndBackupPresenter.getUserData();
    }

    private void initRvMainPage() {
        initCoreAdapter();

        rvMainPage.setAdapter(coreAdapter);
        rvMainPage.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void initCoreAdapter() {
        coreAdapter = new CoreAdapter();
        items = new ArrayList<>();

        putViewTypesToCoreAdapter();
    }

    private void putViewTypesToCoreAdapter() {
        coreAdapter.putViewType(R.layout.content_empty_state, EmptyStateHolder.class);
    }

    @OnClick(R.id.fabAddNewNote)
    public void onFabAddNewNoteClicked() {

    }

    @Override
    public void showUserData(UserData userData) {
    }

    @Override
    public void showEmptyState() {
        rvMainPage.setLayoutManager(new LinearLayoutManager(this));

        items.add(new EmptyStateItem());
        coreAdapter.addItems(items);
        items.clear();
    }
}