#include <iostream>
#include <iomanip>
#include <algorithm>
 
using namespace std;
  
int main() {
    int n;
    cin >> n;
    double r = 100, u = 0, e = 0;
    while (n--) {
        double cur_u, cur_e;
        cin >> cur_u >> cur_e;
        r = max({ r, u * cur_u, e * cur_e });
        u = max({ r / cur_u, u, e * cur_e / cur_u });
        e = max({ r / cur_e, u * cur_u / cur_e, e });
    }
  
    cout << fixed << setprecision(2) << r << endl;
    return 0;
}