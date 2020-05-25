package ar.com.yoaprendo.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import ar.com.yoaprendo.R;
import ar.com.yoaprendo.chat.MensajeRecibir;

public class AdapterChat extends RecyclerView.Adapter<HolderChat> {

    private List<Contacto> listContactos = new ArrayList<>();

    private Context c;

    public AdapterChat(Context c) {
        this.c = c;
    }

    public void addContacto(Contacto m){

        listContactos.add(m);
        notifyItemInserted(listContactos.size());

    }

    @Override
    public HolderChat onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(c).inflate(R.layout.card_contacto_chat,parent,false);

        return new HolderChat(v);

    }

    @Override
    public void onBindViewHolder(HolderChat holder, int position) {

        holder.getNombre().setText(listContactos.get(position).getNombre());
        Glide.with(c).load(listContactos.get(position).getfotoUrl()).into(holder.getFotoMensaje());

    }

    @Override
    public int getItemCount() {
        return listContactos.size();
    }

}
