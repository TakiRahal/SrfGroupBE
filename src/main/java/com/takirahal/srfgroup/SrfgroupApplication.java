package com.takirahal.srfgroup;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("addressBeanJob")
    private Job jobAddress;

    @Autowired
    @Qualifier("authorityBeanJob")
    private Job jobAuthority;

    @Autowired
    @Qualifier("userBeanJob")
    private Job jobUser;

    public static void main(String[] args) {
        SpringApplication.run(SrfgroupApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // For addresses
        Map<String, JobParameter> parms = new HashMap<>();
        parms.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameter = new JobParameters(parms);
        JobExecution jobExecution = jobLauncher.run(jobAddress, jobParameter);
        while (jobExecution.isRunning()){
            System.out.println("...");
        }

        // For authority
        Map<String, JobParameter> parmsAuthority = new HashMap<>();
        parmsAuthority.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameterAuthority = new JobParameters(parmsAuthority);
        JobExecution jobExecutionAuthority = jobLauncher.run(jobAuthority, jobParameterAuthority);
        while (jobExecutionAuthority.isRunning()){
            System.out.println("...");
        }

        // For user
        Map<String, JobParameter> parmsUser = new HashMap<>();
        parmsUser.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameterUser = new JobParameters(parmsUser);
        JobExecution jobExecutionUser = jobLauncher.run(jobUser, jobParameterUser);
        while (jobExecutionUser.isRunning()){
            System.out.println("...");
        }

        // return jobExecution.getStatus();
    }
}
