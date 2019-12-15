// Some Job
freeStyleJob("FreeStyleJob-1") {
    steps {
        gradle('clean build')
    }
}

// Some another Job
freeStyleJob("FreeStyleJob-2") {
    steps {
        gradle('clean build')
    }
}

// Pipeline
pipeline {
    agent any 

    stages {
        stage('Trigger FreeStyleJob-1') {
            agent any 
            steps {
                build job: 'FreeStyleJob-1'
            }
        }
        stage('Trigger FreeStyleJob-2') {
            agent any
            steps {
                build job: 'FreeStyleJob-2'
            }
        }
    }
}