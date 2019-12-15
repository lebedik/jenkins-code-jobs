// Some Job
freeStyleJob("FreeStyleJob-1") {
    steps {
        shell('echo Hello World!')
    }
}

// Some another Job
freeStyleJob("FreeStyleJob-2") {
    steps {
        shell('echo Hello World!')
    }
}

// Pipeline
pipelineJob('Trigger FreeStyleJobs') {
  definition {
    cps {
      script('''
        pipeline {
            agent any
                stages {
                    stage('Trigger FreeStyleJob-1') {
                        steps {
                            build job: './FreeStyleJob-1', parameters: [string(name: 'Trigger FreeStyleJobs', value: env.NAME)], wait: true
                        }
                    }
                    stage('Trigger FreeStyleJob-2') {
                        steps {
                            build job: './FreeStyleJob-2', parameters: [string(name: 'Trigger FreeStyleJobs', value: env.NAME)], wait: true
                        }
                    }
                }
            }
      '''.stripIndent())
      sandbox()     
    }
  }
}