import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyWebMagic implements PageProcessor {
    private static Set<Cookie> cookies;

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    private  SpiderInfo spiderInfo;
    public static SpiderInfo crateSpiderInfo() throws IOException {
        InputStream inputStream = new FileInputStream("E:\\myblog\\webmagic\\src\\main\\resources\\tempany\\config.json");
        String text = IOUtils.toString(inputStream,"utf8");
        SpiderInfo spiderInfo=  JSON.parseObject(text, SpiderInfo.class);
        return spiderInfo;
    }


    @Override
    public void process(Page page) {
        try {
            SpiderInfo spiderInfo = crateSpiderInfo();

            List<String> movieLinkList=new ArrayList<String>();
            //结果抽取
            Selectable moviePage;

            String movieLinkReg=spiderInfo.getLinkReg();
            Pattern movieLinkPattern= Pattern.compile(movieLinkReg);
            //写相应的xpath
            String movieLinkXpath=spiderInfo.getXpathStr();
            Selectable movieNameS;
            Selectable movieDownloadS;
        if(spiderInfo.getUrl().equals(page.getUrl().toString())){
                //抽取结果
                Html html = page.getHtml();
                moviePage= html.xpath(movieLinkXpath);
                //选中结果
                movieLinkList=moviePage.all();
                //循环遍历
                String movieLink="";
                Matcher movieLinkMatcher;
                for(int i=1;i<500;i++) {
                    //第一条过滤，从第二条开始遍历
                    String s = movieLinkList.get(i);
                    //正则匹配
                    movieLinkMatcher = movieLinkPattern.matcher(s);
                    if (movieLinkMatcher.find()) {//匹配子串
                        movieLink = movieLinkMatcher.group();//返回匹配到的字串
                        //将找到的链接放到ddTargetRequest里面，会自动发起请求
                        page.addTargetRequest(movieLink);
                        //输出到控制台
                        System.out.println(movieLink);
                    }
                }
            }
            movieLinkList.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void login()
    {
        System.setProperty("webdriver.chrome.driver", "E:\\develop\\chrome\\chromedriver.exe");
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
       /* site.addCookie("companyId","138259");
        site.addCookie("JSESSIONID","f80472f1-0228-4434-8f96-2e8f6db1f396");
        site.addCookie("yunId","3d3f2eca58b305181a8ccc87ad8a59bc");*/
        return site.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/22.0.1207.1 Safari/537.1");
    }



    public static void main(String[] args) throws IOException {
        MyWebMagic myWebMagic = new MyWebMagic();
        SpiderInfo spiderInfo = crateSpiderInfo();
        myWebMagic.login();
        Spider.create(new MyWebMagic()).addUrl(spiderInfo.getUrl()).thread(1).run();
//        		Spider.create(new MyWebMagic()).addUrl(spiderInfo.getUrl()).addPipeline(new ConsolePipeline()).run();
    /*    // 请求网络获取页面的源数据
        URL url = new URL("https://image.baidu.com/search/index?tn=baiduimage&ct=201326592&lm=-1&cl=2&ie=gb18030&word=%CD%BC%C6%AC&fr=ala&ala=1&alatpl=adress&pos=0&hs=2&xthttps=111111");
        // 获取数据的输入流对象
        InputStream is = url.openStream();
        // 包装成字符缓冲输入流
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        ArrayList<String> list = new ArrayList<String>();
        //  读取每一行数据
        while ((line = br.readLine()) != null) {
            // 正则校验：
            Pattern pattern = Pattern.compile("http.{10,100}\\.jpg");
            Matcher matcher = pattern.matcher(line);
            // 把匹配上正则的字符串，存储到集合中
            while (matcher.find()) {
                String str = matcher.group();
                list.add(str);
            }
        }
        br.close();*/

    }
}
