public class SpiderInfo {
    private String Url;
//    要抓取的详情正则
    private String LinkReg;
//    xPath详情
    private String XpathStr;

    public SpiderInfo() {
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getLinkReg() {
        return LinkReg;
    }

    public void setLinkReg(String linkReg) {
        LinkReg = linkReg;
    }

    public String getXpathStr() {
        return XpathStr;
    }

    public void setXpathStr(String xpathStr) {
        XpathStr = xpathStr;
    }

    @Override
    public String toString() {
        return "SpiderInfo{" +
                "Url='" + Url + '\'' +
                ", LinkReg='" + LinkReg + '\'' +
                ", XpathStr='" + XpathStr + '\'' +
                '}';
    }
}
