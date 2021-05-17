import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class APIhandlerTest {
    @Test
    public void urlNotReachable() throws MalformedURLException {
        Assert.assertEquals("Due to the given URL is not reachable result should be null",null,APIhandler.fetchAPIData(new URL("http://www.ThisIsNotReachable.com")));
    }
    @Test
    public void urlIsNull(){
        Assert.assertEquals("The given URL is null result should be null",null,APIhandler.fetchAPIData(null));
    }
}
