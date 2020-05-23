package ar.com.yoaprendo.activities;

import androidx.appcompat.app.AppCompatActivity;
import ar.com.yoaprendo.R;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView button = (TextView) findViewById(R.id.loginButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginActivity();
            }
        });

        Button button1 = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInActivity();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInActivity();
            }
        });

    }

    public void loginActivity(){

        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);

    }

    public void signInActivity(){

        Intent intent = new Intent(MainActivity.this,SignInActivity.class);
        startActivity(intent);

    }


}
