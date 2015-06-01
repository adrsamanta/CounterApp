package com.samanta.alan.counter;

/**
 * Created by alan on 6/1/2015.
 */
public class Counter {

    private int count;

    public Counter(){
        this(0);
    }

    public Counter(int startCount){
        count=startCount;
    }

    public int increment(){
        return ++count;
    }

    public int decrement(){
        return --count;
    }

    public void setCount(int newCount){
        count=newCount;
    }

    public int getCount(){
        return count;
    }

}
