pipeline{
    agent any
    parameters{
        string(name: 'branch', defaultValue: '')
        string(name: 'buildno', defaultValue: '')
        string(name: 'serverip', defaultValue: '')
    }
    stages{
        stage("clone a code"){
            steps{
                println " clone a code"
                git branch : "${branch}",
                url : 'https://github.com/99yaswanth/boxfuse-sample-java-war-hello.git'
            }
        }
        stage("build a code"){
            steps{
                sh "mvn clean package"
            }
        }
        stage("upload to s3"){
            steps{
                sh "aws s3 cp target/hello-${buildno}.war s3://yashwanth12/${branch}/${buildno}/"
            }
        }
        stage("deploy to tomcat"){
            steps{
                sh "scp -o StrictHostKeyChecking=no -i tmp/yashnv.pem target/hello-${buildno}.war ec2-user@${serverip}:/tmp"
                sh "ssh -o StrictHostKeyChecking=no -i /tmp/yashnv.pem ec2-user@${serverip} \"sudo cp /tmp/hello-${buildno}.war /var/lib/tomcat/webapps\""
            }
        }
    }
}