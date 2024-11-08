# AutoJUnit
LLM Agent which can generate effective JUnit test suites. 

To Run on Google Collab

1) Download Java

```
import os       #importing os to set environment variable
def install_java():
  !apt update
  !apt-get install -y openjdk-8-jdk-headless -qq > /dev/null      #install openjdk
  os.environ["JAVA_HOME"] = "/usr/lib/jvm/java-8-openjdk-amd64"     #set environment variable
  !java -version       #check java version
install_java()
```

2) Pull the Git Repo

```
!git clone https://github.com/steelerfan107/AutoJUnit.git
```
