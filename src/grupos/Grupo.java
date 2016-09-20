package grupos;

import java.util.LinkedHashSet;
import java.util.Set;

public class Grupo {

    private Set<Persona> integrantes = new LinkedHashSet<>();
    private boolean hasCouple;
    private int numInt;
    private int numFalta;

    public boolean itHasCouple() {
        return hasCouple;
    }

    public void setHasCouple(boolean hasCouple) {
        this.hasCouple = hasCouple;
    }

    public Grupo(int numInt) {
        this.numInt = numInt;
    }

    public void setNumInt(int numInt) {
        this.numInt = numInt;
    }

    public void setNumFalta(int numFalta) {
        this.numFalta = numFalta;
    }

    public boolean add(Persona integrante) {
        return   integrantes.add(integrante);
    }

    public Set<Persona> getIntegrantes() {
        return integrantes;
    }
  
    @Override
    public String toString() {
        return "Grupo" + integrantes + "OneCouple=" + hasCouple + '}';
    }

}
