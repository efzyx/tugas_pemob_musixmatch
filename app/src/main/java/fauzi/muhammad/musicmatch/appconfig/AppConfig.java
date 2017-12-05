package fauzi.muhammad.musicmatch.appconfig;

import android.app.Application;

import com.orm.SugarContext;

import fauzi.muhammad.musicmatch.job.JobUtils;

/**
 * Created by fauzi on 02/12/2017.
 */

public class AppConfig extends Application {
    @Override
    public void onCreate() {
        SugarContext.init(this);
        JobUtils.scheduleJob(this);
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        SugarContext.terminate();
        super.onTerminate();
    }
}
