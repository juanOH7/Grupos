package grupos;

public class Main {

    public static void main(String[] args) {
        Grupo ds = new Grupo(true);
        Persona a = new Persona("as,ds", true);
        String dsds = "ds,s";
        if (dsds.contains(",")) {
            System.err.println("Couple");
        }
        Persona b = new Persona("ds", false);
        Persona c = new Persona("re", false);
        Persona d = new Persona("re", false);
        ds.add(a);
        ds.add(b);
        System.out.println(c.equals(d));
        System.out.println( ds.add(c));
        System.out.println( ds.add(d));
        System.out.println(ds.toString());
    }
    
}
