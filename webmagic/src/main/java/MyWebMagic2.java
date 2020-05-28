package org.will.WebMagic;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class MyWebMagic2 implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(0).setTimeOut(3000);

    //用来存储cookie信息
    private Set<Cookie> cookies;

    @Override
    public void process(Page page) {

        //获取用户的id
        page.putField("", page.getHtml().xpath(""));

        //获取用户的详细信息
        List<String> information = page.getHtml().xpath("//ul[@class='member_info_list fn-clear']//li/div[@class='fl pr']/em/text()").all();
        page.putField("information = ", information);

    }

    //使用 selenium 来模拟用户的登录获取cookie信息
    public void login()
    {
        WebDriver driver = new ChromeDriver();
        driver.get("http://test.live.cunwedu.com.cn/login");

        driver.findElement(By.id("username")).clear();

        //在******中填你的用户名
        driver.findElement(By.id("username")).sendKeys("admin1");

        driver.findElement(By.id("password")).clear();
        //在*******填你密码
        driver.findElement(By.id("password")).sendKeys("123456");

        //模拟点击登录按钮
        driver.findElement(By.id("submit")).click();

        //获取cookie信息
        cookies = driver.manage().getCookies();
        driver.close();
    }
    @Override
    public Site getSite() {

        //将获取到的cookie信息添加到webmagic中
        for (Cookie cookie : cookies) {
            site.addCookie(cookie.getName().toString(),cookie.getValue().toString());
        }

        return site.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/22.0.1207.1 Safari/537.1");
    }

    public static void main(String[] args){
        MyWebMagic2 miai = new MyWebMagic2();

        //调用selenium，进行模拟登录
        miai.login();
        Spider.create(miai)
                .addUrl("http://test.live.cunwedu.com.cn/student/studentList")
                .run();
    }
}
