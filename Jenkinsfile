pipeline {
    agent any

    tools {
        maven 'Maven-3.9.0'
        jdk 'JDK-17'
    }

    environment {
        REGISTRY = 'docker.io/viticulture-platform'
        DOCKER_CREDENTIALS = credentials('docker-hub-credentials')
        KUBECONFIG = credentials('kubeconfig')
        NAMESPACE = 'viticulture-platform'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            parallel {
                stage('API Gateway') {
                    steps {
                        dir('api-gateway') {
                            sh 'mvn clean test'
                        }
                    }
                }
                stage('Farmer Management Service') {
                    steps {
                        dir('services/farmer-management-service') {
                            sh 'mvn clean test'
                        }
                    }
                }
                stage('Supplier Management Service') {
                    steps {
                        dir('services/supplier-management-service') {
                            sh 'mvn clean test'
                        }
                    }
                }
                stage('Procurement Service') {
                    steps {
                        dir('services/procurement-service') {
                            sh 'mvn clean test'
                        }
                    }
                }
                stage('Inventory Management Service') {
                    steps {
                        dir('services/inventory-management-service') {
                            sh 'mvn clean test'
                        }
                    }
                }
                stage('Logistics & Distribution Service') {
                    steps {
                        dir('services/logistics-distribution-service') {
                            sh 'mvn clean test'
                        }
                    }
                }
                stage('Quality Assurance Service') {
                    steps {
                        dir('services/quality-assurance-service') {
                            sh 'mvn clean test'
                        }
                    }
                }
                stage('Payment & Financial Service') {
                    steps {
                        dir('services/payment-financial-service') {
                            sh 'mvn clean test'
                        }
                    }
                }
            }
        }

        stage('Build Docker Images') {
            parallel {
                stage('Build API Gateway Image') {
                    steps {
                        dir('api-gateway') {
                            sh """
                                mvn clean package -DskipTests
                                docker build -t \${REGISTRY}/api-gateway:\${BUILD_NUMBER} .
                                docker tag \${REGISTRY}/api-gateway:\${BUILD_NUMBER} \${REGISTRY}/api-gateway:latest
                            """
                        }
                    }
                }
                stage('Build Farmer Management Image') {
                    steps {
                        dir('services/farmer-management-service') {
                            sh """
                                mvn clean package -DskipTests
                                docker build -t \${REGISTRY}/farmer-management-service:\${BUILD_NUMBER} .
                                docker tag \${REGISTRY}/farmer-management-service:\${BUILD_NUMBER} \${REGISTRY}/farmer-management-service:latest
                            """
                        }
                    }
                }
                stage('Build Supplier Management Image') {
                    steps {
                        dir('services/supplier-management-service') {
                            sh """
                                mvn clean package -DskipTests
                                docker build -t \${REGISTRY}/supplier-management-service:\${BUILD_NUMBER} .
                                docker tag \${REGISTRY}/supplier-management-service:\${BUILD_NUMBER} \${REGISTRY}/supplier-management-service:latest
                            """
                        }
                    }
                }
                stage('Build Procurement Image') {
                    steps {
                        dir('services/procurement-service') {
                            sh """
                                mvn clean package -DskipTests
                                docker build -t \${REGISTRY}/procurement-service:\${BUILD_NUMBER} .
                                docker tag \${REGISTRY}/procurement-service:\${BUILD_NUMBER} \${REGISTRY}/procurement-service:latest
                            """
                        }
                    }
                }
                stage('Build Inventory Management Image') {
                    steps {
                        dir('services/inventory-management-service') {
                            sh """
                                mvn clean package -DskipTests
                                docker build -t \${REGISTRY}/inventory-management-service:\${BUILD_NUMBER} .
                                docker tag \${REGISTRY}/inventory-management-service:\${BUILD_NUMBER} \${REGISTRY}/inventory-management-service:latest
                            """
                        }
                    }
                }
                stage('Build Logistics & Distribution Image') {
                    steps {
                        dir('services/logistics-distribution-service') {
                            sh """
                                mvn clean package -DskipTests
                                docker build -t \${REGISTRY}/logistics-distribution-service:\${BUILD_NUMBER} .
                                docker tag \${REGISTRY}/logistics-distribution-service:\${BUILD_NUMBER} \${REGISTRY}/logistics-distribution-service:latest
                            """
                        }
                    }
                }
                stage('Build Quality Assurance Image') {
                    steps {
                        dir('services/quality-assurance-service') {
                            sh """
                                mvn clean package -DskipTests
                                docker build -t \${REGISTRY}/quality-assurance-service:\${BUILD_NUMBER} .
                                docker tag \${REGISTRY}/quality-assurance-service:\${BUILD_NUMBER} \${REGISTRY}/quality-assurance-service:latest
                            """
                        }
                    }
                }
                stage('Build Payment & Financial Image') {
                    steps {
                        dir('services/payment-financial-service') {
                            sh """
                                mvn clean package -DskipTests
                                docker build -t \${REGISTRY}/payment-financial-service:\${BUILD_NUMBER} .
                                docker tag \${REGISTRY}/payment-financial-service:\${BUILD_NUMBER} \${REGISTRY}/payment-financial-service:latest
                            """
                        }
                    }
                }
            }
        }

        stage('Push Docker Images') {
            parallel {
                stage('Push API Gateway Image') {
                    steps {
                        sh "docker login -u \${DOCKER_CREDENTIALS_USR} -p \${DOCKER_CREDENTIALS_PSW}"
                        sh "docker push \${REGISTRY}/api-gateway:\${BUILD_NUMBER}"
                        sh "docker push \${REGISTRY}/api-gateway:latest"
                    }
                }
                stage('Push Farmer Management Image') {
                    steps {
                        sh "docker login -u \${DOCKER_CREDENTIALS_USR} -p \${DOCKER_CREDENTIALS_PSW}"
                        sh "docker push \${REGISTRY}/farmer-management-service:\${BUILD_NUMBER}"
                        sh "docker push \${REGISTRY}/farmer-management-service:latest"
                    }
                }
                stage('Push Supplier Management Image') {
                    steps {
                        sh "docker login -u \${DOCKER_CREDENTIALS_USR} -p \${DOCKER_CREDENTIALS_PSW}"
                        sh "docker push \${REGISTRY}/supplier-management-service:\${BUILD_NUMBER}"
                        sh "docker push \${REGISTRY}/supplier-management-service:latest"
                    }
                }
                stage('Push Procurement Image') {
                    steps {
                        sh "docker login -u \${DOCKER_CREDENTIALS_USR} -p \${DOCKER_CREDENTIALS_PSW}"
                        sh "docker push \${REGISTRY}/procurement-service:\${BUILD_NUMBER}"
                        sh "docker push \${REGISTRY}/procurement-service:latest"
                    }
                }
                stage('Push Inventory Management Image') {
                    steps {
                        sh "docker login -u \${DOCKER_CREDENTIALS_USR} -p \${DOCKER_CREDENTIALS_PSW}"
                        sh "docker push \${REGISTRY}/inventory-management-service:\${BUILD_NUMBER}"
                        sh "docker push \${REGISTRY}/inventory-management-service:latest"
                    }
                }
                stage('Push Logistics & Distribution Image') {
                    steps {
                        sh "docker login -u \${DOCKER_CREDENTIALS_USR} -p \${DOCKER_CREDENTIALS_PSW}"
                        sh "docker push \${REGISTRY}/logistics-distribution-service:\${BUILD_NUMBER}"
                        sh "docker push \${REGISTRY}/logistics-distribution-service:latest"
                    }
                }
                stage('Push Quality Assurance Image') {
                    steps {
                        sh "docker login -u \${DOCKER_CREDENTIALS_USR} -p \${DOCKER_CREDENTIALS_PSW}"
                        sh "docker push \${REGISTRY}/quality-assurance-service:\${BUILD_NUMBER}"
                        sh "docker push \${REGISTRY}/quality-assurance-service:latest"
                    }
                }
                stage('Push Payment & Financial Image') {
                    steps {
                        sh "docker login -u \${DOCKER_CREDENTIALS_USR} -p \${DOCKER_CREDENTIALS_PSW}"
                        sh "docker push \${REGISTRY}/payment-financial-service:\${BUILD_NUMBER}"
                        sh "docker push \${REGISTRY}/payment-financial-service:latest"
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            when {
                branch 'main'
            }
            steps {
                script {
                    // Blue-Green Deployment Strategy
                    def blueDeploymentExists = sh(
                        script: "kubectl get deployment -n \${NAMESPACE} | grep -q 'api-gateway-blue'",
                        returnStatus: true
                    ) == 0

                    if (blueDeploymentExists) {
                        // Deploy to Green
                        sh """
                            kubectl set image deployment/api-gateway-green api-gateway-container=\${REGISTRY}/api-gateway:\${BUILD_NUMBER} -n \${NAMESPACE}
                            kubectl rollout status deployment/api-gateway-green -n \${NAMESPACE}

                            kubectl set image deployment/farmer-management-green farmer-management-container=\${REGISTRY}/farmer-management-service:\${BUILD_NUMBER} -n \${NAMESPACE}
                            kubectl rollout status deployment/farmer-management-green -n \${NAMESPACE}

                            kubectl set image deployment/supplier-management-green supplier-management-container=\${REGISTRY}/supplier-management-service:\${BUILD_NUMBER} -n \${NAMESPACE}
                            kubectl rollout status deployment/supplier-management-green -n \${NAMESPACE}

                            kubectl set image deployment/procurement-green procurement-container=\${REGISTRY}/procurement-service:\${BUILD_NUMBER} -n \${NAMESPACE}
                            kubectl rollout status deployment/procurement-green -n \${NAMESPACE}

                            kubectl set image deployment/inventory-management-green inventory-management-container=\${REGISTRY}/inventory-management-service:\${BUILD_NUMBER} -n \${NAMESPACE}
                            kubectl rollout status deployment/inventory-management-green -n \${NAMESPACE}

                            kubectl set image deployment/logistics-distribution-green logistics-distribution-container=\${REGISTRY}/logistics-distribution-service:\${BUILD_NUMBER} -n \${NAMESPACE}
                            kubectl rollout status deployment/logistics-distribution-green -n \${NAMESPACE}

                            kubectl set image deployment/quality-assurance-green quality-assurance-container=\${REGISTRY}/quality-assurance-service:\${BUILD_NUMBER} -n \${NAMESPACE}
                            kubectl rollout status deployment/quality-assurance-green -n \${NAMESPACE}

                            kubectl set image deployment/payment-financial-green payment-financial-container=\${REGISTRY}/payment-financial-service:\${BUILD_NUMBER} -n \${NAMESPACE}
                            kubectl rollout status deployment/payment-financial-green -n \${NAMESPACE}
                        """

                        // Switch traffic to Green
                        sh """
                            kubectl patch service/api-gateway -n \${NAMESPACE} -p '{"spec":{"selector":{"version":"green"}}}'
                            kubectl patch service/farmer-management-service -n \${NAMESPACE} -p '{"spec":{"selector":{"version":"green"}}}'
                            kubectl patch service/supplier-management-service -n \${NAMESPACE} -p '{"spec":{"selector":{"version":"green"}}}'
                            kubectl patch service/procurement-service -n \${NAMESPACE} -p '{"spec":{"selector":{"version":"green"}}}'
                            kubectl patch service/inventory-management-service -n \${NAMESPACE} -p '{"spec":{"selector":{"version":"green"}}}'
                            kubectl patch service/logistics-distribution-service -n \${NAMESPACE} -p '{"spec":{"selector":{"version":"green"}}}'
                            kubectl patch service/quality-assurance-service -n \${NAMESPACE} -p '{"spec":{"selector":{"version":"green"}}}'
                            kubectl patch service/payment-financial-service -n \${NAMESPACE} -p '{"spec":{"selector":{"version":"green"}}}'
                        """

                        // Scale down Blue deployments
                        sh """
                            kubectl scale deployment/api-gateway-blue --replicas=0 -n \${NAMESPACE}
                            kubectl scale deployment/farmer-management-blue --replicas=0 -n \${NAMESPACE}
                            kubectl scale deployment/supplier-management-blue --replicas=0 -n \${NAMESPACE}
                            kubectl scale deployment/procurement-blue --replicas=0 -n \${NAMESPACE}
                            kubectl scale deployment/inventory-management-blue --replicas=0 -n \${NAMESPACE}
                            kubectl scale deployment/logistics-distribution-blue --replicas=0 -n \${NAMESPACE}
                            kubectl scale deployment/quality-assurance-blue --replicas=0 -n \${NAMESPACE}
                            kubectl scale deployment/payment-financial-blue --replicas=0 -n \${NAMESPACE}
                        """
                    } else {
                        // Deploy to Blue (first deployment)
                        sh """
                            kubectl set image deployment/api-gateway-blue api-gateway-container=\${REGISTRY}/api-gateway:\${BUILD_NUMBER} -n \${NAMESPACE}
                            kubectl rollout status deployment/api-gateway-blue -n \${NAMESPACE}

                            kubectl set image deployment/farmer-management-blue farmer-management-container=\${REGISTRY}/farmer-management-service:\${BUILD_NUMBER} -n \${NAMESPACE}
                            kubectl rollout status deployment/farmer-management-blue -n \${NAMESPACE}

                            kubectl set image deployment/supplier-management-blue supplier-management-container=\${REGISTRY}/supplier-management-service:\${BUILD_NUMBER} -n \${NAMESPACE}
                            kubectl rollout status deployment/supplier-management-blue -n \${NAMESPACE}

                            kubectl set image deployment/procurement-blue procurement-container=\${REGISTRY}/procurement-service:\${BUILD_NUMBER} -n \${NAMESPACE}
                            kubectl rollout status deployment/procurement-blue -n \${NAMESPACE}

                            kubectl set image deployment/inventory-management-blue inventory-management-container=\${REGISTRY}/inventory-management-service:\${BUILD_NUMBER} -n \${NAMESPACE}
                            kubectl rollout status deployment/inventory-management-blue -n \${NAMESPACE}

                            kubectl set image deployment/logistics-distribution-blue logistics-distribution-container=\${REGISTRY}/logistics-distribution-service:\${BUILD_NUMBER} -n \${NAMESPACE}
                            kubectl rollout status deployment/logistics-distribution-blue -n \${NAMESPACE}

                            kubectl set image deployment/quality-assurance-blue quality-assurance-container=\${REGISTRY}/quality-assurance-service:\${BUILD_NUMBER} -n \${NAMESPACE}
                            kubectl rollout status deployment/quality-assurance-blue -n \${NAMESPACE}

                            kubectl set image deployment/payment-financial-blue payment-financial-container=\${REGISTRY}/payment-financial-service:\${BUILD_NUMBER} -n \${NAMESPACE}
                            kubectl rollout status deployment/payment-financial-blue -n \${NAMESPACE}
                        """

                        // Switch traffic to Blue
                        sh """
                            kubectl patch service/api-gateway -n \${NAMESPACE} -p '{"spec":{"selector":{"version":"blue"}}}'
                            kubectl patch service/farmer-management-service -n \${NAMESPACE} -p '{"spec":{"selector":{"version":"blue"}}}'
                            kubectl patch service/supplier-management-service -n \${NAMESPACE} -p '{"spec":{"selector":{"version":"blue"}}}'
                            kubectl patch service/procurement-service -n \${NAMESPACE} -p '{"spec":{"selector":{"version":"blue"}}}'
                            kubectl patch service/inventory-management-service -n \${NAMESPACE} -p '{"spec":{"selector":{"version":"blue"}}}'
                            kubectl patch service/logistics-distribution-service -n \${NAMESPACE} -p '{"spec":{"selector":{"version":"blue"}}}'
                            kubectl patch service/quality-assurance-service -n \${NAMESPACE} -p '{"spec":{"selector":{"version":"blue"}}}'
                            kubectl patch service/payment-financial-service -n \${NAMESPACE} -p '{"spec":{"selector":{"version":"blue"}}}'
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            // Cleanup temporary files
            sh 'docker system prune -f'
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}