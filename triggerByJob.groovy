// Some Job
freeStyleJob("FreeStyleJob-1") {
    steps {
        echo 'I am FreeStyleJob-1'
    }
}

// Some another Job
freeStyleJob("FreeStyleJob-2") {
    steps {
        echo 'I am FreeStyleJob-2'
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