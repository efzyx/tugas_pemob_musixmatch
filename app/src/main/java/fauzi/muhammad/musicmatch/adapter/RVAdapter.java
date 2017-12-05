package fauzi.muhammad.musicmatch.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fauzi.muhammad.musicmatch.Model.Track;
import fauzi.muhammad.musicmatch.Model.TrackList;
import fauzi.muhammad.musicmatch.R;
import fauzi.muhammad.musicmatch.activities.DetailActivity;

/**
 * Created by fauzi on 02/12/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>   {
    private Context context;
    private List<TrackList> tracks;
    public RVAdapter(Context context, List<TrackList> trackList){
        this.tracks = trackList;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.lagu_list, parent, false);
        return new Item(row, context);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)  {
        holder.itemView.setBackgroundColor(
                holder.itemView.getResources()
                        .getColor(position % 2 != 0 ? R.color.ganjil : R.color.genap));
        Item item = (Item) holder;
        Track track = tracks.get(position).getTrack();
        item.setTrackId(track.getTrackId());
        item.textViewLagu.setText(track.getTrackName());
        item.textViewArtis.setText(track.getArtistName());
        Picasso.with(context).load(track.getAlbumCoverart100x100()).into(item.imageView);
        item.ratingBar.setRating(track.getTrackRating()/20);

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
        String trackId;
        RatingBar ratingBar;
        RecyclerView recyclerView;
        ProgressBar progressBar;
        Item(View viewItem, Context context){
            super(viewItem);
            this.context = context;
            viewItem.setOnClickListener(this);
            textViewLagu = viewItem.findViewById(R.id.lagu_text);
            textViewArtis = viewItem.findViewById(R.id.textViewArtis);
            imageView = viewItem.findViewById(R.id.imageView);
            ratingBar = viewItem.findViewById(R.id.ratingBar);
            recyclerView = viewItem.findViewById(R.id.rv_hasil_cari);
            progressBar = viewItem.findViewById(R.id.progressBar);

        }

        private void setTrackId(String trackId){
            this.trackId = trackId;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(this.context, DetailActivity.class);
            intent.putExtra("track_id", trackId);
            context.startActivity(intent);
        }

    }

}
