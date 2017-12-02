package fauzi.muhammad.musicmatch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fauzi.muhammad.musicmatch.Music.Track;
import fauzi.muhammad.musicmatch.Music.TrackList;

/**
 * Created by fauzi on 02/12/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<TrackList> trackList;
    List<Track> tracks;
    boolean isFromDB;
    public RVAdapter(Context context, List<TrackList> trackList){
        this.trackList = trackList;
        this.context = context;
    }
    public RVAdapter(Context context, List<Track> trackList, Boolean isFromDB){
        this.tracks = trackList;
        this.context = context;
        this.isFromDB = isFromDB;
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
        if (isFromDB){
            ((Item)holder).textView.setText(tracks.get(position).getTrackName());
            return;
        }
        ((Item)holder).textView.setText(trackList.get(position).getTrack().getTrackName());
    }

    @Override
    public int getItemCount() {
        if (isFromDB) return  tracks.size();
        return trackList.size();
    }

    public class Item extends RecyclerView.ViewHolder{
        TextView textView;
        public Item(View viewItem){
            super(viewItem);
            textView = viewItem.findViewById(R.id.lagu_text);
        }

    }
}
