pipeline {
    agent any
    tools {
        maven '3.6.3'
    }
    stages {

        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }

        stage('Test: Cucumber Tests') {
            steps {
                sh 'mvn verify'
            }
        }

    }
}