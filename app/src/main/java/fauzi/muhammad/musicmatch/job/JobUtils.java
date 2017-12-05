package fauzi.muhammad.musicmatch.job;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

/**
 * Created by fauzi on 05/12/2017.
 */

public class JobUtils {
    private final static long PERIODIC = 1800000;
    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, RedownloadJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setPeriodic(PERIODIC);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPersisted(true);

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (jobScheduler != null) {
            jobScheduler.schedule(builder.build());
        }
    }

}
