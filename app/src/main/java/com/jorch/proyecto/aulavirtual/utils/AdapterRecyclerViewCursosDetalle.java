package com.jorch.proyecto.aulavirtual.utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jorch.proyecto.aulavirtual.R;
import com.jorch.proyecto.aulavirtual.data.Curso;

import java.util.List;

/**
 * Created by JORCH on 22/01/2017.
 */

public class AdapterRecyclerViewCursosDetalle extends RecyclerView.Adapter<AdapterRecyclerViewCursosDetalle.CursosDetalleViewHolder> {
    private List<Curso> listaCursos;
    private final AdapterRecyclerViewCursosDetalle.OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(Curso curso);
    }
    public AdapterRecyclerViewCursosDetalle(List<Curso> listaCursos,AdapterRecyclerViewCursosDetalle.OnItemClickListener listener){
        this.listaCursos = listaCursos;
        this.listener = listener;
    }
    public class  CursosDetalleViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public ImageView imageCurso;
        public TextView nombreCurso;
        public TextView descripcionCurso;
        public TextView codigoCurso;
        public CursosDetalleViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv_curso_detalle);
            imageCurso = (ImageView) itemView.findViewById(R.id.cv_curso_detalle_imagen);
            nombreCurso = (TextView) itemView.findViewById(R.id.cv_curso_detalle_nombre);
            descripcionCurso = (TextView) itemView.findViewById(R.id.cv_curso_detalle_descripcion);
            codigoCurso = (TextView) itemView.findViewById(R.id.cv_curso_detalle_codigo);
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
    public void onBindViewHolder(CursosDetalleViewHolder holder, final int position) {
        holder.imageCurso.setImageResource(listaCursos.get(position).getFoto_curso());
        holder.nombreCurso.setText(listaCursos.get(position).getNombre());
        holder.descripcionCurso.setText(listaCursos.get(position).getDescripcion());
        holder.codigoCurso.setText(listaCursos.get(position).getCodigoCurso());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(listaCursos.get(position));
            }
        });
        //TODO FALTA FECHA INICO FECHA FINAL
    }
    @Override
    public CursosDetalleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_curso_detalles,parent,false);
        return new CursosDetalleViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return listaCursos.size();
    }

}
