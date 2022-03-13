package com.saigyouji.android.composetest.util;

public class NumberFilter {
    private int max;
    private int min;

    public NumberFilter(int max, int min)
    {
        this.max = max;
        this.min = min;
    }
    public boolean isInRange(int number){
        return number >= min && number <= max;
    }
}
