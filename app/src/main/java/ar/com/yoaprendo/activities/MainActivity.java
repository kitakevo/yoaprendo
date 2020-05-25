package ar.com.yoaprendo.activities;

import androidx.appcompat.app.AppCompatActivity;
import ar.com.yoaprendo.R;
import ar.com.yoaprendo.TipoUsuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        TextView btnLogin = (TextView) findViewById(R.id.loginButton);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginActivity();
            }
        });

        Button btnAlumno = (Button) findViewById(R.id.btnCrearAlumno);
        Button btnProfesor = (Button) findViewById(R.id.btnCrearProfesor);

        btnAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInActivity(TipoUsuario.ALUMNO);
            }
        });

        btnProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInActivity(TipoUsuario.PROFESOR);
            }
        });

    }

    public void loginActivity(){

        startActivity(new Intent(MainActivity.this,LoginActivity.class));

    }

    public void signInActivity(TipoUsuario tipoUsuario){

        startActivity(new Intent(MainActivity.this, SignInActivity.class).putExtra("TIPO_USUARIO",tipoUsuario.toString()));

    }


}
