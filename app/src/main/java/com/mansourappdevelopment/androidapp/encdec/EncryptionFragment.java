package com.mansourappdevelopment.androidapp.encdec;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mansour on 10/16/2018.
 */

public class EncryptionFragment extends Fragment {
    private View mView;
    private TextInputLayout mTextLayoutPlainText;
    private TextInputLayout mTextLayoutKey;
    private TextInputEditText mEditTextPlainText;
    private TextInputEditText mEditTextKey;
    private TextView mTextViewCipherText;
    private Button mBtnEncrypt;
    private FloatingActionButton mFabShare;
    private FloatingActionButton mFabCopy;
    private Spinner mSpinnerAlgorithms;
    private ArrayAdapter<String> mSpinnerAdapter;
    private List<String> alogrithmsList;

    //mAlgorithmFlag is used to determine which algorithm -> 1 for caesar and 2 for play fair
    private int mAlgorithmFlag = 1;

    private TextWatcher mTextWatcherInput = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (mEditTextPlainText.getText().toString().matches(".*\\d+.*")) {
                mTextLayoutPlainText.setErrorEnabled(true);
                mTextLayoutPlainText.setError("Plain text can't contain a number");
                mBtnEncrypt.setEnabled(false);
                mBtnEncrypt.setBackground(getActivity().getResources().getDrawable(R.drawable.button_encrypt_disabled));
            } else {
                mTextLayoutPlainText.setErrorEnabled(false);
                mBtnEncrypt.setEnabled(true);
                mBtnEncrypt.setBackground(getActivity().getResources().getDrawable(R.drawable.button_encrypt));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (mEditTextKey.getInputType() == InputType.TYPE_CLASS_NUMBER) {
                if (mEditTextKey.getText().toString().equals("")) {
                    return;
                }
                if (Integer.parseInt(mEditTextKey.getText().toString()) > 26) {
                    mTextLayoutKey.setErrorEnabled(true);
                    mTextLayoutKey.setError("Should be lower than 26");
                    mBtnEncrypt.setEnabled(false);
                    mBtnEncrypt.setBackground(getActivity().getResources().getDrawable(R.drawable.button_encrypt_disabled));
                } else {
                    mTextLayoutKey.setErrorEnabled(false);
                    mBtnEncrypt.setEnabled(true);
                    mBtnEncrypt.setBackground(getActivity().getResources().getDrawable(R.drawable.button_encrypt));
                }

            } else {
                if (mEditTextKey.getText().toString().matches(".*\\d+.*")) {
                    mTextLayoutKey.setErrorEnabled(true);
                    mTextLayoutKey.setError("Key can't contain a number");
                    mBtnEncrypt.setEnabled(false);
                    mBtnEncrypt.setBackground(getActivity().getResources().getDrawable(R.drawable.button_encrypt_disabled));
                } else if (checkForDuplicate()) {
                    mTextLayoutKey.setErrorEnabled(true);
                    mTextLayoutKey.setError("Key should be unique");
                    mBtnEncrypt.setEnabled(false);
                    mBtnEncrypt.setBackground(getActivity().getResources().getDrawable(R.drawable.button_encrypt_disabled));
                } else {
                    mTextLayoutKey.setErrorEnabled(false);
                    mBtnEncrypt.setEnabled(true);
                    mBtnEncrypt.setBackground(getActivity().getResources().getDrawable(R.drawable.button_encrypt));
                }
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_encryption, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mTextLayoutPlainText = (TextInputLayout) view.findViewById(R.id.layoutTextInput);
        mTextLayoutKey = (TextInputLayout) view.findViewById(R.id.layoutTextKey);

        mEditTextPlainText = (TextInputEditText) view.findViewById(R.id.editTextPlainText);
        mEditTextPlainText.addTextChangedListener(mTextWatcherInput);
        mEditTextKey = (TextInputEditText) view.findViewById(R.id.editTextKey);
        mEditTextKey.addTextChangedListener(mTextWatcher);

        mTextViewCipherText = (TextView) view.findViewById(R.id.textViewCipherText);

        mBtnEncrypt = (Button) view.findViewById(R.id.btnEncryption);

        mBtnEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditTextKey.getText().toString().equals("")) {
                    mTextLayoutKey.setErrorEnabled(true);
                    mTextLayoutKey.setError("Enter a valid key");
                } else {
                    mTextLayoutKey.setErrorEnabled(false);

                }
                if (mEditTextPlainText.getText().toString().equals("")) {
                    mTextLayoutPlainText.setErrorEnabled(true);
                    mTextLayoutPlainText.setError("Enter something to encrypt");

                } else {
                    mTextLayoutPlainText.setErrorEnabled(false);
                }
                if (!mEditTextKey.getText().toString().equals("") && !mEditTextPlainText.getText().toString().equals("") && mAlgorithmFlag == 1) {

                    CaesarCipher caesarCipher = new
                            CaesarCipher(mEditTextPlainText.getText().toString(), Integer.parseInt(mEditTextKey.getText().toString()));
                    mTextViewCipherText.setText(caesarCipher.plainToCipher());
                } else if (!mEditTextKey.getText().toString().equals("") && !mEditTextPlainText.getText().toString().equals("") && mAlgorithmFlag == 2) {
                    PlayFairCipher playFairCipher = new PlayFairCipher(mEditTextPlainText.getText().toString(), mEditTextKey.getText().toString());
                    mTextViewCipherText.setText(playFairCipher.plainToCipher());
                }
            }
        });
        mSpinnerAlgorithms = (Spinner) view.findViewById(R.id.spinnerAlgorithms);

        prepareSpinnerData();

        mSpinnerAdapter = new ArrayAdapter<>(

                getActivity(), android.R.layout.simple_spinner_item, alogrithmsList);
        mSpinnerAlgorithms.setAdapter(mSpinnerAdapter);
        mSpinnerAlgorithms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String algorithmName = adapterView.getItemAtPosition(i).toString();
                switch (algorithmName) {
                    case "Caesar Cipher":
                        mAlgorithmFlag = 1;
                        mEditTextKey.setInputType(InputType.TYPE_CLASS_NUMBER);
                        break;
                    case "Play Fair":
                        mAlgorithmFlag = 2;
                        mEditTextKey.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mFabCopy = (FloatingActionButton) view.findViewById(R.id.fabCopy);
        mFabCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mTextViewCipherText.getText().toString().equals("")) {
                    ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("Cipher Text", mTextViewCipherText.getText().toString());
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(getActivity(), "Text copied to the clipboard", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mFabShare = (FloatingActionButton) view.findViewById(R.id.fabShare);
        mFabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mTextViewCipherText.getText().toString().equals("")) {
                    Intent mSharingIntent = new Intent(Intent.ACTION_SEND);
                    mSharingIntent.setType("text/plain");
                    mSharingIntent.putExtra(Intent.EXTRA_TEXT, mTextViewCipherText.getText().toString());
                    startActivity(Intent.createChooser(mSharingIntent, "Share via"));
                }
            }
        });
    }

    private void prepareSpinnerData() {
        alogrithmsList = new ArrayList<>();
        alogrithmsList.add("Caesar Cipher");
        alogrithmsList.add("Play Fair");
    }

    private boolean checkForDuplicate() {
        for (int i = 0; i < mEditTextKey.getText().toString().length(); i++) {
            for (int j = i + 1; j < mEditTextKey.getText().toString().length(); j++) {
                if (mEditTextKey.getText().toString().charAt(i) == mEditTextKey.getText().toString().charAt(j)) {
                    return true;
                }
            }
        }
        return false;
    }

}
