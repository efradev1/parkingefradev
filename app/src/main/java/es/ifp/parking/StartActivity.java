package es.ifp.parking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {

    protected ImageView logo;


    Timer timer= new Timer();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo=(ImageView) findViewById(R.id.logo_Start);
        float screenWidth= getResources().getDisplayMetrics().widthPixels;
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(2500);
        //Animation animation1 = new ScaleAnimation(0.2f,1.5f,0.2f,1.5f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        logo.startAnimation(animation);

        TimerTask myTimerTask = new TimerTask() {
            public void run(){

                Intent pasarPantalla= new Intent(StartActivity.this, InicioSesionActivity.class);
                finish();
                startActivity(pasarPantalla);
            }
        };
        timer.schedule(myTimerTask, 2500);

    }
}

//hola hola
//más sabe el perro sanxe por perro que por sanxe