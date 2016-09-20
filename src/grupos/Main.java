package grupos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File arc = new File("miembros.txt");

        Scanner userSC = new Scanner(System.in);
        Scanner sc = new Scanner(arc);

        int numGr;
        String REsPUs;

        LinkedList<Persona> personas = new LinkedList();
        LinkedList<Persona> candidatosLider = new LinkedList();
        LinkedList<Persona> lideres = new LinkedList();
        LinkedList<Persona> integrates = new LinkedList();

        ArrayList<Grupo> grupos = new ArrayList();
        Persona tmp;
        while (sc.hasNextLine()) {
            String temp = sc.nextLine();
            String div[] = temp.split(":");
            if (div[0].contains(",")) {
                tmp = new Persona(div[0], true, Integer.valueOf(div[1]));
                personas.add(tmp);
            } else {
                tmp = new Persona(div[0], false, Integer.valueOf(div[1]));
                personas.add(tmp);
            }
        }

        for (int i = 0; i < personas.size(); i++) {
            if (personas.get(i).getTurns() == 0) {
                candidatosLider.add(personas.get(i));
            }
        }

        System.out.print("Número de Grupos: ");
        numGr = userSC.nextInt();

        Collections.shuffle(candidatosLider);
        for (int i = 0; i < numGr; i++) {
            lideres.add(candidatosLider.get(i));
            lideres.get(i).setTurns(2);
        }

        for (int i = 0; i < personas.size(); i++) {
            System.out.println("ori" + personas.get(i).getTurns());
        }

        System.out.println("¿Una Pareja por grupo? [S/N]: ");
        REsPUs = userSC.next();

        for (int i = 0; i < numGr; i++) {
            if (REsPUs.equals("S") || REsPUs.equals("s")) {
                grupos.add(new Grupo(true));
            } else {
                grupos.add(new Grupo(false));
            }
        }

        for (int i = 0; i < numGr; i++) {
            grupos.get(i).add(lideres.get(i));
        }

        for (int i = 0; i < numGr; i++) {
            System.out.println(grupos.get(i).getIntegrantes());
        }

    }

}
