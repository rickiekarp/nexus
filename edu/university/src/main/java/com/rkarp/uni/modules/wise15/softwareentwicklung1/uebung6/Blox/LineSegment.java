package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung6.Blox;

class LineSegment
{
    private int _position;
    private final int _size;
    
    public LineSegment(int position, int size)
    {
        _position = position;
        _size = size;
    }
    
    public int position()
    {
        return _position;
    }
    
    public int size()
    {
        return _size;
    }
    
    public void move(int delta)
    {
        _position += delta;
    }
    
    public boolean collidesWith(LineSegment other)
    {
        return false;
    }
}
