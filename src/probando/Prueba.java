package probando;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Prueba {
    public static void main(String[] args) throws SQLException, Exception {
        Conexion conexion = new Conexion();
        PreparedStatement pst;
        ResultSet rs;
        int opc = 0;
        do {            
            opc = menu();
            switch(opc){
                case 1: 
                    registrarse();
                    break;
                case 2:
                    validarExistenciaUsuario();
                    break;
                case 3:
                    pagoRecibos();
                    break;
                case 4:
                    aministrarCuentas();
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "SALIENDO...");
                    break;
                case 6:
                    JOptionPane.showMessageDialog(null, "SALIENDO...");
                    break;
                default: 
                    JOptionPane.showMessageDialog(null, "Opcion no valida...");
                    break;
            }
        } while (opc != 6);
    }
    
    private static int menu(){
        int opc = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("1. Registrarse \n");
        sb.append("2. Ver mi codigo de usuario \n");
        sb.append("3. Pagar recibos \n");
        sb.append("4. Administrar cuenta bancaria \n");
        sb.append("5. Realizar prestamos \n");
        sb.append("6. Salir \n");
        opc = Integer.parseInt(JOptionPane.showInputDialog(sb));
        return opc;
    }
    
    //opcion 4
    private static void aministrarCuentas() throws Exception{
        int idAccion = Integer.parseInt(JOptionPane.showInputDialog("1. Crear cuenta\n"+
                                                                    "2. Ingresar dinero\n"+
                                                                    "3. Retirar dinero\n"
                                                                   +"4. Transferir a otra cuenta\n"
                                                                   +"5. Hacer doble transaccion"));
        if (idAccion == 1) {
            crearCuenta(idAccion);
        }
        
        if (idAccion == 2) {
            ingresarDinero();
        }
    }
    
    private static int obtenerCuenta(String email, String pass) throws Exception{
        Conexion conexion = new Conexion();
        PreparedStatement id = conexion.con.prepareStatement("select id from cuenta where usuarios_id=?");
        int idCuenta = 0;
        try {
            int idUsuario = retornarIdUsuario(email, pass);
            id.setInt(1, idUsuario);
            ResultSet rs = id.executeQuery();
            if (rs.next()) {
                idCuenta = rs.getInt("id");
                return idCuenta;
            } 
        } catch (Exception e) {
            System.out.println("error al recuperar la cuenta: "+e.getMessage());
        }
        return idCuenta;
    }
    
    private static double obtenerMontoActual(String email, String pass) throws Exception{
        Conexion conexion = new Conexion();
        PreparedStatement montoAc = conexion.con.prepareStatement("select monto from cuenta where usuarios_id=?");
        double monto = 0.00;
        try {
            int idUsuario = retornarIdUsuario(email, pass);
            montoAc.setInt(1, idUsuario);
            ResultSet rs = montoAc.executeQuery();
            if (rs.next()) {
                monto = rs.getDouble("monto");
                return monto;
            }
        } catch (Exception e) {
            System.out.println("erro al obtener el monto: "+e.getMessage());
        }
        return monto;
    }
    
    private static void ingresarDinero() throws  Exception{
        Conexion conexion = new Conexion();
        PreparedStatement ingreso = conexion.con.prepareStatement("UPDATE cuenta SET monto = monto + ? WHERE id = ?");
        PreparedStatement vitacora = conexion.con.prepareStatement("insert into pago_cuenta(usuario_id,acciones_cuenta,cantidadAfectada) values(?,?,?)");
        try {
            String email = JOptionPane.showInputDialog("Ingresa tu email");
            String pass = JOptionPane.showInputDialog("Ingresa tu password");
            int idUsuario = retornarIdUsuario(email, pass);
            if (idUsuario > 0) {
                
                int idCuenta = obtenerCuenta(email, pass);
                if (idCuenta > 0) {
                    double monto = Double.parseDouble(JOptionPane.showInputDialog("Ingresa el monto a ingresar"));
                    double montoActual = obtenerMontoActual(email, pass);
                    
                    double nuevoMonto = montoActual + monto;
                    
                    ingreso.setDouble(1, monto);
                    ingreso.setInt(2, idCuenta);
                    ingreso.executeUpdate();
                    
                    vitacora.setInt(1, idUsuario);
                    vitacora.setInt(2, 2);
                    vitacora.setDouble(3, monto);
                    vitacora.executeUpdate();
                    
                    JOptionPane.showMessageDialog(null, "Dinero ingresado con exito");
                } else {
                    JOptionPane.showMessageDialog(null, "No tiene una cuenta de ahorro con su usuario");
                }
                
            } else {
                JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
            }
        } catch (Exception e) {
            System.out.println("error al ingresar dinero: "+e.getMessage());
        }
    }
    
    private static void crearCuenta(int idAccion) throws Exception{
        Conexion conexion = new Conexion();
        PreparedStatement pst = conexion.con.prepareStatement("insert into cuenta(usuarios_id,monto) values(?,?)");
        try {
            String email = JOptionPane.showInputDialog("Ingresa tu email");
            String pass = JOptionPane.showInputDialog("Ingresa tu password");
            int idUsuario = retornarIdUsuario(email, pass);
            if (idUsuario > 0) {
                
                double monto = Double.parseDouble(JOptionPane.showInputDialog("Ingrese su monto inicial"));
                if (monto > 0) {
                    pst.setInt(1, idUsuario);
                    pst.setDouble(2, monto);
                    pst.executeUpdate();
                    
                    JOptionPane.showMessageDialog(null, "***¡Cuenta creada con exito!***");
                } else {
                    JOptionPane.showMessageDialog(null, "No puede iniciar con menos de 0.01");
                }
                
            } else {
                JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
            }
        } catch (Exception e) {
            System.out.println("error al crear cuenta: "+e.getMessage());
        }
    }
    
    //opcion 3
    private static void pagoRecibos() throws Exception{
        int idRecibo = Integer.parseInt(JOptionPane.showInputDialog("1. Recibo de luz\n"+
                                                                    "2. Recibo de agua\n"+
                                                                    "3. Ambos"));
        if (idRecibo == 1) {
            pagoReciboLuz(idRecibo);
        } 
        
        if (idRecibo == 2) {
            pagoReciboAgua(idRecibo);
        }
        
        if (idRecibo == 3) {
            ambosRecibos(idRecibo);
        }
    }
    
    //opcion 2
    private static void validarExistenciaUsuario() throws Exception{
        Conexion conexion = new Conexion();
        PreparedStatement pst = conexion.con.prepareStatement("select id from usuarios where email=? and password=?");
        String email = JOptionPane.showInputDialog("Ingresa tu email");
        String pass = JOptionPane.showInputDialog("Ingresa tu email");
        pst.setString(1, email);
        pst.setString(2, pass);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("id");
            JOptionPane.showMessageDialog(null, "Tu ID de usuario es: "+id);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro un ID no estas registrado o has ingresado credenciales incorrectas");
        }
    }
    
    //registrarse
    private static void registrarse() throws Exception{
        Conexion conexion = new Conexion();
        Usuario usuario = new Usuario();
        String estado = "ACTIVO";
        PreparedStatement pst = conexion.con.prepareStatement("insert into usuarios(nombre,apellido,password,email,estado) values(?,?,?,?,?)");
        try {
            String email = JOptionPane.showInputDialog("Correo electronico");
            boolean emailEncontrado = encontrarUsuarioPorEmail(email);
            
            if (!emailEncontrado) {
                String nombre = JOptionPane.showInputDialog("Ingresa tu nombre");
                String apellido = JOptionPane.showInputDialog("Ingresa tu apellido");
                String pass = JOptionPane.showInputDialog("Ingresa tu contraseña");
                usuario.setNombre(nombre);
                usuario.setApellido(apellido);
                usuario.setEmail(email);
                usuario.setPassword(pass);
                usuario.setEstado(estado);
                
                pst.setString(1, usuario.getNombre());
                pst.setString(2, usuario.getApellido());
                pst.setString(3, usuario.getPassword());
                pst.setString(4, usuario.getEmail());
                pst.setString(5, usuario.getEstado());
                
                int guardado = pst.executeUpdate();
                if (guardado > 0) {
                    JOptionPane.showMessageDialog(null, "¡Registrado con exito!");
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha podido registrar tu usuario");
                }
            } else {
                JOptionPane.showMessageDialog(null, "El correo electronico ya existe, por favor ingresa otro");
            }
            
        } catch (Exception e) {
            System.out.println("error al insertar usuario: "+e.getMessage());
        }
    }
    
    //funciones para validar
    private static boolean encontrarUsuarioPorEmail(String email) throws Exception{
        Conexion conexion = new Conexion();
        PreparedStatement emailEncontrado = conexion.con.prepareStatement("select * from usuarios where email=?");
        emailEncontrado.setString(1, email);
        ResultSet rs = emailEncontrado.executeQuery();
        boolean emailExiste = rs.first();
        return emailExiste;
    }
    
    private static int retornarIdUsuario(String email, String pass) throws Exception{
        int id = 0;
        Conexion conexion = new Conexion();
        PreparedStatement pst = conexion.con.prepareStatement("select id from usuarios where email=? and password=?");
        pst.setString(1, email);
        pst.setString(2, pass);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return id = rs.getInt("id");
        } else {
            return 0;
        }
    }
    
    private static void pagoReciboLuz(int idRecibo) throws Exception{
        Conexion conexion = new Conexion();
        PreparedStatement pst = conexion.con.prepareStatement("insert into pagorecibos(recibo_id,usuario_id,precioRecibo,pagoRecibo,vueltoRecibo) values(?,?,?,?,?)");
        PreparedStatement select = conexion.con.prepareStatement("select p.id, r.nombre_recibo, p.usuario_id, p.precioRecibo, p.pagoRecibo, p.vueltoRecibo " +
                                                                "from pagorecibos as p " +
                                                                "inner join recivos as r on p.recibo_id = r.id " +
                                                                "where p.usuario_id = ?");
        try {
            String email = JOptionPane.showInputDialog("Ingresa tu email");
            String pass = JOptionPane.showInputDialog("Ingresa tu password");
            int idUsuario = retornarIdUsuario(email, pass);
            if (idUsuario > 0) {
                double precioRecibo = Double.parseDouble(JOptionPane.showInputDialog("Ingresa el precio del recibo"));
                double pagoRecibo = Double.parseDouble(JOptionPane.showInputDialog("Ingresa el pago del recibo"));
                
                if (pagoRecibo > precioRecibo) {
                    double vueltoRecibo = pagoRecibo - precioRecibo;
                    pst.setInt(1, idRecibo);
                    pst.setInt(2, idUsuario);
                    pst.setDouble(3, precioRecibo);
                    pst.setDouble(4, pagoRecibo);
                    pst.setDouble(5, vueltoRecibo);
                    pst.executeUpdate();
                } else {
                    pst.setInt(1, idRecibo);
                    pst.setInt(2, idUsuario);
                    pst.setDouble(3, precioRecibo);
                    pst.setDouble(4, pagoRecibo);
                    pst.setDouble(5, 0.00);
                    pst.executeUpdate();
                }
                
                select.setInt(1, idUsuario);
                ResultSet rs = select.executeQuery();
                if (rs.next()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("*****¡RECIBO PAGADO EXITOSAMENTE!*****\n");
                    sb.append("Recibo \t Usuario \t Precio \t Pago \t vuelto\n");
                    sb.append(rs.getString("r.nombre_recibo")+ " \t "+rs.getInt("p.usuario_id")+ " \t "+rs.getDouble("p.precioRecibo")
                            + " \t "+rs.getDouble("precioRecibo")+ " \t "+rs.getDouble("p.vueltoRecibo"));
                    JOptionPane.showMessageDialog(null, sb);
                }
                
            } else {
                JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
            }
        } catch (Exception e) {
            System.out.println("error al pagar luz: "+e.getMessage());
        }
    }
    
    private static void pagoReciboAgua(int idRecibo) throws Exception{
        Conexion conexion = new Conexion();
        PreparedStatement pst = conexion.con.prepareStatement("insert into pagorecibos(recibo_id,usuario_id,precioRecibo,pagoRecibo,vueltoRecibo) values(?,?,?,?,?)");
        PreparedStatement select = conexion.con.prepareStatement("select p.id, r.nombre_recibo, p.usuario_id, p.precioRecibo, p.pagoRecibo, p.vueltoRecibo " +
                                                                "from pagorecibos as p " +
                                                                "inner join recivos as r on p.recibo_id = r.id " +
                                                                "where p.usuario_id = ?");
        try {
            String email = JOptionPane.showInputDialog("Ingresa tu email");
            String pass = JOptionPane.showInputDialog("Ingresa tu password");
            int idUsuario = retornarIdUsuario(email, pass);
            if (idUsuario > 0) {
                double precioRecibo = Double.parseDouble(JOptionPane.showInputDialog("Ingresa el precio del recibo"));
                double pagoRecibo = Double.parseDouble(JOptionPane.showInputDialog("Ingresa el pago del recibo"));
                
                if (pagoRecibo > precioRecibo) {
                    double vueltoRecibo = pagoRecibo - precioRecibo;
                    pst.setInt(1, idRecibo);
                    pst.setInt(2, idUsuario);
                    pst.setDouble(3, precioRecibo);
                    pst.setDouble(4, pagoRecibo);
                    pst.setDouble(5, vueltoRecibo);
                    pst.executeUpdate();
                } else {
                    pst.setInt(1, idRecibo);
                    pst.setInt(2, idUsuario);
                    pst.setDouble(3, precioRecibo);
                    pst.setDouble(4, pagoRecibo);
                    pst.setDouble(5, 0.00);
                    pst.executeUpdate();
                }
                
                select.setInt(1, idUsuario);
                ResultSet rs = select.executeQuery();
                if (rs.next()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("*****¡RECIBO PAGADO EXITOSAMENTE!*****\n");
                    sb.append("ID \t Recibo \t Usuario \t Precio \t Pago \t vuelto\n");
                    sb.append(rs.getInt("p.id")+ " \t "+rs.getString("r.nombre_recibo")+ " \t "+rs.getInt("p.usuario_id")+ " \t "+rs.getDouble("p.precioRecibo")
                            + " \t "+rs.getDouble("precioRecibo")+ " \t "+rs.getDouble("p.vueltoRecibo"));
                    JOptionPane.showMessageDialog(null, sb);
                }
                
            } else {
                JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
            }
        } catch (Exception e) {
            System.out.println("error al pagar luz: "+e.getMessage());
        }
    }
    
    private static void ambosRecibos(int idRecibo) throws Exception{
        Conexion conexion = new Conexion();
        ArrayList<AccionRecibo> lista = new ArrayList();
        PreparedStatement pst = conexion.con.prepareStatement("insert into pagorecibos(recibo_id,usuario_id,precioRecibo,pagoRecibo,vueltoRecibo) values(?,?,?,?,?)");
        PreparedStatement select = conexion.con.prepareStatement("select p.id, r.nombre_recibo, p.usuario_id, p.precioRecibo, p.pagoRecibo, p.vueltoRecibo " +
                                                                "from pagorecibos as p " +
                                                                "inner join recivos as r on p.recibo_id = r.id " +
                                                                "where p.usuario_id = ?");
        
        try {
            String email = JOptionPane.showInputDialog("Ingresa tu email");
            String pass = JOptionPane.showInputDialog("Ingresa tu password");
            int idUsuario = retornarIdUsuario(email, pass);
            if (idUsuario > 0) {
                int pagos = 0;
                double precioRecibo;
                double pagoRecibo;
                double vueltoRecibo;
                
                while (pagos < 2) {
                    AccionRecibo ar = new AccionRecibo();
                    precioRecibo = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio del recibo"));
                    pagoRecibo = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el pago del recibo"));
                    vueltoRecibo = pagoRecibo - precioRecibo;
                    
                    ar.setRecibo_id(idRecibo);
                    ar.setUsuario_id(idUsuario);
                    ar.setPrecioRecibo(precioRecibo);
                    ar.setPagoRecibo(pagoRecibo);
                    ar.setVueltoRecibo(vueltoRecibo);
                    
                    lista.add(ar);
                    pagos = pagos + 1;
                }
                
                for (AccionRecibo accionRecibo : lista) {
                    pst.setInt(1, accionRecibo.getRecibo_id());
                    pst.setInt(2, accionRecibo.getUsuario_id());
                    pst.setDouble(3, accionRecibo.getPrecioRecibo());
                    pst.setDouble(4, accionRecibo.getPagoRecibo());
                    pst.setDouble(5, accionRecibo.getVueltoRecibo());
                    pst.executeUpdate();
                }
                
                JOptionPane.showMessageDialog(null, "*****¡Recibos pagados!*****");
                
            } else {
                JOptionPane.showMessageDialog(null, "Credenciales incorrectas o el usuario no existe");
            }
        } catch (Exception e) {
            System.out.println("error en pagar ambos recibos: "+e.getMessage());
        }
    }
}
