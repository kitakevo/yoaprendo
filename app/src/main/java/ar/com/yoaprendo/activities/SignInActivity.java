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
import ar.com.yoaprendo.exception.EmptyFieldException;
import ar.com.yoaprendo.exception.PasswordMismatchException;
import ar.com.yoaprendo.placepicker.TransparentProgressDialog;


public class SignInActivity extends AppCompatActivity {

    TransparentProgressDialog mProgressDialog;
    Button button;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        button = (Button) findViewById(R.id.btnSignIn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button.setClickable(false);

                mProgressDialog = new TransparentProgressDialog(SignInActivity.this);

                Utils.loadScreen(mProgressDialog);

                 try{

                    singIn();

                }catch (Exception e){

                    Utils.popup(e.getMessage(),SignInActivity.this);

                }

            }
        });

    }

    private void singIn() throws Exception {

        db = FirebaseDatabase.getInstance();

        TextView txtUsuario = (TextView) findViewById(R.id.txtUsuario);
        TextView txtClave = (TextView) findViewById(R.id.txtClave);
        TextView txtReclave = (TextView) findViewById(R.id.txtReclave);

        String usuario = txtUsuario.getText().toString();
        String clave = txtClave.getText().toString();
        String reclave = txtReclave.getText().toString();

        if((usuario.isEmpty() || clave.isEmpty() || reclave.isEmpty()))
            throw new EmptyFieldException();

        if(!clave.equals(reclave))
            throw new PasswordMismatchException();

        Usuario user = new Usuario(txtUsuario.getText().toString(),txtClave.getText().toString(), TipoUsuario.ALUMNO);

        traerUsuarios(user);

    }

    public void traerUsuarios(final Usuario usuario){

        final List<String> usuarios = new ArrayList<>();

        db.getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, Usuario> td = (Map<String, Usuario>) dataSnapshot.getValue();

                System.out.println(td);

                //List<String> l = new ArrayList<String>(td.keySet());

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    usuarios.add(postSnapshot.getValue(Usuario.class).usuario);
                }

                if(!usuarios.contains(usuario.usuario)) {

                    db.getReference().child("users").child(UUID.randomUUID().toString()).setValue(usuario);

                    //Utils.popup("Usuario Creado",SignInActivity.this);

                    startActivity(new Intent(SignInActivity.this,LocationActivity.class));

                }else{

                    Utils.popup("Ya existe un usuario con el mismo nombre",SignInActivity.this);

                }

                Utils.disableLoadScreen(mProgressDialog);

                button.setClickable(true);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Utils.popup("Error en base de datos, intente nuevamente",SignInActivity.this);
                Utils.disableLoadScreen(mProgressDialog);
                button.setClickable(true);
            }

        });

    }

}
