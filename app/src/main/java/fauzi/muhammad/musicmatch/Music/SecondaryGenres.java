
package fauzi.muhammad.musicmatch.Music;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SecondaryGenres {

    @SerializedName("music_genre_list")
    @Expose
    private List<MusicGenreList> musicGenreList = null;

    public List<MusicGenreList> getMusicGenreList() {
        return musicGenreList;
    }

    public void setMusicGenreList(List<MusicGenreList> musicGenreList) {
        this.musicGenreList = musicGenreList;
    }

}
