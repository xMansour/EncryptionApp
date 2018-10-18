package com.mansourappdevelopment.androidapp.encdec;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mansour on 10/16/2018.
 */

public class DecryptionFragment extends Fragment {
    View mView;
    private TextInputLayout mTextLayoutCipherText;
    private TextInputLayout mTextLayoutKey;
    private TextInputEditText mEditTextCipherText;
    private TextInputEditText mEditTextKey;
    private TextView mTextViewPlainText;
    private Button mBtnDecrypt;
    private FloatingActionButton mFabShare;
    private FloatingActionButton mFabCopy;
    private Spinner mSpinnerAlgorithms;
    private ArrayAdapter<String> mSpinnerAdapter;
    List<String> alogrithmsList;

    //mAlgorithmFlag is used to determine which algorithm -> 1 for caesar and 2 for play fair
    private int mAlgorithmFlag = 1;

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (mEditTextKey.getText().toString().equals("")) {
                return;
            }
            if (Integer.parseInt(mEditTextKey.getText().toString()) > 26) {
                mTextLayoutKey.setErrorEnabled(true);
                mTextLayoutKey.setError("Should be lower than 26");
                mBtnDecrypt.setEnabled(false);
                mBtnDecrypt.setBackground(getActivity().getResources().getDrawable(R.drawable.button_decrypt_disabled));
            } else {
                mTextLayoutKey.setErrorEnabled(false);
                mBtnDecrypt.setEnabled(true);
                mBtnDecrypt.setBackground(getActivity().getResources().getDrawable(R.drawable.button_decrypt));

            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_decryption, container, false);
        return mView;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mTextLayoutCipherText = (TextInputLayout) view.findViewById(R.id.layoutTextInput);
        mTextLayoutKey = (TextInputLayout) view.findViewById(R.id.layoutTextKey);

        mEditTextCipherText = (TextInputEditText) view.findViewById(R.id.editTextCipherText);
        mEditTextKey = (TextInputEditText) view.findViewById(R.id.editTextKey);
        mEditTextKey.addTextChangedListener(mTextWatcher);

        mTextViewPlainText = (TextView) view.findViewById(R.id.textViewPlainText);

        mBtnDecrypt = (Button) view.findViewById(R.id.btnDecryption);

        mBtnDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditTextKey.getText().toString().equals("")) {
                    mTextLayoutKey.setErrorEnabled(true);
                    mTextLayoutKey.setError("Enter a valid key");
                } else {
                    mTextLayoutKey.setErrorEnabled(false);

                }
                if (mEditTextCipherText.getText().toString().equals("")) {
                    mTextLayoutCipherText.setErrorEnabled(true);
                    mTextLayoutCipherText.setError("Enter something to encrypt");

                } else {
                    mTextLayoutCipherText.setErrorEnabled(false);
                }
                if (!mEditTextKey.getText().toString().equals("") && !mEditTextCipherText.getText().toString().equals("") && mAlgorithmFlag == 1) {

                    CaesarCipher caesarCipher = new
                            CaesarCipher(mEditTextCipherText.getText().toString(), Integer.parseInt(mEditTextKey.getText().toString()));
                    mTextViewPlainText.setText(caesarCipher.cipherToPlain());
                } else if (!mEditTextKey.getText().toString().equals("") && !mEditTextCipherText.getText().toString().equals("") && mAlgorithmFlag == 2) {
                    Toast.makeText(getActivity(), "Play Fair isn't implemnted yet!", Toast.LENGTH_SHORT).show();
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
                        break;
                    case "Play Fair":
                        mAlgorithmFlag = 2;
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
                if (!mTextViewPlainText.getText().toString().equals("")) {
                    ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("Cipher Text", mTextViewPlainText.getText().toString());
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(getActivity(), "Text copied to the clipboard", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mFabShare = (FloatingActionButton) view.findViewById(R.id.fabShare);
        mFabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mSharingIntent = new Intent(Intent.ACTION_SEND);
                mSharingIntent.setType("text/plain");
                mSharingIntent.putExtra(Intent.EXTRA_TEXT, mTextViewPlainText.getText().toString());
                startActivity(Intent.createChooser(mSharingIntent, "Share via"));
            }
        });
    }

    private void prepareSpinnerData() {
        alogrithmsList = new ArrayList<>();
        alogrithmsList.add("Caesar Cipher");
        alogrithmsList.add("Play Fair");
    }
}
