package com.farshidabz.supernote.view.ui.note;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.farshidabz.supernote.R;
import com.farshidabz.supernote.view.ui.note.paperstyle.PaperStyleBottomSheet;
import com.farshidabz.supernote.view.ui.note.textstyle.TextStyle;
import com.farshidabz.supernote.view.ui.note.textstyle.TextStyleBottomSheet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TextNoteActivity extends AppCompatActivity {

    @BindView(R.id.textNoteActivityToolbar)
    Toolbar textNoteActivityToolbar;
    @BindView(R.id.imgTextStyle)
    ImageView imgTextStyle;
    @BindView(R.id.etContentField)
    EditText etContentField;
    private NoteInputEditTextHandler noteInputEditTextHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_note);

        ButterKnife.bind(this);

        noteInputEditTextHandler = new NoteInputEditTextHandler(etContentField);

        iniToolbar();
    }

    private void iniToolbar() {
        setSupportActionBar(textNoteActivityToolbar);

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
                onBackPressed();
                break;
            case R.id.mnuPaperStyle:
                showPaperStyle();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPaperStyle() {
        final PaperStyleBottomSheet paperStyleBottomSheet =
                PaperStyleBottomSheet.newInstance(noteInputEditTextHandler.getBackgroundResId(),
                        noteInputEditTextHandler.getPaperStyleId());

        paperStyleBottomSheet.setOnPaperStyleSelectedListener((paperStyleId, paperId) ->
                noteInputEditTextHandler.changePaperStyle(paperStyleId, paperId));

        paperStyleBottomSheet.show(getSupportFragmentManager(), paperStyleBottomSheet.getTag());
    }

    @OnClick(R.id.imgTextStyle)
    public void onImgTextStyleClicked() {
        final TextStyleBottomSheet textStyleBottomSheet = TextStyleBottomSheet.newInstance(TextStyle.REGULAR, R.color.blue);
        textStyleBottomSheet.show(getSupportFragmentManager(), textStyleBottomSheet.getTag());
    }
}