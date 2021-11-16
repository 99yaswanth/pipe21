//Declerative pipeline
pipeline{
    agent any
    stages{
        stage("checkout a code"){
            steps{
                println "cloning a code"
                sh "ls -l"
                checkout([$class: 'GitSCM',branches: [[name: '*/master']], userRemoteConfigs: [[ url: 'https://github.com/99yaswanth/boxfuse-sample-java-war-hello.git']]])
                 sh "ls -lart ./*"            
            }

        }
        stage("build code"){
            steps{
                println "clean package"
                sh "mvn clean package"
                sh " ls -l target/"
            }
        }
        stage("uploading artifacts to s3"){
            steps{
                println "uploading artifacts s3 bucket"
                sh "echo $BUILD_NUMBER"
                sh "aws s3 cp target/hello-${BUILD_NUMBER}.war s3://yashwanth12/master/${BUILD_NUMBER}/"
            }
        }
    }
}