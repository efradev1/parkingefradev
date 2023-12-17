package es.ifp.parking;

public class UnaVenta {
    protected int id_venta;
    protected int id_usuario;
    protected String fecha;
    protected String hora;
    protected Double latitud;
    protected Double longitud;
    protected String detalles;

    public UnaVenta(int idv, int idu, String f, String h, Double lat, Double lon, String det){
        this.id_venta=idv;
        this.id_usuario=idu;
        this.fecha=f;
        this.hora=h;
        this.latitud=lat;
        this.longitud=lon;
        this.detalles=det;
    }
    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
    public String getDetalles(){return detalles;}
    public void setDetalles(String detalles){this.detalles = detalles;}



}
