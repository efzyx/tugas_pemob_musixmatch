package fauzi.muhammad.musicmatch.models;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.NotNull;
import com.orm.dsl.Table;

/**
 * Created by fauzi on 02/12/2017.
 */

@Table
public class TrackMusicGenrePrimary extends SugarRecord {

    @SerializedName("track_id")
    @NotNull
    private String trackId;
    @SerializedName("music_genre_name")
    @NotNull
    private String musicGenreName;

    public TrackMusicGenrePrimary(){

    }
    public TrackMusicGenrePrimary(String trackId, String musicGenreName) {

        this.trackId = trackId;
        this.musicGenreName = musicGenreName;
    }


    public String getTrackId() {
        return trackId;
    }

    public String getMusicGenreName() {
        return musicGenreName;
    }
}
