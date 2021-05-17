import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CityListHelper {
    public static HashMap<Integer, JSONObject> findEntrys(String filter, String fileName){
        try{
            JSONArray cityJSON = JSONhandler.loadJSON(fileName); //calling the loadJSON function to load the city list, self enplaning ..
            if(cityJSON!=null){ //checking the cityJSON hashmap in case an error occurred and the the return value from loadJSON is NULL
                HashMap<Integer,JSONObject> citys = new HashMap<Integer, JSONObject>();
                int indexForCitys = 1; //separate index for the hashmap and to simplify the selection later on if there are multiple city's to select when filter isn't precise enough
                for (Object jsonOBJ : cityJSON) { //checking every list entry to check if there are any containing entry's that can be found with the given filter
                    if(jsonOBJ instanceof JSONObject){
                        if(((JSONObject) jsonOBJ).get("name").toString().contains(filter)){
                            citys.put(indexForCitys++,(JSONObject) jsonOBJ); //in case of multiple found entry's in the list they will be stored in a hashmap to simplify the selection later on
                        }
                    }
                }
                return citys;
            }
            return new HashMap<Integer, JSONObject>(); //returning an empty hashmap when error occurred while loading the JSON
        }catch (NullPointerException e){
            System.out.println("One of the given parameters is null");
        }catch(Exception e){
            System.out.println("An error occurred");
        }
        return new HashMap<Integer, JSONObject>(); //returning an empty hashmap if an exception occurred
    }
    public static int listEntrys(HashMap<Integer,JSONObject> citys){
        try{
            if(citys.size()!=0){
                try{
                    if(citys.size()>1){
                        Scanner userSelection = new Scanner(System.in);
                        for (int i = 1;i<=citys.size();i++){ //printing all found entry's in the city list
                            System.out.println(i+") "+citys.get(i).get("country")+", "+citys.get(i).get("name"));;
                        }
                        System.out.printf("\nPlease select > ");
                        int selection = userSelection.nextInt();
                        System.out.println();
                        return Integer.parseInt(citys.get(selection).get("id").toString()); //returning the ID of the selected city to use it later on for the api call
                    }else{
                        return Integer.parseInt(citys.get(1).get("id").toString()); //returning the ID if only one entry is in the hashmap
                    }
                }catch (InputMismatchException e){
                    System.out.println("\nYour selection wasn't a number");
                }catch (NullPointerException e){
                    System.out.println("\nYour selection isn't valid");
                }catch (Exception e){
                    System.out.println("\nSomething went wrong!?");
                }
                return -1; //returning the value -1 because it isn't a valid ID so it's easy to trace back as an error that occurred
            }else{
                System.out.println("No match found");
                return -1; //returning the value -1 because it isn't a valid ID so it's easy to trace back as an error that occurred
            }
        }catch (NullPointerException e){
            System.out.println("Given hashmap is null");
        }catch (Exception e){
            System.out.println("An error occurred");
        }
        return -1; //returning the value -1 because it isn't a valid ID so it's easy to trace back as an error that occurred
    }
}
