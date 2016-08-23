package com.example.tallesrodrigues.medicament11;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TallesRodrigues on 7/30/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private String[] mDataset;
    List<Medicine> list = new ArrayList<>(); //new


    public static class ViewHolder extends RecyclerView.ViewHolder {
        //private final TextView textView;
        ImageView cardimage;//new
        TextView cardtitle;
        Medicine medicine;
        TextView concentracao;
        TextView dosagem;
        TextView periodo;
        CardView mycardview;
        String id_Consulta;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            //textView = (TextView) v.findViewById(R.id.my_text_viewItem);
            cardimage = (ImageView) itemView.findViewById(R.id.cardimage);
            cardtitle = (TextView) itemView.findViewById(R.id.medicamento);
            concentracao = (TextView) itemView.findViewById(R.id.concentracao);
            dosagem = (TextView) itemView.findViewById(R.id.dosagem);
            periodo = (TextView) itemView.findViewById(R.id.periodo);
            mycardview = (CardView) itemView.findViewById(R.id.card_view);

            mycardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), CardViewBig.class);
                    intent.putExtra("Medicamento", cardtitle.getText().toString());
                    intent.putExtra("id_Consulta", id_Consulta);
                    intent.putExtra("concentracao", concentracao.getText().toString());
                    intent.putExtra("dosagem", dosagem.getText().toString());
                    intent.putExtra("periodo", periodo.getText().toString());
                    v.getContext().startActivity(intent);
                    //Toast.makeText(v.getContext(), "os version is: " + feed.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }

      //  public TextView getTextView() {
      //      return textView;
      //  }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Medicine> list/*String[] myDataset*/) {
        //mDataset = myDataset;
        this.list = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
       // View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

       // ViewHolder vh = new ViewHolder((TextView) v);
        return new MyAdapter.ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Log.d(TAG,"Element" +position+" set.");
       // holder.getTextView().setText(mDataset[position]);
        holder.medicine=getItem(position);// new
        holder.cardtitle.setText(list.get(position).getMedicamento().toString());
        holder.cardimage.setImageResource(list.get(position).getId_image());
        holder.concentracao.setText((String.valueOf(list.get(position).getConcentracao())));
        holder.dosagem.setText(("Dosagem: " + String.valueOf(list.get(position).getDosagem()) + " " + list.get(position).getDosagem_tipo()));
        holder.periodo.setText(("Periodo: " + String.valueOf(list.get(position).getPeriodo()) + " " + list.get(position).getPeriodo_tipo()));

        holder.id_Consulta = String.valueOf(list.get(position).getId_Consulta());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //return mDataset.length;
        return list.size();
    }
    public Medicine getItem(int i) {
        return list.get(i);
    }
}