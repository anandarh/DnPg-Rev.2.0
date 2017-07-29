package com.anandarherdianto.dinas.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anandarherdianto.dinas.R;
import com.anandarherdianto.dinas.model.DocumentationAlbumModel;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Ananda R. Herdianto on 28/04/2017.
 */

public class DocumentationAdapter extends RecyclerView.Adapter<DocumentationAdapter.MyViewHolder> {
    private Context dContext;
    private List<DocumentationAlbumModel> listDoc;

    public DocumentationAdapter(Context dContext, List<DocumentationAlbumModel> listDoc) {
        this.dContext = dContext;
        this.listDoc = listDoc;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.documentation_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DocumentationAlbumModel doc = listDoc.get(position);
        holder.dTitle.setText(doc.getTitle());
        holder.dDesc.setText(doc.getDescription());
        holder.dDate.setText(doc.getDate());
        holder.dTemp.setText("Suhu : "+doc.getTemp()+"\u2103");

        // loading album cover using Glide library
        Glide.with(dContext).load(doc.getThumbnail()).into(holder.dThumbnail);
    }

    @Override
    public int getItemCount() {
        return listDoc.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView dThumbnail;
        public TextView dTitle, dDesc, dDate, dTemp;

        public MyViewHolder(View itemView) {
            super(itemView);
            dThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            dTitle = (TextView) itemView.findViewById(R.id.title);
            dDesc = (TextView) itemView.findViewById(R.id.description);
            dDate = (TextView) itemView.findViewById(R.id.date);
            dTemp = (TextView) itemView.findViewById(R.id.txtTemp);

            Typeface myFont = Typeface.createFromAsset(dContext.getAssets(), "fonts/Courgette-Regular.ttf");
            dTitle.setTypeface(myFont);
        }
    }
}
