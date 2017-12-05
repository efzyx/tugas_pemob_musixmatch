package fauzi.muhammad.musicmatch;

import fauzi.muhammad.musicmatch.Interface.MMClient;
import fauzi.muhammad.musicmatch.Model.Lirik;
import fauzi.muhammad.musicmatch.Model.Music;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by fauzi on 30/11/2017.
 */

public class MusixMatch{
     static final String baseUrl = "http://api.musixmatch.com/";
     static Retrofit.Builder builder = new Retrofit.Builder()
             .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create());
     static final Retrofit retrofit = builder.build();
     static MMClient mmClient = retrofit.create(MMClient.class);

    static void ambilData(Callback<Music> callback){

        Call<Music> call = mmClient.listMusicPopular();

        call.enqueue(callback);

    }

    public static void ambilDataService(Callback<Music> callback){

        Call<Music> call = mmClient.listMusicPopular();

        call.enqueue(callback);

    }

    static void ambilData(Callback<Music> callback, int tipe,  String t){
        Call<Music> call = tipe == 1 ?  mmClient.listMusicSearchByTrack(t) :
                tipe == 2 ? mmClient.listMusicSearchByArtist(t) :
                tipe == 3 ? mmClient.listMusicSearchByLyrics(t) :
                mmClient.listMusicSearchByQ(t);

        call.enqueue(callback);
    }

    public static void getLirik(Callback<Lirik> callback, String t){
        Call<Lirik> call = mmClient.getLirik(t);

        call.enqueue(callback);
    }

}
