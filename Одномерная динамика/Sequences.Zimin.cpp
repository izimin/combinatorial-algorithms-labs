#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

int main() {
	int n;
	cin >> n;

	vector<int> inp(n);
	vector<int> opt(n, 1);

	for (auto i = 0; i < n; i++) cin >> inp[i];

	for (auto i = 1; i < n; i++) {
		for (auto j = 0; j < i; j++)
			if (inp[j] < inp[i] && opt[j] + 1 > opt[i])
				opt[i] = opt[j] + 1;
	}

	cout << *max_element(opt.begin(), opt.end()) << endl;

	return 0;
}