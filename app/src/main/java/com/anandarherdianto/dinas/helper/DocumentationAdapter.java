package com.anandarherdianto.dinas.helper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anandarherdianto.dinas.R;
import com.anandarherdianto.dinas.model.DocumentationAlbumModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

/**
 * Created by Ananda R. Herdianto on 28/04/2017.
 */

public class DocumentationAdapter extends RecyclerView.Adapter<DocumentationAdapter.MyViewHolder> {
    private Context dContext;
    private Activity dActivity;
    private List<DocumentationAlbumModel> listDoc;
    private Animator mCurrentAnimator;


    private int mShortAnimationDuration;

    public DocumentationAdapter(Context dContext, Activity dActivity, List<DocumentationAlbumModel> listDoc) {
        this.dContext = dContext;
        this.listDoc = listDoc;
        this.dActivity = dActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.documentation_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final DocumentationAlbumModel doc = listDoc.get(position);
        holder.dName.setText(doc.getUploader());
        holder.dTitle.setText(doc.getTitle());
        //holder.dDesc.setText(doc.getDescription());
        holder.dDate.setText(doc.getDate());
        holder.dTemp.setText("Suhu : "+doc.getTemp()+"\u2103");
        holder.expandableTextView.setText(doc.getDescription());

        // loading documentation image using Glide library
        Glide.with(dContext).load(doc.getThumbnail()).into(holder.dThumbnail);

        // loading uploader profile image using Glide library
        if((doc.getUploaderImage() == null) || (doc.getUploaderImage().equals(""))) {
            holder.dProfile.setImageDrawable(dContext.getResources().getDrawable(R.drawable.user_placeholder));
        }else {
            Glide.with(dContext).load(doc.getUploaderImage()).into(holder.dProfile);
        }

        holder.dThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomImageFromThumb(doc.getThumbnail());
            }
        });

        holder.opsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.opsi);
            }
        });

        mShortAnimationDuration = dContext.getResources().getInteger(
                android.R.integer.config_longAnimTime);
    }

    @Override
    public int getItemCount() {
        return listDoc.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView dThumbnail,dProfile, opsi;
        public TextView dTitle, dName, dDate, dTemp;
        public ExpandableTextView expandableTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            dThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            dProfile = (ImageView) itemView.findViewById(R.id.imgProfile);
            opsi = (ImageView) itemView.findViewById(R.id.opsiDoc);
            dName = (TextView) itemView.findViewById(R.id.txtName);
            dTitle = (TextView) itemView.findViewById(R.id.title);
            //dDesc = (TextView) itemView.findViewById(R.id.description);
            dDate = (TextView) itemView.findViewById(R.id.date);
            dTemp = (TextView) itemView.findViewById(R.id.txtTemp);
            expandableTextView = (ExpandableTextView) itemView.findViewById(R.id.expand_desc);

            Typeface myFont = Typeface.createFromAsset(dContext.getAssets(), "fonts/Courgette-Regular.ttf");
            dTitle.setTypeface(myFont);
        }
    }

    private void zoomImageFromThumb(String imageRes) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) dActivity.findViewById(
                R.id.expanded_image);

        final RecyclerView thumbView = (RecyclerView) dActivity.findViewById(
                R.id.rv_Documentation);

        /*
        final Button btnDownload = (Button) dActivity.findViewById(
                R.id.btnDownload);
        */

        Glide.with(dContext).load(imageRes).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                Toast.makeText(dContext, "Gagal mengambil gambar dari server. Cek koneksi anda!", Toast.LENGTH_SHORT).show();
                return false;
            }
            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                return false;
            }
        }).into(expandedImageView);


        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        dActivity.findViewById(R.id.container)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);
        //btnDownload.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        //btnDownload.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        //btnDownload.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });

        /*
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(dContext, "Doanload tah", Toast.LENGTH_LONG).show();
            }
        });
        */
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(dContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_delete:
                    Toast.makeText(dContext, "Delete", Toast.LENGTH_SHORT).show();
                    return true;

                default:
            }
            return false;
        }
    }

}
