package familytrees2;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;

@SpringBootApplication
public class SheetsProcessor {
    private static final String APPLICATION_NAME = "SheetsProcessor";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private String processedData = "";

    public static void main(String... args) {
        SpringApplication app = new SpringApplication(SheetsProcessor.class);
        ConfigurableApplicationContext context = app.run(args);
        Environment environment = context.getEnvironment();
        int port = Integer.parseInt(environment.getProperty("local.server.port"));

        System.out.println("http://localhost:" + port+"/family-tree-data");

        SheetsProcessor sheetsProcessor = context.getBean(SheetsProcessor.class);

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            try {
                sheetsProcessor.processAndPrintFamilyForest();
            } catch (GoogleJsonResponseException e) {
                if (e.getStatusCode() == 429) {
                    System.out.println("Rate limit exceeded. Will retry later.");
                } else {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 5, TimeUnit.MINUTES);
    }

    public String processAndPrintFamilyForest() throws IOException {
    	System.out.println("Processing and printing family forest...");
        Sheets service = initializeSheetsService();
        final String spreadsheetId = "1RHXDH5zGcLkAauMLkCo8YidwHxflvNF7wazfW1u3ZYg";
        final String range = "'Form Responses 1'!B3:Y200";
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        this.processedData = processSpreadsheetData(response);
        return this.processedData;
    }







    private static Sheets initializeSheetsService() {
        try {
            HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            GoogleCredentials credentials = GoogleCredentials.fromStream(
            	    SheetsProcessor.class.getResourceAsStream("/tokens/serviceacc4.0.json"))
            	    .createScoped(Collections.singletonList(SheetsScopes.SPREADSHEETS));
            return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpCredentialsAdapter(credentials))
                .setApplicationName(APPLICATION_NAME)
                .build();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String processSpreadsheetData(ValueRange response) {
        FamilyProcessor familyProcessor = new FamilyProcessor();
        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List<Object> row : values) {
                List<String> rowAsString=new ArrayList<String>();
                for (Object cell : row) {
                    if(!cell.toString().isEmpty()){
                        rowAsString.add(cell.toString());
                    }
                }
                familyProcessor.process(rowAsString);
            }
        }
        return familyProcessor.printFamilyForest();
    }

    public String getProcessedData() {
        return this.processedData;
    }
}
