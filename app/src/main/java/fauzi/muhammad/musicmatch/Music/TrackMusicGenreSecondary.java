package fauzi.muhammad.musicmatch.Music;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;
import com.orm.dsl.Table;

/**
 * Created by fauzi on 02/12/2017.
 */

@Table
public class TrackMusicGenreSecondary extends SugarRecord{
    @NotNull
    private Integer music_genre_id;
    @NotNull
    private String track_id;
    public TrackMusicGenreSecondary(){

    }
    public TrackMusicGenreSecondary(Integer music_genre_id, String track_id) {
        this.music_genre_id = music_genre_id;
        this.track_id = track_id;
    }
}
