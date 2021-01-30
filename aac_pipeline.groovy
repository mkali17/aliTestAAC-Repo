// Declarative pipeline - mka
pipeline {
  agent any
  tools
    {
       maven "Maven"
    }
  stages {
     /* stage('checkout_application'){ 
        steps {
          git branch: 'main', url: 'https://github.com/ahossain71/trainingApp.git'
          }
      }
    */
      stage('Tools Init') {
        steps {
            script {
                echo "PATH = ${PATH}"
                echo "M2_HOME = ${M2_HOME}"
            def tfHome = tool name: 'Ansible'
            env.PATH = "${tfHome}:${env.PATH}"
              sh 'ansible --version'
            }
        }
      }

    stage('Execute Maven') {
            steps {
              sh 'mvn package'             
          }
        }
    stage('Ansible Deploy') {
        steps{
             withCredentials([sshUserPrivateKey(credentialsId: '5ef2c402-fa06-4da8-9ba8-83c761328306', keyFileVariable: 'myKey')]) {
               // sh 'ansible-playbook ./ansible/playbooks/deploy_trainingApp.yml --user ubuntu --key-file ${myKEY}'  
            }//end withCredentials
      }//end steps
    }//end stage
  }// end stages
}//end pipeline