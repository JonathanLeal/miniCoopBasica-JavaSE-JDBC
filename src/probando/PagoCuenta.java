package probando;

public class PagoCuenta {
    private int id;
    private int usuario_id;
    private int accionesCuenta;
    private double cantidadAfectada;

    public PagoCuenta() {
    }

    public PagoCuenta(int id, int usuario_id, int accionesCuenta, double cantidadAfectada) {
        this.id = id;
        this.usuario_id = usuario_id;
        this.accionesCuenta = accionesCuenta;
        this.cantidadAfectada = cantidadAfectada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getAccionesCuenta() {
        return accionesCuenta;
    }

    public void setAccionesCuenta(int accionesCuenta) {
        this.accionesCuenta = accionesCuenta;
    }

    public double getCantidadAfectada() {
        return cantidadAfectada;
    }

    public void setCantidadAfectada(double cantidadAfectada) {
        this.cantidadAfectada = cantidadAfectada;
    }
}
