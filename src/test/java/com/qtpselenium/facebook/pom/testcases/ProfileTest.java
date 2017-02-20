package com.qtpselenium.facebook.pom.testcases;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.qtpselenium.facebook.pom.pages.LaunchPage;
import com.qtpselenium.facebook.pom.pages.LoginPage;
import com.qtpselenium.facebook.pom.pages.session.LandingPage;
import com.qtpselenium.facebook.pom.pages.session.ProfilePage;
import com.qtpselenium.facebook.pom.testcases.base.BaseTest;
import com.qtpselenium.facebook.pom.util.DataUtil;
import com.qtpselenium.facebook.pom.util.ExtentManager;
import com.qtpselenium.facebook.pom.util.FBConstants;
import com.relevantcodes.extentreports.LogStatus;



public class ProfileTest extends BaseTest {
	
	String testCaseName="ProfileTest";
	
	@Test
	public void testProfile(){
		
		test=extent.startTest("Profile Test");
		if(!DataUtil.isTestExecutable(xls, testCaseName)){
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}
				
		test.log(LogStatus.INFO, "Starting profile test");
		init("Mozilla");
		LaunchPage launchPage = new LaunchPage(driver,test);
		PageFactory.initElements(driver, launchPage);
		
		LoginPage loginPage = launchPage.gotoLoginPage();		
		loginPage.verityTitle("Facebook Login");
		Object page = loginPage.doLogin(FBConstants.getEnvDetails().get("username"), FBConstants.getEnvDetails().get("password"));
		
		if(page instanceof LoginPage)
			Assert.fail("Login Failed");
		else if(page instanceof LandingPage)
			System.out.println("Logged in Successfully");
			
		LandingPage landingPage = (LandingPage)page;
		//landingPage.getMenu().search();
		//landingPage.verityTitle("xxxx");
		
				
		ProfilePage profPage = landingPage.gotoProfilePage();
		profPage.verifyProfile();
		test.log(LogStatus.PASS, "Test Passed");
		
		//profPage.getMenu().logOut();
		profPage.takeScreenShot();
		
	}
	
	@AfterMethod
	public void quit(){
		if(extent!=null){
			extent.endTest(test);
			extent.flush();
		}
		if(driver!=null)
			driver.quit();
	}

}
