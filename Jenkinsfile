pipeline {
        agent any
        
        tools {
            maven "MavenLocal"
        }
        
        environment {
            PRISMA_API_URL="https://api.prismacloud.io"
        }
        
        stages {
            stage('Checkout') {
              steps {
                  git branch: 'master', url: 'https://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@github.com/hunkOraboni/gemini_teste_code'
                  stash includes: '**/*', name: 'gemini_teste_code'
              }
            }
            stage('Checkov SAST') {
                steps {
                    withCredentials([string(credentialsId: 'PC_USER', variable: 'pc_user'),string(credentialsId: 'PC_PASSWORD', variable: 'pc_password')]) {
                        script {
                            docker.image('bridgecrew/checkov:latest').inside("--entrypoint=''") {
                              unstash 'gemini_teste_code'
                              try {
                                  //sh 'checkov --version'
                                  //sh 'checkov --help'
                                  //sh 'ls'
                                  //sh 'pwd'
                                  sh 'python --version'
                                  sh 'pip --version'
                                  sh 'pip install --upgrade requests'
                                  //sh 'checkov -d . --use-enforcement-rules -o cli -o junitxml --framework dockerfile --output-file-path console,results.xml --bc-api-key ${pc_user}::${pc_password} --repo-id  hunkOraboni/teste_esteira --branch master'
                                  //sh 'checkov -d . --use-enforcement-rules -o cli -o junitxml --framework dockerfile  --framework secrets --framework github_actions --output-file-path console,results.xml --bc-api-key ${pc_user}::${pc_password} --repo-id  hunkOraboni/teste_esteira --branch master'
                                  sh 'checkov -d . --use-enforcement-rules -o cli -o junitxml --framework sast --output-file-path console,results.xml --bc-api-key ${pc_user}::${pc_password} --repo-id  hunkOraboni/gemini_teste_code --branch master'
                                  //--framework dockerfile
                                  //sh 'checkov -d . --use-enforcement-rules -o cli -o junitxml --output-file-path console,results.xml --bc-api-key ${pc_user}::${pc_password} --repo-id  hunkOraboni/teste_esteira --branch master'
                                  //junit skipPublishingChecks: true, testResults: 'results.xml'
                              } catch (err) {
                                  //junit skipPublishingChecks: true, testResults: 'results.xml'
                                  //throw err
                              }
                            }
                        }
                    }
                }
            }
            stage('Checkov Dockerfile') {
                steps {
                    withCredentials([string(credentialsId: 'PC_USER', variable: 'pc_user'),string(credentialsId: 'PC_PASSWORD', variable: 'pc_password')]) {
                        script {
                            docker.image('bridgecrew/checkov:latest').inside("--entrypoint=''") {
                              unstash 'gemini_teste_code'
                              try {
                                  //sh 'checkov --version'
                                  //sh 'checkov --help'
                                  //sh 'ls'
                                  //sh 'pwd'
                                  sh 'python --version'
                                  sh 'pip --version'
                                  sh 'pip install --upgrade requests'
                                  //sh 'checkov -d . --use-enforcement-rules -o cli -o junitxml --framework dockerfile --output-file-path console,results.xml --bc-api-key ${pc_user}::${pc_password} --repo-id  hunkOraboni/teste_esteira --branch master'
                                  //sh 'checkov -d . --use-enforcement-rules -o cli -o junitxml --framework dockerfile  --framework secrets --framework github_actions --output-file-path console,results.xml --bc-api-key ${pc_user}::${pc_password} --repo-id  hunkOraboni/teste_esteira --branch master'
                                  sh 'checkov -d . --use-enforcement-rules -o cli -o junitxml --framework all --output-file-path console,results.xml --bc-api-key ${pc_user}::${pc_password} --repo-id  hunkOraboni/gemini_teste_code --branch master'
                                  //--framework dockerfile
                                  //sh 'checkov -d . --use-enforcement-rules -o cli -o junitxml --output-file-path console,results.xml --bc-api-key ${pc_user}::${pc_password} --repo-id  hunkOraboni/teste_esteira --branch master'
                                  //junit skipPublishingChecks: true, testResults: 'results.xml'
                              } catch (err) {
                                  //junit skipPublishingChecks: true, testResults: 'results.xml'
                                  //throw err
                              }
                            }
                        }
                    }
                }
            }
            stage('Build App') {
                steps {
                    unstash 'gemini_teste_code'
                    sh 'java --version'
                    sh 'mvn --version'
                    sh 'mvn clean install -DskipTests'
                    stash includes: '**/*', name: 'gemini_teste_code'
                }
            }
            stage('SonarQube analysis') {
                steps {
                    unstash 'gemini_teste_code'
                    script{
                        def scannerHome = tool 'sonar_scanner';
                        //withSonarQubeEnv('sonarqube') {
                            sh "${tool("sonar_scanner")}/bin/sonar-scanner -h"
                            sh "ls target"
                            sh "${tool("sonar_scanner")}/bin/sonar-scanner -Dproject.settings=sonar-project.properties"
                        //}
                    }
                    stash includes: '**/*', name: 'gemini_teste_code'
                }
            }
            stage('Build Docker') {
                steps {
                    unstash 'gemini_teste_code'
                    sh 'docker build . --file Dockerfile --tag gemini_teste_code_demo_image'
                    stash includes: '**/*', name: 'gemini_teste_code'
                }
            }
            stage('Checkov Docker Image') {
                steps {
                    withCredentials([string(credentialsId: 'PC_USER', variable: 'pc_user'),string(credentialsId: 'PC_PASSWORD', variable: 'pc_password')]) {
                        script {
                            docker.image('bridgecrew/checkov:latest').inside("--entrypoint=''") {
                              unstash 'gemini_teste_code'
                              try {
                                  //sh 'checkov --version'
                                  //sh 'checkov --help'
                                  //sh 'ls'
                                  //sh 'pwd'
                                  sh 'python --version'
                                  sh 'pip --version'
                                  sh 'pip install --upgrade requests'
                                  //sh 'checkov -d . --use-enforcement-rules -o cli -o junitxml --framework dockerfile --output-file-path console,results.xml --bc-api-key ${pc_user}::${pc_password} --repo-id  hunkOraboni/teste_esteira --branch master'
                                  //sh 'checkov -d . --use-enforcement-rules -o cli -o junitxml --framework dockerfile  --framework secrets --framework github_actions --output-file-path console,results.xml --bc-api-key ${pc_user}::${pc_password} --repo-id  hunkOraboni/teste_esteira --branch master'
                                  sh 'checkov -d . --use-enforcement-rules --image gemini_teste_code_demo_image -o cli -o junitxml --framework all --output-file-path console,results.xml --bc-api-key ${pc_user}::${pc_password} --repo-id  hunkOraboni/gemini_teste_code --branch master'
                                  //--framework dockerfile
                                  //sh 'checkov -d . --use-enforcement-rules -o cli -o junitxml --output-file-path console,results.xml --bc-api-key ${pc_user}::${pc_password} --repo-id  hunkOraboni/teste_esteira --branch master'
                                  //junit skipPublishingChecks: true, testResults: 'results.xml'
                              } catch (err) {
                                  //junit skipPublishingChecks: true, testResults: 'results.xml'
                                  //throw err
                              }
                            }
                        }
                    }
                }
            }
            stage('TwistCLI') {
                steps{
                    withCredentials([string(credentialsId: 'PC_USER', variable: 'pc_user'),string(credentialsId: 'PC_PASSWORD', variable: 'pc_password')]) {
                        //sh 'ls -la /certs'
                        //sh 'ls -la /certs/client'
                        sh 'curl --user ${pc_user}:${pc_password} --output ./twistcli https://us-east1.cloud.twistlock.com/us-1-113034237/api/v1/util/twistcli'
                        sh 'chmod a+x ./twistcli'
                        sh 'echo Running Image Scan'
                        sh './twistcli images scan --docker-address https://docker:2376 --docker-tlskey /certs/client/key.pem --docker-tlscert /certs/client/cert.pem --docker-tlscacert /certs/client/ca.pem --address https://us-east1.cloud.twistlock.com/us-1-113034237 --user ${pc_user} --password ${pc_password} --details gemini_teste_code_demo_image'
                        sh 'echo Finished Image Scan'
                        //sh 'echo Running Sandbox Scan'
                        //sh './twistcli sandbox --docker-address https://docker:2376 --docker-tlskey /certs/client/key.pem --docker-tlscert /certs/client/cert.pem --docker-tlscacert /certs/client/ca.pem --address https://us-east1.cloud.twistlock.com/us-1-113034237 -u ${pc_user} -p ${pc_password} --analysis-duration 2m teste_esteira_demo_image'
                        //sh 'echo Finished Sandbox Scan'
                    }
                }
            }
        }
        options {
            preserveStashes()
            timestamps()
        }
    }