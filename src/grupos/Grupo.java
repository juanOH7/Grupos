package grupos;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class Grupo {

    private Set<Persona> integrantes = new LinkedHashSet<>();
    private boolean OneCouple;

    public Grupo(boolean OneCouple) {
        this.OneCouple = OneCouple;
    }

    public boolean add(Persona integrante) {
        System.out.println(integrantes.add(integrante));
        return true;
    }

    @Override
    public String toString() {
        return "Grupo" + integrantes + "OneCouple=" + OneCouple + '}';
    }

}
