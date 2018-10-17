package com.mansourappdevelopment.androidapp.encdec;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class EncryptionFragment extends Fragment {
    View mView;
    private TextInputLayout mTextLayoutPlainText;
    private TextInputLayout mTextLayoutKey;
    private TextInputEditText mEditTextPlainText;
    private TextInputEditText mEditTextKey;
    private TextView mTextViewCipherText;
    private Button mBtnEncrypt;
    private Spinner mSpinnerAlgorithms;
    private ArrayAdapter<String> mSpinnerAdapter;
    List<String> alogrithmsList;

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (mEditTextKey.getText().toString().equals("")){
                return;
            }
            if (Integer.parseInt(mEditTextKey.getText().toString()) > 26) {
                mTextLayoutKey.setErrorEnabled(true);
                mTextLayoutKey.setError("Should be lower than 26");
                mBtnEncrypt.setEnabled(false);
            } else {
                mTextLayoutKey.setErrorEnabled(false);
                mBtnEncrypt.setEnabled(true);
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
                if (!mEditTextKey.getText().toString().equals("") && !mEditTextPlainText.getText().toString().equals("")) {

                    CaesarCipher caesarCipher = new
                            CaesarCipher(mEditTextPlainText.getText().toString(), Integer.parseInt(mEditTextKey.getText().toString()));
                    mTextViewCipherText.setText(caesarCipher.plainToCipher());
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
                        Toast.makeText(getActivity(), "Caesar", Toast.LENGTH_SHORT).show();
                        break;
                    case "Play Fair":
                        Toast.makeText(getActivity(), "Play Fair", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void prepareSpinnerData() {
        alogrithmsList = new ArrayList<>();
        alogrithmsList.add("Caesar Cipher");
        alogrithmsList.add("Play Fair");
    }
}
