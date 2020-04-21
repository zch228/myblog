
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @author 咸鱼
 * @date 2018/12/28 20:08
 */
public class GithubRepoPageProcessor implements PageProcessor {

    /**
     *  为防止该页面请请求失败
     * 	  setRetryTimes(3)设置该页面请求次数
     * 	  setSleepTime(3000)设置请求次数间隔时间
     */
    private Site site = Site.me().setCycleRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    @Override
    public void process(Page page) {
        /**
         * 抓取匹配符合该正则表达式的URL加入请求队列中
         * .all()--返回的是个集合
         */
        List<String> all = page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all();
        page.addTargetRequests(all);

        /**
         * 抽取结果元素
         */
        page.putField("author", page.getUrl().regex("\"https://github\\.com/(\\w+)/.*\"").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name") == null){
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
    }

    @Override
    public Site getSite() {
        return site;
    }
    public static void main(String[] args) {
        /**
         * Spider入口
         * addUrl--添加爬取的地址
         * thread(5).run()--5个线程去抓取
         */
        Spider.create(new GithubRepoPageProcessor())
                .addUrl("https://github.com/code4craft")
                //覆盖默认的实现 HttpClientDownloader，因为使用webmagic爬取 https 网站时，会
                .setDownloader(new HttpClientDownloader()).addPipeline(new ConsolePipeline())
                .thread(5)
                .run();
    }
}

