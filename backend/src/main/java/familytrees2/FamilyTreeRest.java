package familytrees2;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@RestController
public class FamilyTreeRest {

    private final SheetsProcessor sheetsProcessor;
    private final RestTemplate restTemplate;

    public FamilyTreeRest(SheetsProcessor sheetsProcessor, RestTemplate restTemplate) {
        this.sheetsProcessor = sheetsProcessor;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/family-tree-data")
    @Cacheable("familyTreeData")
    public String getFamilyTreeData() {
        return sheetsProcessor.getProcessedData();
    }

    @Scheduled(fixedRate = 30000) 
    public void updateFamilyTreeData() {
        try {
            System.out.println("Scheduled update started...");
            sheetsProcessor.processAndPrintFamilyForest();
            System.out.println("Family tree data updated.");
            String nodeJsEndpoint = "https://frontend-dot-familytreewebsite-411906.uk.r.appspot.com/update-family-tree-data";
            restTemplate.postForLocation(nodeJsEndpoint, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/notify-change")
    public ResponseEntity<String> notifyChange() {
        System.out.println("Sheet update detected.");
        try {
            sheetsProcessor.processAndPrintFamilyForest();
            String nodeJsEndpoint = "https://frontend-dot-familytreewebsite-411906.uk.r.appspot.com/update-family-tree-data";
            restTemplate.postForLocation(nodeJsEndpoint, null);
            return ResponseEntity.ok("Sheet update processed successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing sheet update");
        }
    }
}

@Configuration
class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
