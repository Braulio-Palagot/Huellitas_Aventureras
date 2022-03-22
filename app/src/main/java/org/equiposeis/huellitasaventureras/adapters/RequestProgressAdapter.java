package org.equiposeis.huellitasaventureras.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.dataModels.Mascota;
import org.equiposeis.huellitasaventureras.dataModels.Paseo;
import org.equiposeis.huellitasaventureras.dataModels.UsuarioPaseador;
import org.equiposeis.huellitasaventureras.ui.RideRequestClientFragment;

import java.util.ArrayList;

import kotlin.jvm.functions.Function1;

public class RequestProgressAdapter extends RecyclerView.Adapter<RequestProgressAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<UsuarioPaseador> UsuarioPaseador;
    private final Function1 clickListener;

    public RequestProgressAdapter(Context context, ArrayList<UsuarioPaseador> UsuarioPaseador, Function1 clickListener) {
        this.context = context;
        this.UsuarioPaseador = UsuarioPaseador;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RequestProgressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestProgressAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView NombreEmployee;
        private final TextView Time;
        private final ImageView UserPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.NombreEmployee = itemView.findViewById(R.id.txtViewNameEmployee);
            this.Time = itemView.findViewById(R.id.txtViewTiempo);
            this.UserPhoto = itemView.findViewById(R.id.imgUserPhoto);

        }
        public void bind(UsuarioPaseador UsuarioPaseador, Paseo Paseo, Context context) {
            NombreEmployee.setText(UsuarioPaseador.getNombre());
            Time.setText(Paseo.getTime());
            UserPhoto.setImageBitmap(UsuarioPaseador.getFoto_perfil());
        }
    }
}
