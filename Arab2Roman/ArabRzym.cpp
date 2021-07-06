//Programy w javie komiplujemy poleceniem: $ javac [nazwa programu.java], a uruchamiamy $ java [nazwa programu][parametry]
//Programy w C++ kompilujemy poleceniem: & g++ -o [nazwa programu][nazwaprogramu.cpp], a uruchamiamy: $ ./[nazwa programu][parametry]


#include <bits/stdc++.h>
using namespace std;

class ArabRzym{
	private:
	static string rzymskie[];
	static int arabskie[];
	//algorytm zaczyna od konca tablicy i poczatku napisu:
    //gdy jestesmy na parzystym indeksie tablicy, pobiera jeden znak napisu i porownuje go ze znakami w tablicy rzym
    //jesli znajdzie dopasowanie skraca napis o 1, zwieksza wynik o odpowiednia wartosc z tablicy arabskie i sprawdza dalej, jesli nie znajdzie zmniejsza indeks tablicy rzymskie o 1 i dalej to samo        
    //gdy jestesmy na nieparzystym indeksie tablicy, pobiera dwa znaki napisu i porownuje go ze znakami w tablicy rzym
    //jesli znajdzie dopasowanie skraca napis o 2, zwieksza wynik o odpowiednia wartosc z tablicy arabskie i sprawdza dalej, jesli nie znajdzie zmniejsza indeks tablicy rzymskie o 1 i dalej to samo 
	public:
	static int rzym2arab(string rzym){
	    //licznik- sprawdza ile razy wystapil ten sam znak(przyda sie przy bledach) 
	    //pocz- indeks poczatkowy pozostalej czesci zamienianego napisu
	    //aktAr-wartosc ktora dodajemy gdy znajdziemy dopasowanie(przyda sie przy bledach)
	    //poprzednie- wartosc ktora znajduje sie w miejscu w tablicy o 1 wiekszym indeksie niz aktAr(przyda sie przy bledach)
	    int i=12,licznik=0,pocz=0,wynik=0,dlugosc=rzym.length(),aktAr=0,poprzednie=3001;
	    //pomoc-porownywana czesc napisu
        //aktRz- rzysmki odpowiednik aktAr(przyda sie przy bledach)
		string pomoc="",aktRz="";
		//powtarzamy petle az przejdziemy przez caly napis lub cala tablic
		while(i>=0 && dlugosc>0){
			if(i%2==1 && dlugosc>1) pomoc=rzym.substr(pocz,2);
			else pomoc=rzym.substr(pocz,1);
			//znalezione dopasowanie
			if(pomoc==rzymskie[i]){
			    //BLAD: gdy znaleziona wartosc zsumowana z aktAr,czyli ta ktora wczesniej dodawalismy jest wieksza lub rowna od liczby poprzednia
			    // oznacza to ze je obie daloby sie zastapic przez liczbe poprzednia, czyli jesy to nieprawidlowa konstrukcja
                //PRZYKLAD: podanie CDC zamiast M
				if(aktAr+arabskie[i]>=poprzednie){
					string ArabRzymException="Nieprawidlowa konstrukcja liczby";
					throw ArabRzymException;
				}
				if(i<12) poprzednie=arabskie[i+1];
				licznik++;
				//BLAD: znak wystapil po sobie wiecej niz 3 razy
				if(licznik>3){
					string ArabRzymException="Wystapily po sobie wiecej niz 3 takie same znaki";
					throw ArabRzymException;
				}
				aktAr=arabskie[i];
				aktRz=rzymskie[i];
				wynik=wynik+aktAr;
				//przesuwamy indeks poczatkowy napisu
				pocz=pocz+rzymskie[i].length();
			    //zmniejszamy dlugosc napisu
				dlugosc=dlugosc-rzymskie[i].length();
			}
			else{
				i--;
				licznik=0;
			}
		}
		//napis nie skrocil sie calkowicie
		if(dlugosc!=0){
		    //spr- sprawdza czy Czesc napisu pozostala w zmiennej pocz jest w systemie rzymskim (true-jest,false-nie)
			bool spr=false;
			for(int j=0;j<13;j=j+2){
				if(pomoc==rzymskie[j]) spr=true;
			}
			//BLAD: jesli pozostala czesc napisu jest w systeme rzymskim to znaczy ze po znaku o mniejszej wartosci pojawil sie znak o wiekszej wartosci
           //bo algorytm ominal jego znak w tablicy rzymskie i juz nie zawrocil 
			if(spr){
				string ArabRzymException="Po znaku o mniejszej wartosci wsytapil znak o wyzszej wartosci";
				throw ArabRzymException;
			}
			//BLAD: algorytm nie znalazl takiego znaku w tablicy rzymskie
			else{
				string ArabRzymException="To nie jest znak w systemie rzymskim";
				throw ArabRzymException;
			}
		}
		return wynik;
	}
	//algorytm sprawdza czy od liczby mozemy odjac wartosc w i-tym miejscu tablicy arabskie(czy to co zostanie bedzie wieksze rowne 0)
    //zaczynamy od konca tablicy arabskie
    //jesli mozemy, to odejmujemy i do napisu wynikowego dodajemy znak w z i-tego miejsca tablicy rzymskie
    //jesli nie mozemy zmiejszamy indeks i i sprawdzamy dalej
	static string arab2rzym(int arab){
        //BLAD: w systemie rzymskim najwieksza liczba to 3999
		if(arab>3999){
			string ArabRzymException="Zly zakres liczby: 0-3999";
			throw ArabRzymException;
		}
		int i=12;
		string wynik="";
		//petle powtarzamy do momentu gdy liczba bedzie rowna 0 lub przejdziemy przez cala tablice  
		while(i>=0 && arab>0){
			if(arab-arabskie[i]>=0){
				arab=arab-arabskie[i];
				wynik=wynik+rzymskie[i];
			}
			else i--;
		}
		return wynik;
	}
};

//incjujemy tablice statyczne
string ArabRzym::rzymskie[]={"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
int ArabRzym::arabskie[]={1,4,5,9,10,40,50,90,100,400,500,900,1000};

int main(int argc,char *argv[]){
    if(argc==1) cout<<"Brak parametrow"<<endl;
	for(int i=1;i<argc;i++){
		try{
            int n=stoi(argv[i]);
            cout<<argv[i]<<": "<<ArabRzym::arab2rzym(n)<<endl;
		}
		catch(string w){
		    cout<<": "<<w<<endl;
		}
        catch(invalid_argument ex){
            try{
				cout<<argv[i]<<": "<<ArabRzym::rzym2arab(argv[i])<<endl;
			}
			catch(string w){
				cout<<": "<<w<<endl;
			}
	    }
    }
        return 0;
}
