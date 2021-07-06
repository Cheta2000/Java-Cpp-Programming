//Programy w javie komiplujemy poleceniem: $ javac [nazwa programu.java], a uruchamiamy $ java [nazwa programu][parametry]
//Programy w C++ kompilujemy poleceniem: & clang++ -o [nazwa programu][nazwaprogramu.cpp], a uruchamiamy: $ ./[nazwa programu][parametry]


public class Test{
        public static void main(String args[]){
		if(args.length==0) System.out.println("Brak parametrow");
                for(int i=0;i<args.length;i++){
                        try{
                                int n=Integer.parseInt(args[i]);
                          	System.out.println(args[i]+": "+ArabRzym.arab2rzym(n));
			}
			catch(ArabRzymException e){
					System.out.println(args[i]+": "+e.getMessage());
			}
                        catch(NumberFormatException ex){
                               try{
					System.out.println(args[i]+": "+ArabRzym.rzym2arab(args[i]));
				}
				catch(ArabRzymException e){
					System.out.println(args[i]+": "+e.getMessage());
				}
                        }
                }
        }
}

