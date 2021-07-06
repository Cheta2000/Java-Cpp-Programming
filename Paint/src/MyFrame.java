import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * klasa oblugujaca okno
 */
public class MyFrame extends JFrame {
    private JMenuBar MyMenu;
    private JMenu Menu1;
    private JRadioButtonMenuItem i1, i2, i3;
    private JMenuItem i4;
    private MyPanel Panel;
    private Dialog MyDialog1, MyDialog2;
    private ButtonGroup Group;
    private JCheckBox MyCheckBox;

    MyFrame() {
        //nazwa okna
        super("Figury");
        ///operacja zamkniecia okna
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //wyglad okna
        setBounds(100, 100, 1000, 600);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        //tworzenie przyciskow
        JButton MyButton1 = new JButton("Info");
        JButton MyButton2 = new JButton("Opusc");
        JButton MyButton3 = new JButton("Wyczysc");
        JButton MyButton4 = new JButton("Rozumiem");
        //dodanie akcji do przyciskow
        MyButton1.addActionListener(new ButtonListener());
        MyButton2.addActionListener(new ButtonListener());
        MyButton3.addActionListener(new ButtonListener());
        MyButton4.addActionListener(new ButtonListener());
        //tworzenie checkboxa
        MyCheckBox = new JCheckBox("Figury aktywne");
        //dodanie akcji do checkoboxa
        MyCheckBox.addItemListener(new CheckBoxListener());
        //tworzenie dialogow, ich wyglad i zawartosc
        MyDialog1 = new Dialog(this, "Info", true);
        MyDialog1.setSize(300, 200);
        MyDialog1.setBackground(Color.CYAN);
        MyDialog1.setLayout(new BorderLayout());
        JLabel MyLabel1 = new JLabel("<html>Autor: Mateusz Checinski<br/>Nazwa programu: Ksztalty<br/>Przeznaczenie: Rysowanie, przemieszczanie i kolorowanie podstawowych figur geometrycznych</html>");
        MyDialog1.add(MyLabel1, BorderLayout.CENTER);
        MyDialog2 = new Dialog(this, "Instrukcja", false);
        MyDialog2.setSize(500, 400);
        MyDialog2.setBackground(Color.YELLOW);
        MyDialog2.setLayout(new BorderLayout());
        JLabel MyLabel2 = new JLabel("<html>Witam w programie!<br/> Aby wybrac, ktora figure chcesz narysowac, wejdz w menu 'Figury' i kliknij jedna z opcji.<br/> Zasady rysowania:<br/> Klikasz myszka w miejscu rozpoczecia rysowania i przeciagasz ja.<br/> Ponadto mozesz oznaczyc figure jako aktywna. Aby to zrobic nalezy zaznaczyc 'Figury aktywne' w prawym gornym rogu.<br/> Nastepnie, aby aktywowac figure kliknij na nia lewym przyciskiem myszy (lub zacznij ja przeciagac), aktywna figura oznacza sie na czerwono.<br/>Gdy figura jest aktywna, mozesz ja:<br/> Przemieszczac- klikasz na figure i przesuwasz myszka<br/>Zmniejszac/zwiekszac- poslugujesz sie scrollem na myszce<br/>Zmienic kolor- klikasz prawy przycisk myszy i wybierasz 'Zmien kolor', nastepnie zaznaczasz wybrany kolor i klikasz 'OK'<br/>Kolor zmieni aktywna figura. Gdy zadna figura nie jest aktywna zmieniasz kolor tla.<br/> Milej zabawy!<html/>");
        MyDialog2.add(MyLabel2, BorderLayout.CENTER);
        //tworzenie panelu
        Panel = new MyPanel();
        //tworzenie ramki i dodanie jej do panelu
        Border PanelBorder = BorderFactory.createLineBorder(Color.BLACK);
        Panel.setBorder(PanelBorder);
        //tworzenie paska menu
        MyMenu = new JMenuBar();
        //tworzenie menu i dodawanie go do paska
        Menu1 = new JMenu("Figury");
        MyMenu.add(Menu1);
        //tworzenie elementu menu, dodanie do niego akcji i dodanie go do paska
        i4 = new JMenuItem("Instrukcja");
        i4.addActionListener(new ButtonListener());
        MyMenu.add(i4);
        //tworzenie grupy przyciskow
        Group = new ButtonGroup();
        //tworzenie elementow menu typu radiobutton,dodawanie do nich akcji, dodanie ich do grupy przyciskow i do menu
        i1 = new JRadioButtonMenuItem("Okrag");
        i1.addItemListener(new MenuListener());
        i2 = new JRadioButtonMenuItem("Prostokat");
        i2.addItemListener(new MenuListener());
        i3 = new JRadioButtonMenuItem("Trojkat");
        i3.addItemListener(new MenuListener());
        Group.add(i1);
        Group.add(i2);
        Group.add(i3);
        Menu1.add(i1);
        Menu1.add(i2);
        Menu1.add(i3);
        //ustawienie menu
        setJMenuBar(MyMenu);
        //dodanie checkboxa do paska menu
        MyMenu.add(MyCheckBox, BorderLayout.NORTH);
        //dodanie przyciskow i panelu do okna
        add(MyButton1, BorderLayout.EAST);
        add(Panel, BorderLayout.CENTER);
        add(MyButton3, BorderLayout.SOUTH);
        //dodanie przyciskow do dialogow
        MyDialog1.add(MyButton2, BorderLayout.SOUTH);
        MyDialog2.add(MyButton4, BorderLayout.SOUTH);
    }

    /**
     * klasa obslugujaca akcje przyciskow
     */
    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Info":
                    //wyswietla sie MyDialog1
                    MyDialog1.setVisible(true);
                    break;
                case "Opusc":
                    //znika MyDialog1
                    MyDialog1.setVisible(false);
                    break;
                case "Wyczysc":
                    //resetuje sie panel, odznacza sie zaznaczony radiobutton(kszalt) i checkbox
                    Panel.reset();
                    Group.clearSelection();
                    MyCheckBox.setSelected(false);
                    break;
                case "Instrukcja":
                    //wyswietla sie MyDialog2
                    MyDialog2.setVisible(true);
                    break;
                case "Rozumiem":
                    //znika MyDialog2
                    MyDialog2.setVisible(false);
                    break;
            }
        }
    }

    /**
     * klasa oblsugujaca akcje elementow menu
     */
    class MenuListener implements ItemListener {
        /**
         * @param ie zaznaczony element
         */
        public void itemStateChanged(ItemEvent ie) {
            //jesli element zmienil sie na ten przez nas wybrany
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                //odznacza sie checkbox
                MyCheckBox.setSelected(false);
                //ustawienie ksztaltu
                if (ie.getSource() == i1) Panel.setShape("Okrag");
                if (ie.getSource() == i2) Panel.setShape("Prostokat");
                if (ie.getSource() == i3) Panel.setShape("Trojkat");
            }
        }
    }

    /**
     * klasa oblugujaca checkboxa
     */
    class CheckBoxListener implements ItemListener {
        public void itemStateChanged(ItemEvent ie) {
            //jesli checkbox zostal zaznaczony ustawiamy aktywnosc na 1, w przeciwnym wypadku ustawiamy na 0
            if (ie.getStateChange() == 1) Panel.setActive(1);
            else Panel.setActive(0);
            //odznaczamy zaznaczony radiobutton(ksztalt)
            Group.clearSelection();
        }
    }
}