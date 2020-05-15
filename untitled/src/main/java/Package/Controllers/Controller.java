package Package.Controllers;

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
        URL u = new URL("https://mdna.azurewebsites.net/api/GetDataFromTableStorage?limitRows=1&orderBy=desc");
        try (InputStream in = u.openStream()) {
            return new String(in.readAllBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
