import hudson.tasks.*
import com.cloudbees.hudson.plugins.folder.Folder


//Iterate over the each Job
activeJobs = hudson.model.Hudson.instance.items.findAll{job -> !(job instanceof Folder) && job.isBuildable()}
println("successjobs = " +activeJobs.size())
failedRuns = activeJobs.findAll{job -> job.lastBuild != null && !(job.lastBuild.isBuilding()) && job.lastBuild.result == hudson.model.Result.FAILURE}

// Do something with them - e.g. listing failed jobs
println("failedjobs = " +failedRuns.size())


BUILD_STRING = "Caused:"

failedRuns.each{ item ->
    println("=====================================================================")
    println "Failed Job Name: ${item.name}"
    item.lastBuild.getLog().eachLine { line ->
        if (line =~ /$BUILD_STRING/) {
            println "error: $line"
        }
    }
}

println("=====================================================================")

// Restart failed jobs
startServer = "admin computer"
startNote   = "bulk start"
cause = new hudson.model.Cause.RemoteCause(startServer, startNote)
failedRuns.each{run -> run.scheduleBuild(cause)}