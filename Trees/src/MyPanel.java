import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * panel
 */
public class MyPanel extends JPanel {
    //typ drzewa
    String T;
    //drzewa
    Tree<Integer> i;
    Tree<String> s;
    Tree<Double> d;
    //listy z ksztatlami i elementami ktore symbolizuja drzewo
    ArrayList<Rectangle> rectangleList = new ArrayList<Rectangle>();
    ArrayList<Line2D.Float> lineList = new ArrayList<Line2D.Float>();
    ArrayList<Integer> integerList = new ArrayList<Integer>();
    ArrayList<String> stringList = new ArrayList<String>();
    ArrayList<Double> doubleList = new ArrayList<Double>();
    //oznaczony znaleziony element ktory mamy podswietlic na czerwono
    int signI;
    String signS;
    double signD;
    //licznik, podswietelenie ma sie wykonac tylko raz
    int counter = 0;

    /**
     * @param t typ drzewa
     */
    public void setT(String t) {
        T = t;
    }

    /**
     * @param i drzewo Integer
     */
    public void setI(Tree<Integer> i) {
        this.i = i;
    }

    /**
     * @param s drzewo String
     */

    public void setS(Tree<String> s) {
        this.s = s;
    }

    /**
     * @param d drzewo Double
     */

    public void setD(Tree<Double> d) {
        this.d = d;
    }

    /**
     * @param i element drzewa i do podswietlenia
     */
    public void setSignI(int i) {
        signI = i;
        counter = 0;
        repaint();
    }

    /**
     * @param s element drzewa s do podswietlenia
     */
    public void setSignS(String s) {
        signS = s;
        counter = 0;
        repaint();
    }

    /**
     * @param d element drzewa d do podswietlenia
     */
    public void setSignD(Double d) {
        signD = d;
        counter = 0;
        repaint();
    }

    /**
     * czyscimy liste elementow
     */
    public void clearList() {
        integerList.clear();
        stringList.clear();
        doubleList.clear();
    }

    /**
     * czyscimy liste ksztaltow i elementow i rysyjemy pusty panel
     */
    public void reset() {
        rectangleList.clear();
        lineList.clear();
        integerList.clear();
        stringList.clear();
        doubleList.clear();
        repaint();
    }

    /**
     * rysujemy panel bez zaznaczenia kolorem elementu
     */
    public void clearColor() {
        repaint();
    }

    /**
     * tworzymy drzewo i z ksztaltow, przechodzimy po nim inOrder
     *
     * @param x wspolrzedna x prostokata
     * @param y wspolrzedna y prostokata
     * @param w wezel drzewa
     * @param a mowi nam czy mamy czyscic liste i tworzyc ja na nowa
     * @param b mowi nam czy linia miedzy prostokatami ma isc w lewo czy w prawo
     */
    public void setListI(int x, int y, TreeElement<Integer> w, int a, int b) {
        repaint();
        //przy pierwszym wejsciu do funkcji czyscimy listy
        if (a == 0) {
            rectangleList.clear();
            lineList.clear();
        }
        //jesli wezel jest pusty konczymy
        if (w == null) return;
        //idziemy do lewego poddrzewa
        setListI(x - 100, y + 100, w.left, 1, 1);
        //jesli funkcja nie wykonuje sie pierwszy raz
        if (b != 0) {
            //tworzymi linie w okreslony sposob i dodajemy ja do listy
            Line2D.Float l = new Line2D.Float(x + 20, y + 20, x + 20 + b * 100, y + 20 - 100);
            lineList.add(l);
        }
        //tworzymy prostokat i dodajemy go do listy
        Rectangle r = new Rectangle(x, y, 40, 40);
        rectangleList.add(r);
        //dodajemy do listy element drzewa
        integerList.add(w.element);
        //idziemy do prawego poddrzewa
        setListI(x + 100, y + 100, w.right, 1, -1);

    }

    /**
     * to samo dla drzewa s
     */
    public void setListS(int x, int y, TreeElement<String> w, int a, int b) {
        repaint();
        if (a == 0) {
            rectangleList.clear();
            lineList.clear();
        }
        if (w == null) return;
        setListS(x - 100, y + 100, w.left, 1, 1);
        if (b != 0) {
            Line2D.Float l = new Line2D.Float(x + 20, y + 20, x + 20 + b * 100, y + 20 - 100);
            lineList.add(l);
        }
        Rectangle r = new Rectangle(x, y, 40, 40);
        rectangleList.add(r);
        stringList.add(w.element);
        setListS(x + 100, y + 100, w.right, 1, -1);

    }

    /**
     * to samo dla drzewa d
     */
    public void setListD(int x, int y, TreeElement<Double> w, int a, int b) {
        repaint();
        if (a == 0) {
            rectangleList.clear();
            lineList.clear();
        }
        if (w == null) return;
        setListD(x - 100, y + 100, w.left, 1, 1);
        if (b != 0) {
            Line2D.Float l = new Line2D.Float(x + 20, y + 20, x + 20 + b * 100, y + 20 - 100);
            lineList.add(l);
        }
        Rectangle r = new Rectangle(x, y, 40, 40);
        rectangleList.add(r);
        doubleList.add(w.element);
        setListD(x + 100, y + 100, w.right, 1, -1);

    }

    /**
     * metoda rysujaca
     *
     * @param g obiekt graficzny
     */
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //poruszamy sie po liscie prostokatow
        for (int i = 0; i < rectangleList.size(); i++) {
            Rectangle current = rectangleList.get(i);
            //ustawiamy kolor
            g2d.setColor(Color.CYAN);
            //obsluga zmiany koloru dla znalezionego elementu
            if (T.equals("I") && counter == 0 && integerList.get(i) == signI) {
                g2d.setColor(Color.RED);
                counter++;
            }
            if (T.equals("S") && counter == 0 && stringList.get(i).equals(signS)) {
                g2d.setColor(Color.RED);
                counter++;
            }
            if (T.equals("D") && counter == 0 && doubleList.get(i) == signD) {
                g2d.setColor(Color.RED);
                counter++;
            }
            //rysujemy wypelniony prostokat
            g2d.fill(current);
            //rysujemy odpowiednie linie
            if (i < lineList.size()) {
                g2d.setColor(Color.GREEN);
                g2d.draw(lineList.get(i));
            }
            //ustawiamy kolor
            g2d.setColor(Color.BLACK);
            //wpisujemy w prostokat odpowiedni element
            if (T.equals("I")) {
                g2d.drawString(Integer.toString(integerList.get(i)), (int) current.getX(), (int) current.getY() + 10);
            }
            if (T.equals("S")) {
                g2d.drawString(stringList.get(i), (int) current.getX() + 5, (int) current.getY() + 15);
            }
            if (T.equals("D")) {
                g2d.drawString(Double.toString(doubleList.get(i)), (int) current.getX() + 5, (int) current.getY() + 15);
            }
        }
    }

    /**
     * @param g obiekt graficzny
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}
