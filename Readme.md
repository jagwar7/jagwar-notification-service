# 1. NOTIFICATION MICROSERVICE -------------------------------------------------------------------------------------------

A scalable, event-driven Java Spring Boot service that handles multi-channel notifications using RabbitMQ and AWS.
Successfully deployed on AWS VPC Private Subnet.




# 2. 🛠 TECH STACK -------------------------------------------------------------------------------------------------------

Backend: Java 21, Spring Boot 3.4.2

Messaging: RabbitMQ (with DLQ support)

Cloud/DevOps: AWS (ECR, S3, SSM), Docker, Jenkins (CI/CD)

Broker: Mailtrap.io




# 3. 🚀 FEATURES --------------------------------------------------------------------------------------------------------

It only supports EMAIL Notification For now
    1: Welcome Email
    2: Password Reset Link

Event-Driven Architecture: Decouples notification triggers from the main application.

Reliability: Implements Dead Letter Queues (DLQ) for failed email retries.

Cloud Native: Automated deployment pipeline via Jenkins and AWS SSM.




# 4. 📐 SYSTEM ARCHITECTURE ---------------------------------------------------------------------------------------------


                                        +-----------+
                        +--->---->---->-| RABBIT MQ |---->---->---->-----+
                        ^               +-----------+                    |
                        |                                                |   
                        ^                                               🔽   
                        |                                                |         
                        |                                                |
            +--------------------+                              +---------------------------+
            | FLASH AUTH BACKEND |                              | NOTIFICATION MICROSERVICE |
            +--------------------+                              +---------------------------+



# 5. ⚙️ GETTING STARTED--------------------------------------------------------------------------------------------------

Prerequisites
Docker & Docker Compose

Java 21 JDK

# 1. Clone the repo
git clone https://github.com/jagwar7/jagwar-notification-service.git

# 2. Setup Environment Variables
run `cp .env.example .env` 
Use: env.sample.txt and use your own credentails

# 3. Spin up infrastructure
docker compose up -d



# 7. 🏗 CI/CD & Deployment ----------------------------------------------------------------------------------------------

Pipeline: Code Push ---> GitHub Webhook ----> Jenkins Build ----> Push ECR ----> upload compose.yaml to S3
                                                                                            ⬇️
                                                                                            ⬇️
                                                                                    Fetch Credentials
                                                                                            ⬇️
                                                                                            ⬇️
                                                                                Make ENV in private EC2 
                                                                                            ⬇️
                                                                                            ⬇️
                                                                                pull the image & Deploy 
