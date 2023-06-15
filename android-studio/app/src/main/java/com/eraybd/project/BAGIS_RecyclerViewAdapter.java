package com.eraybd.project;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;




public class BAGIS_RecyclerViewAdapter extends RecyclerView.Adapter<BAGIS_RecyclerViewAdapter.MyViewHolder>{
    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<BagisModel> bagisModels;




    public BAGIS_RecyclerViewAdapter(Context context, ArrayList<BagisModel> bagisModels,
                                     RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.bagisModels = bagisModels;
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @NonNull
    @Override
    public BAGIS_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row,parent,false);

        return new BAGIS_RecyclerViewAdapter.MyViewHolder(view,recyclerViewInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull BAGIS_RecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.turAdi.setText(bagisModels.get(position).getBagisTur());
        holder.turAciklama.setText(bagisModels.get(position).getBagis_Aciklama());

    }

    @Override
    public int getItemCount() {
        // Kaç adet gösterilecekse o yazacak
        return bagisModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView turAdi;
        TextView turAciklama;

        public MyViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            turAdi = itemView.findViewById(R.id.baslik_texti);

            turAciklama = itemView.findViewById(R.id.aciklama_texti);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }

                }
            });
        }
    }
}
