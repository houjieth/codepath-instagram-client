package com.codepath.instagramclient;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by jie on 11/26/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Deprecated
    public View getView_slow(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);

        // recycle the previous created object if possible
        // only create new one when needed
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);

        tvCaption.setText(photo.caption);

        ivPhoto.setImageDrawable(null); // clear the ImageView first (because it might be from recycled object)
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto); // happens in background thread

        return convertView;
    }

    /* The good way of implementing getView is to use ViewHolder inner class
       We do this because in practice, findViewById is pretty slow
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
            TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
            ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
            TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
            TextView tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
            ImageView ivUserImage = (ImageView) convertView.findViewById(R.id.ivUserImage);

            viewHolder = new ViewHolder();
            viewHolder.ivPhoto = ivPhoto;
            viewHolder.tvCaption = tvCaption;
            viewHolder.tvUsername = tvUsername;
            viewHolder.tvTimestamp = tvTimestamp;
            viewHolder.ivUserImage = ivUserImage;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvCaption.setText(photo.caption);
        viewHolder.tvUsername.setText(photo.username);
        viewHolder.tvTimestamp.setText(DateUtils.getRelativeTimeSpanString(photo.createdTime * 1000,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS));

        // clear the ImageView first (because it might be from recycled object)
        viewHolder.ivUserImage.setImageDrawable(null);
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(30)
                .oval(false)
                .build();
        Picasso.with(getContext())
                .load(photo.userImageUrl)
                .fit()
                .transform(transformation)
                .into(viewHolder.ivUserImage);

        // clear the ImageView first (because it might be from recycled object)
        viewHolder.ivPhoto.setImageDrawable(null);
        Picasso.with(getContext()).load(photo.imageUrl).into(viewHolder.ivPhoto); // happens in background thread

        return convertView;
    }

    private static class ViewHolder {
        ImageView ivPhoto;
        TextView tvCaption;
        TextView tvUsername;
        TextView tvTimestamp;
        ImageView ivUserImage;
    }
}
