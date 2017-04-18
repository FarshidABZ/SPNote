package com.farshidabz.spnote.view.ui.note;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.farshidabz.spnote.R;
import com.farshidabz.spnote.model.NoteModel;
import com.farshidabz.spnote.util.widgets.DrawingView;
import com.farshidabz.spnote.view.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoteActivity extends BaseActivity implements NoteMvpView {

    @BindView(R.id.NoteActivityToolbar)
    Toolbar noteActivityToolbar;
    @BindView(R.id.imgTextStyle)
    ImageView imgTextStyle;
    @BindView(R.id.etContentField)
    EditText etContentField;
    @BindView(R.id.imgEraser)
    ImageView imgEraser;
    @BindView(R.id.imgDrawingPen)
    ImageView imgDrawingPen;
    @BindView(R.id.drawingView)
    DrawingView drawingView;
    @BindView(R.id.imgDraw)
    ImageView imgDraw;
    @BindView(R.id.tvInputType)
    TextView tvInputType;
    @BindView(R.id.llInputTypeSwitcher)
    LinearLayout llInputTypeSwitcher;

    NoteMvpPresenter noteMvpPresenter;

    private NoteInputEditTextHandler noteInputEditTextHandler;
    private int noteId;
    private int folderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_note);

        ButterKnife.bind(this);

        noteMvpPresenter = new NotePresenter(this,
                getSupportFragmentManager(),
                drawingView,
                etContentField);

        noteMvpPresenter.onAttach(this);
        iniToolbar();

        getData();
    }

    private void getData() {
        noteId = getIntent().getIntExtra("noteId", -1);
        folderId = getIntent().getIntExtra("folderId", -1);

        noteMvpPresenter.getNote(noteId, folderId);
    }

    private void iniToolbar() {
        setSupportActionBar(noteActivityToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.text_note_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuDiscard:
                noteMvpPresenter.onBackClicked(false, noteId);
                break;
            case R.id.mnuPaperStyle:
                noteMvpPresenter.onPaperStyleClicked();
                break;
            case android.R.id.home:
                noteMvpPresenter.onBackClicked(true, noteId);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        noteMvpPresenter.onBackClicked(true, noteId);
    }

    @OnClick(R.id.imgTextStyle)
    public void onImgTextStyleClicked() {
        noteMvpPresenter.onTextStyleClicked();
    }

    @OnClick(R.id.llInputTypeSwitcher)
    public void onSwitchModeClicked() {
        noteMvpPresenter.onInputTypeSwitcherClicked(llInputTypeSwitcher);
    }

    @OnClick(R.id.imgEraser)
    public void onEraserClicked() {
        imgEraser.setVisibility(View.INVISIBLE);
        imgDraw.setVisibility(View.VISIBLE);
        noteMvpPresenter.onEraserClicked();
    }

    @OnClick(R.id.imgDraw)
    public void onImgDrawClicked() {
        imgDraw.setVisibility(View.INVISIBLE);
        imgEraser.setVisibility(View.VISIBLE);
        noteMvpPresenter.onDrawClicked();
    }

    @OnClick(R.id.imgDrawingPen)
    public void onImgDrawingPenClicked() {
        noteMvpPresenter.onDrawingStyleClicked();
    }

    @Override
    public void onNoteReceived(NoteModel noteModel) {
    }

    @Override
    public void setEraserVisibility(int visibility) {
        imgEraser.setVisibility(visibility);
    }

    @Override
    public void setTextStyleVisibility(int visibility) {
        imgTextStyle.setVisibility(visibility);
    }

    @Override
    public void setDrawingPenVisibility(int visibility) {
        imgDrawingPen.setVisibility(visibility);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void setInputTypeModeText(String text) {
        tvInputType.setText(text);
    }

    @Override
    public void setTextStyleColorBackground(@DrawableRes int drawable) {
        imgTextStyle.setImageResource(drawable);
    }

    @Override
    public void setDrawingPenColorBackground(@DrawableRes int drawable) {
        imgDrawingPen.setImageResource(drawable);
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
    protected void setUp() {

    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}