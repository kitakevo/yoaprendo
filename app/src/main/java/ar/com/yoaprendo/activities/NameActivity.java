package ar.com.yoaprendo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import androidx.appcompat.app.AppCompatActivity;
import ar.com.yoaprendo.R;
import ar.com.yoaprendo.TipoUsuario;
import ar.com.yoaprendo.Utils;

public class NameActivity extends AppCompatActivity {

    private String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_name);

        uuid = getIntent().getStringExtra("UUID");

        final TextView txtNombre = (TextView) findViewById(R.id.txtNombre);
        final TextView txtApellido = (TextView) findViewById(R.id.txtApellido);

        final Button btnContinuar = (Button) findViewById(R.id.name_btnContinuar);

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = txtNombre.getText().toString();
                String apellido = txtApellido.getText().toString();

                if (nombre.isEmpty() || apellido.isEmpty()) {
                    Utils.popup("No dejes campos vacíos", NameActivity.this);
                    return;
                }

                btnContinuar.setClickable(false);

                FirebaseDatabase db = FirebaseDatabase.getInstance();

                db.getReference().child("users").child(uuid).child("nombre").setValue(nombre + " " + apellido);

                startActivity(new Intent(NameActivity.this, ProfilePhotoActivity.class).putExtra("UUID",uuid));

            }
        });


    }


}