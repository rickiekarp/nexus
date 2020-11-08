#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

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

    std::vector<int> strengthsVector;

    for (int i = 0; i < N; i++) {
        int Pi;
        cin >> Pi; cin.ignore();
        strengthsVector.push_back(Pi);
    }

    sort(strengthsVector.begin(), strengthsVector.end(), greater<int>()); 

    int min = INT32_MAX;
    int diff;

    for (int i = 0; i < strengthsVector.size(); i++) {
        diff = strengthsVector[i] - strengthsVector[i+1];
        if (diff < min) {
            min = diff;
        }
    }

    cout << min << endl;
}