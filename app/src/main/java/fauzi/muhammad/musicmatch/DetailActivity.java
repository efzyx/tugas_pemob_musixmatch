package fauzi.muhammad.musicmatch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.squareup.picasso.Picasso;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import fauzi.muhammad.musicmatch.Model.Lyrics;
import fauzi.muhammad.musicmatch.Model.Track;
import fauzi.muhammad.musicmatch.Model.TrackMusicGenrePrimary;

public class DetailActivity extends AppCompatActivity {
    TextView judul, artis, album, rilis, genre, durasi;
    String lirikText;
    String lirikCR;
    Button showLirik;
    Track track;
    ImageView imageView;

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        judul = findViewById(R.id.textViewTrackname);
        artis = findViewById(R.id.textViewArtisName);
        album = findViewById(R.id.textViewAlbum);
        rilis = findViewById(R.id.textViewRilis);
        genre = findViewById(R.id.textViewGenre);
        durasi = findViewById(R.id.textViewDurasi);
        showLirik = findViewById(R.id.buttonLirik);
        imageView = findViewById(R.id.imageView2);


        Intent intent = getIntent();
        String trackId = intent.getStringExtra("track_id");
        track = Select.from(Track.class).where(Condition.prop("track_id").eq(trackId)).first();
        Log.d("Detail", "Track id : "+track.getTrackName());
        String sDate = "";
        Date date;
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(track.getFirstReleaseDate());
            Log.d("Detail", date.toString());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            sDate += dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Lyrics lyrics = Select.from(Lyrics.class).where(Condition.prop("lyrics_id").eq(track.getLyricsId())).first();
        lirikText = track.getHasLyrics() == 1 ? lyrics.getLyricsBody() : "";
        Log.d("Detail", lirikText);
        lirikCR = track.getHasLyrics() == 1 ? lyrics.getLyricsCopyright() : "";
        judul.setText(track.getTrackName());
        artis.setText(track.getArtistName());
        album.setText(track.getAlbumName());
        rilis.setText(sDate);
        durasi.setText(String.valueOf(track.getTrackLength())+" "+getString(R.string.detik));
        Picasso.with(this).load(track.getAlbumCoverart100x100()).into(imageView);
        List<TrackMusicGenrePrimary> trackMusicGenrePrimaryList = Select.from(TrackMusicGenrePrimary.class).where(
                Condition.prop("track_id").eq(trackId)
        ).list();
        StringBuilder genres= new StringBuilder();
        for (int i = 0; i < trackMusicGenrePrimaryList.size(); i++) {
            genres.append(i == 0 ? trackMusicGenrePrimaryList.get(i).getMusicGenreName() :
                    ", " + trackMusicGenrePrimaryList.get(i).getMusicGenreName());
        }
        genre.setText(genres.length() > 0 ? genres.toString() : getString(R.string.no_genre));

        showLirik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                @SuppressLint("InflateParams") View mView = getLayoutInflater().inflate(R.layout.lirik_dialog, null);
                TextView lirik = mView.findViewById(R.id.textViewLirik);
                lirik.setText(track.getHasLyrics() == 1 ? lirikText + "\n" : getString(R.string.no_lyrics));
                lirik.setMovementMethod(new ScrollingMovementMethod());
                builder.setTitle(getString(R.string.lirik)+" "+track.getTrackName());
                builder.setView(mView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                Log.d("Detail", lirikText);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String content = getString(R.string.yuk)+" "+track.getTrackName()+
                        " "+getString(R.string.disini)+"\n"+track.getTrackShareUrl() +
                        "\n"+getString(R.string.share_watermark);
                String subject = track.getArtistName()+" - "+track.getTrackName();
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, content);
                startActivity(Intent.createChooser(intent, getString(R.string.share_using)));
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
