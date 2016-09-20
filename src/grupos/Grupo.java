package grupos;

import java.util.LinkedHashSet;
import java.util.Set;

public class Grupo {

    private Set<Persona> integrantes = new LinkedHashSet<>();
    private boolean OneCouple;

    public Grupo(boolean OneCouple) {
        this.OneCouple = OneCouple;
    }

    public boolean add(Persona integrante) {
        return   integrantes.add(integrante);
    }

    public Set<Persona> getIntegrantes() {
        return integrantes;
    }

    
    @Override
    public String toString() {
        return "Grupo" + integrantes + "OneCouple=" + OneCouple + '}';
    }

}
