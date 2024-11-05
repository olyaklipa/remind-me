package olya.app.remindme.config;

import olya.app.remindme.quartz.jobs.ConfirmationJob;
import olya.app.remindme.quartz.jobs.NotificationJob;
import olya.app.remindme.quartz.jobs.ReminderJob;
import olya.app.remindme.quartz.jobs.ChasingConfirmationJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail reminderJobDetail() {
        return JobBuilder
                .newJob(ReminderJob.class)
                .withIdentity("reminderJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger reminderJobTrigger() {
        return TriggerBuilder
                .newTrigger()
                .forJob(reminderJobDetail())
                .withIdentity("reminderTrigger")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(8, 00))
                .build();
    }

    @Bean
    public JobDetail notificationJobDetail() {
        return JobBuilder
                .newJob(NotificationJob.class)
                .withIdentity("notificationJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger notificationJobTrigger() {
        return TriggerBuilder
                .newTrigger()
                .forJob(notificationJobDetail())
                .withIdentity("notificationTrigger")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(10, 00))
                .build();
    }

    @Bean
    public JobDetail confirmationJobDetail() {
        return JobBuilder
                .newJob(ConfirmationJob.class)
                .withIdentity("confirmationJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger confirmationJobTrigger() {
        return TriggerBuilder
                .newTrigger()
                .forJob(confirmationJobDetail())
                .withIdentity("confirmationTrigger")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(21, 00))
                .build();
    }

    @Bean
    public JobDetail chasingConfirmationJobDetail() {
        return JobBuilder
                .newJob(ChasingConfirmationJob.class)
                .withIdentity("chasingConfirmationJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger chasingConfirmationJobTrigger() {
        return TriggerBuilder
                .newTrigger()
                .forJob(chasingConfirmationJobDetail())
                .withIdentity("chasingConfirmationTrigger")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(20, 00))
                .build();
    }
}
