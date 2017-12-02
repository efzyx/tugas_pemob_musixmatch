package fauzi.muhammad.musicmatch.Music;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;
import com.orm.dsl.Table;

/**
 * Created by fauzi on 02/12/2017.
 */

@Table
public class TrackMusicGenrePrimary extends SugarRecord {
    @NotNull
    Integer music_genre_id;
    @NotNull
    String track_id;

    public TrackMusicGenrePrimary(){

    }
    public TrackMusicGenrePrimary(Integer music_genre_id, String track_id) {
        this.music_genre_id = music_genre_id;
        this.track_id = track_id;
    }


}
