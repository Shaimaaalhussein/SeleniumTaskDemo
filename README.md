# Floward demo task
***
This is an automation script for test scenarios in floward website

## Framework
***
I am using Page object model as a design pattern and Extent report as reporting.

## Technologies
***
A list of technologies used within the project:
* [Java]: Version 18.0.1.1
* [Maven]: Version 3.8.6
* [Selenium]: Version 4.3.0
* [TestNG]: Version 7.6.1
* [webdrivermanager]: Version 5.2.2
* [Extent report]: Version 3.1.5
* [Chrome]: Version 103.0.5060.134

Follow the instructions in this README to install software requirements and run the project.

# Setup Instructions
* Download maven from https://maven.apache.org/download.cgi.
* Set MAVEN_HOME to your enviroment variables.
* Download JAVA from https://www.java.com/download/ie_manual.jsp.
* Set JAVA_HOME to your enviroment variables.
* Add MAVEN_HOME & JAVA_HOME to path in enviroment variables.
* You will also need [Git](https://git-scm.com/) to copy this project code.
If you are new to Git, [try learning the basics](https://try.github.io/).


## Project Setup and execution

1. Clone this repository.
2. Run all the previous installation to install the dependencies. 
3. Open command promot then Run `cd {project path}' to enter the project.
4. When enter project run "mvn test -Dtestng=testng".
5. Result will be at this path "{Project path}\reports".
6. Screenshot saved at this path "{Project path}\screenshots".

## Repository Branching

* The `main` branch contains the code for the project's starting point.
* The project is basically empty in the `main` branch.

* If you want to code along with the course, then create a branch for your work off the `main` branch.
To create your own branch named `project/develop`, run:

    * > git checkout main
    * > git branch project/develop
    * > git checkout project/develop


* The `tests/*` contain the testcase code for project parts.

* `tests/FlowardTest.java`.

## Create configuration file

Create a new file named "Configuration.properties" at this path "{project path}\configuration" 
and add the following configuration as following:

* browserType=chrome
* URL=https://floward.com/
* reportfilePath=\\reports\\
* screenshotfilePath=\\screenshots\\

## Read from configurations files

you can read from configuration file using "ConfigFileReader.java" class.

## Defining Page Objects

*Example Branch: pageClasses/homePage.java*

A **page object** is an object representing a Web page or component.
They have *locators* for finding elements,
as well as *interaction methods* that interact with the page under test.
Page objects make low-level Selenium WebDriver calls
so that tests can make short, readable calls instead of complex ones.

Since we have our test steps, we know what pages and elements our test needs.
There are 1 page under pageclasses and 1 base page for common interaction like click or scroll 
each with a few interactions:

1. The base page
    * make action on element by passing locators for web
2. The home page page
    * Get the locators of floward pages and interactions

## Extent report
*Extent report manager is resposible for naming and appending on the current report.

public static ExtentReports getInstance() {

		
		String FileName = "Release"+".html";
		String directory = configFileReader.getReportFilePath();
		new File(directory).mkdirs();
		String FullPath = directory + FileName;
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(FullPath);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setDocumentTitle("Automation Reports");
		htmlReporter.config().setReportName("Automation Test Results");
		htmlReporter.config().setTheme(Theme.STANDARD);
		extent = new ExtentReports();
		
		htmlReporter.setAppendExisting(false);
		
		extent.attachReporter(htmlReporter);
		

		return extent;
	}

* Handling test result and screenshot in TestListeners

	public void onTestFailure(ITestResult result) {
			// TODO Auto-generated method stub
			
			
			WebDriver driver = ((TestBase)result.getInstance()).driver;
			
			String path = CaptureScreenshot.takeScreenshot(driver, result.getMethod().getMethodName());
			//log test result in report
                       extentTest.get().log(Status.FAIL, "Test Case Failed is " + result.getMethod().getMethodName());
			extentTest.get().log(Status.FAIL, "Test Case Failed is " + result.getThrowable());
			
			try {
                        //log screen shot in report
				extentTest.get().addScreenCaptureFromPath(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//create Jira ticket
			//createJiraTkt(result);
			}

## Create Jira ticket on failure
* Creating annotation to be added to testcase as "JiraCreateIssue.java".

# code

@Retention(RetentionPolicy.RUNTIME)

 public @interface JiraCreateIssue {
       boolean isCreateIssue();
 }
* "JiraServiceProvider.java" class responsible for search if issue exist or create a new one if not.

# Constructor 
public JiraServiceProvider(String JiraUrl, String username, String password, String project) {

         this.JiraUrl=JiraUrl;

         // create basic authentication object

         BasicCredentials creds = new BasicCredentials(username, password);

         // initialize the Jira client with the url and the credentials

         Jira = new JiraClient(JiraUrl, creds);

         this.project = project;

     }
# create ticket function

public void createJiraIssue(String issueType, String summary, String description, String reporterName) {

        try {

            //Avoid Creating Duplicate Issue

            Issue.SearchResult sr = Jira.searchIssues("summary ~ \""+summary+"\"");

            if(sr.total!=0) {

                System.out.println("Same Issue Already Exists on Jira");

                return;
            }
            //Create issue if not exists

            FluentCreate fleuntCreate = Jira.createIssue(project, issueType);

            fleuntCreate.field(Field.SUMMARY, summary);

            fleuntCreate.field(Field.DESCRIPTION, description);

            Issue newIssue = fleuntCreate.execute();

            System.out.println("********************************************");

            System.out.println("New issue created in Jira with ID: " + newIssue);

            System.out.println("New issue URL is :"+JiraUrl+"/browse/"+newIssue);

            System.out.println("*******************************************");

        } catch (JiraException e) {

            e.printStackTrace();
        }
    }
*At testlistener adding Jira URL, username and API token.
# Calling create ticket by passing variables


private void createJiraTkt(ITestResult result) {
	
      boolean islogIssue = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(JiraCreateIssue.class).isCreateIssue();
	       
           if (islogIssue) {
	              //Provide proper Jira project URL ex:https://shaimaa.atlassian.net
	              //Jira User name ex: shaimaaelhosen1@gmail.com
	              //API token copy from Jira dashboard ex: SlQyRlynkFugLDupJsdz7F11
	              //Project key should be, Short name ex: SWJ
	              JiraServiceProvider JiraServiceProvider = new JiraServiceProvider("https://shaimaa.atlassian.net", "shaimaaelhosen1@gmail.com", "SlQyRlynkFugLDupJsdz7F11", "SWJ");
                      String issueDescription = "Failure Reason from Automation Testing\n\n" + result.getThrowable().getMessage()
	                    + "\n";
	              issueDescription.concat(ExceptionUtils.getFullStackTrace(result.getThrowable()));
                      String issueSummary = result.getMethod().getConstructorOrMethod().getMethod().getName()
	                    + " Failed in Automation Testing";
	              JiraServiceProvider.createJiraIssue("Bug", issueSummary, issueDescription, "Automated Testing");
	                      }
	}

## Run with jenkins

* Open jenkins
* Create a new pipline job
* In pipline section :

      1.Change definition to be "Pipline script from SCM".
      2.Choose SCM to be "Git".
      3.Put Repository URL "https://github.com/Shaimaaalhussein/SeleniumTaskDemo.git"
      4.Add in Script Path "jenkinsFile"

## Jenkins file pipeline

* Jenkins file will be as the following:

pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "MAVEN_HOME"
    }

    stages {
        stage('checkout') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/Shaimaaalhussein/SeleniumTaskDemo.git'

            }
            }
        stage('Build') {
            steps {
             
   
                // To run Maven on a Windows agent, use
                 bat "mvn test -Dtestng=testng"
            }
           }
         
      stage('result') {
                
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                
           steps {
                   
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'

                }
            }
        
    }
}