#include <bits/stdc++.h>
using namespace std;

//klasa elementu drzewa o dowolnym typie
template<typename T>
class TreeElement{
    public:
        //dana w elemencie drzewa
        T element;
        //wskazanie na lewego syna
        TreeElement *left;
        //wskazanie na prawego syna
        TreeElement *right;
        //wskazanie na rodzica
        TreeElement *parent;
        //gdy dodajemu nowy element nie ma on synow i ma jednego rodzica
        TreeElement(T element, TreeElement *w){
            this->element=element;
            left=NULL;
            right=NULL;
            parent=w;
        }
};
//klasa drzewa o dowolnym typie
template<typename T>
class Tree{
    private:
        //korzen drzewa
        TreeElement<T> *root;
    public:
        //gdy tworzymy drzewo jest ono puste
        Tree(){
            root=NULL;
        }
        //funkcja ktora sprawdzy czy drzewo jest puste
        bool empty(){
            return (root==NULL);
        }
        //funkcja ktora dodaje element drzewa
        void insert(T element){
            root=add(element, root, NULL);
        }
        //funkcja ktora wypisuje drzewo
        void draw(){
            //gdy drzewo jest puste
            if(empty()) throw (string) "Drzewo jest puste";
            cout<<"InOrder:"<<endl;
                inOrder(root);
        }
        //funkcja ktora szuka elementu w drzewie
        void search(T element){
            if(find(element, root)) cout<<"Znaleziono element: "<<element<<endl;
            else cout<<"Nie znaleziono elementu: "<<element<<endl;
        }
        //funkcja ktora resetuje drzewo
        void reset(){
            root=removeAll(&root);
            cout<<"Zresetowano drzewo"<<endl;
        }
        //funkcja ktora usuwa element z drzewa
        void deleteElement(T element){
            //wykonuje sie gdy w drzewie jest taki element
            if(find(element,root)){
                root=remove(element,&root);
                cout<<"Usunieto element: "<<element<<endl;
            }
            else cout<<"Nie ma takiego elementu: "<<element<<endl;
        }

