package grupos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File arc = new File("./miembros.txt");
        File gur = new File("./guardar.txt");
        Scanner userSC = new Scanner(System.in);
        Scanner sc;

        int numInt, numGr, sobra, numTur, totPEr = 0, totParejas = 0;
        String REsPUs;
        boolean forcedCouple;
        boolean oneCouple;
        boolean cont = false;
        ArrayList<Integer> numXgru = new ArrayList();

        LinkedList<Persona> personas = new LinkedList();
        LinkedList<Persona> candidatosLider = new LinkedList();
        LinkedList<Persona> lideres = new LinkedList();
        LinkedList<Persona> integrantes = new LinkedList();
        LinkedList<Persona> integrantesPa = new LinkedList();
        LinkedList<Persona> integrantesNoPa = new LinkedList();

        ArrayList<Grupo> grupos = new ArrayList();
        Persona tmp;
        if (gur.exists()) {
            System.out.println("Continuar [S/N]");
            REsPUs = userSC.next();
            cont = (REsPUs.equals("S") || REsPUs.equals("s"));
        }

        if (!cont) {
            sc = new Scanner(arc);
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
        } else {
            sc = new Scanner(gur);
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

        System.out.println("Número de turnos:");
        numTur = userSC.nextInt();

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
            numXgru.set(i, numXgru.get(i) + 1);
        }

        Collections.shuffle(candidatosLider);
        for (int i = 0; i < numGr; i++) {
            lideres.add(candidatosLider.get(i));
            lideres.get(i).setTurns(numTur);
            lideres.get(i).setAssignado(true);
            if (candidatosLider.get(i).isIsCouple()) {
                totParejas -= 1;
            }
        }

        for (int i = 0; i < numGr; i++) {
            grupos.add(new Grupo(numXgru.get(i)));
        }

        for (int i = 0; i < numGr; i++) {
            grupos.get(i).add(lideres.get(i));
            if (lideres.get(i).isIsCouple()) {
                grupos.get(i).setNumFalta(grupos.get(i).getNumInt() - 2);
                grupos.get(i).setHasCouple(true);
            } else {
                grupos.get(i).setNumFalta(grupos.get(i).getNumInt() - 1);
                grupos.get(i).setHasCouple(false);
            }
        }

        for (int i = 0; i < personas.size(); i++) {
            if (!personas.get(i).isAssignado()) {
                integrantes.add(personas.get(i));
            }
        }
        for (int i = 0; i < integrantes.size(); i++) {
            if (integrantes.get(i).isIsCouple()) {
                integrantesPa.add(integrantes.get(i));
            } else {
                integrantesNoPa.add(integrantes.get(i));
            }
        }

        Collections.shuffle(integrantesPa);
        Collections.shuffle(integrantesNoPa);

        int indPare = 0;
        if (oneCouple) {
            for (int i = 0; i < numGr && indPare < totParejas; i++) {
                if (!grupos.get(i).isHasCouple() || forcedCouple) {
                    grupos.get(i).add(integrantesPa.get(indPare));
                    grupos.get(i).setNumFalta(grupos.get(i).getNumFalta() - 2);
                    indPare++;
                }
            }
        } else {
            for (int i = 0; i < numGr && indPare < totParejas; i++) {
                grupos.get(i).add(integrantesPa.get(indPare));
                grupos.get(i).setNumFalta(grupos.get(i).getNumFalta() - 2);
                indPare++;
            }
        }

        int indice = 0;
        for (int i = 0; i < numGr && indice < integrantesNoPa.size(); i++) {
            for (int j = 0; j < grupos.get(i).getNumFalta() && indice < integrantesNoPa.size(); j++) {
                grupos.get(i).add(integrantesNoPa.get(indice));
                indice++;
            }
        }

        for (int i = 0; i < integrantes.size(); i++) {
            if (integrantes.get(i).getTurns() != 0) {
                integrantes.get(i).setTurns(integrantes.get(i).getTurns() - 1);
            }
        }

        for (int i = 0; i < grupos.size(); i++) {
            System.out.println("Grupo" + (i + 1));
            System.out.println(grupos.get(i).getIntegrantes());
        }

        PrintWriter bw = new PrintWriter(new File("guardar.txt"));
        for (int i = 0; i < personas.size(); i++) {
            bw.write(personas.get(i).getName() + ":" + personas.get(i).getTurns() + "\n");
        }
        bw.close();

    }

}
