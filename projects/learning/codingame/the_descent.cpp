/**
 * See: https://www.codingame.com/training/easy/the-descent
 * The Goal
 *  Destroy the mountains before your starship collides with one of them. For that, shoot the highest mountain on your path.
 *
 * Rules
 *  At the start of each game turn, you are given the height of the 8 mountains from left to right.
 *  By the end of the game turn, you must fire on the highest mountain by outputting its index (from 0 to 7).
 *
 *  Firing on a mountain will only destroy part of it, reducing its height. Your ship descends after each pass.  
 **/

#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

/**
 * The while loop represents the game.
 * Each iteration represents a turn of the game
 * where you are given inputs (the heights of the mountains)
 * and where you have to print an output (the index of the mountain to fire on)
 * The inputs you are given are automatically updated according to your last actions.
 **/
int main()
{
    // game loop
    while (1) {
        int maxHeight = 0;
        int nextMountainIndex = 0;
        
        for (int i = 0; i < 8; i++) {
            int mountainH; // represents the height of one mountain.
            cin >> mountainH; cin.ignore();
            
            if (mountainH > maxHeight) {
                maxHeight = mountainH;
                nextMountainIndex = i;
            }
        }

        // Write an action using cout. DON'T FORGET THE "<< endl"
        // To debug: cerr << "Debug messages..." << endl;
        cout << nextMountainIndex << endl;
    }
}