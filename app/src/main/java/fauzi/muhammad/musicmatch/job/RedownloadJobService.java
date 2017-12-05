package fauzi.muhammad.musicmatch.job;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;
import com.orm.query.Condition;
import com.orm.query.Select;
import java.util.List;
import fauzi.muhammad.musicmatch.models.Lirik;
import fauzi.muhammad.musicmatch.models.Music;
import fauzi.muhammad.musicmatch.models.MusicGenreList;
import fauzi.muhammad.musicmatch.models.Track;
import fauzi.muhammad.musicmatch.models.TrackList;
import fauzi.muhammad.musicmatch.models.TrackMusicGenrePrimary;
import fauzi.muhammad.musicmatch.musixmatch.MusixMatch;
import fauzi.muhammad.musicmatch.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by fauzi on 05/12/2017.
 */

public class RedownloadJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("Service", "Job berjalan");
        MusixMatch.ambilDataService(new Callback<Music>() {

            @Override
            public void onResponse(@NonNull Call<Music> call, @NonNull Response<Music> response) {
                Music object =  response.body();
                assert object != null;
                saveToDB(object.getMessage().getBody().getTrackList());

            }

            @Override
            public void onFailure(@NonNull Call<Music> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.no_signal, Toast.LENGTH_SHORT).show();

            }

        });
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("Service", "Job berhenti");
        return false;
    }

    void saveToDB(List<TrackList> trackList){
        int baru = 0;
        for (int i = 0; i < trackList.size(); i++) {
            Log.d("MAIN", trackList.get(i).getTrack().getTrackName());
            Track track = trackList.get(i).getTrack();
            Log.d("MAIN", "Track  : " +track.getTrackName());
            List<Track> listInDb = Select.from(Track.class).where(Condition.prop("track_id").eq(track.getTrackId())).list();
            //Track.find(Track.class, "track_id = ?", track.getTrackId());
            Log.d("MAIN", "Track ID yang ditemukan : " +track.getTrackId());
            Log.d("MAIN", "List yang ditemukan : " +String.valueOf(listInDb.size()));

            if(listInDb.isEmpty()){
                Log.d("MAIN", "Masuk Simpan!");
                track.save();
                baru++;
                List<MusicGenreList> musicGenrePrimaryLists = track.getPrimaryGenres().getMusicGenreList();
                Log.d("MAIN", "Jumlah genre "+musicGenrePrimaryLists.size());
                if(!musicGenrePrimaryLists.isEmpty()){
                    for (int j = 0; j < musicGenrePrimaryLists.size(); j++) {
                        TrackMusicGenrePrimary trackMusicGenrePrimary = new TrackMusicGenrePrimary(
                                track.getTrackId(),
                                musicGenrePrimaryLists.get(j).getMusicGenre().getMusicGenreName()
                        );
                        Log.d("MAIN", "Jumlah genre "+musicGenrePrimaryLists.get(j).getMusicGenre().getMusicGenreName());
                        trackMusicGenrePrimary.save();
                    }
                }

                if(track.getHasLyrics() == 1){
                    MusixMatch.getLirik(new Callback<Lirik>() {

                        @Override
                        public void onResponse(@NonNull Call<Lirik> call, @NonNull Response<Lirik> response) {
                            Lirik object =  response.body();
                            assert object != null;
                            object.getMessage().getBody().getLyrics().save();

                        }

                        @Override
                        public void onFailure(@NonNull Call<Lirik> call, @NonNull Throwable t) {
                            Log.d("MAIN", t.getMessage());
                        }

                    },track.getTrackId());
                }

            }
        }
        Toast.makeText(getApplicationContext(),"Lagu diperbarui. "+baru+" lagu baru telah ditambahkan", Toast.LENGTH_SHORT).show();
    }

}
