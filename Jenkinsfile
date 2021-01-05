pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                tool name: '3.6.3', type: 'maven'
                sh 'mvn -B -DskipTests clean package'
            }
        }
    }
}