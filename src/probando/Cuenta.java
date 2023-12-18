package probando;

public class Cuenta {
    private int id;
    private int usuarios_id;
    private double monto;

    public Cuenta() {
    }

    public Cuenta(int id, int usuarios_id, double monto) {
        this.id = id;
        this.usuarios_id = usuarios_id;
        this.monto = monto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarios_id() {
        return usuarios_id;
    }

    public void setUsuarios_id(int usuarios_id) {
        this.usuarios_id = usuarios_id;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
