package com.farshidabz.supernote.view.ui.note;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.farshidabz.supernote.R;
import com.farshidabz.supernote.util.widgets.DrawingView;
import com.farshidabz.supernote.view.ui.base.BaseActivity;

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
    @BindView(R.id.imgInputTypeSwitcher)
    ImageView imgInputTypeSwitcher;
    @BindView(R.id.tvInputType)
    TextView tvInputType;


    NoteMvpPresenter noteMvpPresenter;

    private NoteInputEditTextHandler noteInputEditTextHandler;

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
                noteMvpPresenter.onBackClicked(false);
                break;
            case R.id.mnuPaperStyle:
                noteMvpPresenter.onPaperStyleClicked();
                break;
            case android.R.id.home:
                noteMvpPresenter.onBackClicked(true);
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.imgTextStyle)
    public void onImgTextStyleClicked() {
        noteMvpPresenter.onTextStyleClicked();
    }

    @OnClick(R.id.imgInputTypeSwitcher)
    public void onSwitchModeClicked() {
        noteMvpPresenter.onInputTypeSwitcherClicked(imgInputTypeSwitcher);
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