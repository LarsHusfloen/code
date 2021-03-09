//----------------------------------------------------------------------------
 //
 // finnMoms.cpp
 //
 // Program som beregner moms

 //
 #include <iostream>
 using namespace std;
 int main() {
 const double moms = 25.0;
 double medMoms;
 cout << "Skriv beløpet med moms: ";
 cin >> medMoms;
 double utenMoms = medMoms / (100.0 + moms) * 100.0;
 double momsBeloep = medMoms - utenMoms;
 cout << "Uten moms er beløpet: " << utenMoms << " kroner." << endl;
 cout << "Det vil si at momsen er: " << momsBeloep << " kroner." << endl;
 return 0;
 } // main