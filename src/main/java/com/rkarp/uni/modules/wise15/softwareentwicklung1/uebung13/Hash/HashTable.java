package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Hash;

public class HashTable {

    private HashFunktion _fun;
    private Stack[] _table;
    private int _size;


    public HashTable(HashFunktion fun) {
        _fun = fun;
        _table = new Stack[10];
        _size = 0;
    }

    public int size() {
        return _size;
    }

    public void insert(Object obj) {
        if (!contains(obj)) {
            int index = indexFuer(obj);
            _table[index] = new Stack(obj, _table[index]);
            _size++;
        }
    }

    private int indexFuer(Object obj) {
        int hash = _fun.f(obj);
        int index = Math.abs(hash % 10);
        return index;
    }

    public boolean contains(Object obj) {
        int index = indexFuer(obj);
        Stack s = _table[index];
        while (s != null) {
            if (s.getElement().equals(obj)) {
                return true;
            }
            s = s.getNext();
        }
        return false;
    }
}
