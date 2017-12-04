package fauzi.muhammad.musicmatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.Serializable;

import fauzi.muhammad.musicmatch.Model.Track;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView textView = findViewById(R.id.textView3);
        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        assert bundle != null;
//        Track trackSer = (Track) bundle.getSerializable("track");
//
//        Log.d("Detail", trackSer.getTrackName());
//        String trackname = trackSer.getTrackName();
//        try {
//            textView.setText(trackname);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        textView.setText(intent.getStringExtra("judul"));


    }
}
