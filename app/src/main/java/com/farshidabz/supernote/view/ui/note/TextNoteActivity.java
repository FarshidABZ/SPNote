package com.farshidabz.supernote.view.ui.note;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.farshidabz.supernote.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TextNoteActivity extends AppCompatActivity {

    @BindView(R.id.etContentField)
    EditText etContentField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_note);

        ButterKnife.bind(this);

    }
}