//descriptive pipeline
pipeline{
    agent any
    parameters{
        string(name: 'BRANCH_NAME', defaultValue: 'master', description: 'from which branch artifacts want to deploy?')
        string(name: 'BUILD_NUM', defaultValue: '', description: 'from which BUILD NUM artifacts want to deploy?')
        string(name: 'SERVER_IP', defaultValue: '', description: 'server ip?')
        
    }
    stages{
        stage("download artifacts"){
            steps{
                println "Iam downloading artifacts from s3"
                sh """
                aws s3 ls
                aws s3 ls s3://yashartifacts
                aws s3 ls s3://yashartifacts/${BRANCH_NAME}/${BUILD_NUM}/
                aws s3 ls s3://yashartifacts/${BRANCH_NAME}/${BUILD_NUM}/hello-${BUILD_NUM}.war .

                """
            }
        }
        stage("copying artifacts"){
            steps{
                println "copying artifacts from jenkins to tomcat"
                sh " ssh -i /tmp/nvirginia.pem ec2-user@${SERVER_IP}"
            }
        }
    }
}