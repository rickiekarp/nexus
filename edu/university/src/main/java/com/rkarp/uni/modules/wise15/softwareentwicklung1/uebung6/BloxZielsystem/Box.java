package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung6.BloxZielsystem;

class Box
{
    private final LineSegment _horizontal;
    private final LineSegment _vertical;
    
    public Box(int x, int width, int y, int height)
    {
        _horizontal = new LineSegment(x, width);
        _vertical = new LineSegment(y, height);
    }
    
    public int x()
    {
        return _horizontal.position();
    }
    
    public int width()
    {
        return _horizontal.size();
    }
    
    public int y()
    {
        return _vertical.position();
    }
    
    public int height()
    {
        return _vertical.size();
    }
    
    public void moveHorizontally(int delta)
    {
        _horizontal.move(delta);
    }
    
    public void moveVertically(int delta)
    {
        _vertical.move(delta);
    }
    
    public boolean collidesWith(Box other)
    {
        return _horizontal.collidesWith(other._horizontal) && _vertical.collidesWith(other._vertical);
    }
}
