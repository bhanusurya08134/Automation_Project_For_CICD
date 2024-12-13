pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Clone the repository
                checkout scm
            }
        }
        stage('Build') {
            steps {
                // Clean and build the Maven project
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                // Run tests and generate reports
                sh 'mvn clean test -P regression'
            }
        }
        stage('Generate Report') {
            steps {
                // Archive the HTML report
                archiveArtifacts artifacts: 'target/surefire-reports/*.html', fingerprint: true
                
                // Publish the HTML report
                publishHTML([
                    reportDir: 'target/surefire-reports', // Directory where reports are generated
                    reportFiles: 'index.html',          // The main report file
                    reportName: 'Test Report'          // Display name in Jenkins UI
                ])
            }
        }
        stage('Notify Teams') {
            steps {
                script {
                    def webhookUrl = "https://globalcsg.webhook.office.com/webhookb2/7b1869f0-dc57-44b7-997c-b56d4d25c99a@a9c50c6c-2ecc-4653-99b2-58024af91866/JenkinsCI/0e7368f444b24cd89c851a88a41589aa/b71eb985-bd6c-4b62-91b9-71b533e6aa73/V2UkufH6xyDwb0XYPqJw7-s5JaZ-ANRmjwusO9WXqcqGM1"
                    def reportLink = "${env.BUILD_URL}HTML_Report" // Link to the HTML report in Jenkins

                    def message = """
                        {
                            "@type": "MessageCard",
                            "@context": "http://schema.org/extensions",
                            "summary": "Jenkins Build Notification",
                            "sections": [{
                                "activityTitle": "Build #${env.BUILD_NUMBER} Completed",
                                "activitySubtitle": "View the report for more details",
                                "activityImage": "https://www.jenkins.io/images/logos/jenkins/jenkins.png",
                                "facts": [
                                    { "name": "Project", "value": "${env.JOB_NAME}" },
                                    { "name": "Status", "value": "Success" },
                                    { "name": "Report", "value": "[View HTML Report](${reportLink})" }
                                ],
                                "markdown": true
                            }]
                        }
                    """
                    
                    // Send the notification to Teams
                    httpRequest(
                        acceptType: 'APPLICATION_JSON',
                        contentType: 'APPLICATION_JSON',
                        httpMode: 'POST',
                        requestBody: message,
                        url: webhookUrl
                    )
                }
            }
        }
    }
    post {
        failure {
            // Notify on failure
            script {
                def webhookUrl = "https://globalcsg.webhook.office.com/webhookb2/7b1869f0-dc57-44b7-997c-b56d4d25c99a@a9c50c6c-2ecc-4653-99b2-58024af91866/JenkinsCI/0e7368f444b24cd89c851a88a41589aa/b71eb985-bd6c-4b62-91b9-71b533e6aa73/V2UkufH6xyDwb0XYPqJw7-s5JaZ-ANRmjwusO9WXqcqGM1"
                def message = """
                    {
                        "@type": "MessageCard",
                        "@context": "http://schema.org/extensions",
                        "summary": "Jenkins Build Failure Notification",
                        "sections": [{
                            "activityTitle": "Build #${env.BUILD_NUMBER} Failed",
                            "activitySubtitle": "Please check the logs for details",
                            "activityImage": "https://www.jenkins.io/images/logos/jenkins/jenkins.png",
                            "facts": [
                                { "name": "Project", "value": "${env.JOB_NAME}" },
                                { "name": "Status", "value": "Failed" },
                                { "name": "Jenkins URL", "value": "[View Logs](${env.BUILD_URL})" }
                            ],
                            "markdown": true
                        }]
                    }
                """
                
                httpRequest(
                    acceptType: 'APPLICATION_JSON',
                    contentType: 'APPLICATION_JSON',
                    httpMode: 'POST',
                    requestBody: message,
                    url: webhookUrl
                )
            }
        }
    }
}
