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
import com.imranaliafzal.seattlemission.seattlemissionapp.model.VenueSearchResponse;
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
 * Copyright © 2018 Spendlabs Inc. All rights reserved.
 */
public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.VenueViewHolder> {

    private List<VenueSearchResponse.Venue> mVenues;

    public VenuesAdapter(List<VenueSearchResponse.Venue> pVenues) {
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
        VenueSearchResponse.Venue lVenue = mVenues.get(position);
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

        public void bindTo(VenueSearchResponse.Venue pVenue) {

            title.setText(pVenue.getName());
            category.setText(Util.categoryListToDisplay(pVenue.getCategories()));
            address.setText(pVenue.getLocation().getAddress());
            distance.setText(Util.distanceFromSeattleCenterInMiles(pVenue.getLocation().getLat(), pVenue.getLocation().getLng()));


            FourSquareWebService.getInstance().fetchPhotoList(pVenue.getId(), Constants.CLIENT_ID, Constants.CLIENT_SECRET, Constants.V, "venue", 1, 1).enqueue(new Callback<VenueSearchResponse.VenuePhotosResponse>() {
                @Override
                public void onResponse(Call<VenueSearchResponse.VenuePhotosResponse> call, Response<VenueSearchResponse.VenuePhotosResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {

                        //now fetch the image
                        VenueSearchResponse.PhotoItem lPhotoItem = response.body().getResponse().getPhotos().getItems().get(0);
                        String prefix = lPhotoItem.getPrefix();
                        String suffix = lPhotoItem.getSuffix();
                        FourSquareWebService.getInstance().fetchPhoto(prefix, suffix).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                // display the image data in a ImageView or save it
                                Bitmap bm = BitmapFactory.decodeStream(response.body().byteStream());
                                mImageViewPhoto.setImageBitmap(bm);
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<VenueSearchResponse.VenuePhotosResponse> call, Throwable t) {
                    t.getCause();
                }
            });

            String lPrefix = pVenue.getCategories().get(0).getIcon().getPrefix();
            String lSuffix = pVenue.getCategories().get(0).getIcon().getSuffix();
            FourSquareWebService.getInstance().fetchImage(lPrefix, lSuffix).enqueue(new Callback<ResponseBody>() {
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




            mCardView.setOnClickListener(v ->{
                        Intent i =  VenueDetailsActivity.newIntent(v.getContext(), pVenue);
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
