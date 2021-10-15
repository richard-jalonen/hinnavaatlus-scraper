package ee.richja.backend.service;

import ee.richja.backend.model.ScrapeResult;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ScrapeService {

    public List<ScrapeResult> scrape(String scrapeQuery) {
        String URL_PREFIX = "https://foorum.hinnavaatlus.ee/";
        List<ScrapeResult> results = new ArrayList<>();

        Document firstPagePCParts = null;
        Document secondPagePCParts = null;

        Document firstPageConsoles = null;
        //Document secondPageConsoles = null;

        Document firstPageVarious = null;
        Document secondPageVarious = null;

        // PC parts forum
        try {
            firstPagePCParts = Jsoup.connect(URL_PREFIX + "viewforum.php?f=3").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements topics = firstPagePCParts.select("a.topictitle");

        try {
            secondPagePCParts = Jsoup.connect(URL_PREFIX + "viewforum.php?f=3&topicdays=0&start=25").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        topics.addAll(secondPagePCParts.select("a.topictitle"));

        // Console & games forum
        try {
            firstPageConsoles = Jsoup.connect(URL_PREFIX + "viewforum.php?f=81").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        topics.addAll(firstPageConsoles.select("a.topictitle"));

        // Various other stuff forums
        try {
            firstPageVarious = Jsoup.connect(URL_PREFIX + "viewforum.php?f=70").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        topics.addAll(firstPageVarious.select("a.topictitle"));

        try {
            secondPageVarious = Jsoup.connect(URL_PREFIX + "viewforum.php?f=70&topicdays=0&start=25").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        topics.addAll(secondPageVarious.select("a.topictitle"));

        for (Element topic : topics) {
            if (org.apache.commons.lang3.StringUtils.containsIgnoreCase(topic.text(), scrapeQuery) && !topic.text().contains("reeglid")) {
                ScrapeResult scrapeResult = new ScrapeResult();
                scrapeResult.setTopic(topic.text());
                scrapeResult.setTopicUrl(URL_PREFIX + topic.attr("href"));
                results.add(scrapeResult);
                //log.info(topic.text());
                //log.info(URL_PREFIX + topic.attr("href"));
            }
        }

        return results;
    }
}
