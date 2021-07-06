//Programy w javie komiplujemy poleceniem: $ javac [nazwa programu.java], a uruchamiamy $ java [nazwa programu][parametry]
//Programy w C++ kompilujemy poleceniem: & clang++ -o [nazwa programu][nazwaprogramu.cpp], a uruchamiamy: $ ./[nazwa programu][parametry]


class ArabRzymException extends Exception{
	ArabRzymException(String komunikat){
		super(komunikat);
	};
}

public class ArabRzym{
	private static String[] rzymskie={ "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
	private static int[] arabskie={1,4,5,9,10,40,50,90,100,400,500,900,1000};
	//algorytm zaczyna od konca tablicy i poczatku napisu:
	//gdy jestesmy na parzystym indeksie tablicy, pobiera jeden znak napisu i porownuje go ze znakami w tablicy rzym
	//jesli znajdzie dopasowanie skraca napis o 1, zwieksza wynik o odpowiednia wartosc z tablicy arabskie i sprawdza dalej, jesli nie znajdzie zmniejsza indeks tablicy rzymskie o 1 i dalej to samo
	//gdy jestesmy na nieparzystym indeksie tablicy, pobiera dwa znaki napisu i porownuje go ze znakami w tablicy rzym
	//jesli znajdzie dopasowanie skraca napis o 2, zwieksza wynik o odpowiednia wartosc z tablicy arabskie i sprawdza dalej, jesli nie znajdzie zmniejsza indeks tablicy rzymskie o 1 i dalej to samo 
	public static int rzym2arab(String rzym) throws ArabRzymException{
		//licznik- sprawdza ile razy wystapil ten sam znak(przyda sie przy bledach) 
		//pocz- indeks poczatkowy pozostalej czesci zamienianego napisu
		//aktAr-wartosc ktora dodajemy gdy znajdziemy dopasowanie(przyda sie przy bledach)
		//poprzednie- wartosc ktora znajduje sie w miejscu w tablicy o 1 wiekszym indeksie niz aktAr(przyda sie przy bledach)
		int i=12,licznik=0,pocz=0,wynik=0,dlugosc=rzym.length(),aktAr=0,poprzednie=3001;
		//pomoc-porownywana czesc napisu
		//aktRz- rzysmki odpowiednik aktAr(przyda sie przy bledach)
		String pomoc="",aktRz="";
		//powtarzamy petle az przejdziemy przez caly napis lub cala tablice
		while(i>=0 && dlugosc>0){
			if(i%2==1 && dlugosc>1) pomoc=rzym.substring(pocz,pocz+2);
			else pomoc=rzym.substring(pocz,pocz+1);
			//znalezione dopasowanie
			if(pomoc.equals(rzymskie[i])){
				//BLAD: gdy znaleziona wartosc zsumowana z aktAr,czyli ta ktora wczesniej dodawalismy jest wieksza lub rowna od liczby poprzednia
				// oznacza to ze je obie daloby sie zastapic przez liczbe poprzednia, czyli jesy to nieprawidlowa konstrukcja
				//PRZYKLAD: podanie CDC zamiast M
				if(aktAr+arabskie[i]>=poprzednie) throw new ArabRzymException("Nieprawidlowa konstrukcja liczby: "+aktRz+rzymskie[i]);
				if(i<12) poprzednie=arabskie[i+1];
				licznik++;
				//BLAD: znak wystapil po sobie wiecej niz 3 razy
				if(licznik>3) throw new ArabRzymException("Wystapily po sobie wiecej niz 3 takie same znaki: "+rzymskie[i]);
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
		boolean spr=false;
		for(int j=0;j<13;j=j+2){
			if(pomoc.contains(rzymskie[j])) spr=true;
		} 
		//BLAD: jesli pozostala czesc napisu jest w systeme rzymskim to znaczy ze po znaku o mniejszej wartosci pojawil sie znak o wiekszej wartosci
		//bo algorytm ominal jego znak w tablicy rzymskie i juz nie zawrocil 
		if(spr) throw new ArabRzymException("Po znaku o mniejszej wartosci wystapil znak o wyzszej wartosci: "+pomoc);
		//BLAD: algorytm nie znalazl takiego znaku w tablicy rzymskie
		else throw new ArabRzymException("To nie jest znak w systemie rzymskim: "+pomoc);
	}
	return wynik;
}

	//algorytm sprawdza czy od liczby mozemy odjac wartosc w i-tym miejscu tablicy arabskie(czy to co zostanie bedzie wieksze rowne 0)
	//zaczynamy od konca tablicy arabskie
	//jesli mozemy, to odejmujemy i do napisu wynikowego dodajemy znak w z i-tego miejsca tablicy rzymskie
	//jesli nie mozemy zmiejszamy indeks i i sprawdzamy dalej
	public static String arab2rzym(int arab) throws ArabRzymException{
		//BLAD: w systemie rzymskim najwieksza liczba to 3999
		if(arab>3999) throw new ArabRzymException("Zly zakres liczby: 0-3999");
		int i=12;
		String wynik="";
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
}

