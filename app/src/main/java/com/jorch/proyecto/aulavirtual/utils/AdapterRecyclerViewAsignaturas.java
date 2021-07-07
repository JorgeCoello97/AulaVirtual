package com.jorch.proyecto.aulavirtual.utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jorch.proyecto.aulavirtual.R;
import com.jorch.proyecto.aulavirtual.data.Asignatura;

import java.util.List;

/**
 * Created by JORCH on 21/01/2017.
 */

public class AdapterRecyclerViewAsignaturas extends RecyclerView.Adapter<AdapterRecyclerViewAsignaturas.AsignaturasViewHolder> {
    private List<Asignatura> listaAsignatura;
    private final OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(Asignatura asignatura);
    }
    public AdapterRecyclerViewAsignaturas(List<Asignatura> listaAsignatura,AdapterRecyclerViewAsignaturas.OnItemClickListener listener){
        this.listaAsignatura = listaAsignatura;
        this.listener = listener;
    }
    public class  AsignaturasViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public ImageView imageCurso;
        public TextView nombreCurso, descripcionCurso,fechaInicio,fechaFin,horaInicio,horaFin;
        public AsignaturasViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv_asignatura);
            imageCurso = (ImageView) itemView.findViewById(R.id.cv_asignatura_imagen);
            nombreCurso = (TextView) itemView.findViewById(R.id.cv_asignatura_nombre);
            descripcionCurso = (TextView) itemView.findViewById(R.id.cv_asignatura_descripcion);
            fechaInicio = (TextView) itemView.findViewById(R.id.cv_asignatura_fecha_inicio);
            fechaFin = (TextView) itemView.findViewById(R.id.cv_asignatura_fecha_fin);
            horaInicio = (TextView) itemView.findViewById(R.id.cv_asignatura_hora_inicio);
            horaFin = (TextView) itemView.findViewById(R.id.cv_asignatura_hora_fin);
        }
    }
    public void addAll(List<Asignatura> lista){
        listaAsignatura.addAll(lista);
        notifyDataSetChanged();
    }

    public void clear(){
        listaAsignatura.clear();
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(AsignaturasViewHolder holder, final int position) {
        holder.imageCurso.setImageResource(listaAsignatura.get(position).getFoto_asignatura());
        holder.nombreCurso.setText(listaAsignatura.get(position).getNombre());
        holder.descripcionCurso.setText(listaAsignatura.get(position).getDescripcion());
        holder.fechaInicio.setText(listaAsignatura.get(position).getFecha_inicio());
        holder.fechaFin.setText(listaAsignatura.get(position).getFecha_fin());
        holder.horaInicio.setText(listaAsignatura.get(position).getHora_inicio());
        holder.horaFin.setText(listaAsignatura.get(position).getHora_fin());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(listaAsignatura.get(position));
            }
        });
        //TODO FALTA FECHA INICO FECHA FINAL
    }
    @Override
    public AsignaturasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_asignatura,parent,false);
        return new AsignaturasViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return listaAsignatura.size();
    }
}