    private:
        //glowna funkcja dodajaca
        TreeElement<T>* add(T element, TreeElement<T> *w, TreeElement<T> *parent){
            //jesli jestesmy na pustym wezle to w tym miejscu dodajemy nowy element
            if(w==NULL) {
                return new TreeElement<T>(element, parent);
                }
            //jesli nasz element jest mniejszy niz obecnie wskazywany element to idziemy do lewego podrzewa
            if(w->element>element){
                //ustawiamy rodzica
                parent=w;
                w->left=add(element, w->left, parent);
            }
            //jesli nasz element jest wiekszy niz obecnie wskazywany element to idziemy do prawego poddrzewa
            else if(w->element<element){
                parent=w;
                w->right=add(element, w->right, parent);
            }
            return w;
        }
        //glowna funkcja wypisujaca inOrder
        void inOrder(TreeElement<T> *w){
            //jesli jestesmy na pustym wezle konczymy
            if(w==NULL) return;
            //wypisujemy lewe podrzewo
            inOrder(w->left);
            cout<<w->element<<endl;
            //wypisujemy prawe podrzewo
            inOrder(w->right);
        }
        //glowna funkcja szukajaca elementu
        bool find(T element, TreeElement<T> *w){
            //gdy dojdziemy do pustego wezla zwracamy falsz
            if(w==NULL) return false;
            //jesli nasz element rowna sie wartosci w aktualnym wezle to zwracamy true
            if(element==w->element) return true;
            //jesli nasz element jest mniejszy niz obecnie wskazywany element to idziemy do lewego podrzewa
            if(element<w->element) return find(element,w->left);
            //jesli nasz element jest wiekszy niz obecnie wskazywany element to idziemy do prawego podrzewa
            if(element>w->element) return find(element,w->right);
        }
        //glowna funkcja resetujaca drzewo, przekazujemy parametr przez wskaznik gdyz musi sie on zmieniac w funkcji
        TreeElement<T>* removeAll(TreeElement<T> **w){
            //jesli jestesmy na pustym elemencie zwracamy pusty element
            if(*w==NULL) return NULL;
            //jesli lewe podrzewo nie jest puste wykonujmey funkcje usuwania na nim
            if((*w)->left!=NULL) removeAll(&(*w)->left);
            //jesli prawe podrzewo nie jest puste wykonujemy funkcje usuwania na nim
            if((*w)->right!=NULL) removeAll(&(*w)->right);
            return NULL;
        }
        //glowna funkcja usuwajaca element, tez przekazujmey parametr przez wskaznik
        TreeElement<T>* remove(T element, TreeElement<T> **w){
            //pomocniczy wezel
            TreeElement<T> *y;
            //jesli dojdziemy do pustego wezla to zwracamy go
            if(*w==NULL) return NULL;
            //jesli znajdziemy nasz element
            if((*w)->element==element){
                //element nie ma synow
                if((*w)->left==NULL && (*w)->right==NULL) return NULL;
                //element ma prawego syna
                if((*w)->left==NULL && (*w)->right!=NULL) return (*w)->right;
                //element ma lewego syna
                if((*w)->left!=NULL && (*w)->right==NULL) return (*w)->left;
                //element ma obu synow
                if((*w)->left!=NULL && (*w)->right!=NULL){
                    //bierzemy nastepnik naszego wezla
                    y=succ(*w);
                    //usuwamy z drzewa ten nastepnik i dajemy go na miejsce naszego wezla
                    *w=remove(y->element,&(*w));
                    //przepisujemy jego wskazniki
                    y->left=(*w)->left;
                    y->right=(*w)->right;
                    return y;
                }
            }
            //jesli nasz element jest mniejszy niz obecnie wskazywany element to idziemy do lewego podrzewa
            if((*w)->element>element) (*w)->left=remove(element,&(*w)->left);
            //jesli nasz element jest wiekszy niz obecnie wskazywany element to idziemy do prawego podrzewa
            if((*w)->element<element) (*w)->right=remove(element,&(*w)->right);
            return *w;
        }
        //wyszkuwanie elementu min
        TreeElement<T>* mini(TreeElement<T> *w){
            //schodzimy jak najnizej w lewym poddrzewie
            while(w->left!=NULL) mini(w);
            return w;
        }
        //wyszukiwanie nastpenika
        TreeElement<T>* succ(TreeElement<T> *w){
            //jesli istnieje prawy syn naszego wezla
            if(w->right!=NULL) return(mini(w->right));
            //pomocniczy wezel
            TreeElement<T> *y;
            //dopoki nasz wezel nie jest pusty i poruszamy sie w sposob taki ze lewym synem naszego wezla nie jest poprzedni wezel
            do{
                y=w;
                //za w podstawiamy rodzica w aby poruszac sie w gore drzewa
                w=w->parent;
            }while(w!=NULL && w->left!=y);
                return w;
        }
};

