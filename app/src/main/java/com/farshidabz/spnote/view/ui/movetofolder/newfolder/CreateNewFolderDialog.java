package com.farshidabz.spnote.view.ui.movetofolder.newfolder;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Window;
import android.widget.EditText;

import com.farshidabz.spnote.R;
import com.farshidabz.spnote.util.ScreenUtils;
import com.farshidabz.spnote.view.ui.OnDialogDismissListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FarshidAbz.
 * Since 4/17/2017.
 */

public class CreateNewFolderDialog extends Dialog {

    @BindView(R.id.etFolderName)
    EditText etFolderName;
    private OnDialogDismissListener onDialogDismissListener;

    public CreateNewFolderDialog(@NonNull Context context) {
        super(context);
    }

    public void setOnDialogDismissListener(OnDialogDismissListener onDialogDismissListener) {
        this.onDialogDismissListener = onDialogDismissListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_new_folder);

        ButterKnife.bind(this);

        getWindow().setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.white_background));
        getWindow().setLayout(ScreenUtils.getScreenHeight(getContext()) / 2, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @OnClick(R.id.tvCreateFolder)
    public void onCreateFolderClicked() {
        onDismiss(true);
    }

    @OnClick(R.id.tvCancelCreatingFolder)
    public void onCancelClicked() {
        onDismiss(false);
    }

    private void onDismiss(boolean save) {
        String folderName = etFolderName.getText().toString();
        if (TextUtils.isEmpty(folderName)) {
            folderName = getContext().getString(R.string.folder_name);
        }
        if (save) {
            onDialogDismissListener.onDismiss(true, folderName);
        } else {
            onDialogDismissListener.onDismiss(false, "");
        }
        dismiss();
    }
}
