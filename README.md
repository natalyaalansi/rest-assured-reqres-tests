# Test API automation project for [REQRES](https://reqres.in/)
<img alt="reqres logo" src="images/logo/reqres.png" />

## About REQRES
Reqres.in is a resource that provides a public API that you can use to build and test your applications.

## Contents
- <a href="#technologies">Tools and technologies</a>
- <a href="#testcases">Executed automation test cases</a>
- <a href="#running">Running Autotests</a>
- <a href="#report">Allure Report</a>
- <a href="#testops">Allure TestOps integration</a>
- <a href="#jira">Jira integration</a>
- <a href="#telegram">Telegram notifications via bot</a>

<a id="technologies"></a>
## Tools and technologies
<p  align="center">

<code><img width="5%" title="IntelliJ IDEA" src="images/logo/Idea.svg"></code>
<code><img width="5%" title="Java" src="images/logo/Java.svg"></code>
<code><img width="5%" title="Junit5" src="images/logo/Junit5.svg"></code>
<code><img width="5%" title="Gradle" src="images/logo/Gradle.svg"></code>
<code><img width="5%" title="REST Assured" src="images/logo/Ra.png"></code>
<code><img width="5%" title="GitHub" src="images/logo/GitHub.svg"></code>
<code><img width="5%" title="Jenkins" src="images/logo/Jenkins_logo.svg"></code>
<code><img width="5%" title="Allure Report" src="images/logo/Allure.svg"></code>
<code><img width="5%" title="Allure TestOps" src="images/logo/Allure_TO.svg"></code>
<code><img width="5%" title="Jira" src="images/logo/Jira.svg"></code>
<code><img width="5%" title="Telegram" src="images/logo/Telegram.svg"></code>
</p>


<a id="testcases"></a>
## Executed automation test cases
- Successful login
- Unsuccessful login with a missing Content Type
- Unsuccessful login with a missing password
- Users list is displayed
- A single user is displayed

<a id="running"></a>
## Running Autotests

### Local run
```
gradle clean test
```

### Jenkins run
```
clean test
```

<a id="report"></a>
## <img alt="Allure Reports" src="images/logo/Allure.svg" width="40" height="40"/> Allure Report
<img title="Allure Overview" src="images/attachment/allure_overview.png"> 
<img title="Allure Suites" src="images/attachment/allure_suites.png"> 

<a id="testops"></a>
## <img alt="Allure TestOps" src="images/logo/Allure_TO.svg" width="40" height="40"/> Allure TestOps integration
<img title="TestOps Results" src="images/attachment/testops_launches.png"> 
<img title="TestOps Suites" src="images/attachment/testops_tc.png"> 

<a id="jira"></a>
## <img alt="Jira" src="images/logo/Jira.svg" width="40" height="40"/> Jira integration
<img title="Jira integration" src="images/attachment/jira.png"> 

<a id="telegram"></a>
## <img alt="Telegram" src="images/logo/Telegram.svg" width="40" height="40"/>Telegram notifications via bot 
<img title="Telegram notifications via bot" src="images/attachment/tg.png">  
