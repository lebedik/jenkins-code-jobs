
job(‘base_job’) {
disabled true
    logRotator –1, 10, –1, –1
}


job('job-with-template') {
    disabled false
    steps {
        shell ‘echo Rotates just like the template!’
    }
    using('base_job')
}