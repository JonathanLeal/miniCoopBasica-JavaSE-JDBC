package probando;

public class AccionRecibo {
    private int id;
    private int recibo_id;
    private int usuario_id;
    private double precioRecibo;
    private double pagoRecibo;
    private double vueltoRecibo;

    public AccionRecibo() {
    }

    public AccionRecibo(int id, int recibo_id, int usuario_id, double precioRecibo, double pagoRecibo, double vueltoRecibo) {
        this.id = id;
        this.recibo_id = recibo_id;
        this.usuario_id = usuario_id;
        this.precioRecibo = precioRecibo;
        this.pagoRecibo = pagoRecibo;
        this.vueltoRecibo = vueltoRecibo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecibo_id() {
        return recibo_id;
    }

    public void setRecibo_id(int recibo_id) {
        this.recibo_id = recibo_id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public double getPrecioRecibo() {
        return precioRecibo;
    }

    public void setPrecioRecibo(double precioRecibo) {
        this.precioRecibo = precioRecibo;
    }

    public double getPagoRecibo() {
        return pagoRecibo;
    }

    public void setPagoRecibo(double pagoRecibo) {
        this.pagoRecibo = pagoRecibo;
    }

    public double getVueltoRecibo() {
        return vueltoRecibo;
    }

    public void setVueltoRecibo(double vueltoRecibo) {
        this.vueltoRecibo = vueltoRecibo;
    }
}
