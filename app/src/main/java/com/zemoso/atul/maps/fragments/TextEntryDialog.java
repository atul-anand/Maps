package com.zemoso.atul.maps.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zemoso.atul.maps.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnTextDialogCallBack} interface
 * to handle interaction events.
 */
public class TextEntryDialog extends DialogFragment {

    public final static int OK = 1;
    public final static int CANCEL = 0;
    private OnTextDialogCallBack mListener;
    private TextView mTitle;
    private EditText mTextInput;
    private Button mOkButton;
    private Button mCancelButton;

    private String mTextEntry;


    public TextEntryDialog() {
        // Required empty public constructor
    }

    public static TextEntryDialog newInstance(OnTextDialogCallBack callBack) {
        TextEntryDialog textEntryDialog = new TextEntryDialog();
        textEntryDialog.mListener = callBack;
        return textEntryDialog;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text_entry_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitle = view.findViewById(R.id.label_text_entry);
        mTextInput = view.findViewById(R.id.text_entry);
        mOkButton = view.findViewById(R.id.ok);
        mCancelButton = view.findViewById(R.id.cancel);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String mTitleText = getArguments().getString("title");
        int inputType = getArguments().getInt("inputType");
        String content = getArguments().getString("content");
        mTextInput.setInputType(inputType);
        mTitle.setText(mTitleText);
        mTextInput.setText(content);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextEntry = mTextInput.getText().toString().trim();
                validateTextInput();
                mListener.onFragmentInteraction(mTextEntry, 1);
                TextEntryDialog.this.dismiss();
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextEntry = mTextInput.getText().toString().trim();
                mListener.onFragmentInteraction(mTextEntry, 0);
                TextEntryDialog.this.dismiss();
            }
        });
    }

    private void validateTextInput() {
        boolean cancel = false;
        View focusView = null;

        String fieldRequired = getResources().getString(R.string.error_field_required);
//        switch (inputType){
//            case InputType.TYPE_CLASS_TEXT:
//
        if (mTextEntry.isEmpty()) {
            mTextInput.setError(fieldRequired);
            focusView = mTextInput;
            cancel = true;
        }
//
//                break;
//            case InputType.TYPE_CLASS_NUMBER:
//
//                if(mTextEntry)
//                break;
//        }
        if (cancel)
            focusView.requestFocus();

    }


    public interface OnTextDialogCallBack {
        void onFragmentInteraction(String text, int i);
    }
}
