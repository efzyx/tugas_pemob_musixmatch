package fauzi.muhammad.musicmatch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import fauzi.muhammad.musicmatch.Music.Track;
import fauzi.muhammad.musicmatch.Music.TrackList;

/**
 * Created by fauzi on 02/12/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
        Item item = new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = (Item) holder;
        Track track = tracks.get(position).getTrack();
        item.textViewLagu.setText(track.getTrackName());
        item.textViewArtis.setText(track.getArtistName());
        Picasso.with(context).load(track.getAlbumCoverart100x100()).into(item.imageView);
    }

    @Override
    public int getItemCount() {
        return  tracks.size();
    }

    public class Item extends RecyclerView.ViewHolder{
        TextView textViewLagu;
        TextView textViewArtis;
        ImageView imageView;
        public Item(View viewItem){
            super(viewItem);
            textViewLagu = viewItem.findViewById(R.id.lagu_text);
            textViewArtis = viewItem.findViewById(R.id.textViewArtis);
            imageView = viewItem.findViewById(R.id.imageView);
        }

    }
}
