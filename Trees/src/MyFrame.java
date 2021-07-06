import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * klasa okna
 */
public class MyFrame extends JFrame {
    //typ drzewa
    String T = "";
    //pole tekstowe z danymi dla drzewa
    JTextField data;
    //grupa przyciskow w menu
    ButtonGroup Group;
    //menu
    JMenuBar MyMenu;
    JMenu Menu1;
    //przyciski menu
    JRadioButton i1;
    JRadioButton i2;
    JRadioButton i3;
    //4 panele- ulatwiaja rozklad elementow
    JPanel P1 = new JPanel();
    JPanel P2 = new JPanel();
    JPanel P3 = new JPanel();
    JPanel P4 = new JPanel();
    //panel z drzewem
    MyPanel Panel = new MyPanel();
    //drzewa
    Tree<Integer> i = new Tree<Integer>();
    Tree<String> s = new Tree<String>();
    Tree<Double> d = new Tree<Double>();

    MyFrame() {
        //operacja zamkniecia okna
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //wymiary okna
        setBounds(500, 500, 1600, 1400);
        //rozmieszczenie komponentow
        setLayout(new BorderLayout());
        P1.setLayout(new GridLayout(1, 1));
        P2.setLayout(new GridLayout(3, 1));
        P3.setLayout(new GridLayout(1, 1));
        P4.setLayout(new GridLayout(4, 1));
        //tworzymy pole tekstowe
        data = new JTextField("Wybierz typ", 50);
        //tworzymy menu
        MyMenu = new JMenuBar();
        Menu1 = new JMenu("Typ");
        MyMenu.add(Menu1);
        i1 = new JRadioButton("Integer");
        i2 = new JRadioButton("String");
        i3 = new JRadioButton("Double");
        //dodajemu sluchaczy przyciskow menu
        i1.addItemListener(new RadioListener());
        i2.addItemListener(new RadioListener());
        i3.addItemListener(new RadioListener());
        Group = new ButtonGroup();
        Group.add(i1);
        Group.add(i2);
        Group.add(i3);
        Menu1.add(i1);
        Menu1.add(i2);
        Menu1.add(i3);
        setJMenuBar(MyMenu);
        //tworzymy przyciski, dodajemy sluchaczy
        JButton MyButton1 = new JButton("Insert");
        MyButton1.addActionListener(new ButtonListener());
        JButton MyButton2 = new JButton("Search");
        MyButton2.addActionListener(new ButtonListener());
        JButton MyButton3 = new JButton("inOrder");
        MyButton3.addActionListener(new ButtonListener());
        JButton MyButton4 = new JButton("preOrder");
        MyButton4.addActionListener(new ButtonListener());
        JButton MyButton5 = new JButton("postOrder");
        MyButton5.addActionListener(new ButtonListener());
        JButton MyButton6 = new JButton("Delete");
        MyButton6.addActionListener(new ButtonListener());
        JButton MyButton7 = new JButton("Reset");
        MyButton7.addActionListener(new ButtonListener());
        //rozklad elementow
        P1.add(Panel, BorderLayout.CENTER);
        P2.add(MyButton3);
        P2.add(MyButton4);
        P2.add(MyButton5);
        P4.add(MyButton1);
        P4.add(MyButton2);
        P4.add(MyButton6);
        P4.add(MyButton7);
        P3.add(data);
        add(P1, BorderLayout.CENTER);
        add(P3, BorderLayout.SOUTH);
        add(P2, BorderLayout.EAST);
        add(P4, BorderLayout.WEST);
    }

