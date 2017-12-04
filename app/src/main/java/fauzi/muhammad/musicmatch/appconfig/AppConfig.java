package fauzi.muhammad.musicmatch.appconfig;

import android.app.Application;

import com.orm.SugarContext;

/**
 * Created by fauzi on 02/12/2017.
 */

public class AppConfig extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
