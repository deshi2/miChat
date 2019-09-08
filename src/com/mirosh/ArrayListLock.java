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

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public ArrayList<String> getArrayList() {
        return this.arrayList;
    }

    public ArrayListLock() {
        initializeArrayList();
    }

    public void clearAll() {
        getArrayList().clear();
    }

    public void addElement(String s) {
        getArrayList().add(s);
    }

    private void initializeArrayList() {
        arrayList = new ArrayList<String>();
    }

}