package com.example.tallesrodrigues.medicament11;

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

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //private final TextView textView;
        ImageView cardimage;//new
        TextView cardtitle;
        Medicine medicine;

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
            cardtitle = (TextView) itemView.findViewById(R.id.cardtitle);
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Log.d(TAG,"Element" +position+" set.");
       // holder.getTextView().setText(mDataset[position]);
        holder.medicine=getItem(position);// new
        holder.cardtitle.setText(list.get(position).name);
        holder.cardimage.setImageResource(list.get(position).id);

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