package com.farshidabz.spnote.view.ui.mainpage;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.farshidabz.spnote.R;
import com.farshidabz.spnote.flowcontroller.ActivityFactory;
import com.farshidabz.spnote.model.FolderModel;
import com.farshidabz.spnote.model.NoteModel;
import com.farshidabz.spnote.model.UserData;
import com.farshidabz.spnote.view.ui.mainpage.viewtypes.EmptyStateItem;
import com.farshidabz.spnote.view.ui.mainpage.viewtypes.FolderItem;
import com.farshidabz.spnote.view.ui.mainpage.viewtypes.NotesItem;
import com.farshidabz.spnote.view.ui.note.NoteActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.coderz.ghostadapter.GhostAdapter;

/**
 * Created by FarshidAbz.
 * Since 4/12/2017.
 */

public class MainPageActivity extends AppCompatActivity implements MainPageMvpView {
    @BindView(R.id.rvMainPage)
    RecyclerView rvMainPage;

    MainPageMvpPresenter mainPageMvpPresenter;

    GhostAdapter ghostAdapter;
    List<Object> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        ButterKnife.bind(this);

        mainPageMvpPresenter = new MainPagePresenter(this, getSupportFragmentManager());
        mainPageMvpPresenter.onAttach(this);

        initRvMainPage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPageMvpPresenter.getUserData();
    }

    private void initRvMainPage() {
        ghostAdapter = new GhostAdapter();
        items = new ArrayList<>();

        rvMainPage.setAdapter(ghostAdapter);
    }

    @OnClick(R.id.fabAddNewNote)
    public void onFabAddNewNoteClicked() {
        ActivityFactory.startActivity(this, NoteActivity.class.getSimpleName());
    }

    @Override
    public void showUserData(UserData userData) {
        rvMainPage.setLayoutManager(new GridLayoutManager(this, 3));
        ghostAdapter.removeAll();
        items.clear();

        for (FolderModel folderModel : userData.getFolderModel()) {
            FolderItem folderItem = new FolderItem(folderModel);
            items.add(folderItem);
        }

        for (NoteModel noteModel : userData.getNoteModel()) {
            NotesItem noteItem = new NotesItem(noteModel);
            noteItem.setOnItemLongClickListener((position, object) ->
                    mainPageMvpPresenter.onNoteLongClicked(position, (NoteModel) object));
            items.add(noteItem);
        }
        ghostAdapter.addItems(items);
    }

    @Override
    public void noteNameChanged(int position, NoteModel noteModel) {
        mainPageMvpPresenter.getUserData();
    }

    @Override
    public void noteRemoved(NoteModel noteModel) {
        mainPageMvpPresenter.getUserData();
    }

    @Override
    public void showEmptyState() {
        items.clear();
        ghostAdapter.removeAll();
        rvMainPage.setLayoutManager(new LinearLayoutManager(this));
        ghostAdapter.addItem(new EmptyStateItem());
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(@StringRes int resId) {
    }

    @Override
    public void onError(String message) {
    }
}