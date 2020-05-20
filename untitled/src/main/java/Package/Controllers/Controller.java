package Package.Controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;



@RestController
public class Controller {

    @RequestMapping("/")
    public String index() throws MalformedURLException {
        String value1 = "",value2 = "" ;
        URL u = new URL("https://mdna.azurewebsites.net/api/GetDataFromTableStorage?limitRows=1&orderBy=desc");
        try (InputStream in = u.openStream()) {
            Object obj = new String(in.readAllBytes());
            String name = obj.toString();
            JSONArray jsonArr = new JSONArray(name);



            
            for (int i = 0; i < jsonArr.length(); ++i) {
                JSONObject rec = jsonArr.getJSONObject(i);
                 value1 = rec.optString("temperature");
                 value2 = rec.optString("timestamp");

            }
            return "Temperature: "+ value1 + " Timestamp: "+value2;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
