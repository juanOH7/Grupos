package grupos;

import java.util.Objects;

public class Persona {
    private String nombre;
    private boolean isCouple;
    private byte turns = 0;

    public Persona(String nombre, boolean isCouple) {
        this.nombre = nombre;
        this.isCouple = isCouple;
    }

    public boolean isIsCouple() {
        return isCouple;
    }

    public void setIsCouple(boolean isCouple) {
        this.isCouple = isCouple;
    }

    public byte getTurns() {
        return turns;
    }

    public void setTurns(byte turns) {
        this.turns = turns;
    }
    
    public String getName(){
        return nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == getClass()) {
            return ((Persona)obj).getName().equals(nombre);
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
