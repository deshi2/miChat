package com.mirosh;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ArrayList class with functionality of lock/unlock for use in threads.
 */
public class ArrayListLock {

    private ArrayList<String> arrayList;
    private final Lock lock = new ReentrantLock();

    {
        setArrayList(new ArrayList<String>());
    }

    public void lock() {
        getLock().lock();
    }

    public void unlock() {
        getLock().unlock();
    }

    public void clearAll() {
        getArrayList().clear();
    }

    public void addElement(String s) {
        getArrayList().add(s);
    }

    /* SETTERS AND GETTERS */

    private void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<String> getArrayList() {
        return this.arrayList;
    }

    private Lock getLock() {
        return this.lock;
    }

}