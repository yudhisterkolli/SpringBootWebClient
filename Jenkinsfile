pipeline {
    agent any
    tools {
        // This requires Maven to be installed on the Jenkins agent
        maven 'Maven 3.6.3' // Or whatever version is installed
    }
    environment {
        // Define any environment variables needed
        SPRING_PROFILES_ACTIVE = 'dev' // Specify profile if needed
    }
    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from GitHub
                git branch: 'develop', url: 'https://github.com/yudhisterkolli/SpringBootWebClient.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    // Use Maven to build the Spring Boot project
                    sh 'mvn clean install -DskipTests=true' // Skip tests during build, or change as needed
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Run tests with Maven
                    sh 'mvn test'
                }
            }
        }

        stage('Package') {
            steps {
                script {
                    // Package the Spring Boot project into a jar file
                    sh 'mvn package'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Deploy to your environment (could be a simple copy or a full deploy)
                    echo 'Deploying the Spring Boot application to the server...'
                    // Example: sh 'scp target/myapp.jar user@server:/path/to/deploy'
                }
            }
        }
    }

    post {
        always {
            // Clean up or notify after the build, even if it fails
            echo 'Build process completed'
        }
        success {
            // Actions to take when the build is successful
            echo 'Build successful!'
        }
        failure {
            // Actions to take when the build fails
            echo 'Build failed!'
        }
    }
}
