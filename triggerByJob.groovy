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
            def checkjob1 = build job: './FreeStyleJob-1', wait: true
            checklog1 = Jenkins.getInstance().getItemByFullName('FreeStyleJob-1').getBuildByNumber(checkjob.getNumber()).log
            println checklog1

            def checkjob2 = build job: './FreeStyleJob-2', wait: true
            checklog2 = Jenkins.getInstance().getItemByFullName('FreeStyleJob-2').getBuildByNumber(checkjob.getNumber()).log
            println checklog2

            pipeline {
                agent any
                    stages {
                        stage('Trigger FreeStyleJob-1') {
                            steps {
                                build job: './FreeStyleJob-1', wait: true
                            }
                        }
                        stage('Trigger FreeStyleJob-2') {
                            steps {
                                build job: './FreeStyleJob-2', wait: true
                            }
                        }
                    }
                }
      '''.stripIndent())
      sandbox()     
    }
  }
}