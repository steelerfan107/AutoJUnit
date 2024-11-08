# AutoJUnit
LLM Agent which can generate effective JUnit test suites. 

##To Run on Google Collab

1) Download Java/MVN

```
!wget https://download.java.net/java/GA/jdk13.0.1/cec27d702aa74d5a8630c65ae61e4305/9/GPL/openjdk-13.0.1_linux-x64_bin.tar.gz
!tar -xvf openjdk-13.0.1_linux-x64_bin.tar.gz
!mv jdk-13.0.1 /opt/
```

```
!JAVA_HOME='/opt/jdk-13.0.1'
!PATH="$JAVA_HOME/bin:$PATH"
!export PATH
```

```
!wget https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz
!tar -xvf apache-maven-3.9.9-bin.tar.gz
!mv apache-maven-3.9.9 /opt/
```

```
!M2_HOME='/opt/apache-maven-3.9.9'
!PATH="$M2_HOME/bin:$PATH"
!export PATH
```

2) Pull the Git Repo

```
!git clone https://github.com/steelerfan107/AutoJUnit.git
```

3) Modify Params in main.py

```
MVN_LOC = "/opt/apache-maven-3.9.9/bin/mvn"
JAVA_HOME = "/opt/jdk-13.0.1/"
```

4) In Temrinal

```
cd AutoJUnit
python main.py
```
