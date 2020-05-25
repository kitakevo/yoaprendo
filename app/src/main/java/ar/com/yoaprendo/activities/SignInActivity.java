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

import androidx.appcompat.app.AppCompatActivity;
import ar.com.yoaprendo.LoadingDialog;
import ar.com.yoaprendo.R;
import ar.com.yoaprendo.TipoUsuario;
import ar.com.yoaprendo.Utils;
import ar.com.yoaprendo.beans.Usuario;
import ar.com.yoaprendo.exception.EmptyFieldException;
import ar.com.yoaprendo.exception.PasswordMismatchException;
import ar.com.yoaprendo.exception.SpaceCharacterException;

public class SignInActivity extends AppCompatActivity {

    private Button button;
    private FirebaseDatabase db;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signin);

        button = findViewById(R.id.btnSignIn);

        loadingDialog = new LoadingDialog(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        if((usuario.contains(" ")|| clave.contains(" ") || reclave.contains(" ")))
            throw new SpaceCharacterException();

        if((usuario.isEmpty() || clave.isEmpty() || reclave.isEmpty()))
            throw new EmptyFieldException();

        if(!clave.equals(reclave))
            throw new PasswordMismatchException();

        Usuario user = new Usuario(txtUsuario.getText().toString(),txtClave.getText().toString(), TipoUsuario.valueOf(getIntent().getStringExtra("TIPO_USUARIO")));

        traerUsuarios(user);

    }

    public void traerUsuarios(final Usuario usuario){

        button.setClickable(false);

        loadingDialog.startLoadingDialog();

        final List<String> usuarios = new ArrayList<>();

        db.getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, Usuario> td = (Map<String, Usuario>) dataSnapshot.getValue();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    usuarios.add(postSnapshot.getValue(Usuario.class).getUsuario());
                }

                if(!usuarios.contains(usuario.getUsuario())) {

                    String uuid = Utils.hash(usuario.getUsuario() + usuario.getClave());

                    usuario.setClave("");

                    db.getReference().child("users").child(uuid).setValue(usuario);

                    startActivity(new Intent(SignInActivity.this,NameActivity.class).putExtra("UUID",uuid));

                }else{

                    Utils.popup("Ya existe un usuario con el mismo nombre",SignInActivity.this);

                }

                button.setClickable(true);

                loadingDialog.dismissDialog();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Utils.popup("Error en base de datos, intente nuevamente",SignInActivity.this);

                button.setClickable(true);

                loadingDialog.dismissDialog();

            }

        });

    }

}
