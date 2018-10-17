package com.mansourappdevelopment.androidapp.encdec;

/**
 * Created by Mansour on 10/17/2018.
 */

public class Item {
    private String algorithmName;
    private String descritption;

    public Item(String algorithmName, String descritption){
        this.algorithmName = algorithmName;
        this.descritption = descritption;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public String getDescritption() {
        return descritption;
    }
}
