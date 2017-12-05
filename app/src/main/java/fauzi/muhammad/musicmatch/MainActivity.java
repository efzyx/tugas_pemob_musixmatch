package fauzi.muhammad.musicmatch;

import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import fauzi.muhammad.musicmatch.Model.Body;
import fauzi.muhammad.musicmatch.Model.Lirik;
import fauzi.muhammad.musicmatch.Model.Lyrics;
import fauzi.muhammad.musicmatch.Model.Music;
import fauzi.muhammad.musicmatch.Model.MusicGenreList;
import fauzi.muhammad.musicmatch.Model.Track;
import fauzi.muhammad.musicmatch.Model.TrackList;
import fauzi.muhammad.musicmatch.Model.TrackMusicGenrePrimary;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    RecyclerView recyclerView;
    Button button;
    final String optionsArray[] = new String[]{
            "Pilih Tipe", "Apa Saja", "Judul", "Artis", "Album"
    };
    ArrayAdapter<String> adapter;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, optionsArray );
        textView = findViewById(R.id.textViewJudul);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.rv_hasil_cari);
        button = findViewById(R.id.buttonPencarian);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                @SuppressLint("InflateParams") View mView = getLayoutInflater().inflate(R.layout.dialog_search, null);
                EditText editText = findViewById(R.id.editText);

                builder.setView(mView);
                AlertDialog alertDialog = builder.create();

                alertDialog.show();
                final Spinner spinner = alertDialog.findViewById(R.id.spinner);
                Log.d("Main", "adapter "+ adapter.getItem(0));
                assert spinner != null;
                spinner.setAdapter(adapter);

            }
        });

        startProgressBar();
        Log.d("AMBIL", "AMBIL DATA DARI SERVER");
        MusixMatch.ambilData(getMusicCallback());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                if(isConnected()){
                    Log.d("AMBIL", "AMBIL DATA DARI SERVER");
                    startProgressBar();
                    MusixMatch.ambilData(getMusicCallback());
                    return true;
                }
                default:
                    return super.onOptionsItemSelected(item);
        }

    }

    void setViewList(List<TrackList> trackList){

        Log.d("MAIN", "Lagu : "+trackList.size());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RVAdapter(this, trackList ));
        recyclerView.scrollToPosition(0);
    }


    void saveToDB(List<TrackList> trackList){

        for (int i = 0; i < trackList.size(); i++) {
            Log.d("MAIN", trackList.get(i).getTrack().getTrackName());
            Track track = trackList.get(i).getTrack();
            Log.d("MAIN", "Track  : " +track.getTrackName());
            List<Track> listInDb = Select.from(Track.class).where(Condition.prop("track_id").eq(track.getTrackId())).list();
                    //Track.find(Track.class, "track_id = ?", track.getTrackId());
            Log.d("MAIN", "Track ID yang ditemukan : " +track.getTrackId());
            Log.d("MAIN", "List yang ditemukan : " +String.valueOf(listInDb.size()));
            if(listInDb.isEmpty()){
                Log.d("MAIN", "Masuk Simpan!");
                track.save();
                List<MusicGenreList> musicGenrePrimaryLists = track.getPrimaryGenres().getMusicGenreList();
                Log.d("MAIN", "Jumlah genre "+musicGenrePrimaryLists.size());
                if(!musicGenrePrimaryLists.isEmpty()){
                    for (int j = 0; j < musicGenrePrimaryLists.size(); j++) {
                        TrackMusicGenrePrimary trackMusicGenrePrimary = new TrackMusicGenrePrimary(
                                track.getTrackId(),
                                musicGenrePrimaryLists.get(j).getMusicGenre().getMusicGenreName()
                        );
                        Log.d("MAIN", "Jumlah genre "+musicGenrePrimaryLists.get(j).getMusicGenre().getMusicGenreName());
                        trackMusicGenrePrimary.save();
                    }
                }

                if(track.getHasLyrics() == 1){
                    MusixMatch.getLirik(getLirikCallback(),track.getTrackId());
                }

            }
        }
    }

    Callback<Music> getMusicCallback(){
        return new Callback<Music>() {

            @Override
            public void onResponse(Call<Music> call, @NonNull Response<Music> response) {
                Music object =  response.body();
                stopProgressBar();
                assert object != null;
//                Log.d("Main", "Respon : "+response.body().getMessage().getBody().getTrackList().get(0).getTrack().getTrackName());

                saveToDB(object.getMessage().getBody().getTrackList());
                setViewList(object.getMessage().getBody().getTrackList());

            }

            @Override
            public void onFailure(Call<Music> call, Throwable t) {
                Log.d("MAIN", t.getMessage());
                setViewList(getTopTrack());
            }

        };
    }

    Callback<Lirik> getLirikCallback(){
        return new Callback<Lirik>() {

            @Override
            public void onResponse(Call<Lirik> call, @NonNull Response<Lirik> response) {
                Lirik object =  response.body();
                assert object != null;
                saveLirikToDB(object.getMessage().getBody().getLyrics());

            }

            @Override
            public void onFailure(Call<Lirik> call, Throwable t) {
                Log.d("MAIN", t.getMessage());
            }

        };
    }

    private void saveLirikToDB(Lyrics lyrics) {
        lyrics.save();
    }

    List<TrackList> getAllFromDB() throws InterruptedException {
        Body body = new Body();
        List<TrackList> trackLists = new ArrayList<>();
        List<Track> tracks = Select.from(Track.class).list();
        for (int i = 0; i < tracks.size(); i++) {
            trackLists.add(new TrackList(tracks.get(i)));
            Thread.sleep(300);
        }
        stopProgressBar();
        return trackLists;

    }

    List<TrackList> getTopTrack(){
        List<TrackList> trackLists = new ArrayList<>();
        List<Track> tracks = Select.from(Track.class).orderBy("track_rating desc").limit("100").list();
        Log.d("Main", "Jumlah : "+String.valueOf(tracks.size()));
        for (int i = 0; i < tracks.size(); i++) {
            trackLists.add(new TrackList(tracks.get(i)));
        }
        return trackLists;
    }

    boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    void startProgressBar(){
        recyclerView.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        button.setVisibility(View.INVISIBLE);
    }
    void stopProgressBar(){
        recyclerView.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        button.setVisibility(View.VISIBLE);
    }

}
