/**
 * @author Mateusz Checinski
 */
//POPRAWIONE

import javax.swing.*;

/**
 * klasa obslugujaca nasz program
 */
public class Colors {
    /**
     * metoda main
     *
     * @param args parametry
     */
    public static void main(String[] args) {
        /* n,m-wymiary
        k-szybkosc zmian koloru
         */
        int n, m, k;
        //p-prawdopodobienstwo
        float p;
        try {
            //pobieramy potrzebne parametry
            n = Integer.parseInt(args[0]);
            m = Integer.parseInt(args[1]);
            k = Integer.parseInt(args[2]);
            p = Float.parseFloat(args[3]);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Za malo parametrow");
            return;
        } catch (NumberFormatException ex) {
            System.out.println("Zly format parametrow");
            return;
        }
        if (args.length > 4) {
            System.out.println("Za duzo parametow");
            return;
        }
        if (n <= 0 || m <= 0) {
            System.out.println("Wymiary musza byc dodatnie");
            return;
        }
        if (k < 0) {
            System.out.println("Opoznienie nie moze byc ujemne");
            return;
        }
        if (p < 0 || p > 1) {
            System.out.println("Prawdopodobiensto musi byc w zakresie [0,1]");
            return;
        }
        //tworzymy okno
        JFrame MyFrame = new JFrame("Colors");
        //tworzymy tablice watkow
        MyThread t[] = new MyThread[n * m];
        //dodajemy operacje zamnkniecia okna
        MyFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //ustawiamy wymiar okna
        MyFrame.setBounds(100, 100, 500, 500);
        //tworzymy panel
        MyPanel Panel = new MyPanel(n, m, p);
        //dodajemy panel do okna
        MyFrame.add(Panel);
        //tworzymy watek dla kazdego pola i uruchamiamy go
        for (int i = 0; i < n * m; i++) {
            t[i] = new MyThread(i, k, Panel);
            t[i].start();
        }
        //ustawiamy nasze okno jako widoczne
        MyFrame.setVisible(true);

    }
}
