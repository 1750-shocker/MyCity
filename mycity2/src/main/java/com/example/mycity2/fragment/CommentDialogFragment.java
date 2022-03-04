package com.example.mycity2.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.mycity2.R;
import com.example.mycity2.util.DialogFragmentDataCallBack;


public class CommentDialogFragment extends DialogFragment implements View.OnClickListener{
    private EditText editComment;
    private ImageView imageBtnCommentSend;
    private DialogFragmentDataCallBack dataCallBack;
    private InputMethodManager inputMethodManager;

    @Override
    public void onClick(View view) {
       if (view.getId() == R.id.image_btn_comment_send) {
            Toast.makeText(getActivity(), editComment.getText().toString(), Toast.LENGTH_SHORT).show();
            editComment.setText("");
            dismiss();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        if (!(getActivity() instanceof DialogFragmentDataCallBack)) {
            throw new IllegalStateException("DialogFragment所在的Activity必须实现" +
                    "DialogFragmentDataCallBack的所有接口");
        }
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog mDialog = new Dialog(getActivity(), R.style.BottomDialog);

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.fragment_comment_dialog);
        mDialog.setCanceledOnTouchOutside(true);
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams layoutParams;
        if (window != null) {
            layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(layoutParams);
        }
        editComment = (EditText) mDialog.findViewById(R.id.edit_comment);
        imageBtnCommentSend = (ImageView) mDialog.findViewById(R.id.image_btn_comment_send);
        fillEditText();
        setSoftKeyboard();
        editComment.addTextChangedListener(mTextWatcher);
        imageBtnCommentSend.setOnClickListener(this);

        return mDialog;
    }

    private void setSoftKeyboard() {
        editComment.setFocusable(true);
        editComment.setFocusableInTouchMode(true);
        editComment.requestFocus();
        editComment.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    if (inputMethodManager.showSoftInput(editComment, 0)) {
                        editComment.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }

    private void fillEditText() {
        dataCallBack = (DialogFragmentDataCallBack)getActivity();
        editComment.setText(dataCallBack.getCommentText());
        editComment.setSelection(dataCallBack.getCommentText().length());
        if (dataCallBack.getCommentText().length() == 0) {
            imageBtnCommentSend.setEnabled(false);
            imageBtnCommentSend.setColorFilter(ContextCompat.getColor(getActivity(), R.color.iconCover));
        }
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            temp = charSequence;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (temp.length() > 0) {
                imageBtnCommentSend.setEnabled(true);
                imageBtnCommentSend.setClickable(true);
                imageBtnCommentSend.setColorFilter(ContextCompat.getColor(getActivity(), R.color.purple_200));
            } else {
                imageBtnCommentSend.setEnabled(false);
                imageBtnCommentSend.setColorFilter(ContextCompat.getColor(getActivity(), R.color.iconCover));
            }
        }
    };

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        dataCallBack.setCommentText(editComment.getText().toString());
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        dataCallBack.setCommentText(editComment.getText().toString());
        super.onCancel(dialog);
    }
}
