package Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

import CustomControls.CustomButtonBold;
import CustomControls.CustomTextView;
import CustomControls.CustomTextViewBold;
import ir.ncis.phonebook.R;

public class DialogConfirm extends Dialog {
    private CustomButtonBold btnNo;
    private CustomButtonBold btnYes;
    private CustomTextView txtMessage;
    private CustomTextViewBold txtTitle;
    private OnConfirmListener onConfirmListener;

    public DialogConfirm(Context context) {
        super(context);
        init();
    }

    public DialogConfirm(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        setCancelable(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_confirm);

        btnNo = (CustomButtonBold) findViewById(R.id.btnNo);
        btnYes = (CustomButtonBold) findViewById(R.id.btnYes);
        txtMessage = (CustomTextView) findViewById(R.id.txtMessage);
        txtTitle = (CustomTextViewBold) findViewById(R.id.txtTitle);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onConfirmListener != null) {
                    onConfirmListener.onConfirm();
                }
                dismiss();
            }
        });
    }

    public DialogConfirm setMessage(String message) {
        txtMessage.setText(message);
        return this;
    }

    public DialogConfirm setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
        return this;
    }

    public DialogConfirm setTitle(String title) {
        txtTitle.setText(title);
        return this;
    }

    public interface OnConfirmListener {
        void onConfirm();
    }
}