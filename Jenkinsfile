node('docker') {
	def app
	environment {
		DOCKER_CREDS = credentials('docker-hub')
	}
	stage('Clone repository on slave') {
		/* Let's make sure we have the repository cloned to our workspace */
	checkout scm
	}

	stage('Gradle Build') {
	sh './gradlew --stop'
	sh './gradlew build --verbose --status'
	}

	stage('Gradle Tests') {
	sh './gradlew test'
	}	

	stage('Build image') {
		/* This builds the actual image; synonymous to
		* docker build on the command line */
	app = docker.build("iceberg00/hp-docker-capstone-workera")
	}

	stage('Test image') {

	}
	stage('Push image'){
		docker.withRegistry('https://registry.hub.docker.com', 'docker-hub') {
				app.push("${env.BUILD_NUMBER}")
				app.push("latest")
		}
	}
}
