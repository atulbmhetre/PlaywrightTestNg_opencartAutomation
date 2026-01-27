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
                    script {
                        def suiteMap = [
                            'smoke'        : 'testng_smoke',        // No path, no .xml
                            'regression'   : 'testng_regression',
                            'crossbrowser' : 'testng_crossbrowser'
                        ]
                        def selectedFile = suiteMap[params.testSuite]

                        bat "mvn clean test -DsuiteFile=${selectedFile} -Denv=${params.env}"
                    }
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
            bat 'dir /s test-output'
            archiveArtifacts artifacts: 'test-output/Test-ExtentReport/**/*.*', fingerprint: true
        }
    }


}
