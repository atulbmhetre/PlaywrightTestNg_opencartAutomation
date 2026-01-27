pipeline {
    agent any
    tools {
        // Match the exact names you gave in Jenkins Global Tool Configuration
        nodejs 'Node25' 
        maven 'M3' 
    }
    stages {
        stage('Run Playwright Tests') {
            steps {
                bat 'mvn clean test'
            }
        }
    }
    post {
        always {

            // Publish clickable HTML report
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'test-output/Test-ExtentReport',
                reportFiles: '*.html',
                reportName: 'Playwright Extent Report'
            ])

            // Archive full test-output folder
            archiveArtifacts artifacts: 'test-output/**', fingerprint: true
        }
    }


}
