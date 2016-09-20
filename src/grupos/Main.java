package grupos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File arc = new File("./miembros.txt");
        File gur = new File("./guardar.txt");
        File arc2 = new File("aristas.txt");
        Scanner userSC = new Scanner(System.in);
        Scanner sc;

        Graph graph = new MultiGraph("grafo");

        int numInt, numGr, idEdg = 0, sobra, numTur, totPEr = 0, totParejas = 0;
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
            sc = new Scanner(arc2);
            while (sc.hasNextLine()) {
                String comp = sc.nextLine();
                String divCom[] = comp.split(":");
                if ((graph.getNode(divCom[0]) == null) && (graph.getNode(divCom[1]) == null)) {
                    graph.addNode(divCom[0]);
                    graph.addNode(divCom[1]);
                    graph.addEdge(String.valueOf(idEdg), divCom[0], divCom[1], true).addAttribute("length", 1);
                } else if ((graph.getNode(divCom[0]) != null) && (graph.getNode(divCom[1]) == null)) {
                    graph.addNode(divCom[1]);
                    graph.addEdge(String.valueOf(idEdg), divCom[0], divCom[1], true).addAttribute("length", 1);
                } else if ((graph.getNode(divCom[1]) != null) && (graph.getNode(divCom[0]) == null)) {
                    graph.addNode(divCom[0]);
                    graph.addEdge(String.valueOf(idEdg), divCom[0], divCom[1], true).addAttribute("length", 1);
                } else {
                    graph.addEdge(String.valueOf(idEdg), divCom[0], divCom[1], true).addAttribute("length", 1);
                }
                idEdg++;
                for (Node node : graph) {
                    node.addAttribute("ui.label", node.getId());
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
                    integrantesPa.get(indPare).addCasa(lideres.get(i));
                    indPare++;
                }
            }
        } else {
            for (int i = 0; i < numGr && indPare < totParejas; i++) {
                grupos.get(i).add(integrantesPa.get(indPare));
                integrantesPa.get(indPare).addCasa(lideres.get(i));
                grupos.get(i).setNumFalta(grupos.get(i).getNumFalta() - 2);
                indPare++;
            }
        }

        int indice = 0;
        for (int i = 0; i < numGr && indice < integrantesNoPa.size(); i++) {
            for (int j = 0; j < grupos.get(i).getNumFalta() && indice < integrantesNoPa.size(); j++) {
                grupos.get(i).add(integrantesNoPa.get(indice));
                integrantesNoPa.get(indice).addCasa(lideres.get(i));
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

        for (int i = 0; i < personas.size(); i++) {
            for (int j = 0; j < personas.get(i).getCasa().size(); j++) {
                System.out.println(personas.get(i) + " conoce casa de " + personas.get(i).getCasa().get(j));
            }
        }

        PrintWriter bw = new PrintWriter(new File("guardar.txt"));
        for (int i = 0; i < personas.size(); i++) {
            bw.write(personas.get(i).getName() + ":" + personas.get(i).getTurns() + "\n");
        }
        bw.close();

        PrintWriter bw2 = new PrintWriter(new File("aristas.txt"));
        for (int i = 0; i < personas.size(); i++) {
            for (int j = 0; j < personas.get(i).getCasa().size(); j++) {
                bw2.write(personas.get(i).getName() + ":" + personas.get(i).getCasa().get(j) + "\n");
            }
        }
        bw2.close();

        sc = new Scanner(arc2);
        while (sc.hasNextLine()) {
            String comp = sc.nextLine();
            String divCom[] = comp.split(":");
            if ((graph.getNode(divCom[0]) == null) && (graph.getNode(divCom[1]) == null)) {
                graph.addNode(divCom[0]);
                graph.addNode(divCom[1]);
                graph.addEdge(String.valueOf(idEdg), divCom[0], divCom[1], true).addAttribute("length", 1);
            } else if ((graph.getNode(divCom[0]) != null) && (graph.getNode(divCom[1]) == null)) {
                graph.addNode(divCom[1]);
                graph.addEdge(String.valueOf(idEdg), divCom[0], divCom[1], true).addAttribute("length", 1);
            } else if ((graph.getNode(divCom[1]) != null) && (graph.getNode(divCom[0]) == null)) {
                graph.addNode(divCom[0]);
                graph.addEdge(String.valueOf(idEdg), divCom[0], divCom[1], true).addAttribute("length", 1);
            } else {
                graph.addEdge(String.valueOf(idEdg), divCom[0], divCom[1], true).addAttribute("length", 1);
            }
            idEdg++;
        }
        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
        }
        System.out.println("Ver Relacion? [S/N] ");
        REsPUs = userSC.next();

        if (REsPUs.equals("S") || REsPUs.equals("s")) {
            graph.display();
        }
    }

}
