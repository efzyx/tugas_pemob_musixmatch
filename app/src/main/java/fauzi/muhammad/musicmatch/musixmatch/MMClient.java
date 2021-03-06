package fauzi.muhammad.musicmatch.musixmatch;


import fauzi.muhammad.musicmatch.models.Lirik;
import fauzi.muhammad.musicmatch.models.Music;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by fauzi on 30/11/2017.
 */

public interface MMClient {
    String API_KEY = "07dbc171ad8b49cd11769f2f4f518142";
    String PAGE_SIZE = "100";


    @GET("/ws/1.1/track.search?apikey="+API_KEY+"&page_size="+PAGE_SIZE)
    Call<Music> listMusicSearchByTrack(@Query("q_track") String track);
    @GET("/ws/1.1/track.search?apikey="+API_KEY+"&page_size="+PAGE_SIZE)
    Call<Music> listMusicSearchByArtist(@Query("q_artist") String artist);
    @GET("/ws/1.1/track.search?apikey="+API_KEY+"&page_size="+PAGE_SIZE)
    Call<Music> listMusicSearchByLyrics(@Query("q_lyrics") String lyrics);
    @GET("/ws/1.1/track.search?apikey="+API_KEY+"&page_size="+PAGE_SIZE)
    Call<Music> listMusicSearchByQ(@Query("q") String q);
    @GET("/ws/1.1/track.search?s_track_rating=desc&apikey="+API_KEY+"&page_size="+PAGE_SIZE)
    Call<Music> listMusicPopular();
    @GET("/ws/1.1/track.lyrics.get?&apikey="+API_KEY)
    Call<Lirik> getLirik(@Query("track_id") String trackId);

}
