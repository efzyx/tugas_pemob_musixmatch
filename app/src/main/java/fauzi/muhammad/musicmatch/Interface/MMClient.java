package fauzi.muhammad.musicmatch.Interface;


import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONObject;

import java.util.List;

import fauzi.muhammad.musicmatch.Music.Music;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by fauzi on 30/11/2017.
 */

public interface MMClient {
    final String apikey = "07dbc171ad8b49cd11769f2f4f518142";
    @GET("/ws/1.1/track.search?apikey="+apikey)
    Call<Music> listMusicSearchByTitle(@Query("q_artist") String artist);
    @GET("/ws/1.1/track.search?apikey=07dbc171ad8b49cd11769f2f4f518142&s_track_rating=desc")
    Call<Music> listMusicPopular();

}
