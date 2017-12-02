package fauzi.muhammad.musicmatch;

import android.util.Log;

import fauzi.muhammad.musicmatch.Interface.MMClient;
import fauzi.muhammad.musicmatch.Music.Music;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    static void ambilData(Callback<Music> callback, String t){
        Call<Music> call = mmClient.listMusicSearchByTitle(t);

        call.enqueue(callback);
    }


}
