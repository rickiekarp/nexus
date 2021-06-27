#include "Speaker.h"

using namespace ChapterOne;
using namespace std;

namespace ChapterOne {
    std::string helloWorld = "Hello, world!\n";

    void Speaker::sayHello() {
        cout << helloWorld;
    }

    void Speaker::printInput() {
        cout << "Enter something: ";
        string i;
        cin >> i;
        cout << "You have entered: ";
        cout << i;
    }
}
