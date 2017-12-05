
package fauzi.muhammad.musicmatch.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.NotNull;
import com.orm.dsl.Table;

@Table
public class Lyrics extends SugarRecord{
    public Lyrics() {
    }

    public Lyrics(Integer lyricsId, String lyricsBody, String lyricsLanguage, String lyricsLanguageDescription, String lyricsCopyright) {
        this.lyricsId = lyricsId;
        this.lyricsBody = lyricsBody;
        this.lyricsLanguage = lyricsLanguage;
        this.lyricsLanguageDescription = lyricsLanguageDescription;
        this.lyricsCopyright = lyricsCopyright;
    }

    @NotNull
    @SerializedName("lyrics_id")
    @Expose
    private Integer lyricsId;
    @SerializedName("lyrics_body")
    @Expose
    private String lyricsBody;
    @SerializedName("lyrics_language")
    @Expose
    private String lyricsLanguage;
    @SerializedName("lyrics_language_description")
    @Expose
    private String lyricsLanguageDescription;
    @SerializedName("lyrics_copyright")
    @Expose
    private String lyricsCopyright;

    public Integer getLyricsId() {
        return lyricsId;
    }

    public void setLyricsId(Integer lyricsId) {
        this.lyricsId = lyricsId;
    }


    public String getLyricsBody() {
        return lyricsBody;
    }

    public void setLyricsBody(String lyricsBody) {
        this.lyricsBody = lyricsBody;
    }

    public String getLyricsLanguage() {
        return lyricsLanguage;
    }

    public void setLyricsLanguage(String lyricsLanguage) {
        this.lyricsLanguage = lyricsLanguage;
    }

    public String getLyricsLanguageDescription() {
        return lyricsLanguageDescription;
    }

    public void setLyricsLanguageDescription(String lyricsLanguageDescription) {
        this.lyricsLanguageDescription = lyricsLanguageDescription;
    }
    public String getLyricsCopyright() {
        return lyricsCopyright;
    }

    public void setLyricsCopyright(String lyricsCopyright) {
        this.lyricsCopyright = lyricsCopyright;
    }
}
