package com.anandarherdianto.dinas.helper;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anandarherdianto.dinas.R;
import com.anandarherdianto.dinas.model.DocumentationAlbumModel;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Ananda R. Herdianto on 27/04/2017.
 */

public class DocumentationAlbumAdapter extends RecyclerView.Adapter<DocumentationAlbumAdapter.MyViewHolder> {

    private Context mContext;
    private List<DocumentationAlbumModel> albumList;

    public DocumentationAlbumAdapter(Context mContext, List<DocumentationAlbumModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.documentation_album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        DocumentationAlbumModel album = albumList.get(position);
        holder.title.setText(album.getTitle());
        holder.count.setText(album.getNumOfImages() + " foto");

        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        /*
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            //overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    /**
     * Showing popup menu when tapping on 3 dots
     */
    /*
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }
    */

    /**
     * Click listener for popup menu items
     */
    /*
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_upload:
                    Toast.makeText(mContext, "Unggah", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
    */



}
