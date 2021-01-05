pipeline {
    agent any

    stages {
        stage('Build') {
            tool name: '3.6.3', type: 'maven'
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
    }
}