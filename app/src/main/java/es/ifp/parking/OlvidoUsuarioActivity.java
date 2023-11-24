package es.ifp.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OlvidoUsuarioActivity extends AppCompatActivity {

    protected TextView labelOlvido;
    protected TextView labelInstrucciones;
    protected Button botonEnviar;
    protected Button botonVolver;
    protected EditText cajaEmail;
    protected BaseDatosUsuario db;

    private String contenidoCaja="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvido_usuario);

        labelOlvido=(TextView) findViewById(R.id.label_Titulo_OlvidoUsuario);
        labelInstrucciones=(TextView) findViewById(R.id.label_instrucciones_OlvidoUsuario);
        botonVolver=(Button) findViewById(R.id.boton_Volver_OlvidoUsuario);
        botonEnviar=(Button) findViewById(R.id.boton_Enviar_OlvidoUsuario);
        cajaEmail=(EditText) findViewById(R.id.editTextEmail_OlvidoUsuario);
        db= new BaseDatosUsuario(this);

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(OlvidoUsuarioActivity.this, "Redirigiendo", Toast.LENGTH_SHORT).show();
                Intent pasarPantalla= new Intent(OlvidoUsuarioActivity.this, InicioSesionActivity.class);
                finish();
                startActivity(pasarPantalla);
            }
        });
        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contenidoCaja=cajaEmail.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{contenidoCaja});

                intent.putExtra(Intent.EXTRA_SUBJECT, "Recuperación de contraseña");
                intent.putExtra(Intent.EXTRA_TEXT, "Hola, para recuperar tu contraseña, sigue los pasos indicados en el correo.");

                    try {
                        startActivity(Intent.createChooser(intent, "Enviar correo"));
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(OlvidoUsuarioActivity.this, "No hay aplicaciones de email instaladas.", Toast.LENGTH_SHORT).show();
                    }

            }

        });
    }
}