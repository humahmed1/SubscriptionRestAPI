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

        stage('Run') {
            steps {
                sh 'mvn install -DskipTests exec:java'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

    }
}