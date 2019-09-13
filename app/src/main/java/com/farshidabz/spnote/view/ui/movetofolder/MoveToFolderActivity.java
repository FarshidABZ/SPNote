package com.farshidabz.spnote.view.ui.movetofolder;

import android.os.Bundle;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.farshidabz.spnote.R;
import com.farshidabz.spnote.model.FolderModel;
import com.farshidabz.spnote.view.ui.movetofolder.viewtypes.FolderEmptyStateItem;
import com.farshidabz.spnote.view.ui.movetofolder.viewtypes.FolderListItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.coderz.ghostadapter.GhostAdapter;

public class MoveToFolderActivity extends AppCompatActivity implements MoveToFolderMvpView {
    @BindView(R.id.moveNoteActivityToolbar)
    Toolbar moveNoteActivityToolbar;

    @BindView(R.id.rvFolderList)
    RecyclerView rvFolderList;

    @BindView(R.id.imgNewFolder)
    ImageView imgNewFolder;
    @BindView(R.id.tvMoveNoteTo)
    TextView tvMoveNoteTo;

    MoveToFolderMvpPresenter moveToFolderMvpPresenter;

    GhostAdapter ghostAdapter;
    ArrayList<Object> items;
    private int noteId;
    private String noteTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_to_folder);

        ButterKnife.bind(this);

        moveToFolderMvpPresenter = new MoveToFolderPresenter(this, getSupportFragmentManager());
        moveToFolderMvpPresenter.onAttach(this);

        initToolbar();
        initRecyclerView();

        getIntentData();

        moveToFolderMvpPresenter.getFolders();
    }

    private void getIntentData() {
        noteId = getIntent().getIntExtra("noteId", -1);
        noteTitle = getIntent().getStringExtra("noteTitle");

        tvMoveNoteTo.setText(getString(R.string.move_note_to, noteTitle));
    }

    private void initToolbar() {
        setSupportActionBar(moveNoteActivityToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white);
    }

    private void initRecyclerView() {
        ghostAdapter = new GhostAdapter();
        items = new ArrayList<>();

        rvFolderList.setAdapter(ghostAdapter);
        rvFolderList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.imgNewFolder)
    public void onImgNewFolderClicked() {
        moveToFolderMvpPresenter.onNewFolderClicked();
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

    @Override
    public void showEmptyState() {
        items.clear();
        ghostAdapter.removeAll();
        FolderEmptyStateItem folderEmptyStateItem = new FolderEmptyStateItem();
        items.add(folderEmptyStateItem);
        ghostAdapter.addItems(items);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showFolders(List<FolderModel> folders) {
        items.clear();
        ghostAdapter.removeAll();

        FolderModel rootFolder = new FolderModel();
        rootFolder.setTitle("Home");
        rootFolder.setId(-1);

        folders.add(0, rootFolder);

        for (FolderModel folderModel : folders) {
            FolderListItem folderListItem = new FolderListItem(folderModel);
            folderListItem.setOnItemClickListener((position, object) ->
                    moveToFolderMvpPresenter.onFolderCLicked((FolderModel) object, noteId));
            items.add(folderListItem);
        }

        ghostAdapter.addItems(items);
    }
}
