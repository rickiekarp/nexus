#include <iostream>
#include <string>
#include <vector>
#include <algorithm>
#include <list>  

using namespace std;

/**
 * See: https://www.codingame.com/training/easy/horse-racing-duals
 * 
 * Casablanca’s hippodrome is organizing a new type of horse racing: duals. 
 * During a dual, only two horses will participate in the race. In order for the race to be interesting, 
 * it is necessary to try to select two horses with similar strength.
 * Write a program which, using a given number of strengths, identifies the two closest strengths and shows their difference with an integer (≥ 0).
 **/
int main()
{
    int N;
    cin >> N; cin.ignore();

    std::vector<int> horseVector;

    // populate vector
    for (int i = 0; i < N; i++) {
        int Pi;
        cin >> Pi; cin.ignore();
        horseVector.push_back(Pi);
    }

    sort(horseVector.begin(), horseVector.end(), greater<int>()); 

    int min = INT32_MAX;
    int diff;

    // calculate diff
    for (int i = 0; i < horseVector.size(); i++) {
        diff = horseVector[i] - horseVector[i+1];
        if (diff < min) {
            min = diff;
        }
    }

    cout << min << endl;
}