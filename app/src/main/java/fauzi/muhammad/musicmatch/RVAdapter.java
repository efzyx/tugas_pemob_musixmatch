package fauzi.muhammad.musicmatch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fauzi.muhammad.musicmatch.Model.PrimaryGenres;
import fauzi.muhammad.musicmatch.Model.SecondaryGenres;
import fauzi.muhammad.musicmatch.Model.Track;
import fauzi.muhammad.musicmatch.Model.TrackList;

/**
 * Created by fauzi on 02/12/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>   {
    private Context context;
    private List<TrackList> tracks;
    RVAdapter(Context context, List<TrackList> trackList){
        this.tracks = trackList;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.lagu_list, parent, false);
        Item item = new Item(row, context);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)  {
        Item item = (Item) holder;
        Track track = tracks.get(position).getTrack();

        item.setSerializedData(track);
        item.textViewLagu.setText(track.getTrackName());
        item.textViewArtis.setText(track.getArtistName());
        Picasso.with(context).load(track.getAlbumCoverart100x100()).into(item.imageView);
    }

    @Override
    public int getItemCount() {
        return  tracks.size();
    }

    public class Item extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewLagu;
        TextView textViewArtis;
        ImageView imageView;
        Context context;
        Track serializedData;
        public Item(View viewItem, Context context){
            super(viewItem);
            this.context = context;
            viewItem.setOnClickListener(this);
            textViewLagu = viewItem.findViewById(R.id.lagu_text);
            textViewArtis = viewItem.findViewById(R.id.textViewArtis);
            imageView = viewItem.findViewById(R.id.imageView);


        }

        void setSerializedData(Track serializedData){
            this.serializedData = serializedData;
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Intent intent = new Intent(this.context, DetailActivity.class);
            Log.d("MAINSERIAL", serializedData.getTrackName());
            SecondaryGenres secondaryGenres = this.serializedData.getSecondaryGenres();
            PrimaryGenres primaryGenres = this.serializedData.getPrimaryGenres();
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("track", this.serializedData);
//            bundle.putSerializable("primaryGenres", primaryGenres);
//            bundle.putSerializable("secondaryGenres", secondaryGenres);
//            intent.putExtras(bundle);
            intent.putExtra("judul", serializedData.getTrackName());
            context.startActivity(intent);
        }
    }

}