    /**
     * sluchacz przyciskow
     */
    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                //jesli drzewo jest Integer
                if (T.equals("I")) {
                    //wybor przycisku
                    switch (e.getActionCommand()) {
                        case "Insert":
                            //dodajemy do drzewa element z pola tekstowego
                            i.insert((Integer.parseInt(data.getText())));
                            System.out.println("Dodano element: " + data.getText());
                            //zmieniamy tekst w polu tekstowym
                            data.setText("Podaj element");
                            //przekazujemy do panelu drzewo
                            Panel.setI(i);
                            //czycismy liste zawieracaja prostokaty i linie ktore symbolizuja drzewo
                            Panel.clearList();
                            //rysujemy nowe drzewo
                            Panel.setListI(650, 0, i.getRoot(), 0, 0);
                            break;
                        case "Search":
                            //gdy znajdziemy element w drzewie
                            if (i.search(Integer.parseInt(data.getText()))) {
                                System.out.println("Znaleziono element: " + data.getText());
                                //przekazujemy ten element do panelu aby podswietlic go na czerwono
                                Panel.setSignI(Integer.parseInt(data.getText()));
                            } else {
                                System.out.println("Nie znaleziono elementu: " + data.getText());
                                //nie kolorujemy zadnego elementu
                                Panel.clearColor();
                            }
                            data.setText("Podaj element");
                            break;
                        case "Delete":
                            //usuwamy element z drzewa
                            i.delete(Integer.parseInt(data.getText()));
                            System.out.println("Usunieto element: " + data.getText());
                            data.setText("Podaj element");
                            Panel.clearList();
                            //rysujemy nowe drzewo
                            Panel.setListI(650, 0, i.getRoot(), 0, 0);
                            break;
                        case "inOrder":
                            //wypisujemy
                            System.out.println("inOrder:");
                            i.draw("inOrder");
                            break;
                        case "preOrder":
                            System.out.println("preOrder:");
                            i.draw("preOrder");
                            break;
                        case "postOrder":
                            System.out.println("postOrder:");
                            i.draw("postOrder");
                            break;
                        case "Reset":
                            //resetujemy drzewo
                            i.reset();
                            Panel.reset();
                    }
                }
                //jesli drzewo jest String
                if (T.equals("S")) {
                    switch (e.getActionCommand()) {
                        case "Insert":
                            s.insert(data.getText());
                            System.out.println("Dodano element: " + data.getText());
                            data.setText("Podaj element");
                            Panel.setS(s);
                            Panel.clearList();
                            Panel.setListS(650, 0, s.getRoot(), 0, 0);
                            break;
                        case "Search":
                            if (s.search(data.getText())) {
                                System.out.println("Znaleziono element: " + data.getText());
                                Panel.setSignS(data.getText());
                            } else {
                                System.out.println("Nie znaleziono elementu: " + data.getText());
                                Panel.clearColor();
                            }
                            data.setText("Podaj element");
                            break;
                        case "Delete":
                            s.delete(data.getText());
                            System.out.println("Usunieto element: " + data.getText());
                            data.setText("Podaj element");
                            Panel.clearList();
                            Panel.setListS(650, 0, s.getRoot(), 0, 0);
                            break;
                        case "inOrder":
                            System.out.println("inOrder:");
                            s.draw("inOrder");
                            break;
                        case "preOrder":
                            System.out.println("preOrder:");
                            s.draw("preOrder");
                            break;
                        case "postOrder":
                            System.out.println("postOrder:");
                            s.draw("postOrder");
                            break;
                        case "Reset":
                            s.reset();
                            Panel.reset();
                    }
                }
                //jesli drzewo jest Double
                if (T.equals("D")) {
                    switch (e.getActionCommand()) {
                        case "Insert":
                            System.out.println("Dodano element: " + data.getText());
                            d.insert(Double.parseDouble(data.getText()));
                            data.setText("Podaj element");
                            Panel.setD(d);
                            Panel.clearList();
                            Panel.setListD(650, 0, d.getRoot(), 0, 0);
                            break;
                        case "Search":
                            if (d.search(Double.parseDouble(data.getText()))) {
                                System.out.println("Znaleziono element: " + data.getText());
                                Panel.setSignD(Double.parseDouble(data.getText()));
                            } else {
                                System.out.println("Nie znaleziono elementu: " + data.getText());
                                Panel.clearColor();
                            }
                            data.setText("Podaj element");
                            break;
                        case "Delete":
                            d.delete((Double.parseDouble(data.getText())));
                            System.out.println("Usunieto element: " + data.getText());
                            data.setText("Podaj element");
                            Panel.clearList();
                            Panel.setListD(650, 0, d.getRoot(), 0, 0);
                            break;
                        case "inOrder":
                            System.out.println("inOrder:");
                            d.draw("inOrder");
                            break;
                        case "preOrder":
                            System.out.println("preOrder:");
                            d.draw("preOrder");
                            break;
                        case "postOrder":
                            System.out.println("postOrder:");
                            d.draw("postOrder");
                            break;
                        case "Reset":
                            d.reset();
                            Panel.reset();
                    }
                }
            } catch (MyException ex) {
                System.out.println("Drzewo jest puste");
            } catch (NumberFormatException ex) {
                System.out.println("Zly format danych");
            }
            ;
        }
    }

    /**
     * sluchacz przyciskow menu
     */
    class RadioListener implements ItemListener {
        public void itemStateChanged(ItemEvent ie) {
            //gdy zmienimy zaznczony element
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                data.setText("Podaj element");
                //resetujemy panel i wszystkie drzewa
                Panel.reset();
                i.reset();
                s.reset();
                d.reset();
                if (ie.getSource() == i1) T = "I";
                if (ie.getSource() == i2) T = "S";
                if (ie.getSource() == i3) T = "D";
                //przekazujemy do panelu typ drzewa
                Panel.setT(T);

            }
        }
    }
}
