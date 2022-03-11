package org.equiposeis.huellitasaventureras.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.dataModels.Mascota;

import java.util.ArrayList;

import kotlin.jvm.functions.Function1;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Mascota> mascotas;
    private final Function1 clickListener;

    public PetAdapter(Context context, ArrayList<Mascota> mascotas, Function1 clickListener) {
        this.context = context;
        this.mascotas = mascotas;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public PetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rclr_item_pet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetAdapter.ViewHolder holder, int position) {
        Mascota mascota = mascotas.get(position);
        holder.bind(mascota, context);

        holder.itemView.setOnClickListener(view -> PetAdapter.this.clickListener.invoke(mascota));
    }

    @Override
    public int getItemCount() {
        return mascotas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView petName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.petName = itemView.findViewById(R.id.txtViewPetName);
        }

        public void bind(Mascota mascota, Context context) {
            petName.setText(mascota.getNombre_mascota());
        }
    }
}
