package com.example.androidrecupracticatareasroom;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidrecupracticatareasroom.data.Nota;
import com.example.androidrecupracticatareasroom.data.Priority;
import com.example.androidrecupracticatareasroom.data.RoomDB;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NotaRecyclerAdapter extends RecyclerView.Adapter<NotaRecyclerAdapter.ViewHolder> {
    private final NotaItemClickListener clickListener;
    List<Nota> notas;
    private Activity context;
    RoomDB db;

    public NotaRecyclerAdapter(NotaItemClickListener clickListener, List<Nota> notas, Activity context) {
        this.clickListener = clickListener;
        this.notas = notas;
        this.context = context;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotaRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nota_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Nota nota = notas.get(position);

        db = RoomDB.getInstance(context);

        holder.tvTitle.setText(nota.getTitle());
        holder.tvDescription.setText(nota.getDescription());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate date = LocalDate.of(nota.getYear(), nota.getMonth(), nota.getDay());
            holder.tvDateTime.setText(date.format(DateTimeFormatter.ISO_DATE));
        } else {
            holder.tvDateTime.setText(nota.getDay()+"/"+nota.getMonth()+"/"+nota.getYear());
        }
        if (nota.getPriority() == Priority.BAJA) {
            holder.ivPriority.setImageResource(R.drawable.ic_baseline_circle_24_green);
        } else if (nota.getPriority() == Priority.MEDIA) {
            holder.ivPriority.setImageResource(R.drawable.ic_baseline_circle_24_orange);
        } else if (nota.getPriority() == Priority.ALTA) {
            holder.ivPriority.setImageResource(R.drawable.ic_baseline_circle_24_red);
        } else holder.ivPriority.setImageResource(R.drawable.ic_baseline_circle_24_red);
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tvTitle, tvDateTime, tvDescription;
        ImageView ivPriority;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title_item);
            tvDateTime = itemView.findViewById(R.id.tv_date_item);
            tvDescription = itemView.findViewById(R.id.tv_description_item);
            ivPriority = itemView.findViewById(R.id.iv_priority_item);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            clickListener.onListItemClick(position);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onLongClick();
            return true;
        }
    }
}
