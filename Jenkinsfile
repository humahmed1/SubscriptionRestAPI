pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                def mvn = tool name: '3.6.3', type: 'maven'
                sh "${mvn}/bin/mvn -B -DskipTests clean package"
            }
        }
    }
}