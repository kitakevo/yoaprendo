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
import java.util.Map;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;
import ar.com.yoaprendo.R;
import ar.com.yoaprendo.TipoUsuario;
import ar.com.yoaprendo.Utils;
import ar.com.yoaprendo.beans.Usuario;

public class LoginActivity extends AppCompatActivity {

    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setClickable(false);
                try{

                login();

                }catch (Exception e){

                Utils.popup(e.getMessage(),LoginActivity.this);

                }finally{

                    button.setClickable(true);

                }

            }
        });

    }

    private void login(){

        db = FirebaseDatabase.getInstance();

        TextView txtNombre = (TextView) findViewById(R.id.editText);
        TextView txtClave = (TextView) findViewById(R.id.editText2);

        final String id = txtNombre.getText().toString() + txtClave.getText().toString();

        final List<String> usuarios = new ArrayList<>();

        db.getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, Usuario> td = (Map<String, Usuario>) dataSnapshot.getValue();

                System.out.println(td);

                //List<String> l = new ArrayList<String>(td.keySet());

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    usuarios.add(postSnapshot.getValue(Usuario.class).usuario+postSnapshot.getValue(Usuario.class).clave);
                }

                System.out.println(usuarios);
                System.out.println(id);

                if(usuarios.contains(id)) {

                    //Intent intent = new Intent(LoginActivity.this,ChatActivity.class);
                    Intent intent = new Intent(LoginActivity.this, PlacePickerActivity.class);
                    startActivity(intent);

                }else{

                    Utils.popup("Login Incorrecto",LoginActivity.this);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Utils.popup("Error en base de datos, intente nuevamente",LoginActivity.this);
            }

        });

    }

}
