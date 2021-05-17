import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class APIhandler {

    public static JSONObject fetchAPIData(URL url){
        try {
            HttpURLConnection apiConn = (HttpURLConnection)url.openConnection();
            apiConn.setRequestMethod("GET");
            apiConn.connect();
            String stringJSON = "";
            Scanner scan1 = new Scanner(url.openStream());
            while (scan1.hasNext()){
                stringJSON+=scan1.nextLine();
            }
            scan1.close();
            JSONParser parser = new JSONParser();
            return (JSONObject)parser.parse(stringJSON);
        }catch (UnknownHostException e){
            System.out.println("Host seams not to be reachable");
        }catch(NullPointerException e){
            System.out.println("Given URL is null");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static URL GenerateAPIurl(int cityID){

        //http://api.openweathermap.org/data/2.5/forecast?id=2825297&APPID=bb4ce93b554eb1474eb6d652eb1a85ae&units=metric <-- Example Url for API call forecast
        //http://api.openweathermap.org/data/2.5/weather?id=2825297&APPID=bb4ce93b554eb1474eb6d652eb1a85ae&units=metric <-- Example Url for API call current data

        try{
            if(cityID==-1){
                return new URL(""); //returning an empty URL
            }
            StringBuilder apiCall = new StringBuilder();
            String apiUrlForecast = "http://api.openweathermap.org/data/2.5/forecast";
            String apiUrlCurrentData = "http://api.openweathermap.org/data/2.5/weather";
            String apiKey = "bb4ce93b554eb1474eb6d652eb1a85ae";
            apiCall.append(apiUrlForecast);
            apiCall.append("?id="+cityID);
            apiCall.append("&APPID="+apiKey);
            apiCall.append("&units=metric");
            apiCall.append("&lang=de");
            return new URL(apiCall.toString());
        }catch(NullPointerException e){
            System.out.println("Object is null");
        }catch (Exception e){
            System.out.println("An error occurred");
        }
        return null; //returning an empty URL
    }
    public static void printForecast(JSONObject apiDATA){
        try{
            System.out.printf("Wetter in %s\n",((JSONObject)apiDATA.get("city")).get("name"));
            String date = "";
            JSONArray dataArrayFromApi = (JSONArray)apiDATA.get("list");
            for (Object jsonOBJ : dataArrayFromApi) {
                if(jsonOBJ instanceof JSONObject){
                    if(!date.equals(((JSONObject) jsonOBJ).get("dt_txt").toString().split("\\s+")[0])){
                        System.out.println("\n"+getWeekday((JSONObject) jsonOBJ)+" "+getFormatDate((JSONObject) jsonOBJ));
                        date = ((JSONObject) jsonOBJ).get("dt_txt").toString().split("\\s+")[0];
                    }
                    System.out.printf("\t%s %6s %s\n",getTime((JSONObject) jsonOBJ),getTemp((JSONObject) jsonOBJ)[0],getWeatherCondition((JSONObject) jsonOBJ));
                }
            }
        }catch (Exception e){
            System.err.println("Fetched data is null");
        }
    }
    private static String getWeekday(JSONObject currentDay){
        String inputDate = currentDay.get("dt_txt").toString().split("\\s+")[0];
        SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");
        Date dateDay;
        try{
            dateDay = formatDay.parse(inputDate);
        }catch (ParseException e){
            dateDay = new Date();
        }
        DateFormat formatDaySimple=new SimpleDateFormat("EEEE");
        switch(formatDaySimple.format(dateDay)){
            case "Monday":
                return "Montag";
            case "Tuesday":
                return "Dienstag";
            case "Wednesday":
                return "Mittwoch";
            case "Thursday":
                return "Donnerstag";
            case "Friday":
                return "Freitag";
            case "Saturday":
                return "Samstag";
            case "Sunday":
                return "Sonntag";
        }
        return formatDaySimple.format(dateDay);
    }
    private static String getFormatDate(JSONObject currentDay){
        StringBuilder formatDate = new StringBuilder();
        formatDate.append(currentDay.get("dt_txt").toString().split("\\s+")[0].split("-")[2]);
        formatDate.append(".");
        formatDate.append(currentDay.get("dt_txt").toString().split("\\s+")[0].split("-")[1]);
        return (formatDate.toString());
    }
    private static String getTime(JSONObject currentDay){
        StringBuilder time = new StringBuilder();
        time.append(currentDay.get("dt_txt").toString().split("\\s+")[1].split(":")[0]);
        time.append(":");
        time.append(currentDay.get("dt_txt").toString().split("\\s+")[1].split(":")[1]);
        time.append(":");
        return (time.toString());
    }
    private static String[] getTemp(JSONObject currentDay){
        String[] temps = new String[4];
        temps[0]=((JSONObject) currentDay.get("main")).get("temp")+"°C";
        temps[1]=((JSONObject) currentDay.get("main")).get("feels_like")+"°C";
        temps[2]=((JSONObject) currentDay.get("main")).get("temp_min")+"°C";
        temps[3]=((JSONObject) currentDay.get("main")).get("temp_max")+"°C";
        return temps;
    }
    private static String getWeatherCondition(JSONObject currentDay){
        JSONArray weatherArray = (JSONArray)currentDay.get("weather");
        return ((JSONObject) weatherArray.get(0)).get("description").toString();
    }
}
