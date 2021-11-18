//descriptive pipeline
pipeline{
    agent any
    parameters{
        string(name: 'BRANCH_NAME', defaultValue: 'master', description: 'from which branch artifacts want to deploy?')
        string(name: 'BUILD_NUM', defaultValue: ' ', description: 'from which build num artifacts want to deploy?')
    }
    stages{
        stage("download artifacts"){
            steps{
                println "Iam downloading artifacts from s3"
                sh """
                aws s3 ls
                aws s3 ls s3://yashwanth12
                aws s3 ls s3://yashwanth12/${BRANCH_NAME}/${BUILD_NUM}/
                """
            }
        }
    }
}