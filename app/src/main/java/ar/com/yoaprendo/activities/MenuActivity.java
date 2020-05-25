package ar.com.yoaprendo.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ar.com.yoaprendo.R;
import ar.com.yoaprendo.Utils;
import ar.com.yoaprendo.beans.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

public class MenuActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private String uuid;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);

        uuid = getIntent().getStringExtra("UUID");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();

        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        FirebaseDatabase db = FirebaseDatabase.getInstance();

        db.getReference().child("users").child(uuid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                usuario = dataSnapshot.getValue(Usuario.class);

                CircleImageView fotoPerfil = (CircleImageView) findViewById (R.id.fotoPerfilHeader);
                TextView nombreUsuario = (TextView) findViewById(R.id.txtNombreUsuario);

                if(usuario.getNombre().equals("preder")){
                    nombreUsuario.setText(usuario.getUsuario());
                }else{
                    nombreUsuario.setText(usuario.getNombre());
                }

                if(!usuario.getFotoPerfil().equals("preder"))
                Glide.with(MenuActivity.this).load(usuario.getFotoPerfil()).into(fotoPerfil);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                //TODO
                //Cerrar App

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
