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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import ar.com.yoaprendo.LoadingDialog;
import ar.com.yoaprendo.R;
import ar.com.yoaprendo.Utils;
import ar.com.yoaprendo.placepicker.TransparentProgressDialog;

public class LoginActivity extends AppCompatActivity {

    private Button button;
    private FirebaseDatabase db;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        button = (Button) findViewById(R.id.btnLogin);

        loadingDialog = new LoadingDialog(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{ login(); }catch (Exception e){

                Utils.popup(e.getMessage(),LoginActivity.this);

                }

            }
        });

    }

    private void login(){

        button.setClickable(false);

        loadingDialog.startLoadingDialog();

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

                loadingDialog.dismissDialog();

                button.setClickable(true);

                if (listId.contains(id)) {

                    startActivity(new Intent(LoginActivity.this, MenuActivity.class).putExtra("UUID", id));

                } else {

                    Utils.popup("Clave o usuario incorrecto", LoginActivity.this);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Utils.popup("Error en base de datos, intente nuevamente",LoginActivity.this);

                loadingDialog.dismissDialog();

                button.setClickable(true);

            }

        });

    }

}
