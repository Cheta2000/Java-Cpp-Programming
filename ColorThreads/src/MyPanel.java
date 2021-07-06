import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * klasa oblugujaca panel z prostokatami
 */
public class MyPanel extends JPanel {
    //n,m-wymiary
    private int n, m;
    /*p-prawdopodobienstwo
    x,y-wspolrzedne poczatku pierwszego pola
    */
    private float p = 0, x = 0, y = 0;
    //tablica zawierajaca kolory pol
    private Color Colors[];
    //tablica zawierajaca informacje czy pole jest aktywne(0) czy nieaktywne(1)
    public int active[];
    //pole-prostokat
    private Rectangle rectangle = new Rectangle(0, 0, 0, 0);
    //lista z polami
    private ArrayList<Rectangle> rectangleList = new ArrayList<Rectangle>();

    /**
     * konstruktor panelu, ustawiamy wymiary i prawdopodobienstwo, tworzymy i inicjujemy tablice i dodajemy sluchaczy
     *
     * @param n ilosc pol w poziomie
     * @param m ilosc pol w pionie
     * @param p prawdopodobienstwo
     */
    MyPanel(int n, int m, float p) {
        this.n = n;
        this.m = m;
        this.p = p;
        Colors = new Color[n * m];
        for (int i = 0; i < n * m; i++) {
            Colors[i] = new Color(0, 0, 0);
        }
        active = new int[n * m];
        addMouseMotionListener(new MovingAdapter());
        addMouseListener(new MovingAdapter());
        addComponentListener(new ScalingAdapter());
    }

    /**
     * klasa oblusgujaca pola-prostokaty
     */
    class Rectangle extends Rectangle2D.Float {
        /**
         * ustawienie wymiarow prostokata
         *
         * @param x wspolrzedna x poczatku
         * @param y wspolrzedna y poczatku
         * @param w szerokosc
         * @param h wysokosc
         */
        Rectangle(float x, float y, float w, float h) {
            setRect(x, y, w, h);
        }

        /**
         * metoda sprawdza czy jestesmy w obszarze pola
         *
         * @param x sprawdzana wspolrzedna x
         * @param y sprawdzana wspolrzedna y
         * @return true, jesli jestesmy w obszarze pola, false w przeciwnym wypadku
         */
        public boolean isHit(float x, float y) {
            return getBounds2D().contains(x, y);
        }

        /**
         * dodawanie wspolrzednej x
         *
         * @param x ile mamy dodac
         */
        public void addX(float x) {
            this.x += x;
        }

        /**
         * dodawanie wspolrzednej y
         *
         * @param y ile mamy dodac
         */
        public void addY(float y) {
            this.y += y;
        }

        /**
         * dodawanie szerokosci
         *
         * @param w ile mamy dodac
         */
        public void addWidth(float w) {
            this.width += w;
        }

        /**
         * dodawanie wysokosci
         *
         * @param h ile mamy dodac
         */
        public void addHeight(float h) {
            this.height += h;
        }

    }

    /**
     * generarator liczb losowych od 0 do 1
     *
     * @return losowa liczba typu double od 0 do 1
     */
    public double generate() {
        Random generator = new Random();
        double score = generator.nextDouble();
        return score;
    }

    /**
     * klasa odpowiedzalna za czynnosc myszki
     */
    class MovingAdapter extends MouseAdapter {
        //wspolrzedne x,y kliknieca myszki
        float x, y;

        /**
         * myszka kliknieta
         *
         * @param e wydarzenie myszki
         */
        public void mouseClicked(MouseEvent e) {
            //pobieramy wspolrzedne miejsca klikniecia
            x = e.getX();
            y = e.getY();
            doActive(e);
        }

        /**
         * aktywowanie i dezaktywowanie pola
         *
         * @param e wydarzenie myszki
         */
        private void doActive(MouseEvent e) {
            //petla po naszej liscie z polami
            for (Rectangle current : rectangleList) {
                //jesli pole zostalo klikniete
                if (current.isHit(x, y)) {
                    //ustawiamy pole jako nieaktywne
                    active[rectangleList.indexOf(current)] = 1;
                    //ustawiamy kolor pola na czarny
                    Colors[rectangleList.indexOf(current)] = Color.BLACK;
                }
            }
            repaint();
        }
    }

    /**
     * klasa odpowiedzalna za skalowanie okna
     */
    class ScalingAdapter extends ComponentAdapter {
        /**
         * rozmiar okna zostal zmieniony lub zostalo stworzone
         *
         * @param e wydarzenie okna
         */
        public void componentResized(ComponentEvent e) {
            doAdapt();
        }

