package grupos;

import java.util.ArrayList;
import java.util.Objects;

public class Persona {

    private String nombre;
    private boolean isCouple;
    private int turns = 0;
    private boolean assignado;
    private ArrayList<Persona> casa = new ArrayList<>();

    public Persona(String nombre, boolean isCouple, int turn) {
        this.nombre = nombre;
        this.isCouple = isCouple;
        this.turns = turn;
    }

    public boolean isAssignado() {
        return assignado;
    }

    public void addCasa(Persona nueva) {
        casa.add(nueva);
    }

    public ArrayList<Persona> getCasa() {
        return casa;
    }
    
    

    public void setAssignado(boolean assignado) {
        this.assignado = assignado;
    }

    public boolean isIsCouple() {
        return isCouple;
    }

    public void setIsCouple(boolean isCouple) {
        this.isCouple = isCouple;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public String getName() {
        return nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == getClass()) {
            return ((Persona) obj).getName().equals(nombre);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.nombre);
        hash = 97 * hash + (this.isCouple ? 1 : 0);
        hash = 97 * hash + this.turns;
        return hash;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
