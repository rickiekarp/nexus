/**
 * Given a time in AM/PM format, convert it to military (24-hour) time.
 * Note: 
 * - 12:00:00AM on a 12-hour clock is 00:00:00 on a 24-hour clock.
 * - 12:00:00PM on a 12-hour clock is 12:00:00 on a 24-hour clock.
 * Example
 * Return '12:01:00'.
 * Return '00:01:00'.
**/
#include <bits/stdc++.h>

using namespace std;

/*
 * Complete the 'timeConversion' function below.
 *
 * The function is expected to return a STRING.
 * The function accepts STRING s as parameter.
 */

string timeConversion(string s) {
    string hourAsString = s.substr(0, 2);
    int hourAsInt = stoi(hourAsString);
    size_t pos = s.find("PM");
    
    // The position of the first character of the first match.
    // If no matches were found, the function returns string::npos.
    if (pos != string::npos) {
        if (hourAsInt + 12 < 24)
            // add 12 to convert from AM/PM format to military time
            hourAsString = to_string(hourAsInt + 12);
    } else {
        if (hourAsInt == 12)
            hourAsString = "00";
    }
    
    // overwrite current hour with new hourAsString
    s = s.replace(0, 2, hourAsString);
    // remove the AM/PM from the end
    s = s.erase(s.size() - 2);
    return s;
}

int main()
{
    ofstream fout(getenv("OUTPUT_PATH"));

    string s;
    getline(cin, s);

    string result = timeConversion(s);

    fout << result << "\n";

    fout.close();

    return 0;
}
