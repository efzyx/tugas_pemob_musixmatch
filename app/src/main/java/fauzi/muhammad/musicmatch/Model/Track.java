
package fauzi.muhammad.musicmatch.Model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.NotNull;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

@Table
public class Track extends SugarRecord implements Serializable {
    public Track(){

    }
    public Track(String trackId, String trackName, Integer trackRating,
                 Integer trackLength, Integer hasLyrics,String lyricsId,
                 String albumId, String albumName, String artistId, String artistName,
                 String albumCoverart100x100, String albumCoverart350x350, String albumCoverart500x500,
                 String albumCoverart800x800, String trackShareUrl,String firstReleaseDate) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.trackRating = trackRating;
        this.trackLength = trackLength;
        this.hasLyrics = hasLyrics;
        this.lyricsId = lyricsId;
        this.albumId = albumId;
        this.albumName = albumName;
        this.artistId = artistId;
        this.artistName = artistName;
        this.albumCoverart100x100 = albumCoverart100x100;
        this.albumCoverart350x350 = albumCoverart350x350;
        this.albumCoverart500x500 = albumCoverart500x500;
        this.albumCoverart800x800 = albumCoverart800x800;
        this.trackShareUrl = trackShareUrl;

        this.firstReleaseDate = firstReleaseDate;

    }

    @SerializedName("track_id")
    @Expose
    @Unique
    @NotNull
    private String trackId;
    @SerializedName("track_name")
    @Expose
    private String trackName;
    @SerializedName("track_rating")
    @Expose
    private Integer trackRating;
    @SerializedName("track_length")
    @Expose
    private Integer trackLength;
    @SerializedName("has_lyrics")
    @Expose
    private Integer hasLyrics;
    @SerializedName("lyrics_id")
    @Expose
    private String lyricsId;
    @SerializedName("album_id")
    @Expose
    private String albumId;
    @SerializedName("album_name")
    @Expose
    private String albumName;
    @SerializedName("artist_id")
    @Expose
    private String artistId;
    @SerializedName("artist_name")
    @Expose
    private String artistName;
    @SerializedName("album_coverart_100x100")
    @Expose
    private String albumCoverart100x100;
    @SerializedName("album_coverart_350x350")
    @Expose
    private String albumCoverart350x350;
    @SerializedName("album_coverart_500x500")
    @Expose
    private String albumCoverart500x500;
    @SerializedName("album_coverart_800x800")
    @Expose
    private String albumCoverart800x800;
    @SerializedName("track_share_url")
    @Expose
    private String trackShareUrl;
    @SerializedName("first_release_date")
    @Expose
    private String firstReleaseDate;
    @SerializedName("primary_genres")
    @Expose
    @Ignore
    private PrimaryGenres primaryGenres;

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }



    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public Integer getTrackRating() {
        return trackRating;
    }

    public void setTrackRating(Integer trackRating) {
        this.trackRating = trackRating;
    }

    public Integer getTrackLength() {
        return trackLength;
    }

    public void setTrackLength(Integer trackLength) {
        this.trackLength = trackLength;
    }
    public String getLyricsId() {
        return lyricsId;
    }

    public void setLyricsId(String lyricsId) {
        this.lyricsId = lyricsId;
    }
    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumCoverart100x100() {
        return albumCoverart100x100;
    }

    public void setAlbumCoverart100x100(String albumCoverart100x100) {
        this.albumCoverart100x100 = albumCoverart100x100;
    }

    public String getAlbumCoverart350x350() {
        return albumCoverart350x350;
    }

    public void setAlbumCoverart350x350(String albumCoverart350x350) {
        this.albumCoverart350x350 = albumCoverart350x350;
    }

    public String getAlbumCoverart500x500() {
        return albumCoverart500x500;
    }

    public void setAlbumCoverart500x500(String albumCoverart500x500) {
        this.albumCoverart500x500 = albumCoverart500x500;
    }

    public String getAlbumCoverart800x800() {
        return albumCoverart800x800;
    }

    public void setAlbumCoverart800x800(String albumCoverart800x800) {
        this.albumCoverart800x800 = albumCoverart800x800;
    }
    public String getTrackShareUrl() {
        return trackShareUrl;
    }

    public void setTrackShareUrl(String trackShareUrl) {
        this.trackShareUrl = trackShareUrl;
    }
    public String getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public void setFirstReleaseDate(String firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }
    public PrimaryGenres getPrimaryGenres() {
        return primaryGenres;
    }

    public void setPrimaryGenres(PrimaryGenres primaryGenres) {
        this.primaryGenres = primaryGenres;
    }

    public Integer getHasLyrics() {
        return hasLyrics;
    }
}
