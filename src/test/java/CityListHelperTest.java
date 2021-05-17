import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class CityListHelperTest {
    @Test
    public void findEntryFilterIsNull() throws IOException, ParseException {
        Assert.assertEquals("The filter is null result should be an empty hashmap",new HashMap<Integer, JSONObject>(),CityListHelper.findEntrys(null,"city.list.json"));
    }
    @Test
    public void findEntryFileNameIsNull() throws IOException, ParseException {
        Assert.assertEquals("The fileName is null result should be an empty hashmap",new HashMap<Integer,JSONObject>(),CityListHelper.findEntrys("Stuttgart",null));
    }
    @Test
    public void findEntryParametersNull() throws IOException, ParseException {
        Assert.assertEquals("Both paramters are null result should be an empty hashmap",new HashMap<Integer,JSONObject>(),CityListHelper.findEntrys(null,null));
    }
    @Test
    public void listEntrysHashMapIsNull(){
        Assert.assertEquals("Hashmap is null result should be -1 as it isnt a valid ID",-1,CityListHelper.listEntrys(null));
    }
    @Test
    public void listEntrysHashMapIsEmpty(){
        Assert.assertEquals("Hashmap is empty result should be -1",-1,CityListHelper.listEntrys(new HashMap<Integer, JSONObject>()));
    }
}
