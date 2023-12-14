package es.ifp.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;

public class RegistroUsuario extends AppCompatActivity {

    protected EditText editTextNombre;
    protected EditText editTextApellido;
    protected EditText editTextEmail;
    protected EditText editTextTelefono;
    protected EditText editTextCodigoPostal;
    protected EditText editTextMarcaCoche;
    protected EditText editTextModeloCoche;
    protected EditText editTextMatricula;
    protected EditText editTextPassword;
    protected EditText editTextPasswordV;
    protected TextView registroTitulo;
    protected Button registrarse;
    protected Button volver;

    private String contenidoNombre="";
    private String contenidoApellido="";
    private String contenidoEmail="";
    private String contenidoTelefono="";
    private String contenidoCP="";
    private String contenidoMarcaC="";
    private String contenidoModeloC="";
    private String contenidoMatricula="";
    private String contenidoPas="";
    private String contenidoPasV="";
    protected BaseDatosUsuario db;

    private boolean esEmailValido(String email) {
        String expresionRegularEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(expresionRegularEmail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        editTextNombre=(EditText) findViewById(R.id.editTextNombre_RegistroUsuario);
        editTextApellido=(EditText) findViewById(R.id.editTextApellido_RegistroUsuario);
        editTextEmail=(EditText) findViewById(R.id.editTextEmail_RegistroUsuario);
        editTextTelefono=(EditText) findViewById(R.id.editTextTelefono_RegistroUsuario);
        editTextCodigoPostal=(EditText) findViewById(R.id.editTextCodigoPostal_RegistroUsuario);
        editTextMarcaCoche=(EditText) findViewById(R.id.editTextMarcaCoche_RegistroUsuario);
        editTextModeloCoche=(EditText) findViewById(R.id.editTextModeloCoche_RegistroUsuario);
        editTextMatricula=(EditText) findViewById(R.id.editTextMatricula_RegistroUsuario);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword_RegistroUsuario);
        editTextPasswordV=(EditText) findViewById(R.id.editTextVerifyPassword_RegistroUsuario);
        registroTitulo=(TextView) findViewById(R.id.textView_Registro_RegistroUsuario);
        registrarse=(Button) findViewById(R.id.botonRegistro_RegistroUsuario);
        volver=(Button) findViewById(R.id.botonVolver_RegistroUsuario);
        db= new BaseDatosUsuario(this );

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contenidoNombre= editTextNombre.getText().toString();
                contenidoApellido=editTextApellido.getText().toString();
                contenidoEmail= editTextEmail.getText().toString();
                contenidoTelefono=editTextTelefono.getText().toString();
                contenidoCP= editTextCodigoPostal.getText().toString();
                contenidoMarcaC=editTextMarcaCoche.getText().toString();
                contenidoModeloC= editTextModeloCoche.getText().toString();
                contenidoMatricula=editTextMatricula.getText().toString();
                contenidoPas= editTextPassword.getText().toString();
                contenidoPasV=editTextPasswordV.getText().toString();

                if(contenidoNombre.equals("")){
                    Toast.makeText(RegistroUsuario.this, "Campo obligatorio", Toast.LENGTH_LONG).show();
                }else if(contenidoApellido.equals("")){
                    Toast.makeText(RegistroUsuario.this, "Campo obligatorio", Toast.LENGTH_LONG).show();
                }else if(contenidoEmail.equals("")) {
                    Toast.makeText(RegistroUsuario.this, "Campo obligatorio", Toast.LENGTH_LONG).show();
                }else if(!esEmailValido(contenidoEmail)){
                    Toast.makeText(RegistroUsuario.this, "Email no válido", Toast.LENGTH_LONG).show();
                }else if (contenidoTelefono.equals("")) {
                    Toast.makeText(RegistroUsuario.this, "Campo obligatorio", Toast.LENGTH_LONG).show();
                }else if(!TextUtils.isDigitsOnly(contenidoTelefono)){
                    Toast.makeText(RegistroUsuario.this, "El teléfono solo admite números", Toast.LENGTH_SHORT).show();
                }else if(contenidoTelefono.length()!=9){
                    Toast.makeText(RegistroUsuario.this, "Número incorrecto", Toast.LENGTH_SHORT).show();
                }else if (contenidoCP.equals("")) {
                    Toast.makeText(RegistroUsuario.this, "Campo obligatorio", Toast.LENGTH_LONG).show();
                }else if(!TextUtils.isDigitsOnly(contenidoCP)){
                    Toast.makeText(RegistroUsuario.this, "Código postal solo admite números", Toast.LENGTH_SHORT).show();
                }else if(contenidoCP.length()!=5){
                    Toast.makeText(RegistroUsuario.this, "El Código postal debe tener 5 números", Toast.LENGTH_SHORT).show();
                }else if (contenidoMarcaC.equals("")) {
                    Toast.makeText(RegistroUsuario.this, "Campo obligatorio", Toast.LENGTH_LONG).show();
                }else if (contenidoModeloC.equals("")) {
                    Toast.makeText(RegistroUsuario.this, "Campo obligatorio", Toast.LENGTH_LONG).show();
                }else if (contenidoMatricula.equals("")) {
                    Toast.makeText(RegistroUsuario.this, "Campo obligatorio", Toast.LENGTH_LONG).show();
                }else if(contenidoMatricula.length()!=7){
                    Toast.makeText(RegistroUsuario.this, "Matricula inválida", Toast.LENGTH_SHORT).show();
                }else if (contenidoPas.equals("")) {
                    Toast.makeText(RegistroUsuario.this, "Campo obligatorio", Toast.LENGTH_LONG).show();
                }else if (contenidoPasV.equals("")) {
                    Toast.makeText(RegistroUsuario.this, "Campo obligatorio", Toast.LENGTH_LONG).show();
                }else if (!contenidoPasV.equals(contenidoPas)){
                    Toast.makeText(RegistroUsuario.this, "No coinciden", Toast.LENGTH_LONG).show();

                } else{

                    boolean existe =db.UnUsuarioV(contenidoEmail, contenidoPas);
                    if(!existe){
                        db.insertUsuario(contenidoNombre,contenidoApellido,contenidoEmail,contenidoTelefono,contenidoCP,contenidoMarcaC,contenidoModeloC,contenidoMatricula,contenidoPas);
                        Toast.makeText(RegistroUsuario.this,"Registro realizado", Toast.LENGTH_LONG).show();

                        SharedPreferences preferences = getSharedPreferences("usuario_info", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor= preferences.edit();
                        editor.putString("email",contenidoEmail);
                        editor.putString("password",contenidoPas);
                        editor.apply();

                        Intent pasarPantalla = new Intent(RegistroUsuario.this, InicioSesionActivity.class);
                        finish();
                        startActivity(pasarPantalla);

                    }else{
                        Toast.makeText(RegistroUsuario.this, "Usuario existente", Toast.LENGTH_SHORT).show();

                    }

                }



            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pasarPantalla = new Intent(RegistroUsuario.this, InicioSesionActivity.class);
                finish();
                startActivity(pasarPantalla);
            }
        });
    }
}