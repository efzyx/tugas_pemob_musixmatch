package fauzi.muhammad.musicmatch;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import fauzi.muhammad.musicmatch.Music.Music;
import fauzi.muhammad.musicmatch.Music.MusicGenreList;
import fauzi.muhammad.musicmatch.Music.Track;
import fauzi.muhammad.musicmatch.Music.TrackList;
import fauzi.muhammad.musicmatch.Music.TrackMusicGenrePrimary;
import fauzi.muhammad.musicmatch.Music.TrackMusicGenreSecondary;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Spinner tipe;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tipe = findViewById(R.id.tipeSpinner);
        String optionsArray[] = new String[]{
            "Pilih Tipe", "Apa Saja", "Judul", "Artis", "Album"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, optionsArray);
        tipe.setAdapter(adapter);
        if(isConnected()){
            Log.d("AMBIL", "AMBIL DATA DARI SERVER");
//            MusixMatch.ambilData(getMusicCallback());
            MusixMatch.ambilData(getMusicCallback(), "Justin Bieber");
            return;
        }
        Log.d("AMBIL", "AMBIL DATA DARI DATABASE");
        setViewList(getAllFromDB());


    }


    void setViewList(List<TrackList> trackList){
        Log.d("MAIN", "Lagu : "+trackList.get(0).getTrack().getTrackName());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView = findViewById(R.id.rv_hasil_cari);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RVAdapter(this, trackList ));
    }


    void saveToDB(List<TrackList> trackList){

        for (int i = 0; i < trackList.size(); i++) {
            Log.d("MAIN", trackList.get(i).getTrack().getTrackName());
            Track track = trackList.get(i).getTrack();
            Log.d("MAIN", "Track  : " +track.getTrackName());
            List<Track> listInDb = Track.find(Track.class, "track_id = ?", track.getTrackId());
            Log.d("MAIN", "Track ID yang ditemukan : " +track.getTrackId());
            Log.d("MAIN", "List yang ditemukan : " +String.valueOf(listInDb.size()));
            if(listInDb.isEmpty()){
                Log.d("MAIN", "Masuk Simpan!");
                track.save();
                List<MusicGenreList> musicGenrePrimaryLists = track.getPrimaryGenres().getMusicGenreList();
                List<MusicGenreList> musicGenreSecondaryLists = track.getSecondaryGenres().getMusicGenreList();
                if(!musicGenrePrimaryLists.isEmpty()){
                    for (int j = 0; j < musicGenrePrimaryLists.size(); j++) {
                        TrackMusicGenrePrimary trackMusicGenrePrimary = new TrackMusicGenrePrimary(
                                musicGenrePrimaryLists.get(j).getMusicGenre().getMusicGenreId(),
                                track.getTrackId()
                        );
                        trackMusicGenrePrimary.save();
                    }
                }
                if(!musicGenreSecondaryLists.isEmpty()){
                    for (int j = 0; j < musicGenreSecondaryLists.size(); j++) {
                        TrackMusicGenreSecondary trackMusicGenreSecondary = new TrackMusicGenreSecondary(
                                musicGenreSecondaryLists.get(j).getMusicGenre().getMusicGenreId(),
                                track.getTrackId()
                        );
                        trackMusicGenreSecondary.save();
                    }
                }

            }
        }
    }

    Callback<Music> getMusicCallback(){
        return new Callback<Music>() {

            @Override
            public void onResponse(Call<Music> call, Response<Music> response) {
                Music object =  response.body();

                saveToDB(object.getMessage().getBody().getTrackList());
                setViewList(object.getMessage().getBody().getTrackList());

            }

            @Override
            public void onFailure(Call<Music> call, Throwable t) {
                Log.d("MAIN", t.getMessage());
            }

        };
    }

    List<TrackList> getAllFromDB(){
        List<TrackList> trackLists = new ArrayList();
        List<Track> tracks = Select.from(Track.class).list();
        for (int i = 0; i < tracks.size(); i++) {
            trackLists.add(new TrackList(tracks));
        }
        return trackLists;

    }

    void getTopTrack(){

    }

    boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
