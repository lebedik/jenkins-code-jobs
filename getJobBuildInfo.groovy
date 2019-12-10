import hudson.model.*
import hudson.maven.*
import hudson.tasks.*
import jenkins.model.Jenkins
import hudson.maven.reporters.*

name = "Jenkins_Training"
//If we want to add more then one job
def items = new LinkedHashSet();
def job = Hudson.instance.getJob(name)
items.add(job);

def findCause(upStreamBuild) {
    //Check if the build was triggered by SCM change
    scmCause = upStreamBuild.getCause(hudson.triggers.SCMTrigger.SCMTriggerCause)
    if (scmCause != null) {
        return scmCause.getShortDescription()
    }
    
    //Check if the build was triggered by timer
    timerCause = upStreamBuild.getCause(hudson.triggers.TimerTrigger.TimerTriggerCause)
    if (timerCause != null) {
        return timerCause.getShortDescription()
    }
    
    //Check if the build was triggered by some jenkins user
    usercause = upStreamBuild.getCause(hudson.model.Cause.UserIdCause.class)
    if (usercause != null) {
        return usercause.getUserId()
    }
    
    //Check if the build was triggered by some jenkins project(job)
    upstreamcause = upStreamBuild.getCause(hudson.model.Cause.UpstreamCause.class)
    if (upstreamcause != null) {
        job = Jenkins.getInstance().getItemByFullName(upstreamcause.getUpstreamProject(), hudson.model.Job.class)
        if (job != null) {
            upstream = job.getBuildByNumber(upstreamcause.getUpstreamBuild())
            if (upstream != null) {
                return upstream
            }
        }
    }
    return;
}

// Iterate through each item.
items.each { item ->
    try {
        def job_data = Jenkins.instance.getItemByFullName(item.fullName)
        println 'Job: ' + item.fullName
        
        //Check if job had at least one build done
        if (job_data.getLastBuild()) {
            last_job_num = job_data.getLastBuild().getNumber()
            def upStreamBuild = Jenkins.getInstance().getItemByFullName(item.fullName).getBuildByNumber(last_job_num)
            
            println 'LastBuildNumber: ' + last_job_num
            println "LastBuildTime: ${upStreamBuild.getTime()}"
            println 'LastBuildCause: ' + findCause(upStreamBuild)
            
            //Check if job had at least one successful build
            if (job_data.getLastSuccessfulBuild()) {
                println 'LastSuccessNumber: ' + job_data.getLastSuccessfulBuild().getNumber()
                println 'LastSuccessResult: ' + job_data.getLastSuccessfulBuild().result
            } else {
                println 'LastSuccessNumber: Null'
                println 'LastSuccessResult: Null'
            }
        } else {
            println 'LastBuildNumber: Null'
        }

    } catch (Exception e) {
        println ' Ignoring exception ' + e
    }
}


/*This script shows how to get basic information about a job and its builds*/

println "Job type: ${job.getClass()}"
println "Is building: ${job.isBuilding()}"
println "Is in queue: ${job.isInQueue()}"
println "Last successful build: ${job.getLastSuccessfulBuild()}"
println "Last failed build: ${job.getLastFailedBuild()}"
println "Last build: ${job.getLastBuild()}"
println "All builds: ${job.getBuilds().collect{ it.getNumber()}}"