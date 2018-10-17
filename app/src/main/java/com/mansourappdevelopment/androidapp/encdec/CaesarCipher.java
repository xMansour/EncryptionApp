package com.mansourappdevelopment.androidapp.encdec;

/**
 * Created by Mansour on 10/17/2018.
 */

public class CaesarCipher {
    private String mPlainText = "";
    private String mCipherText = "";
    private int ascii = 0;
    private int temp = 0;
    private int mKey = 0;
    private int asciiA = 97;
    private int asciiZ = 122;

    public CaesarCipher(String mPlainText, int mKey) {
        this.mPlainText = mPlainText;
        this.mKey = mKey;
    }

    public String plainToCipher() {
        for (int i = 0; i < mPlainText.length(); i++) {
            ascii = (int) mPlainText.charAt(i);
            temp = ascii;
            ascii += mKey;
            //if char(i)'s ascii is more than the ascii code of z (122) -> reset to a (97) + (the new cipher char's ascii - the normal
            //text) - ( the left characters until z (122) + 1
            if (ascii > asciiZ)
                ascii = asciiA + (ascii - temp - (asciiZ - temp + 1));
            mCipherText += (char) ascii;
        }
        return mCipherText;
    }
}
