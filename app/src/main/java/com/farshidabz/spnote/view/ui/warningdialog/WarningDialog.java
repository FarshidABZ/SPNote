package com.farshidabz.spnote.view.ui.warningdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.farshidabz.spnote.R;
import com.farshidabz.spnote.util.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FarshidAbz.
 * Since 4/17/2017.
 *
 * A dialog to show warning messages, pass title and messages to show,
 * and set setOnWarningDialogDismissListener() to get user response
 *
 */

public class WarningDialog extends Dialog {
    private OnWarningDialogDismissListener onDismissListener;

    @BindView(R.id.tvWarningDialogMessage)
    TextView tvWarningDialogMessage;
    @BindView(R.id.tvWarningDialogTitle)
    TextView tvWarningDialogTitle;
    @BindView(R.id.tvWarningDialogOK)
    TextView tvWarningDialogOK;

    String title;
    String message;

    public WarningDialog(@NonNull Context context) {
        super(context);
    }

    public WarningDialog(@NonNull Context context, String title, String message) {
        super(context);
        this.title = title;
        this.message = message;
    }

    public void setOnWarningDialogDismissListener(OnWarningDialogDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_warning);

        ButterKnife.bind(this);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        getWindow().setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.white_background));
        getWindow().setLayout(ScreenUtils.getScreenHeight(getContext()) / 2, RecyclerView.LayoutParams.WRAP_CONTENT);

        tvWarningDialogMessage.setText(message);
        tvWarningDialogTitle.setText(title);
        tvWarningDialogOK.setText(title);
    }

    @OnClick(R.id.tvWarningDialogOK)
    public void onDiscardChangesClicked() {
        dismiss(true);
    }

    @OnClick(R.id.tvWarningDialogCancel)
    public void onCancelDiscardChangesClicked() {
        dismiss(false);
    }

    private void dismiss(boolean state) {
        onDismissListener.onDismissDialog(state);
        dismiss();
    }
}
