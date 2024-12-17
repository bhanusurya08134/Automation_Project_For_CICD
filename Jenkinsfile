pipeline {
    agent any

    environment {
        TEAMS_WEBHOOK_URL = "https://globalcsg.webhook.office.com/webhookb2/7b1869f0-dc57-44b7-997c-b56d4d25c99a@a9c50c6c-2ecc-4653-99b2-58024af91866/JenkinsCI/0e7368f444b24cd89c851a88a41589aa/b71eb985-bd6c-4b62-91b9-71b533e6aa73/V2UkufH6xyDwb0XYPqJw7-s5JaZ-ANRmjwusO9WXqcqGM1"  // Replace with your actual webhook URL
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    // Run Maven build and tests
                    def mvnStatus = sh(returnStatus: true, script: 'mvn clean test -P regression')
                    if (mvnStatus != 0) {
                        currentBuild.result = 'FAILURE'
                    }
                }
            }
        }

        stage('Publish HTML Report') {
            steps {
                publishHTML([
                    reportDir: 'target/surefire-reports',
                    reportFiles: 'index.html',
                    reportName: 'Test Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: false
                ])
            }
        }

        stage('Send Teams Notification') {
            steps {
                script {
                    // Extract test results
                    def testResults = sh(returnStdout: true, script: """
                        grep -E "Tests run:|Failures:|Errors:|Skipped:" target/surefire-reports/*.txt | tail -1
                    """).trim()

                    def totalTests = testResults =~ /Tests run: ([0-9]+)/ ? (testResults =~ /Tests run: ([0-9]+)/)[0][1] : "N/A"
                    def failures = testResults =~ /Failures: ([0-9]+)/ ? (testResults =~ /Failures: ([0-9]+)/)[0][1] : "0"
                    def errors = testResults =~ /Errors: ([0-9]+)/ ? (testResults =~ /Errors: ([0-9]+)/)[0][1] : "0"
                    def skipped = testResults =~ /Skipped: ([0-9]+)/ ? (testResults =~ /Skipped: ([0-9]+)/)[0][1] : "0"
                    def passed = (totalTests.toInteger() - failures.toInteger() - errors.toInteger())

                    // Build status
                    def buildStatus = currentBuild.result ?: 'SUCCESS'

                    // Create JSON message for Teams
                    def message = """
                    {
                        "@type": "MessageCard",
                        "@context": "https://schema.org/extensions",
                        "summary": "Jenkins Build Notification",
                        "themeColor": "${buildStatus == 'SUCCESS' ? '00FF00' : 'FF0000'}",
                        "title": "Jenkins Build Results - ${buildStatus}",
                        "sections": [{
                            "activityTitle": "**Jenkins Test Results**",
                            "facts": [
                                {"name": "Total Tests", "value": "${totalTests}"},
                                {"name": "Passed", "value": "${passed}"},
                                {"name": "Failed", "value": "${failures}"},
                                {"name": "Errors", "value": "${errors}"},
                                {"name": "Skipped", "value": "${skipped}"},
                                {"name": "Build Status", "value": "${buildStatus}"}
                            ],
                            "markdown": true
                        }]
                    }
                    """

                    // Send notification to Teams
                    httpRequest(
                        url: "${env.TEAMS_WEBHOOK_URL}",
                        httpMode: 'POST',
                        contentType: 'APPLICATION_JSON',
                        requestBody: message
                    )
                }
            }
        }
    }

    post {
        failure {
            echo 'Build failed!'
        }
        success {
            echo 'Build succeeded!'
        }
    }
}
