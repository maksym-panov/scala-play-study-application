#!/bin/bash

echo "🔍 Checking Java Version..."
java --version
echo $JAVA_HOME
echo $GITHUB_WORKSPACE

echo "📂 Listing coverage report directory..."
ls -lah ../../target/scala-3.3.5/scoverage-report/

echo "🛠 Fixing paths in scoverage.xml..."
sed -i 's|${GITHUB_WORKSPACE}|/github/workspace|g' ../../target/scala-3.3.5/scoverage-report/scoverage.xml

echo "🚀 Installing SonarScanner..."
sudo apt-get update && sudo apt install -y unzip wget && \
wget -O sonar-scanner.zip https://binaries.sonarsource.com/Distribution/sonar-scanner-cli/sonar-scanner-cli-5.0.1.3006-linux.zip && \
sudo unzip sonar-scanner.zip -d /opt && \
  rm sonar-scanner.zip && \
  sudo ln -s /opt/sonar-scanner-5.0.1.3006-linux/bin/sonar-scanner /usr/local/bin/sonar-scanner && \
  echo "export PATH=/opt/sonar-scanner-5.0.1.3006-linux/bin:\$PATH" >> ~/.bashrc && \
  source ~/.bashrc

echo "✅ SonarScanner installation complete!"