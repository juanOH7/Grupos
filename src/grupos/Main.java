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

        int numInt, numGr, sobra, totPEr = 0, totParejas = 0;
        String REsPUs;
        boolean forcedCouple = false;
        boolean oneCouple = false;
        ArrayList<Integer> numXgru = new ArrayList();

        LinkedList<Persona> personas = new LinkedList();
        LinkedList<Persona> candidatosLider = new LinkedList();
        LinkedList<Persona> lideres = new LinkedList();
        LinkedList<Persona> integrantes = new LinkedList();
        LinkedList<Persona> integrantesPa = new LinkedList();
        LinkedList<Persona> integrantesNoPa = new LinkedList();

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
            if (personas.get(i).isIsCouple()) {
                totPEr += 2;
            } else {
                totPEr += 1;
            }
        }

        System.out.print("Número de Integrantes: ");
        numInt = userSC.nextInt();

        System.out.println("¿Una Pareja por grupo? [S/N]: ");
        REsPUs = userSC.next();

        for (int i = 0; i < personas.size(); i++) {
            if (personas.get(i).isIsCouple()) {
                totParejas += 1;
            }
        }

        oneCouple = (REsPUs.equals("S") || REsPUs.equals("s"));

        numGr = (int) (Math.floor((double) totPEr / (double) numInt));
        sobra = totPEr - (numGr * numInt);

        forcedCouple = (totParejas > numGr);

        for (int i = 0; i < personas.size(); i++) {
            if (personas.get(i).getTurns() == 0) {
                candidatosLider.add(personas.get(i));
            }
        }

        for (int i = 0; i < numGr; i++) {
            numXgru.add(numInt);
        }

        for (int i = 0; i < sobra; i++) {
            numXgru.add(numXgru.get(i) + 1);
        }

        System.out.println(numGr);

        Collections.shuffle(candidatosLider);
        for (int i = 0; i < numGr; i++) {
            lideres.add(candidatosLider.get(i));
            lideres.get(i).setTurns(2);
            lideres.get(i).setAssignado(true);
        }

        for (int i = 0; i < numGr; i++) {
            grupos.add(new Grupo(numInt));
        }

        for (int i = 0; i < numGr; i++) {
            grupos.get(i).add(lideres.get(i));
        }

        for (int i = 0; i < personas.size(); i++) {
            if (!personas.get(i).isAssignado()) {
                integrantes.add(personas.get(i));
            }
        }

        for (int i = 0; i < integrantes.size(); i++) {
            if (integrantes.get(i).isIsCouple()) {

            }
        }

        for (int i = 0; i < grupos.size(); i++) {
            System.out.println(grupos.get(i).getIntegrantes());
        }

    }

}
