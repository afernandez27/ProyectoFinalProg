package prog.proyectofinalprog;

public class Coche {
    private String matricula;
    private int kilometraje;
    private int potencia;
    private String color;
    private int precio;
    private Modelo modelo;

    public Coche(String matricula, int kilometraje, int potencia, String color, int precio, Modelo modelo) {
        this.matricula = matricula;
        this.kilometraje = kilometraje;
        this.potencia = potencia;
        this.color = color;
        this.precio = precio;
        this.modelo = modelo;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
