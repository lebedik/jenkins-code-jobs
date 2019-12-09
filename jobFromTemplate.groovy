// Define Base template 
freeStyleJob('base-template-job') {
    jdk('Java 8')
    logRotator(-1, 10)
    scm {
        github('jenkinsci/job-dsl-plugin', 'master')
    }
    triggers {
        githubPush()
    }
}

// Define numbers of copies
def n = 10
// Create Job using using Base template
n.times {
    freeStyleJob("job-with-template-${it}") {
        disabled false
        steps {
            gradle('clean build')
        }
        publishers {
            archiveArtifacts("job-dsl-plugin/build/libs/job-dsl-${it}.hpi")
        }
        using('base-template-job')
    }
}