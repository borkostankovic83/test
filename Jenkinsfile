updateGitlabCommitStatus state: 'pending'

pipeline {
    // Makes our pipeline run on any node
    agent any

    options {
        gitLabConnection('gitlab')
    }

    environment {
        CF = credentials('pws-credentials')
    }

    stages {
        // Verify that the feature branch is not behind develop
        stage('check-for-rebase-before') {
            when {
                expression {
                    return env.GIT_BRANCH != 'origin/develop'
                }
            }
            steps {
                script {
                    try {
                        sh "./gradlew clean verifyMergeRequest -PMERGE_ID=$env.gitlabMergeRequestIid"
                        updateGitlabCommitStatus name: 'check-for-rebase', state: 'success'
                    } catch (exc) {
                        // this is so we can capture the results in 'finally' below
                        updateGitlabCommitStatus name: 'check-for-rebase', state: 'failed'
                        throw exec
                    }
                }
            }
        }

        stage('build') {
            steps {
                updateGitlabCommitStatus state: 'running'
                script {
                    sh './gradlew clean build -x test'
                }
            }
        }

        stage('unit-test') {
            when {
                expression {
                    return env.GIT_BRANCH != 'origin/develop'
                }
            }
            steps {
                script {
                    try {
                        sh './gradlew test'
                        updateGitlabCommitStatus name: 'unit test', state: 'success'
                    } catch (exc) {
                        updateGitlabCommitStatus name: 'unit test failed', state: 'failed'
                        throw exec
                    }
                }
            }
        }
        stage('integration-test') {
            when {
                expression {
                    return env.GIT_BRANCH != 'origin/develop'
                }
            }
            steps {
                script {
                    try {
                        sh './gradlew integrationTest'
                        updateGitlabCommitStatus name: 'integration test', state: 'success'
                    } catch (exc) {
                        // this is so we can capture the results in 'finally' below
                        updateGitlabCommitStatus name: 'integration test failed', state: 'failed'
                        throw exec
                    }
                }
            }
        }

        stage('sonar') {
            when {
                expression {
                    return env.GIT_BRANCH != 'origin/develop'
                }
            }
            steps {
                withSonarQubeEnv('Sonar_GCP') {
                    sh './gradlew check jacocoTestCoverageVerification sonar'
                }
            }
        }
        stage("sonar-qa") {
            when {
                expression {
                    return env.GIT_BRANCH != 'origin/develop'

                }
            }
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    // sleep is only a temporary fix to a bug
                    sleep(10)
                    waitForQualityGate abortPipeline: true
                }
                updateGitlabCommitStatus name: 'sonar', state: 'success'
            }
        }

        // Verify again that the feature branch is still not behind develop
        stage('check-for-rebase-after') {
            when {
                expression {
                    return env.GIT_BRANCH != 'origin/develop'
                }
            }
            steps {
                script {
                    try {
                        sh "./gradlew verifyMergeRequest -PMERGE_ID=$env.gitlabMergeRequestIid"
                        updateGitlabCommitStatus name: 'check-for-rebase', state: 'success'
                    } catch (exc) {
                        // this is so we can capture the results in 'finally' below
                        updateGitlabCommitStatus name: 'check-for-rebase', state: 'failed'
                        throw exec
                    }
                }
            }
        }

        stage("merge-code") {
            when {
                expression {
                    return  env.GIT_BRANCH != 'origin/develop'
                }
            }
            steps {
                acceptGitLabMR()
            }
        }

        stage('deploy-develop') {
            when {
                expression {
                    return env.GIT_BRANCH == 'origin/develop'
                }
            }
            steps {
                sh '''
                   ./gradlew -PCF_USR=$CF_USR -PCF_PSW=$CF_PSW -PCF_SPACE=$CCSpace cf-push
                   '''
                updateGitlabCommitStatus name: 'cf-push', state: 'success'
            }
        }
    }
    
    post {
        always {
            // Cleans the workspace - so Jenkins will run fast and efficiently!
            cleanWs()
        }
        success {
            updateGitlabCommitStatus state: 'success'
        }
        failure {
            updateGitlabCommitStatus state: 'failed'
        }
    }
}
