/**
 * klasa elementu drzewa
 *
 * @param <T> typ drzewa
 */
class TreeElement<T extends Comparable<T>> {
    //element wezla
    final T element;
    //wskazania na lewe i prawe poddrzewo oraz rodzica
    TreeElement<T> left;
    TreeElement<T> right;
    TreeElement<T> parent;

    /**
     * konstuktor elementu drzewa
     *
     * @param element element wezla
     * @param w       rodzic wezla
     */
    TreeElement(T element, TreeElement<T> w) {
        this.element = element;
        //gdy dodajemy nowy element to nie ma on synow
        left = null;
        right = null;
        parent = w;
    }

}

/**
 * klasa wyjatkow
 */
class MyException extends Exception {
};

/**
 * klasa drzewa
 *
 * @param <T> typ drzewa
 */
public class Tree<T extends Comparable<T>> {
    //korzen drzewa
    private TreeElement<T> root;

    /**
     * gdy tworzymy drzewo jest ono puste
     */
    Tree() {
        root = null;
    }

    /**
     * @return korzen drzewa
     */
    public TreeElement<T> getRoot() {
        return root;
    }

    /**
     * @return true jesli drzewo jest puste, false wpp
     */
    private boolean empty() {
        if (root == null) return true;
        else return false;
    }

    /**
     * dodajemy element do drzewa
     *
     * @param element dodawany element
     */

    public void insert(T element) {
        root = add(element, root, null);
    }

    /**
     * @param element dodawany element
     * @param w       aktualny wezel drzewa
     * @param parent  rodzic w
     * @return wezel drzewa z elementem element i bez synow
     */
    private TreeElement<T> add(T element, TreeElement<T> w, TreeElement<T> parent) {
        //jesli jestesmy na pustym wezle to zwracamy nowy element
        if (w == null) return new TreeElement<T>(element, parent);
        //idziemy na lewe poddrzewo
        if (element.compareTo(w.element) < 0) {
            parent = w;
            w.left = add(element, w.left, parent);
        }
        //idziemy na prawe poddrzewo
        else if (element.compareTo(w.element) > 0) {
            parent = w;
            w.right = add(element, w.right, parent);
        }
        return w;
    }

    /**
     * @param order wybor porzadku wypisywania
     * @throws MyException rzucamy wyjatek gdy drzewo jest puste
     */
    public void draw(String order) throws MyException {
        if (empty()) throw new MyException();
        switch (order) {
            case "inOrder":
                inOrder(root);
                break;
            case "preOrder":
                preOrder(root);
                break;
            case "postOrder":
                postOrder(root);
                break;
        }
    }

    /**
     * wypisywanie inOrder
     *
     * @param w wezel drzewa
     */
    private void inOrder(TreeElement<T> w) {
        //jesli wezel jest pusty konczymy
        if (w == null) return;
        //poruszamy sie po lewym poddrzewie
        inOrder(w.left);
        System.out.println(w.element);
        //poruszamy sie po prawym poddrzewie
        inOrder(w.right);
    }

    /**
     * wypisywanie preOrder
     *
     * @param w wezel drzewa
     */
    private void preOrder(TreeElement<T> w) {
        if (w == null) return;
        System.out.println(w.element);
        preOrder(w.left);
        preOrder(w.right);
    }

    /**
     * wypisywanie postOrder
     *
     * @param w wezel drzewa
     */
    private void postOrder(TreeElement<T> w) {
        if (w == null) return;
        postOrder(w.left);
        postOrder(w.right);
        System.out.println(w.element + " ");
    }

    /**
     * @param element element do znalezienia
     * @return true jesli jest taki element, false wpp
     */
    public boolean search(T element) {
        return find(element, root);
    }

