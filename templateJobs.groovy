import jenkins.model.Jenkins;
import hudson.model.FreeStyleProject;
import hudson.tasks.Shell;

// Define numbers of copies
def n = 10
n.times {
    job = Jenkins.instance.createProject(FreeStyleProject, "template-job-${it}")
    job.buildersList.add(new Shell('echo hello world'))
    job.save()
//    build = job.scheduleBuild2(5, new hudson.model.Cause.UserIdCause())
//    build.get()
}


