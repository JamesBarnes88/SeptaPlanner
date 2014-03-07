package com.drexel.septaplanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class myData {

	//http connection using url stream and reading in char by char, might be slow.
	public static JsonElement getJson(String URL) {
		JsonElement json;

		try {

			BufferedReader bf = new BufferedReader(new InputStreamReader(
					getHttpConnection(URL)));

			String cat;
			String input = "";
			// reads in the json char by char. this can be saved as another
			while ((cat = bf.readLine()) != null) {
				input = input.concat(cat);
			}
			// input line is now the full json response
			bf.close();
			json = (JsonElement) new JsonParser().parse(input);
			return json;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
    /** returns input stream of this URL connection. This can be used to get files from the specified location
     * @param urlString url location that has the data to be received
     * @return	InputStream of the URL connection started by the url passed to this function
     * @throws IOException
     */
    public static InputStream getHttpConnection(String urlString)
            throws IOException {
        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stream;

    }
       

	/**This functions replaces all spaces in the string with "%20" to conform with url 
	 * standards outside of a query
	 * @param s
	 * @return formatted string
	 */
	public static String encodeUrl(String s) {
		s = s.replaceAll(" ", "%20");
		return s;
	}
}
