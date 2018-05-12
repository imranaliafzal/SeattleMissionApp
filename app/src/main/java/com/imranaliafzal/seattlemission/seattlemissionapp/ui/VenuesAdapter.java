package com.imranaliafzal.seattlemission.seattlemissionapp.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imranaliafzal.seattlemission.seattlemissionapp.R;
import com.imranaliafzal.seattlemission.seattlemissionapp.api.FourSquareWebService;
import com.imranaliafzal.seattlemission.seattlemissionapp.model.Models;
import com.imranaliafzal.seattlemission.seattlemissionapp.utils.Constants;
import com.imranaliafzal.seattlemission.seattlemissionapp.utils.Util;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MovieApp
 * <p>
 * Created by iafzal on 5/8/18.
 * Copyright © 2018 Imran Afzal All rights reserved.
 */
public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.VenueViewHolder> {

    private List<Models.Venue> mVenues;

    public VenuesAdapter(List<Models.Venue> pVenues) {
        mVenues = pVenues;

    }

    @Override
    public VenueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_item, parent, false);
        return new VenueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VenueViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Models.Venue lVenue = mVenues.get(position);
        if (lVenue != null) {
            holder.bindTo(lVenue);
        } else {

            holder.clear();
        }
    }

    @Override
    public int getItemCount() {
        return mVenues.size();
    }


    public static class VenueViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView category;
        private TextView address;
        private TextView distance;

        private ImageView mImageViewIcon;
        private ImageView mImageViewPhoto;
        private CardView mCardView;

        public VenueViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.tv_title);
            this.category = itemView.findViewById(R.id.tv_category);
            this.address = itemView.findViewById(R.id.tv_address);
            this.distance = itemView.findViewById(R.id.tv_distance);
            this.mImageViewIcon = itemView.findViewById(R.id.iv_icon);
            this.mImageViewPhoto = itemView.findViewById(R.id.iv_photo);
            this.mCardView = itemView.findViewById(R.id.cardView);
        }

        public void bindTo(Models.Venue pVenue) {

            title.setText(pVenue.getName());
            category.setText(Util.categoryListToDisplay(pVenue.getCategories()));
            address.setText(pVenue.getLocation().getAddress());
            distance.setText(Util.distanceFromSeattleCenterInMiles(pVenue.getLocation().getLat(),
                    pVenue.getLocation().getLng()) + " mi");


            FourSquareWebService.getInstance().fetchPhotoList(pVenue.getId(), Constants.CLIENT_ID,
                    Constants.CLIENT_SECRET, Constants.V, "venue", 1, 1)
                    .enqueue(new Callback<Models.VenuePhotosResponse>() {
                @Override
                public void onResponse(Call<Models.VenuePhotosResponse> call,
                                       Response<Models.VenuePhotosResponse> response) {
                    if (response.isSuccessful() && response.body() != null
                            &&
                            response.body().getResponse().getPhotos().getItems()
                                    != null
                            && response.body().getResponse().getPhotos().getItems().size() > 0) {

                        //now fetch the image
                        Models.PhotoItem lPhotoItem = response.body().getResponse().getPhotos()
                                .getItems().get(0);
                        String prefix = lPhotoItem.getPrefix();
                        String suffix = lPhotoItem.getSuffix();
                        FourSquareWebService.getInstance().fetchPhoto(prefix, suffix)
                                .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call,
                                                   Response<ResponseBody> response) {
                                // display the image data in a ImageView or save it
                                Bitmap bm = BitmapFactory.decodeStream(
                                        response.body().byteStream());
                                mImageViewPhoto.setImageBitmap(bm);
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<Models.VenuePhotosResponse> call, Throwable t) {
                    t.getCause();
                }
            });

            List<Models.Category> lCategories = pVenue.getCategories();

            if(lCategories.size() > 0) {
                String lPrefix = lCategories.get(0).getIcon().getPrefix();
                String lSuffix = lCategories.get(0).getIcon().getSuffix();

                FourSquareWebService.getInstance().fetchImage(lPrefix, lSuffix).enqueue(
                        new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    // display the image data in a ImageView or save it
                                    Bitmap bm = BitmapFactory.decodeStream(response.body().byteStream());
                                    mImageViewIcon.setImageBitmap(bm);
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                t.getCause();
                            }
                        });
            }

            mCardView.setOnClickListener(v -> {
                Intent i = VenueDetailsActivity.newIntent(v.getContext(), pVenue);
                v.getContext().startActivity(i);
            });
        }


        public void clear() {
            mImageViewPhoto.setImageURI(null);
            mImageViewIcon.setImageURI(null);
            title.setText(null);
            category.setText(null);
            address.setText(null);
            distance.setText(null);
        }
    }
}
