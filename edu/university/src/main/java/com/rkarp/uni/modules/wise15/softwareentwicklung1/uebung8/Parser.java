package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung8;

public class Parser
{
    private String _input;
    private int _position;
    private char _current;
    
    private void advance()
    {
        ++_position;
        if (_position < _input.length())
        {
            _current = _input.charAt(_position);
        }
        else
        {
            _current = '@';
        }
    }
    
    public int parse(String expression)
    {
        _input = expression;
        _position = -1;
        advance();
        
        int result = parseSum();
        // TODO error handling
        return result;
    }
    
    private int parseSum()
    {
        int a = parseProduct();
        while (_current == '+')
        {
            advance();
            int b = parseProduct();
            a = a + b;
        }
        return a;
    }
    
    private int parseProduct()
    {
        int a = parseFactor();
        while (_current == '*')
        {
            advance();
            int b = parseFactor();
            a = a * b;
        }
        return a;
    }
    
    private int parseFactor()
    {
        int result = 0;
        switch (_current)
        {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            result = _current - '0';
            advance();
            break;
            
            case '(':
            advance();
            result = parseSum();
            advance(); // )
            break;
            
            default:
            // TODO error handling
        }
        return result;
    }
}

