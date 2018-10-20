package net.rickiekarp.core.net;

import com.squareup.okhttp.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class NetResponse {

    public static String getResponseString(InputStream inputStream) {
        String inputLine;
        StringBuilder response = new StringBuilder();

        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return response.toString();
        } catch (NullPointerException e) {
            return response.toString();
        }
        try {
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return response.toString();
        }
        return response.toString();
    }

    public static String getResponseString(Response r) throws IOException {
        InputStream inputStream = inputStream = r.body().byteStream();
        return getResponseString(inputStream);
    }

    private static byte[] getResponseByteArray(InputStream inputStream) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len;
        byte[] buffer = new byte[4096];
        try {
            while (-1 != (len = inputStream.read(buffer))) {
                bos.write(buffer, 0, len);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

    public static JSONObject getResponseJson(InputStream inputStream) {
        String responseString = getResponseString(inputStream);
        try {
            return new JSONObject(responseString);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Converts a given XML String to a DOM Object and returns the document
     */
    public static Document getResponseXml(InputStream inputStream) throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(getResponseString(inputStream))));
    }
}
