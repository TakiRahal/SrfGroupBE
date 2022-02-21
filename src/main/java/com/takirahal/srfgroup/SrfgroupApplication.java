package com.takirahal.srfgroup;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableAsync
public class SrfgroupApplication implements CommandLineRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    public static void main(String[] args) {
        SpringApplication.run(SrfgroupApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, JobParameter> parms = new HashMap<>();
        parms.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameter = new JobParameters(parms);
        JobExecution jobExecution = jobLauncher.run(job, jobParameter);
        while (jobExecution.isRunning()){
            System.out.println("...");
        }
        // return jobExecution.getStatus();
    }
}
