package SampleClass;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CheckViewPort {
	static WebDriver driver;
	
	public static void main(String[] args){
		driver = new FirefoxDriver();
		driver.get("http://www.snapdeal.com/");
		driver.findElement(By.xpath("//div[@id='shoppingFeedHook']/div[2]")).click();
		CheckViewPort checkVP_obj = new CheckViewPort();
		System.out.println(checkVP_obj.checkElementVisibilityInViewPort("smart-feed-content", driver));
	}

	public boolean checkElementVisibilityInViewPort(String elementID, WebDriver driver){
		String loadJQuery = "$.fn.inView = function(options){" +
			                "var topOff = 0," +
			                "                bottomOff = 0," +
			                "                callback = null," +
			                "                win = $(window)," +
			                "obj = $(this)," +
			                "scrollPosition = win.scrollTop()," +
			                "visibleArea = win.scrollTop() + win.height()," +
			                "objEndPos = (obj.offset().top + obj.outerHeight())," +
			                "                stat," +
			                "                pos;" +
			                "if(options){" +
			                "                topOff = options.topOff || 0;" +
			                "                bottomOff = options.bottomOff || 0;" +
			                "                callback = options.callback || null;" +
			                "}" +
			                "if(scrollPosition > objEndPos+bottomOff){pos = 'top';}" +
			                "else if(visibleArea < objEndPos-topOff){pos = 'bottom';}" +
			                "else{pos = 'inside';}" +
			                "stat = (visibleArea >= objEndPos-topOff && scrollPosition <= objEndPos+bottomOff) ? true : false;" +
			                "if(callback && stat){callback.call($(this))}" +
			                "return {status:stat,position:pos};" +
							"};";

		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
		js.executeScript(loadJQuery);
		String result = js.executeScript("return $('#"+elementID+"').inView();").toString();
		if (result.contains("status=true")) {
			return true;
		} else {
			return false;
		}
	}
}