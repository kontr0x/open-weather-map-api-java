import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class JSONhandlerTest {
    @Test
    public void falseFileName() throws IOException, ParseException {
        Assert.assertEquals("While the filename is misspoken or incorrect the result should be null",null,JSONhandler.loadJSON("tr4sh"));
    }
    @Test
    public void fileNameIsNull() throws IOException, ParseException {
        Assert.assertEquals("While the filename isn't a filename result should be null",null,JSONhandler.loadJSON(null));
    }
}
