package com.stephenfg.sre.util;

public class Range {
    public int start;
    public int end;
    public int size;

    public Range(int start, int end){
        this.start = start;
        this.end = end;
        this.size = count();
    }

    private int count(){
        return (end - start) +1;
    }
}
