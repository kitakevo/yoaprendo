package ar.com.yoaprendo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import ar.com.yoaprendo.R;
import ar.com.yoaprendo.Utils;
import ar.com.yoaprendo.placepicker.TransparentProgressDialog;

public class LoginActivity extends AppCompatActivity {

    Button button;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        final Button button = (Button) findViewById(R.id.btnLogin);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button.setClickable(false);

                try{

                login();

                }catch (Exception e){

                Utils.popup(e.getMessage(),LoginActivity.this);

                }

            }
        });

    }

    private void login(){

        db = FirebaseDatabase.getInstance();

        TextView txtUsuario = (TextView) findViewById(R.id.txtUsuario);
        TextView txtClave = (TextView) findViewById(R.id.txtClave);

        final String id = Utils.hash(txtUsuario.getText().toString() + txtClave.getText().toString());

        db.getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<String> listId = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    listId.add(postSnapshot.getKey());

                }

                if(listId.contains(id)) {

                    startActivity(new Intent(LoginActivity.this, MenuActivity.class).putExtra("ID",id));

                }else{

                    Utils.popup("Clave o usuario incorrecto",LoginActivity.this);

                    button.setClickable(true);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Utils.popup("Error en base de datos, intente nuevamente",LoginActivity.this);

                button.setClickable(true);

            }

        });

    }

}
