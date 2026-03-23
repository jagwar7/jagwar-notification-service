pipeline {
    agent any
    environment {
        AWS_REGION          = 'ap-southeast-2'
        AWS_ACCOUNT_ID      = '696737009734'
        ECR_REPO_NAME       = 'ecr-notification-microservice'
        ECR_REPO_URL        = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO_NAME}"
        INSTANCE_ID         = 'i-0d17995cfcf998868'  //MICROSERVICE_EC2
        S3_BUCKET           = 'notification-microservice-s3-696737009734-ap-southeast-2-an'
    }

    stages{
        stage('#1. Checkout Notificaiton Microservice Repo'){
            steps{
                checkout scm  
            }
        }

        stage("#2. Build Docker Image"){
            steps{
                script{
                    echo "Building docker image of Notification Microservice"
                    sh "docker build -t ${ECR_REPO_NAME}:latest ."
                }
            }
        }

        stage('#3. Push the built image to AWS ECR'){
            steps{
                script{
                    echo "🧑‍💻Logging into AWS ECR..."
                    sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"

                    
                    echo "➡️Assgining tag to built image..."
                    sh "docker tag ${ECR_REPO_NAME}:latest ${ECR_REPO_URL}:latest"
                    
                    echo "⏫Pushing docker image to AWS ECR..."
                    sh "docker push ${ECR_REPO_URL}:latest"

                    echo "⏫Uploading compose file to S3..."

                    sh "aws s3 cp compose.yaml s3://${S3_BUCKET}/notification-service/compose.yaml"
                }
            }
        }


        stage('#4. Deploy to EC2 via SSM'){
            steps{
                script{
                    echo "Fetching credentials from Parameter Store..."
                    def getParam = { name ->
                        sh(script: "aws ssm get-parameter --name '$name' --with-decryption --query 'Parameter.Value' --output text --region ${AWS_REGION}", returnStdout: true).trim()
                    }

                    def rabbitHost      = getParam('/prod/notification-service/RABBIT_HOST')
                    echo "rabbit host: ${rabbitHost} "
                    def rabbitUsername  = getParam('/prod/notification-service/RABBIT_USERNAME')
                    echo "rabbitUsername: ${rabbitUsername} "
                    def rabbitPassword  = getParam('/prod/notification-service/RABBIT_PASSWORD')
                    echo "rabbitPassword: ${rabbitPassword} "
                    def mailPassword    = getParam('/prod/notification-service/MAIL_PASSWORD')
                    echo "mailPassword: ${mailPassword} "
                    def mailFrom        = getParam('/prod/notification-service/MAIL_FROM_ADDRESS')
                    echo "mailFrom: ${mailFrom} "
                    def mailUser        = getParam('/prod/notification-service/MAIL_USERNAME')
                    echo "mailUser: ${mailUser} "
                    def mailFromName    = getParam('/prod/notification-service/MAIL_FROM_NAME')
                    echo "mailFromName: ${mailFromName} "

                    echo "Deploying to Private Instance: ${INSTANCE_ID}"

                    sh """
                    aws ssm send-command \
                    --instance-ids ${INSTANCE_ID} \
                    --region ${AWS_REGION} \
                    --document-name "AWS-RunShellScript" \
                    --parameters 'commands=[
                        \"aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com\",
                        
                        \"mkdir -p /home/ubuntu/notification-service\",

                        \"cat <<\'EOF\' > /home/ubuntu/notification-service/.env
RABBIT_HOST=${rabbitHost}
RABBIT_PORT=5672
RABBIT_USERNAME=${rabbitUsername}
RABBIT_PASSWORD=${rabbitPassword}
RABBIT_VHOST=/
MAIL_HOST=live.smtp.mailtrap.io
MAIL_PORT=587
MAIL_USERNAME=${mailUser}
MAIL_PASSWORD=${mailPassword}
MAIL_FROM_ADDRESS=${mailFrom}
MAIL_FROM_NAME=${mailFromName}
SERVER_PORT=5700
RABBIT_EMAIL_QUEUE_NAME=email-queue
RABBIT_EMAIL_DLQ_NAME=dead-letter-queue
RABBIT_NOTIFICATION_EXCHANGE=notification_exchange
RABBIT_DEAD_LETTER_EXCHANGE=dead_letter_exchange
EMAIL_ROUTING_KEY=notification.email
EMAIL_DLQ_ROUTING_KEY=notification.email.dlq
ECR_IMAGE_URL=${ECR_REPO_URL}:latest
EOF\",

                        \"aws s3 cp s3://${S3_BUCKET}/notification-service/compose.yaml /home/ubuntu/notification-service/compose.yaml\",
                        \"cd /home/ubuntu/notification-service && docker compose pull && docker compose up -d\"
                    ]'
                    """
                }
            }
        }
    }

    post{
        success{
            echo "🌐🚀 Successfully deployed Notification Microservice!"
        }
        failure{
            echo "⚠️❌ Successfully deployed Notification Microservice!"
        }
        always{
            echo "🧹🧼 Cleaning up local built image..."
            sh "docker rmi ${ECR_REPO_NAME}:latest || true"
            sh "docker rmi ${ECR_REPO_URL}:latest || true"
        }
    }
}