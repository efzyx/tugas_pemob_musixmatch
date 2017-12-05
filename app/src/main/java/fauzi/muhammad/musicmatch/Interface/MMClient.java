package fauzi.muhammad.musicmatch.Interface;


import fauzi.muhammad.musicmatch.Model.Lirik;
import fauzi.muhammad.musicmatch.Model.Music;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by fauzi on 30/11/2017.
 */

public interface MMClient {
    String apikey = "07dbc171ad8b49cd11769f2f4f518142";

    @GET("/ws/1.1/track.search?apikey="+apikey)
    Call<Music> listMusicSearchByTrack(@Query("q_track") String track);
    @GET("/ws/1.1/track.search?apikey="+apikey)
    Call<Music> listMusicSearchByArtist(@Query("q_artist") String artist);
    @GET("/ws/1.1/track.search?apikey="+apikey)
    Call<Music> listMusicSearchByLyrics(@Query("q_lyrics") String lyrics);
    @GET("/ws/1.1/track.search?apikey="+apikey)
    Call<Music> listMusicSearchByQ(@Query("q") String q);
    @GET("/ws/1.1/track.search?page_size=100&s_track_rating=desc&apikey="+apikey)
    Call<Music> listMusicPopular();
    @GET("/ws/1.1/track.lyrics.get?&apikey="+apikey)
    Call<Lirik> getLirik(@Query("track_id") String trackId);

}