    /**
     * szukanie elementu
     *
     * @param element szukany element
     * @param w       wezel drzewa
     * @return true jesli znajdzie element, false wpp
     */
    private boolean find(T element, TreeElement<T> w) {
        //jesli wezel jest pusty zwracamy false
        if (w == null) return false;
        //jesli nasz element jest rowny elementowi w aktualnym wezle zwracamy true
        if (element.compareTo(w.element) == 0) return true;
        //jesli nasz element jest mneijszy idzemy do lewego poddrzewa
        if (element.compareTo(w.element) < 0)
            return find(element, w.left);
            //idziemy do prawego poddrzewa
        else
            return find(element, w.right);
    }

    /**
     * @param element element do usunieca
     * @throws MyException rzucamy wyjatek gdy drzewo jest puste
     */
    public void delete(T element) throws MyException {
        //jesli drzewo jest puste rzucamy wyjatek
        if (empty()) throw new MyException();
        //jesli znajdziemy w drzewie element do usuniecia to wykonujemy funkcje
        if (find(element, root)) root = remove(element, root);
            //jesli nie znajdziemy
        else System.out.println("Nie ma takiego elementu");
    }

    /**
     * usuwanie elementu
     *
     * @param element element do usuniecia
     * @param w       wezel drzewa
     * @return wezel ktory wskakuje w miejsce usunietego
     */
    private TreeElement<T> remove(T element, TreeElement<T> w) {
        //pomocniczy wezel
        TreeElement<T> y;
        //jesli wezel jest pusty zwracamy pusty wezel
        if (w == null) return null;
        //jesli znajdziemy nasz wezel do usuniecia
        if (element.compareTo(w.element) == 0) {
            //jesli nie ma synow to po prostu go usuwamy
            if (w.left == null && w.right == null) {
                return null;
            }
            //jesli ma lewego syna to zastepujemy go nim
            if (w.left != null && w.right == null) {
                return w.left;
            }
            //jesli ma prawego syna to zastepujemy go nim
            if (w.left == null && w.right != null) {
                return w.right;
            }
            //jesli ma obu synow
            if (w.left != null && w.right != null) {
                //szukamy nastepnika w
                y = succ(w);
                //w miejsce w wstawiamy jego nastepnik i usuwamy ten nastepnik z pierwotnego miejsca
                w = remove(y.element, w);
                //przelaczamy wskazniki
                y.left = w.left;
                y.right = w.right;
                return y;

            }
        }
        //idziemy do lewego poddrzewa
        if (element.compareTo(w.element) < 0)
            w.left = remove(element, w.left);
            //idziemy do prawego poddrzewa
        else {
            w.right = remove(element, w.right);
        }
        return w;
    }

    /**
     * resetowanie drzewa
     */
    public void reset() {
        System.out.println("Zresetowano drzewo");
        root = removeAll(root);
    }

    /**
     * @param w wezel
     * @return pusty wezel
     */
    private TreeElement<T> removeAll(TreeElement<T> w) {
        if (w == null) return null;
        //jesli lewe poddrzewo nie jest puste to usuwamy je
        if (w.left != null) w.left = removeAll(w.left);
        //jesli prawe poddrzewo nie jest puste to usuwamy je
        if (w.right != null) w.right = removeAll(w.right);
        return null;
    }

    /**
     * @param w wezel
     * @return wezel z elementem minimalnym w drzewie o korzeniu w
     */
    private TreeElement<T> min(TreeElement<T> w) {
        //schodzimy w dol po lewym poddrzewie
        while (w.left != null) w = w.left;
        return w;
    }

    /**
     * @param w wezel
     * @return nastepnik w
     */
    private TreeElement<T> succ(TreeElement<T> w) {
        //jesli w ma prawe poddrzewo to zwracamy min stamtad
        if (w.right != null) return min(w.right);
        //pomocniczy wezel
        TreeElement<T> y;
        //dopoki wezel nie jest pusty i poruszamy sie w lewa gorna czesc drzewa
        do {
            y = w;
            w = w.parent;
        } while (w != null && w.left != y);
        return w;
    }
}