int main(){
    //typ drzewa
    char type;
    //co chcemy zrobic
    int command=0;
    //nasze drzewa
    Tree<int> i;
    Tree<string> s;
    Tree<double> db;
    cout<<"Podaj typ drzewa (i,s,d): "<<endl;
    cin>>type;
    //gdy komenda nie wynosi 7, czyli nie wychodzimy
    while(command!=7){
        //wybor drzewa
        switch(type){
            case 'i':{
                int d;
                //menu
                cout<<"Podaj co chcesz zrobic: "<<endl<<"1. Insert"<<endl<<"2. Draw"<<endl<<"3. Search"<<endl<<"4. Delete"<<endl<<"5. Reset"<<endl<<"6. Change type"<<endl<<"7. End"<<endl;
                cin>>command;
                try{
                    //wybor zadanie
                    switch(command){
                        case 1:{
                            cout<<"Podaj element do dodania: "<<endl;
                            cin>>d;
                            i.insert(d);
                            cout<<"Dodano: "<<d<<endl;
                            break;
                        }
                        case 2:{
                            i.draw();
                            break;
                        }
                        case 3:{
                            cout<<"Podaj element do znalezienia: "<<endl;
                            cin>>d;
                            i.search(d);
                            break;
                        }
                        case 4:{
                            cout<<"Podaj element do usuniecia: "<<endl;
                            cin>>d;
                            i.deleteElement(d);
                            break;
                        }
                        case 5:{
                            i.reset();
                            break;
                        }
                        case 6:{
                            cout<<"Podaj typ drzewa (i,s,d): "<<endl;
                            i.reset();
                            cin>>type;
                            break;
                        }
                        case 7:{
                            cout<<"Do widzenia";
                            break;
                        }
                        default:{
                            cout<<"Nie ma takiej komendy"<<endl;
                        }
                    }
                }
                catch(string s){
                    cout<<s<<endl;
                }
                break;
            }
            case 's':{
                string d;
                cout<<"Podaj co chcesz zrobic: "<<endl<<"1. Insert"<<endl<<"2. Draw"<<endl<<"3. Search"<<endl<<"4. Delete"<<endl<<"5. Reset"<<endl<<"6. Change type"<<endl<<"7. End"<<endl;
                cin>>command;
                try{
                    switch(command){
                        case 1:{
                            cout<<"Podaj element do dodania: "<<endl;
                            cin>>d;
                            s.insert(d);
                            cout<<"Dodano: "<<d<<endl;
                            break;
                        }
                        case 2:{
                            s.draw();
                            break;
                        }
                        case 3:{
                            cout<<"Podaj element do znalezienia: "<<endl;
                            cin>>d;
                            s.search(d);
                            break;
                        }
                        case 4:{
                            cout<<"Podaj element do usuniecia: "<<endl;
                            cin>>d;
                            s.deleteElement(d);
                            break;
                        }
                        case 5:{
                            s.reset();
                            break;
                        }
                        case 6:{
                            cout<<"Podaj typ drzewa (i,s,d): "<<endl;
                            s.reset();
                            cin>>type;
                            break;
                        }
                        case 7:{
                            cout<<"Do widzenia";
                            break;
                        }
                        default:{
                            cout<<"Nie ma takiej komendy"<<endl;
                        }
                    }
                }
                catch(string s){
                    cout<<s<<endl;
                }
                break;
            }
            case 'd':{
                double d;
                cout<<"Podaj co chcesz zrobic: "<<endl<<"1. Insert"<<endl<<"2. Draw"<<endl<<"3. Search"<<endl<<"4. Delete"<<endl<<"5. Reset"<<endl<<"6. Change type"<<endl<<"7. End"<<endl;
                cin>>command;
                try{
                    switch(command){
                        case 1:{
                            cout<<"Podaj element do dodania: "<<endl;
                            cin>>d;
                            db.insert(d);
                            cout<<"Dodano: "<<d<<endl;
                            break;
                        }
                        case 2:{
                            db.draw();
                            break;
                        }
                        case 3:{
                            cout<<"Podaj element do znalezienia: "<<endl;
                            cin>>d;
                            db.search(d);
                            break;
                        }
                        case 4:{
                            cout<<"Podaj element do usuniecia: "<<endl;
                            cin>>d;
                            db.deleteElement(d);
                            break;
                        }
                        case 5:{
                            db.reset();
                            break;
                        }
                        case 6:{
                            cout<<"Podaj typ drzewa (i,s,d): "<<endl;
                            cin>>type;
                            db.reset();
                            break;
                        }
                        case 7:{
                            cout<<"Do widzenia";
                            break;
                        }
                        default:{
                            cout<<"Nie ma takiej komendy"<<endl;
                        }
                    }
                }
                catch(string s){
                    cout<<s<<endl;
                }
                break;
            }
            default:{
                cout<<"Nie ma takiego typu"<<endl;
                command=6;
                return 0;
            }
        }
    }
return 0;
}
