/* 
 * All content copyright Terracotta, Inc., unless otherwise indicated. All rights reserved. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */
 
package com.zyc.learn_quartz.example6;

import org.quartz.*;

import java.util.Date;

/**
 * <p>
 * A job dumb job that will throw a job execution exception
 * </p>
 * 
 * @author Bill Kratzer
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class BadJob2 implements Job {
    private int calculation;

    /**
     * Empty public constructor for job initialization
     */
    public BadJob2() {
    }

    /**
     * <p>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a <code>{@link org.quartz.Trigger}</code>
     * fires that is associated with the <code>Job</code>.
     * </p>
     * 
     * @throws JobExecutionException
     *           if there is an exception while executing the job.
     */
    public void execute(JobExecutionContext context)
        throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        System.out.println("---" + jobKey + " executing at " + new Date());

        // a contrived example of an exception that
        // will be generated by this job due to a 
        // divide by zero error
        try {
            int zero = 0;
            calculation = 4815 / zero;
        } catch (Exception e) {
            System.out.println("--- Error in job!");
            JobExecutionException e2 = new JobExecutionException(e);
            // Quartz will automatically unschedule
            // all triggers associated with this job
            // so that it does not run again
            e2.setUnscheduleAllTriggers(true);
            throw e2;
        }

        System.out.println("---" + jobKey + " completed at " + new Date());
    }

}
