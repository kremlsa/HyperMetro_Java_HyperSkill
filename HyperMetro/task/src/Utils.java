import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Utils {

    public static List<String>readF(String filePath) {
        List<String> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                result.add(line);
            }
        }
        catch (IOException e)
        {
            System.out.print("Error");
            e.printStackTrace();
        }
        return result;
    }

    public static Map<String, List<Station>> loadFromJson(String filePath) {
        String jsonString = "";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                jsonString += line;
            }
        }
        catch (IOException e)
        {
            System.out.print("Error");
            e.printStackTrace();
        }
        /*Gson gson = new Gson();
       // JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        Type empMapType = new TypeToken<Map<String, JsonObject>>() {}.getType();
        Map<String, JsonObject> metros = gson.fromJson(jsonString, empMapType);*/

        Gson gson = new Gson();
        Type empMapType = new TypeToken<Map<String, List<Station>>>() {}.getType();
        Map<String, List<Station>> metros = gson.fromJson(jsonString, empMapType);

      /*  for (Map.Entry<String, List<Station>> entry : metros.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println("-----------");
            for (Station s : entry.getValue()) {
                System.out.println(s.print());
            }
        }*/


        return metros;
    }

    public static List<Station> getStations(String input) {
        /*input = input.replace("{", "");
        input = input.replace("}", "");
        String[] array = input.split(",");
        ArrayList<Station> result = new ArrayList<>(array.length);
        while(result.size() < array.length) result.add(new Station());
        for (String s : array) {
            s = s.replace("\"", "");
            //result.set(Integer.parseInt(s.split(":")[0]) - 1, s.split(":")[1]);
            Station temp = new Station();
            temp.setName(s.split(":")[1]);
            result.set(Integer.parseInt(s.split(":")[0]) - 1, temp);
        }*/
      //  Gson gson = new Gson();
      //  Station station = gson.fromJson(input, Station.class);
       // List<Station> stations = gson.fromJson(input, Station.class);
       // return result;
        Gson gson = new Gson();
        Type empMapType = new TypeToken<Map<String, Station>>() {}.getType();
        Map<String, Station> metros = gson.fromJson(input, empMapType);

        ArrayList<Station> result = new ArrayList<>(metros.size());
        while(result.size() < metros.size()) result.add(new Station());

        int bias = Integer.MAX_VALUE;

        for (Map.Entry<String, Station> entry : metros.entrySet()) {
            if (Integer.parseInt(entry.getKey()) < bias) bias = Integer.parseInt(entry.getKey());
        }
        //System.out.println(bias);
        for (Map.Entry<String, Station> entry : metros.entrySet()) {
           // System.out.println(entry.getKey());
           // System.out.println("---" + bias);
            result.set(Integer.parseInt(entry.getKey()) - bias, entry.getValue());
        }

        return result;
    }

}
