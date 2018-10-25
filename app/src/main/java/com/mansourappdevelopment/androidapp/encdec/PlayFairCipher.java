package com.mansourappdevelopment.androidapp.encdec;

import android.util.Log;

import java.util.Arrays;

/**
 * Created by Mansour on 10/18/2018.
 */

public class PlayFairCipher {
    private char[] mOrderedMatrix = new char[25];
    private char[] mAlphabetMatrix = "abcdefghiklmnopqrstuvwxyz".toCharArray();
    private char[][] mModifiedMatrix = new char[5][5];
    private String mKey = "";
    private String mText = "";
    private String mModifiedText = "";
    private String mCipherText = "";
    private String mPlainText = "";
    private String mPlainTextWithoutX = "";
    private int mNextCharIndex = 0;
    private int mNextSubString = 0;
    private int mTempI1 = 0;
    private int mTempJ1 = 0;
    private int mTempI2 = 0;
    private int mTempJ2 = 0;
    private char mTempChar1;
    private char mTempChar2;


    public PlayFairCipher(String mText, String mKey) {
        this.mText = mText;
        this.mKey = mKey;

    }

    public String plainToCipher() {
        checkForJ();        //to replace the j with i
        checkFOrJInKey();   //to replace the j with i in the key
        removeDublicates(); //to add a x between dublicates
        oddLength();        //to check if the entered text has odd number of characters, we add a X to it
        fillMatrix();       //to enter the key inside the matrix

        //mModifiedText is the plain
        //mOrderedMatrix is the ordered matrix

        mNextCharIndex = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mModifiedMatrix[i][j] = mOrderedMatrix[mNextCharIndex];
                mNextCharIndex++;
            }
        }
        mNextCharIndex = 0;
        while (mNextSubString < mModifiedText.length() / 2) {
            if (mNextCharIndex < mModifiedText.length()) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (mModifiedMatrix[i][j] == mModifiedText.charAt(mNextCharIndex)) {
                            mTempI1 = i;
                            mTempJ1 = j;
                        }
                    }
                }
                mNextCharIndex++;
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (mModifiedMatrix[i][j] == mModifiedText.charAt(mNextCharIndex)) {
                            mTempI2 = i;
                            mTempJ2 = j;
                        }
                    }
                }
                mNextCharIndex++;
                mNextSubString++;

                if (mTempI1 == mTempI2) {
                    mTempChar1 = mModifiedMatrix[mTempI1][(Math.abs(mTempJ1 + 1)) % 5];
                    mTempChar2 = mModifiedMatrix[mTempI2][(Math.abs(mTempJ2 + 1)) % 5];
                } else if (mTempJ1 == mTempJ2) {
                    mTempChar1 = mModifiedMatrix[(Math.abs(mTempI1 + 1)) % 5][mTempJ1];
                    mTempChar2 = mModifiedMatrix[(Math.abs(mTempI2 + 1)) % 5][mTempJ2];
                } else if (mTempJ1 < mTempJ2) {
                    mTempChar1 = mModifiedMatrix[mTempI1][(Math.abs(mTempJ1 + Math.abs(mTempJ1 - mTempJ2))) % 5];
                    mTempChar2 = mModifiedMatrix[mTempI2][(Math.abs(mTempJ2 - Math.abs(mTempJ1 - mTempJ2))) % 5];

                } else if (mTempJ1 > mTempJ2) {
                    mTempChar1 = mModifiedMatrix[mTempI1][(Math.abs(mTempJ1 - Math.abs(mTempJ1 - mTempJ2))) % 5];
                    mTempChar2 = mModifiedMatrix[mTempI2][(Math.abs(mTempJ2 + Math.abs(mTempJ1 - mTempJ2))) % 5];
                }
            }
            mCipherText += mTempChar1;
            mCipherText += mTempChar2;
        }
        return /*Arrays.deepToString(mModifiedMatrix) +*/ mCipherText;
    }

    public String cipherToPlain() {
        checkForJ();        //to replace the j with i
        checkFOrJInKey();   //to replace the j with i in the key
        removeDublicates(); //to add a x between dublicates
        oddLength();        //to check if the entered text has odd number of characters, we add a X to it
        fillMatrix();       //to enter the key inside the matrix

        //mModifiedText is the plain
        //mOrderedMatrix is the ordered matrix

        mNextCharIndex = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mModifiedMatrix[i][j] = mOrderedMatrix[mNextCharIndex];
                mNextCharIndex++;
            }
        }
        mNextCharIndex = 0;
        while (mNextSubString < mModifiedText.length() / 2) {
            if (mNextCharIndex < mModifiedText.length()) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (mModifiedMatrix[i][j] == mModifiedText.charAt(mNextCharIndex)) {
                            mTempI1 = i;
                            mTempJ1 = j;
                        }
                    }
                }
                mNextCharIndex++;
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (mModifiedMatrix[i][j] == mModifiedText.charAt(mNextCharIndex)) {
                            mTempI2 = i;
                            mTempJ2 = j;
                        }
                    }
                }
                mNextCharIndex++;
                mNextSubString++;

                if (mTempI1 == mTempI2) {
                    mTempChar1 = mModifiedMatrix[mTempI1][(Math.abs(mTempJ1 - 1)) % 5];
                    mTempChar2 = mModifiedMatrix[mTempI2][(Math.abs(mTempJ2 - 1)) % 5];
                } else if (mTempJ1 == mTempJ2) {
                    mTempChar1 = mModifiedMatrix[(Math.abs(mTempI1 - 1)) % 5][mTempJ1];
                    mTempChar2 = mModifiedMatrix[(Math.abs(mTempI2 - 1)) % 5][mTempJ2];
                } else if (mTempJ1 < mTempJ2) {
                    mTempChar1 = mModifiedMatrix[mTempI1][(Math.abs(mTempJ1 + Math.abs(mTempJ1 - mTempJ2))) % 5];
                    mTempChar2 = mModifiedMatrix[mTempI2][(Math.abs(mTempJ2 - Math.abs(mTempJ1 - mTempJ2))) % 5];

                } else if (mTempJ1 > mTempJ2) {
                    mTempChar1 = mModifiedMatrix[mTempI1][(Math.abs(mTempJ1 - Math.abs(mTempJ1 - mTempJ2))) % 5];
                    mTempChar2 = mModifiedMatrix[mTempI2][(Math.abs(mTempJ2 + Math.abs(mTempJ1 - mTempJ2))) % 5];
                }
            }
            mPlainText += mTempChar1;
            mPlainText += mTempChar2;
        }

        if (mPlainText.charAt(mPlainText.length() - 1) == 'x') {
            mPlainTextWithoutX = mPlainText.substring(0, mPlainText.length() - 1);
        } else {
            for (int i = 0; i < mPlainText.length(); i++) {
                if (!(mPlainText.charAt(i) == 'x' && mPlainText.charAt(i - 1) == mPlainText.charAt(i + 1))) {
                    mPlainTextWithoutX += mPlainText.charAt(i);
                }
            }
        }
        return /*Arrays.deepToString(mModifiedMatrix) +*/ mPlainTextWithoutX;
    }

    public void fillMatrix() {
        int temp = 0;
        //first loop to add the key in the matrix
        for (int i = 0; i < mKey.length(); i++) {
            mOrderedMatrix[i] = mKey.charAt(i);
            temp = i + 1;
        }
        //second loop to add the other alphabet to the matrix
        for (int j = 0; j < 25; j++) {
            mNextCharIndex = 0;
            int counter = 1;
            while (mNextCharIndex < mKey.length()) {
                if (mAlphabetMatrix[j] != mKey.charAt(mNextCharIndex)) {
                    counter++;
                }
                mNextCharIndex++;
            }
            Log.i("matrix", "Executed 1 " + String.valueOf(mKey.length()) + " " + String.valueOf(counter));
            if (!(counter == mKey.length())) {
                if (temp < 25) {
                    mOrderedMatrix[temp] = mAlphabetMatrix[j];
                    temp++;
                }
                Log.i("matrix", "Executed 2");

            }
            Log.i("matrix", "Executed 3 End");

        }

    }


    //this function returns the plain text with no duplicates
    public void removeDublicates() {
        int i = 0;
        while (i < mText.length()) {
            mModifiedText += mText.charAt(i);
            if (i % 2 == 0 && i < mText.length() - 1) {
                if (mText.charAt(i) == mText.charAt(i + 1)) {
                    mModifiedText += "x";
                    mText = mText.substring(i + 1);
                    i = 0;
                } else
                    i++;
            } else
                i++;
        }

    }

    public void oddLength() {
        if (mModifiedText.length() % 2 != 0) {
            mModifiedText += "x";
        }
    }

    public void checkForJ() {
        mText = mText.replace('j', 'i');
    }

    public void checkFOrJInKey(){
        mKey = mKey.replace('j', 'i');
    }
}
