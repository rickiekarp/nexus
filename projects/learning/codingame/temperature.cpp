#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

/**
 * See: https://www.codingame.com/training/easy/temperatures
 * 
 * Write a program that prints the temperature closest to 0 among input data. 
 * If two numbers are equally close to zero, positive integer has to be considered closest to zero 
 * (for instance, if the temperatures are -5 and 5, then display 5).
 **/
int main()
{
    int n; // the number of temperatures to analyse
    cin >> n; cin.ignore();

    // no number
    if (n == 0) {
        cout << n << endl;
        return 0;
    }

    int lowestTemperature;
    cin >> lowestTemperature; cin.ignore();

    for (int i = 0; i < n; i++) {
        int temperature; // a temperature expressed as an integer ranging from -273 to 5526
        cin >> temperature; cin.ignore();

        if (abs(temperature) < abs(lowestTemperature) || (temperature > 0 && -temperature == lowestTemperature)) {
            lowestTemperature = temperature;
        }
    }

    cout << lowestTemperature << endl;
}