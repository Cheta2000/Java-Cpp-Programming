/**
 * klasa naszego watku
 */

public class MyThread extends Thread {
    //Panel-nasz panel
    private MyPanel Panel;
    /*i- i-te pole
    k-szybkosc zmian koloru
     */
    private int i, k;

    /**
     * konstruktor watku
     *
     * @param i     i-te pole
     * @param k     szybkosc zmian koloru
     * @param Panel nasz panel
     */
    MyThread(int i, int k, MyPanel Panel) {
        this.i = i;
        this.Panel = Panel;
        this.k = k;
    }

    /**
     * cialo watku
     */
    public void run() {
        //petla wykonuje sie w nieskonczonosc
        while (true) {
            //late- nasze opoznienia zmiany koloru
            long late = (long) ((Panel.generate() + 0.5) * k);

            try {
                //usypiamy watek na czas late
                sleep(late);
            } catch (Exception ex) {
                System.out.println(ex);
            }
            //synchronizujemy, aby zadan watek nie zaczal dzialania przed zakonczeniem innego
            synchronized (Panel) {
                //ustawiamy kolor pola i-tego pola
                Panel.setColor(i);
            }
        }
    }
}
