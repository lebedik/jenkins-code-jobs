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

freeStyleJob('job-with-template') {
    disabled false
    steps {
        gradle('clean build')
    }
    publishers {
        archiveArtifacts('job-dsl-plugin/build/libs/job-dsl.hpi')
    }
    useing('base-template-job')
}