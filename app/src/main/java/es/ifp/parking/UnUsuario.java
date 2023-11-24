package es.ifp.parking;

public class UnUsuario {
    protected String nombre;
    protected String apellido;
    protected String email;
    protected String telefono;
    protected String cp;
    protected String marcaC;
    protected String modeloC;
    protected String matricula;
    protected String password;

    public UnUsuario(String n, String ap, String email, String tel, String cp, String maC, String moC, String mat, String pas){
        this.nombre=n;
        this.apellido=ap;
        this.email=email;
        this.telefono=tel;
        this.cp=cp;
        this.marcaC=maC;
        this.modeloC=moC;
        this.matricula=mat;
        this.password=pas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getMarcaC() {
        return marcaC;
    }

    public void setMarcaC(String marcaC) {
        this.marcaC = marcaC;
    }

    public String getModeloC() {
        return modeloC;
    }

    public void setModeloC(String modeloC) {
        this.modeloC = modeloC;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
