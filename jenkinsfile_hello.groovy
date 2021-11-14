//Declerative pipeline

pipeline{
    agent any
    stages{
        stage("hello world"){
            steps{
                println "welcome to pipeline"
            }
        }
    }
}
