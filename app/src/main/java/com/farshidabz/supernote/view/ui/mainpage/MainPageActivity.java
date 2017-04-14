package com.farshidabz.supernote.view.ui.mainpage;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.farshidabz.supernote.R;
import com.farshidabz.supernote.flowcontroller.ActivityFactory;
import com.farshidabz.supernote.model.FolderModel;
import com.farshidabz.supernote.model.NoteModel;
import com.farshidabz.supernote.model.UserData;
import com.farshidabz.supernote.presenter.impls.RecoveryAndBackupPresenterImpl;
import com.farshidabz.supernote.presenter.presenter.RecoveryAndBackupPresenter;
import com.farshidabz.supernote.view.ui.drawing.DrawingActivity;
import com.farshidabz.supernote.view.ui.mainpage.viewtypes.EmptyStateItem;
import com.farshidabz.supernote.view.ui.mainpage.viewtypes.FolderItem;
import com.farshidabz.supernote.view.ui.mainpage.viewtypes.NotesItem;
import com.farshidabz.supernote.view.ui.note.TextNoteActivity;
import com.farshidabz.supernote.view.views.mainpage.MainPageView;

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

public class MainPageActivity extends AppCompatActivity implements MainPageView, OnFabClickListener {
    @BindView(R.id.rvMainPage)
    RecyclerView rvMainPage;

    @BindView(R.id.fabDrawingMode)
    FloatingActionButton fabDrawingMode;
    @BindView(R.id.fabAddNewNote)
    FloatingActionButton fabAddNewNote;
    @BindView(R.id.fabTextMode)
    FloatingActionButton fabTextMode;

    GhostAdapter ghostAdapter;
    List<Object> items;
    private FabHandler fabHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        ButterKnife.bind(this);

        fabHandler = new FabHandler(this, this, fabTextMode, fabDrawingMode);

        initRvMainPage();
        getUserNotes();
    }

    private void getUserNotes() {
        RecoveryAndBackupPresenter recoveryAndBackupPresenter = new RecoveryAndBackupPresenterImpl(this, this);
        recoveryAndBackupPresenter.getUserData();
    }

    private void initRvMainPage() {
        ghostAdapter = new GhostAdapter();
        items = new ArrayList<>();

        rvMainPage.setAdapter(ghostAdapter);
    }

    @OnClick(R.id.fabAddNewNote)
    public void onFabAddNewNoteClicked() {
        fabHandler.fabClicked();
        fabHandler.hideSpecificFab(fabAddNewNote);
        fabHandler.showSpecificFab(fabAddNewNote);
    }

    @Override
    public void showUserData(UserData userData) {
        rvMainPage.setLayoutManager(new GridLayoutManager(this, 3));
        ghostAdapter.removeAll();

        for (FolderModel folderModel : userData.getFolderModel()) {
            FolderItem folderItem = new FolderItem(folderModel);
            items.add(folderItem);
        }

        for (NoteModel noteModel : userData.getNoteModel()) {
            NotesItem notesItem = new NotesItem(noteModel);
            items.add(notesItem);
        }
        ghostAdapter.addItems(items);
    }

    @Override
    public void showEmptyState() {
        rvMainPage.setLayoutManager(new LinearLayoutManager(this));
        ghostAdapter.addItem(new EmptyStateItem());
    }

    @Override
    public void onFabClicked(int id) {
        Bundle bundle = new Bundle();

        switch (id) {
            case R.id.fabTextMode:
                ActivityFactory.startActivity(this, TextNoteActivity.class.getSimpleName());
                break;
            case R.id.fabDrawingMode:
                ActivityFactory.startActivity(this, DrawingActivity.class.getSimpleName());
                break;
        }
    }
}