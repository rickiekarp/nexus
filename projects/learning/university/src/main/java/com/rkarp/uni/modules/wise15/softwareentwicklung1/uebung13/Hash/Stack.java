package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung13.Hash;

public class Stack {

    private Object _element;
    private Stack _next;

    public Stack(Object element, Stack next)
    {
        _element = element;
        _next = next;
    }

    public Object getElement() {
        return _element;
    }

    public Stack getNext() {
        return _next;
    }
}
