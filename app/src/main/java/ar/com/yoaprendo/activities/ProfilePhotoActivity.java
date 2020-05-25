package ar.com.yoaprendo.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import ar.com.yoaprendo.R;
import ar.com.yoaprendo.Utils;
import ar.com.yoaprendo.chat.MensajeEnviar;
import ar.com.yoaprendo.placepicker.TransparentProgressDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePhotoActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Button btnContinuar;
    private Button btnSubirFoto;
    private static final int PHOTO_SEND = 1;
    private CircleImageView imagen;
    private StorageReference fotoReferencia;
    private String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profilepic);

        imagen = (CircleImageView) findViewById(R.id.fotoPerfil);

        btnContinuar = (Button) findViewById(R.id.profilepic_btnContinuar);
        btnSubirFoto = (Button) findViewById(R.id.btnSubirFoto);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        storage = FirebaseStorage.getInstance();

        uuid = getIntent().getStringExtra("UUID");

        btnSubirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Selecciona una foto"), PHOTO_SEND);
            }
        });

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfilePhotoActivity.this, LocationActivity.class).putExtra("UUID", uuid));

            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PHOTO_SEND && resultCode == RESULT_OK) {

            final Uri u = data.getData();

            storageReference = storage.getReference();

            fotoReferencia = storageReference.child(u.getLastPathSegment());

            UploadTask uploadTask = fotoReferencia.putFile(u);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                    Toast.makeText(ProfilePhotoActivity.this, "No se pudo subir la imagen, intente mas tarde.", Toast.LENGTH_SHORT).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    imagen.setImageURI(u);

                    fotoReferencia.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            databaseReference.child("users").child(uuid).child("fotoPerfil").setValue(uri.toString());

                        }
                    });

                }
            });

        }

    }


}

