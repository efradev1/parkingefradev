package es.ifp.parking;

public class UnaReserva {
    protected int id_reserva;
    protected int id_usuario;
    protected String fecha;
    protected String hora;
    protected Double latitud;
    protected Double longitud;

    public UnaReserva(int idr, int idu, String f, String h, Double lat, Double lon, String detalles){
        this.id_reserva=idr;
        this.id_usuario=idu;
        this.fecha=f;
        this.hora=h;
        this.latitud=lat;
        this.longitud=lon;
    }
    public int getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
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



}
