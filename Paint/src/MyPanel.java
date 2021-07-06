import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class MyPanel extends JPanel {
    //zmienne zajmujace sie wspolrzednymi, wysokosciami figur itp.
    private float x1 = 0, y1 = 0, x2 = 0, y2 = 0, w = 0, h = 0;
    //shape ksztalt ktory jest wybrany
    private String shape = "";
    /*
     active przyjmuje wartosc 1 jesli figury sa aktywne,0 jesli nie
     side ktory bok trojkata jest aktualnie tworzony,
     ews przyjmuje 1 gdy powstala nowa figura, 0 gdy nie powstala
     */
    private int active = 0, news = 0;
    //color[] tablica kolorow, na i-tym polu jest kolor i-tego ksztaltu
    private Color color[] = new Color[30];
    //index[] tablica indeksow ksztaltow ktore kolorujemy, i-te miejsce tablicy to i-ty ksztalt i przyjmuje albo 1-gdy kolorujemy ten ksztalt albo 0-gdy nie kolorujemy
    private int index[] = new int[30];
    //indexActive[] tablica indexow ksztaltow aktywnych, dziala podobnie jak ta wyzej
    private int indexActive[] = new int[30];
    /*
     x[] tablica przechowujaca x wspolrzedne wierzcholkow trojkata
     y[] tablica przechowujaca y wspolrzedne wierzcholkow trojkata
     */
    private int x[] = new int[3];
    private int y[] = new int[3];
    //shapeList lista z naszymi ksztaltami
    private ArrayList<Shape> shapeList;
    private JPopupMenu menuPopup = new JPopupMenu();
    private JMenuItem i1 = new JMenuItem("Zmiana koloru");
    /*
     zainicjowanie naszych ksztalow
     circle kolo
     rectangle prostokat
     triangle trojkat
     */
    Ellipse circle = new Ellipse(x1, y1, w, h);
    Rectangle rectangle = new Rectangle(x1, y1, w, h);
    MyPolygon triangle = new MyPolygon(x, y, 3);

    /**
     * w konstruktorze dodajemy sluchaczy myszki, scrolla i menu
     */
    MyPanel() {
        setBackground(Color.WHITE);
        addMouseMotionListener(new MovingAdapter());
        addMouseListener(new MovingAdapter());
        addMouseWheelListener(new ScaleHandler());
        i1.addActionListener(new MouseMenuListener());
        shapeList = new ArrayList<Shape>();
    }

    /**
     * ustawienie wybranego ksztaltu
     *
     * @param shape wybrany ksztalt jako napis
     */
    public void setShape(String shape) {
        this.shape = shape;
        //automatycznie wylacza aktywnosc figur
        active = 0;
    }

    /**
     * ustawienie aktywnosci
     *
     * @param a przyjmuje 1 gdy figury sa aktywne, 0 gdy nie sa
     */
    public void setActive(int a) {
        active = a;
        ///automatycznie wylacza wybrana ksztalt i daje mozliwosc pojawienia sie menu zmiany koloru
        shape = "";
        askColor(a);
    }

    /**
     * kiedy pojawia sie menu zmiany koloru
     *
     * @param a przyjmuje 1 gdy ma sie pojawic menu, 0 gdy ma sie nie pojawiac
     */
    public void askColor(int a) {
        if (a == 1) menuPopup.add(i1);
        else menuPopup.remove(i1);
        setComponentPopupMenu(menuPopup);
    }

    /**
     * ustawienie koloru
     *
     * @param cr wybrany kolor
     */
    public void setColor(Color cr) {
        //counter liczy ile figur kolorujemy(ile bylo aktywnych)
        int counter = 0;
        //gdy zaznaczona jest figura o indeksie i (index[i]=1) to wrzucamy wybrany kolor do tablicy kolory o indeksie i
        for (int i = 0; i < 30; i++) {
            if (index[i] == 1) {
                counter++;
                color[i] = cr;
            }
        }
        if (counter == 0) setBackground(cr);
    }

    /**
     * klasa obslugujaca okrag
     */
    class Ellipse extends Ellipse2D.Float {
        /**
         * @param x wspolrzedna x poczatku figury
         * @param y wspolrzedna y poczatku figury
         * @param w szerokosc figury
         * @param h wysokosc figury
         */
        Ellipse(float x, float y, float w, float h) {
            setFrame(x, y, w, h);
        }

        /**
         * sprawdzanie czy myszka zostala nacisnieta w strefie figury
         *
         * @param x wspolrzedna x gdzie zostala nacisniete mysz
         * @param y wspolrzedna y gdzie zostala nacisnieta mysz
         * @return true jesli (x,y) nalezy do figury, false w przeciwnym wypadku
         */
        public boolean isHit(float x, float y) {
            return getBounds2D().contains(x, y);
        }

        /**
         * dodawanie wspolrzednej x
         *
         * @param x wspolrzedna x o ile zostala przesunieta mysz w stosunku do poczatkowego polozenia
         */
        public void addX(float x) {
            this.x += x;
        }

        /**
         * dodawanie wspolrzednej y
         *
         * @param y wspolrzedna y o ile zostala przesunieta mysz w stosunku do poczatkowego polozenia
         */

        public void addY(float y) {
            this.y += y;
        }

        /**
         * dodawanie szerokosci
         *
         * @param w ile dodajemy szerokosci
         */
        public void addWidth(float w) {
            this.width += w;
        }

        /**
         * dodawanie wysokosci
         *
         * @param h ile dodajemy wysokosci
         */
        public void addHeight(float h) {
            this.height += h;
        }
    }

    /**
     * klasa obslugujaca prostokat
     */
    class Rectangle extends Rectangle2D.Float {
        Rectangle(float x, float y, float w, float h) {
            setRect(x, y, w, h);
        }

        public boolean isHit(float x, float y) {
            return getBounds2D().contains(x, y);
        }

        public void addX(float x) {
            this.x += x;
        }

        public void addY(float y) {
            this.y += y;
        }

        public void addWidth(float w) {
            this.width += w;
        }

        public void addHeight(float h) {
            this.height += h;
        }

    }

    /**
     * klasa obslugujaca trojkat
     */
    class MyPolygon extends Polygon {

        /**
         * @param x wspolrzedne x wierzcholkow trojkata
         * @param y wspolrzedne y wierzcholkow trojkata
         * @param n ilosc wierzcholkow
         */
        MyPolygon(int x[], int y[], int n) {
            super(x, y, n);
        }

        public boolean isHit(float x, float y) {
            return getBounds2D().contains(x, y);
        }

        /**
         * przesuniecie dwoch wierzcholkow trojkata
         *
         * @param amount o ile przesuwamy
         */
        public void reSize(int amount) {
            this.xpoints[2] -= amount / 2;
            this.ypoints[2] += amount;
            this.xpoints[1] += amount / 2;
            this.ypoints[1] += amount;
        }

    }

    /**
     * klasa obslugujaca czynnosci myszki i efekty tych czynnosci
     */
    class MovingAdapter extends MouseAdapter {
        /**
         * myszka nacisnieta
         *
         * @param e wydarzenie myszki
         */
        public void mousePressed(MouseEvent e) {
            x1 = e.getX();
            y1 = e.getY();
        }

        /**
         * myszka kliknieta
         */
        public void mouseClicked(MouseEvent e) {
            //wykonujemy gdy jest wlaczona aktywnosc
            if (active == 1) {
                doActive(e);
                doColor(e);
            }
        }

        /**
         * myszka przeciagnieta
         */
        public void mouseDragged(MouseEvent e) {
            doCreate(e);
            //wykoujemy gdy jest wlaczona aktywnosc i nie zostal wcisniety prawy przycisk myszy
            if (active == 1 && !(e.getModifiers() == MouseEvent.BUTTON3_MASK)) {
                doColor(e);
                doActive(e);
                doMove(e);
            }
        }

        /**
         * myszka puszczona
         */
        public void mouseReleased(MouseEvent e) {
            //dopiero gdy puscimy myszke dodajemy skonczona figure
            if (shape.equals("Okrag") && news == 1) {
                shapeList.add(circle);
                news = 0;
            }
            if (shape.equals("Prostokat") && news == 1) {
                shapeList.add(rectangle);
                news = 0;
            }
            if (shape.equals("Trojkat") && news == 1) {
                shapeList.add(triangle);
                news = 0;
            }
        }

        /**
         * uzupelniamy indexActive, dla indeksu i mamy 1:gdy figura jest aktywna, 0 gdy nie jest
         */
        private void doActive(MouseEvent e) {
            int i;
            //poczatkowo nie ma zadnej aktywnej figury
            for (int j = 0; j < 30; j++) indexActive[j] = 0;
            //petla po naszej liscie ksztaltow
            for (Shape current : shapeList) {
                //jesli ksztalt jest kolem
                if (current.getClass() == circle.getClass()) {
                    circle = (Ellipse) current;
                    if (circle.isHit(x1, y1)) {
                        i = shapeList.indexOf(circle);
                        indexActive[i] = 1;
                    }
                }
                //jesli ksztalt jest prostokatem
                if (current.getClass() == rectangle.getClass()) {
                    rectangle = (Rectangle) current;
                    if (rectangle.isHit(x1, y1)) {
                        i = shapeList.indexOf(rectangle);
                        indexActive[i] = 1;
                    }
                }
                //jesli ksztalt jest trojkatem
                if (current.getClass() == triangle.getClass()) {
                    triangle = (MyPolygon) current;
                    if (triangle.isHit(x1, y1)) {
                        i = shapeList.indexOf(triangle);
                        indexActive[i] = 1;
                    }
                }
            }
            //rysujemy na nowo uwzgledniajac nowo wypelniona tablice
            repaint();
        }

        /**
         * rysyjemy figure
         */
        private void doCreate(MouseEvent e) {
            x2 = e.getX();
            y2 = e.getY();
            w = x2 - x1;
            h = y2 - y1;
            news = 1;
            if (shape.equals("Okrag")) {
                //sprawdzenie w ktora strone zostala ruszona myszka i tworzenie odpowiedniego obiektu
                if (x2 < x1 || y2 < y1) {
                    if (x2 < x1 && y2 < y1) circle = new Ellipse(x2, y2, -w, -h);
                    if (x2 < x1 && !(y2 < y1)) circle = new Ellipse(x2, y1, -w, h);
                    if (y2 < y1 && !(x2 < x1)) circle = new Ellipse(x1, y2, w, -h);
                } else circle = new Ellipse(x1, y1, w, h);
            }
            if (shape.equals("Prostokat")) {
                if (x2 < x1 || y2 < y1) {
                    if (x2 < x1 && y2 < y1) rectangle = new Rectangle(x2, y2, -w, -h);
                    if (x2 < x1 && !(y2 < y1)) rectangle = new Rectangle(x2, y1, -w, h);
                    if (y2 < y1 && !(x2 < x1)) rectangle = new Rectangle(x1, y2, w, -h);
                } else rectangle = new Rectangle(x1, y1, w, h);
            }
            if (shape.equals("Trojkat")) {
                x[0] = (int) x1;
                y[0] = (int) y1;
                x[1] = (int) x2;
                y[1] = (int) y2;
                x[2] = (int) (x1 - (x2 - x1));
                y[2] = y[1];
                triangle = new MyPolygon(x, y, 3);
            }
            repaint();
        }

        /**
         * poruszanie figurami
         */
        private void doMove(MouseEvent e) {
            float dx = e.getX() - x1;
            float dy = e.getY() - y1;
            for (Shape current : shapeList) {
                if (current.getClass() == circle.getClass()) {
                    circle = (Ellipse) current;
                    if (circle.isHit(x1, y1)) {
                        circle.addX(dx);
                        circle.addY(dy);
                        repaint();
                    }
                }
                if (current.getClass() == rectangle.getClass()) {
                    rectangle = (Rectangle) current;
                    if (rectangle.isHit(x1, y1)) {
                        rectangle.addX(dx);
                        rectangle.addY(dy);
                        repaint();
                    }
                }
                if (current.getClass() == triangle.getClass()) {
                    triangle = (MyPolygon) current;
                    int xp = (int) dx;
                    int yp = (int) dy;
                    if (triangle.isHit(x1, y1)) {
                        //metoda zmiania wierzcholki trojkata o xp,yp
                        triangle.translate(xp, yp);
                        repaint();
                    }
                }
            }
            x1 += dx;
            y1 += dy;
        }

        /**
         * kolorowanie figur, dzialanie podobne jak z aktywowaniem
         */
        private void doColor(MouseEvent e) {
            int i;
            for (int j = 0; j < 30; j++) index[j] = 0;
            for (Shape current : shapeList) {
                if (current.getClass() == circle.getClass()) {
                    circle = (Ellipse) current;
                    if (circle.isHit(x1, y1)) {
                        i = shapeList.indexOf(circle);
                        index[i] = 1;
                    }
                }
                if (current.getClass() == rectangle.getClass()) {
                    rectangle = (Rectangle) current;
                    if (rectangle.isHit(x1, y1)) {
                        i = shapeList.indexOf(rectangle);
                        index[i] = 1;
                    }
                }
                if (current.getClass() == triangle.getClass()) {
                    triangle = (MyPolygon) current;
                    if (triangle.isHit(x1, y1)) {
                        i = shapeList.indexOf(triangle);
                        index[i] = 1;
                    }
                }
            }
        }

    }

    /**
     * klasa obslugujaca scrolla
     */
    class ScaleHandler implements MouseWheelListener {
        /**
         * @param e ruch scrollem
         */
        public void mouseWheelMoved(MouseWheelEvent e) {
            //jesli wlaczona jest aktywnosc
            if (active == 1) doScale(e);
        }

        /**
         * skalowanie figury
         */
        private void doScale(MouseWheelEvent e) {
            int x1 = e.getX();
            int y1 = e.getY();
            //jesli zostal poruszony scroll
            if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
                for (Shape current : shapeList) {
                    if (current.getClass() == circle.getClass()) {
                        circle = (Ellipse) current;
                        //jesli okrag jest oznaczony jako aktywny
                        if (indexActive[shapeList.indexOf(circle)] == 1) {
                            float amount = e.getWheelRotation() * 5f;
                            circle.addWidth(amount);
                            circle.addHeight(amount);
                            repaint();
                        }
                    }
                    if (current.getClass() == rectangle.getClass()) {
                        rectangle = (Rectangle) current;
                        if (indexActive[shapeList.indexOf(rectangle)] == 1) {
                            float amount = e.getWheelRotation() * 5f;
                            rectangle.addWidth(amount);
                            rectangle.addHeight(amount);
                            repaint();
                        }
                    }
                    if (current.getClass() == triangle.getClass()) {
                        triangle = (MyPolygon) current;
                        if (indexActive[shapeList.indexOf(triangle)] == 1) {
                            int amount = e.getWheelRotation() * 5;
                            triangle.reSize(amount);
                            repaint();
                        }
                    }
                }
            }
        }
    }

    /**
     * klasa obslugujaca menu prawego przycisku myszy
     */
    class MouseMenuListener implements ActionListener {
        //okno dialogowe z wyborem kolorow
        private JDialog Dialog;
        //wybieracz kolorow
        private JColorChooser Chooser;

        MouseMenuListener() {
            Chooser = new JColorChooser();
            /*
             tworzymi dialog z wyborem kolorow, kolejno w metodzie: komponent nadrzedny, nazwa okna dialogowego, okno jest niemodalne,
             komponent wyboru kolorow, sluchacz przycisku OK, i brak sluchacza przycisku Cancel
             */
            Dialog = JColorChooser.createDialog(MyPanel.this, "Kolor figury", false, Chooser, new ActionListener() {
                /**
                 * @param e akcja przycisku
                 */
                public void actionPerformed(ActionEvent e) {
                    //pobieramy kolor ze zmiennej Chooser, ustawiamy wybrany kolor i rysujemy
                    setColor(Chooser.getColor());
                    repaint();
                }
            }, null);
        }

        /**
         * akcja po wcisnieciu przycisku
         */
        public void actionPerformed(ActionEvent e) {
            //pobieranie koloru do zmiennej Chooser
            Chooser.setColor(getBackground());
            //pojawienie sie okna dialogowego
            Dialog.setVisible(true);
        }

    }

    /**
     * rysowanie
     *
     * @param g obiekt graficzny
     */
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //zmienna odpowiedzialna za przemieszczanie sie po tablicach indexActive i Color
        int i = 0;
        //ustawienie grubosci konturu figury
        g2d.setStroke(new BasicStroke(3));
        if (shape.equals("Okrag")) g2d.draw(circle);
        if (shape.equals("Prostokat")) g2d.draw(rectangle);
        if (shape.equals("Trojkat")) g2d.drawPolygon(triangle);
        for (Shape current : shapeList) {
            //jesli figura o indeksie i jest aktywna to  ustawiamy kolor na czerowny, jak nie to na czarny
            if (indexActive[i] == 1) g2d.setColor(Color.RED);
            else g2d.setColor(Color.BLACK);
            //jesli kolor i-ej figury nie byl zmieniany to rysujemy jedynie jej kontury (czerwone jesli jest aktywna, czarne jesli nie jest)
            if (color[i] == null) g2d.draw(current);
                //jesli kolor byl zmieniany
            else {
                //jesli jest aktywna to rysyjemy kontur na wczesniej wybrany kolor czerwony
                if (indexActive[i] == 1) g2d.draw(current);
                //zmieniamy kolor na ten z tablicy i wypelniamy figure, jesli nie jest aktywne jej kontury beda mialy ten sam kolor co srodek
                g2d.setColor(color[i]);
                g2d.fill(current);
            }
            i++;
        }
    }

    /**
     * metoda odpowiedzialna za rysowanie, wywoluje sie przy repaint()
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    /**
     * resetowanie panelu
     */
    public void reset() {
        //czyscimy liste
        shapeList.clear();
        //zaden ksztalt nie jest wybrany
        shape = "";
        //figury nie sa aktywne
        active = 0;
        //usuwamy element z menu myszki
        menuPopup.remove(i1);
        //tworzymy puste obiekty figur
        circle = new Ellipse(0, 0, 0, 0);
        rectangle = new Rectangle(0, 0, 0, 0);
        //zerujemy tabice wierzcholkow trojkata, kolorow i indeksow
        for (int i = 0; i < 3; i++) {
            x[i] = 0;
            y[i] = 0;
        }
        for (int i = 0; i < 30; i++) {
            color[i] = null;
            indexActive[i] = 0;
            index[i] = 0;
        }
        triangle = new MyPolygon(x, y, 3);
        //tlo ustawiamy na biale
        setBackground(Color.WHITE);
        //usuwamy wszytsko z panelu i tworzymy go na nowo
        removeAll();
        revalidate();
        repaint();
    }
}

