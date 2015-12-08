package rest.flyfetcher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AirlineInternalModel;


public class FlyFetcher implements Runnable{

    private String url;
    private String apiUrl;
    private volatile List<AirlineInternalModel> airlines;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();

    public FlyFetcher(String url, List<AirlineInternalModel> airlines, String apiUrl) {
        this.url = url;
        this.airlines = airlines;
        this.apiUrl = apiUrl;
    }
  
    
    @Override
    public void run() {
        BufferedReader in = null;
        try {
            URL page = new URL(url);
            in = new BufferedReader(
                    new InputStreamReader(page.openStream()));
            String inputLine;
            String json = "";
            while ((inputLine = in.readLine()) != null)
                json += inputLine;
            in.close();
            
            AirlineInternalModel aim = gson.fromJson(json, AirlineInternalModel.class);
            aim.setUrl(apiUrl);
            if(!aim.getAirline().equals(""))
                airlines.add(aim);
            
        } catch (IOException ex) {
            Logger.getLogger(FlyFetcher.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(FlyFetcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
