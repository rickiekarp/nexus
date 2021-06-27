#include <Speaker.h>

using namespace std;
using namespace ChapterOne;

int main(int argc, char *argv[]) {
    int option;
    cout << "Please enter an option (0-1): ";
    cin >> option;

    Speaker* speaker = new Speaker();

    switch(option) {
        case 0:
            speaker -> sayHello();
            break;
        case 1:
            speaker -> printInput();
            break;
        default: cout << "Error";
    }

    return 0;
}
