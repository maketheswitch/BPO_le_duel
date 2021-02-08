package appli;

import joueur.Joueur;
import java.util.Scanner;

public class Application {

    private static boolean jouer(int carteJouee, String Carte, Joueur J, Joueur J2){
        if(Carte.length() == 4){
            if(Carte.charAt(3) == '\''){
                switch (Carte.charAt(2)){
                    case 'v' :
                        if(carteJouee < J2.showLastDsc().getNumero()){
                            J2.setPileDsc(carteJouee);
                            return true;
                        }
                    case '^' :
                        if(carteJouee > J2.showLastAsc().getNumero()){
                            J2.setPileAsc(carteJouee);
                            return true;
                        }
                    default : return false;
                }
            }
            else
                return false;
        }
        else{
            switch (Carte.charAt(2)){
                case 'v' :
                    if(carteJouee > J.showLastDsc().getNumero() || carteJouee < (J.showLastDsc().getNumero() - 10)){
                        J.setPileAsc(carteJouee);
                        return true;
                    }
                case '^' :
                    if(carteJouee < J.showLastAsc().getNumero() || carteJouee > (J.showLastAsc().getNumero() + 10)){
                        J.setPileAsc(carteJouee);
                        return true;
                    }
                default : return false;
            }
        }
    }

    private static boolean verifCarte(int[] intTab, Joueur J){
        int cartesExistantes = 0;
        for(int cartes : intTab){
            for(int i = 0; i < J.getMainJoueur().getTailleMain(); ++i){
                if (cartes == J.getMainJoueur().showCarteMain(i).getNumero()){
                    cartesExistantes++;
                    break;
                }
            }
        }

        return cartesExistantes == intTab.length;
    }

    private static void tour(Joueur J, Joueur J2){
        System.out.println(J.toString());
        System.out.println(J2.toString());

        System.out.println(J.getMainJoueur().afficheMain(J));
        int nbEssais = 0;
        boolean coupValide = false;

        do {
            if (nbEssais == 0){
                System.out.print("> ");
            }
            else
                System.out.print("#> ");

            nbEssais++;

            // separer
            Scanner sc = new Scanner(System.in);
            String s = sc.nextLine();
            String[] tab = s.split("\\s+");
            int[] intTab = new int[tab.length];

            for (int i = 0; i < tab.length; ++i) {
                intTab[i] = Integer.parseInt(String.valueOf(tab[i].charAt(0)));
                intTab[i] *= 10;
                intTab[i] += Integer.parseInt(String.valueOf(tab[i].charAt(1)));
            }

            //12^` -> on créé une carte 12
            //-> ^ pile ascendante
            //-> ` adverse

            //(boolean) verifCarte(intTab, J);
            //  x(nombre de cartes jouees)

            if (!verifCarte(intTab, J)){
                continue;
            }
                /*(boolean) jouer(intTab[i], tab[i], J, J2);
                    //-> créé une carte 12
                    //-> pas besoin de vérifier l'existence de la carte
                    //-> on traite le troisième et eventuellement quatrième caractère si il existe
                    //-> en fonction des  caractères lu (if/switch), alo
                        si "^`"; si "^"; si "v"; si "v`"; default : faux;
                            alors on verifie là -> faux;
                                alors on joue et on modifie
                                    alors on l'enlève soit de J soit de J2' et on la remets etc...*/
            int nbIterations = 0;
            for(int i = 0; i < intTab.length; ++i){
                if(jouer(intTab[i], tab[i], J, J2)){
                    nbIterations++;
                }
                else
                    break;
            }

            if(nbIterations == intTab.length){
                coupValide = true;
            }

        }while (!coupValide);
    }

    public static void main(String[] args) {
        Joueur j = new Joueur("NORD");
        Joueur j2 = new Joueur("SUD");
        int nbTours = 1;
        boolean etatJeu = false;

        do{
            tour(j, j2);
            nbTours++;
            /* if(PartieFinie()){ // partiefinie vérifie à la fois la victoire de J1 et la défaite de J2
                break;
            }*/

            //Sud joue
            tour(j2, j);
            nbTours++;
            /*if(PartieFinie()){ // partiefinie vérifie à la fois la victoire de J1 et la défaite de J2
                break;
            }*/

       }while (!etatJeu);
    }
}
