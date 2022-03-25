package org.equiposeis.huellitasaventureras.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.equiposeis.huellitasaventureras.Glide.GlideApp;
import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.dataModels.Paseo;
import org.equiposeis.huellitasaventureras.dataModels.UsuarioCliente;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kotlin.jvm.functions.Function1;

public class RidesCompletedAdapter extends RecyclerView.Adapter<RidesCompletedAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Paseo> paseos;
    private final Task<QuerySnapshot> users;
    private final Function1 clickListener;

    public RidesCompletedAdapter(Context context, ArrayList<Paseo> paseos, Task<QuerySnapshot> users, Function1 clickListener) {
        this.context = context;
        this.paseos = paseos;
        this.users = users;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RidesCompletedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rclr_item_ride,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RidesCompletedAdapter.ViewHolder holder, int position) {
        Paseo paseo = paseos.get(position);
        users.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot user : users.getResult()) {
                    if (user.get("ID_Usuario").toString().equals(paseo.getId_usuario())) {
                        UsuarioCliente cliente = new UsuarioCliente(
                                Integer.parseInt(user.get("Mascotas_Alta").toString()),
                                user.get("Foto_Usuario").toString(),
                                user.get("ID_Usuario").toString(),
                                user.get("Nombre").toString(),
                                Integer.parseInt(user.get("Genero").toString()),
                                Integer.parseInt(user.get("Edad").toString()),
                                Long.parseLong(user.get("Numero_Telefonico").toString()),
                                user.get("Domicilio").toString(),
                                user.get("Correo_Electronico").toString(),
                                Integer.parseInt(user.get("Tipo_Usuario").toString())
                        );

                        holder.bind(cliente, paseo, context);;
                    }
                }
            }
        });

        holder.itemView.setOnClickListener(view -> RidesCompletedAdapter.this.clickListener.invoke(paseo));
    }

    @Override
    public int getItemCount() {
        return paseos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtUser;
        private final TextView txtTime;
        private final ShapeableImageView imgUserPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtUser = itemView.findViewById(R.id.txtViewNameEmployee);
            this.txtTime = itemView.findViewById(R.id.txtViewTiempo);
            this.imgUserPhoto = itemView.findViewById(R.id.imgUserPhoto);

        }

        public void bind(UsuarioCliente cliente, Paseo Paseo, Context context) {
            if (cliente.getNombre().length() > 14)
                txtUser.setText(cliente.getNombre().substring(0, 14) + "...");
            else
                txtUser.setText(cliente.getNombre());
            txtTime.setText(Paseo.getDuracionPaseo());
            GlideApp.with(context)
                    .load(cliente.getFoto_perfil())
                    .apply(RequestOptions.bitmapTransform(new CropCircleTransformation()))
                    .into(imgUserPhoto);
        }
    }
}
