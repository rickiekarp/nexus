package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung6.BloxZielsystem;

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
        return !(this.isLeftOf(other) || other.isLeftOf(this));
    }

//    /*
//     * Pimp my Kollisionsabfrage
//     * Im Prinzip kommt man mit nur einer Abfrage, nämlich "Ist Abstand kleiner als Summe der Objektdurchmesser?" aus.
//     * (Abstand ist ein Betrag abs(), insofern sind es auch zwei Abfragen: nach +abs und -abs.
//     * Aber ich wäre glücklich über jedes "this", was aus meinem Code fliegt. :-)
//     */
//    public boolean collidesWith(LineSegment other)
//    {
//        int distance = Math.abs(middle() - other.middle());
//        return distance < (_size + other._size) / 2;
//    }
//
//    private int middle()
//    {
//        return _position + _size / 2;
//    }
    
    private boolean isLeftOf(LineSegment other)
    {
        return this._position + this._size <= other._position;
    }
}
