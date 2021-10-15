package ee.richja.backend.controller;

import ee.richja.backend.model.ScrapeResult;
import ee.richja.backend.service.ScrapeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequiredArgsConstructor
public class ScrapeController {
    private final ScrapeService scrapeService;

    @GetMapping("/scrape")
    public List<ScrapeResult> getScrapeResults(@RequestParam String scrapeQuery) {
        log.info("called");
        return scrapeService.scrape(scrapeQuery);
    }
}
