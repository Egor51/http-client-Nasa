import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;


public class Main {

    public static ObjectMapper mapper = new ObjectMapper();

    public static String getFilename(String url) throws URISyntaxException {
        var uri = new URI(url);
        return new File(uri.getPath()).getName();
    }


    public static void main(String[] args) throws IOException, URISyntaxException {

        final String REMOTE_SERVICE_URI = "https://api.nasa.gov/planetary/apod?api_key=V7bycDcNikg3i8cyZm2CKlPhbNDFBR4Fi2vnQg8p";

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setUserAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36\n")
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request = new HttpGet(REMOTE_SERVICE_URI);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
        CloseableHttpResponse response = httpClient.execute(request);

        NASAClass nasaClass = mapper.readValue(response.getEntity().getContent(), new TypeReference<NASAClass>() {
        });

        String urlNasa = nasaClass.url();
        System.out.println("Url получен: " + urlNasa);

        HttpGet requestUrl = new HttpGet(urlNasa);
        CloseableHttpResponse responseUrl = httpClient.execute(requestUrl);
        HttpEntity entity = responseUrl.getEntity();

        InputStream inputStream = entity.getContent();
        String fileName = getFilename(urlNasa);
        System.out.println("Имя файла: "+fileName);
        String path = "/home/egor/"+ fileName;


        Files.copy(inputStream, new File(path).toPath());
        try {
            if (Files.exists(Path.of(path)) && !Files.isDirectory(Path.of(path))) {
           System.out.println("Фаил "+ " "+ fileName + " " + "скачен!");
                   }
               } catch (Exception e) {
           e.getStackTrace();
       }
    }
}
