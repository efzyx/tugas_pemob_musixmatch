package fauzi.muhammad.musicmatch.activities;

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
import android.widget.Toast;
import com.orm.query.Condition;
import com.orm.query.Select;
import java.util.ArrayList;
import java.util.List;

import fauzi.muhammad.musicmatch.musixmatch.MusixMatch;
import fauzi.muhammad.musicmatch.R;
import fauzi.muhammad.musicmatch.adapter.RVAdapter;
import fauzi.muhammad.musicmatch.job.JobUtils;
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
    private static final String OPTIONS_ARRAY[] = new String[]{"Apa Saja", "Judul", "Artis", "Lirik"};
    TextView textView;
    RecyclerView recyclerView;
    Button button;
    ArrayAdapter<String> adapter;
    ProgressBar progressBar;
    RVAdapter rvAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JobUtils.scheduleJob(this);

        adapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, OPTIONS_ARRAY);
        textView = findViewById(R.id.textViewJudul);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.rv_hasil_cari);
        button = findViewById(R.id.buttonPencarian);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                @SuppressLint("InflateParams") View mView = getLayoutInflater().inflate(R.layout.dialog_search, null);

                builder.setView(mView);
                final AlertDialog alertDialog = builder.create();

                alertDialog.show();
                final EditText editText = alertDialog.findViewById(R.id.editText);
                final Spinner spinner = alertDialog.findViewById(R.id.spinner);
                Log.d("Main", "adapter "+ adapter.getItem(0));
                assert spinner != null;
                spinner.setAdapter(adapter);
                final Button cari = alertDialog.findViewById(R.id.button);
                assert cari != null;
                cari.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        startProgressBar();
                        assert editText != null;
                        String q = editText.getText() != null ? editText.getText().toString() : "";
                        int t = spinner.getSelectedItemPosition();
                        if (q.length() > 0){
                            alertDialog.dismiss();
                             if(isConnected()){
                                  MusixMatch.ambilData(getSearchMusicCallback(t, q), t, q);

                             }else {
                                 textView.setText(getResources().getString(R.string.hasil)+" "+q);
                                 setViewList(getTrack(t,q));
                             }

                        }else {
                            Toast.makeText(getApplicationContext(), "Silahkan isi keyword!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        startProgressBar();

        if(isConnected()){
            MusixMatch.ambilData(getMusicCallback());
            Log.d("AMBIL", "AMBIL DATA DARI SERVER");
        }else {
            setViewList(getTopTrack());
        }


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
                    Log.d("AMBIL", "AMBIL DATA DARI SERVER");
                    startProgressBar();
                    return actionRefresh();
                default:

                    return super.onOptionsItemSelected(item);
        }

    }


    void setViewList(List<TrackList> trackList){

        Log.d("MAIN", "Lagu : "+trackList.size());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        rvAdapter = new RVAdapter(this, trackList);
        recyclerView.setAdapter(rvAdapter);
        stopProgressBar();
        recyclerView.scrollToPosition(0);
    }


    void saveToDB(List<TrackList> trackList){

        for (int i = 0; i < trackList.size(); i++) {
            Log.d("MAIN", trackList.get(i).getTrack().getTrackName());
            Track track = trackList.get(i).getTrack();
            Log.d("MAIN", "Track  : " +track.getTrackName());
            List<Track> listInDb = Select.from(Track.class).where(Condition.prop("track_id").eq(track.getTrackId())).list();
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
            public void onResponse(@NonNull Call<Music> call, @NonNull Response<Music> response) {
                Music object =  response.body();
                stopProgressBar();
                assert object != null;
                saveToDB(object.getMessage().getBody().getTrackList());
                setViewList(object.getMessage().getBody().getTrackList());
                textView.setText(R.string.lagu_populer);
            }

            @Override
            public void onFailure(@NonNull Call<Music> call, @NonNull Throwable t) {
                Log.d("MAIN", t.getMessage());
                setViewList(getTopTrack());
                textView.setText(R.string.lagu_populer);
            }

        };
    }

    Callback<Music> getSearchMusicCallback(final int tipe, final String q){
        return new Callback<Music>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Music> call, @NonNull Response<Music> response) {
                Music object =  response.body();
                stopProgressBar();
                assert object != null;
                saveToDB(object.getMessage().getBody().getTrackList());
                setViewList(object.getMessage().getBody().getTrackList());
                textView.setText(getResources().getString(R.string.hasil) + " "+ q);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<Music> call, @NonNull Throwable t) {
                setViewList(getTrack(tipe, q));
                textView.setText(getResources().getString(R.string.hasil) + " "+ q);
            }

        };
    }

    Callback<Lirik> getLirikCallback(){
        return new Callback<Lirik>() {

            @Override
            public void onResponse(@NonNull Call<Lirik> call, @NonNull Response<Lirik> response) {
                Lirik object =  response.body();
                assert object != null;
                saveLirikToDB(object.getMessage().getBody().getLyrics());

            }

            @Override
            public void onFailure(@NonNull Call<Lirik> call, @NonNull Throwable t) {
                Log.d("MAIN", t.getMessage());
            }

        };
    }

    private void saveLirikToDB(Lyrics lyrics) {
        lyrics.save();
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

    List<TrackList> getTrack(int tipe, String keyword){
        List<TrackList> trackLists = new ArrayList<>();
        List<Track> tracks = tipe == 1 ?
                Select.from(Track.class).where(Condition.prop("track_name").like(keyword)).limit("100").list() :
                tipe == 2 ? Select.from(Track.class).where(Condition.prop("artist_name").like(keyword)).limit("100").list() :
                tipe == 3 ? Track.findWithQuery(Track.class, "select * from track join lyrics on (lyrics.lyrics_id = track.lyrics_id) " +
                        "where lyrics.lyrics_body like '%"+keyword+"%'") :
                        Track.findWithQuery(Track.class, "select * from track join lyrics on (lyrics.lyrics_id = track.lyrics_id) " +
                                "where lyrics.lyrics_body like '%"+keyword+"%' or track.artist_name like '%"+keyword+"%' or track.track_name like '%"+keyword+"%'");
        Log.d("Main", "Jumlah : "+String.valueOf(tracks.size()));
        for (int i = 0; i < tracks.size(); i++) {
            trackLists.add(new TrackList(tracks.get(i)));
        }
        return trackLists;
    }

    boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
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
        Log.d("Mausk", "Masuk stop progress");
        recyclerView.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        button.setVisibility(View.VISIBLE);
    }

    boolean actionRefresh(){
        textView.setText(getResources().getString(R.string.lagu_populer));
        if(isConnected()){
            Toast.makeText(getApplicationContext(), "Refreshed! Data loaded from server", Toast.LENGTH_SHORT).show();
            MusixMatch.ambilData(getMusicCallback());
            return true;
        }
        Toast.makeText(getApplicationContext(), "Refreshed! Data loaded from database", Toast.LENGTH_SHORT).show();
        setViewList(getTopTrack());
        return true;
    }
}