        /**
         * dostosowywanie rozmiaru pol do okna lub ich tworzenie
         */
        private void doAdapt() {
            //w,h- poczatkowe wymiary
            float w = 500 / n, h = 470 / m;
            //jesli jeszcze nie ma pol
            if (rectangleList.isEmpty()) {
                //ruch po wierszach
                for (int i = 0; i < m; i++) {
                    //ruch po kolumnach
                    for (int j = 0; j < n; j++) {
                        //tworzymi pole
                        rectangle = new Rectangle(x, y, w, h);
                        //dodajemy je do listy
                        rectangleList.add(rectangle);
                        //zwiekszamy wspolrzedna x o szerokosc pola
                        x = x + w;
                    }
                    //po zmianie wiersza zwiekszamy wspolrzedna y o wysokosc pola
                    y = y + h;
                    //po zmianie wiersza znowu zaczynamy rysowac od x=0
                    x = 0;
                }
                //wypelnianie pol losowymi kolorami
                for (Rectangle current : rectangleList) {
                    //losowanie kolorow
                    int R = (int) (generate() * 256);
                    int G = (int) (generate() * 256);
                    int B = (int) (generate() * 256);
                    //wrzucamy kolor do tablicy
                    Colors[rectangleList.indexOf(current)] = new Color(R, G, B);
                }
            }
            //gdy juz sa utworzone pola
            else {
                //zerujemy wspolrzedne poczatkowe
                x = 0;
                y = 0;
                //c- zmienna pomocnicza, sprawdza kiedy "zmieniamy wiersz"
                int c = 0;
                for (Rectangle current : rectangleList) {
                    //poieramy nowa szerokosc i wysokosc okna
                    w = getWidth();
                    h = getHeight();
                    //dw,dh- szerokosc i wysokosc o jaka trzeba zmniejszych/zwiekszyc kazde pole
                    float dw = (w - (float) current.getWidth() * n) / n;
                    float dh = (h - (float) current.getHeight() * m) / m;
                    //jesli szerokosc sie zmienila
                    if (dw != 0) {
                        current.addX(x);
                        current.addWidth(dw);
                    }
                    //jesli wysokosc sie zmienila
                    if (dh != 0) {
                        current.addY(y);
                        current.addHeight(dh);
                    }
                    //przesuwamy kazda kolejna figure w wierszu
                    x = x + dw;
                    c++;
                    //gdy przechodzimy do nowego wiersza
                    if (c % n == 0) {
                        //zaczynamy znowu od x=0
                        x = 0;
                        //przesuwamy kazda kolejna figure w kolumnie
                        y = y + dh;
                    }
                }

            }
        }

    }

    /**
     * rysowanie
     *
     * @param g obiekt graficzny
     */
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (Rectangle current : rectangleList) {
            //ustawiamy kolor pola
            g2d.setColor(Colors[rectangleList.indexOf(current)]);
            //rysujemy pole wypelnione kolorem
            g2d.fill(current);
        }
    }

    /**
     * wywoluje sie przy repaint()
     *
     * @param g obiekt graficzny
     */
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    /**
     * ustawienie sredniego koloru z sasiadow
     *
     * @param i i-te pole
     * @return sredni kolor
     */
    private Color aveColor(int i) {
        /* amount- ile sasiadow jest aktywnych
        choice-sasiad
         */
        int amount = 0, choice = 0;
        //nasz zwracany kolor
        Color clr;
        //tablica z sasiadami
        int next[] = new int[4];
        //R,G,B- wyznacza kolor
        int R = 0, G = 0, B = 0;
        //szukanie sasiada z prawej strony
        if (((i + 1) % n) != 0) choice = i + 1;
        else choice = i + 1 - n;
        //jesli jest aktywny
        if (active[choice] == 0) {
            //dodajemy go do tablicy
            next[0] = choice;
            //zwiekszamy licznik sasiadow
            amount++;
        }
        //jak nie jest aktywny nie bedziemy go brali pod uwage
        else next[0] = -1;
        //reszta dziala podobnie
        if ((i % n) != 0) choice = i - 1;
        else choice = i - 1 + n;
        if (active[choice] == 0) {
            next[1] = choice;
            amount++;
        } else next[1] = -1;
        if ((i - n) >= 0) choice = i - n;
        else choice = i + n * (m - 1);
        if (active[choice] == 0) {
            next[2] = choice;
            amount++;
        } else next[2] = -1;
        if ((i + n) <= (n * m - 1)) choice = i + n;
        else choice = i - n * (m - 1);
        if (active[choice] == 0) {
            next[3] = choice;
            amount++;
        } else next[3] = -1;
        //jesli istenieje jakis aktywny sasiad
        if (amount > 0) {
            //liczymy srednia kolorow z sasiadow
            for (int j = 0; j < 4; j++) {
                if (next[j] >= 0) {
                    R = R + Colors[next[j]].getRed();
                    G = G + Colors[next[j]].getGreen();
                    B = B + Colors[next[j]].getBlue();
                }
            }
            R = R / amount;
            G = G / amount;
            B = B / amount;
            clr = new Color(R, G, B);
        }
        //jesli nie ma zadnego sasiada nie zmieniamy koloru
        else clr = Colors[i];
        return clr;
    }

    /**
     * ustawiamy kolor naszych pol
     *
     * @param i i-te pole
     */
    public void setColor(int i) {
        //jesli pole jest aktywne
        if (active[i] == 0) {
            //zmiana koloru w zaleznosci od prawdopodobienstwa
            if (generate() < p) {
                //kolor losowy
                int R = (int) (generate() * 256);
                int G = (int) (generate() * 256);
                int B = (int) (generate() * 256);
                Colors[i] = new Color(R, G, B);
            }
            //kolor ze sredniej kolorow sasiadow
            else Colors[i] = aveColor(i);
        }
        //zatrzymujemy watek
        else {
            try {
                wait();
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
        //rysujemy
        repaint();
    }
}