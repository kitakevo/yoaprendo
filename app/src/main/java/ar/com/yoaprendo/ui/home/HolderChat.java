package ar.com.yoaprendo.ui.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import ar.com.yoaprendo.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class HolderChat extends RecyclerView.ViewHolder{

    private TextView nombre;
    private CircleImageView fotoMensaje;

    public HolderChat(View itemView) {

        super(itemView);
        nombre = (TextView) itemView.findViewById(R.id.contacto);
        fotoMensaje = (CircleImageView) itemView.findViewById(R.id.fotoPerfil);

    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public ImageView getFotoMensaje() {
        return fotoMensaje;
    }

    public void setFotoMensaje(CircleImageView fotoMensaje) {
        this.fotoMensaje = fotoMensaje;
    }

}
