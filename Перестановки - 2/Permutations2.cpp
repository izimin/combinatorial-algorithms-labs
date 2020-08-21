#define  _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <string>
#include <algorithm>

using namespace std;

int main() {
	freopen("INPUT.TXT", "r", stdin);
	freopen("OUTPUT.TXT", "w", stdout);
	string p;
	cin >> p;
	sort(p.begin(), p.end());
	const int n = p.length();
	while (true) {
		cout << p << "\n";
		int i = n - 2, j = n - 1;
		
		while (i > -1 && p[i] >= p[i + 1]) i--;
		if (i < 0) break;
		
		while (p[i] >= p[j]) j--;
		swap(p[i], p[j]);
		
		int k = i + 1, m = n - 1;
		while (k < m) 
			swap(p[k++], p[m--]);
	}
}