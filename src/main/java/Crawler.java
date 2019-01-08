import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Crawler {
    private static final String url = "https://music.163.com/#/discover/playlist/?order=hot&cat=%E5%85%A8%E9%83%A8&limit=35&offset=0";
    private static final String BASE_URL = "https://music.163.com/";
    public static void main(String[] args) {

    }

    public static List<WebPage> parsePlaylists(String url) {
        List<WebPage> res = null;
        Document document = Jsoup.parse(fetch(url));
        new WebPage(BASE_URL, WebPage.PageType.playlist);
        Elements playlists = document.select(".tit.f-thide.s-fc0");
        res = playlists.stream().map(
                e -> new WebPage(BASE_URL + e.attr("href"), WebPage.PageType.playlist)).
                collect(Collectors.toList());
    }

    public static Long parseSong(String url) {
        try {
            return getCommentCount(url.split("=")[1]);
        } catch (Exception e)  {
            return 0L;
        }
    }

    public static String fetch(String url) {
        try {
            Connection.Response response = Jsoup.connect(url).timeout(3000)
                    .header("Host", "http://music.163.com")
                    .header("User-Agent", "  Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0")
                    .header("Accept", "  text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Accept-Language", "zh-cn,zh;q=0.5")
                    .header("Accept-Charset", "  GB2312,utf-8;q=0.7,*;q=0.7")
                    .header("Connection", "keep-alive")
                    .execute();

            return response.statusCode() / 100 == 2 ? response.body() : null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }
}
