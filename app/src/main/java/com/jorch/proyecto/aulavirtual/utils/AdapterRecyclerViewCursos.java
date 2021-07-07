package com.jorch.proyecto.aulavirtual.utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jorch.proyecto.aulavirtual.R;
import com.jorch.proyecto.aulavirtual.data.Alumno;
import com.jorch.proyecto.aulavirtual.data.Asignatura;
import com.jorch.proyecto.aulavirtual.data.AulaVirtualContract;
import com.jorch.proyecto.aulavirtual.data.Curso;
import com.jorch.proyecto.aulavirtual.data.Profesor;

import java.util.List;

/**
 * Created by JORCH on 18/01/2017.
 */

public class AdapterRecyclerViewCursos extends RecyclerView.Adapter<AdapterRecyclerViewCursos.CursosViewHolder> {
    private List<Curso> listaCursos;
    private final OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(Curso curso);
    }
    public AdapterRecyclerViewCursos(List<Curso> listaCursos,OnItemClickListener listener){
        this.listaCursos = listaCursos;
        this.listener = listener;
    }
    public class  CursosViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public ImageView imageCurso;
        public TextView nombreCurso;
        public TextView descripcionCurso;
        public CursosViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv_curso);
            imageCurso = (ImageView) itemView.findViewById(R.id.cv_curso_imagen);
            nombreCurso = (TextView) itemView.findViewById(R.id.cv_curso_nombre);
            descripcionCurso = (TextView) itemView.findViewById(R.id.cv_curso_descripcion);
        }
    }

    public void addAll(List<Curso> lista){
        listaCursos.addAll(lista);
        notifyDataSetChanged();
    }

    public void clear(){
        listaCursos.clear();
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(CursosViewHolder holder, final int position) {
        holder.imageCurso.setImageResource(listaCursos.get(position).getFoto_curso());
        holder.nombreCurso.setText(listaCursos.get(position).getNombre());
        holder.descripcionCurso.setText(listaCursos.get(position).getDescripcion());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(listaCursos.get(position));
            }
        });
        //TODO FALTA FECHA INICO FECHA FINAL
    }

    @Override
    public CursosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_curso,parent,false);
        return new CursosViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return listaCursos.size();
    }
}
