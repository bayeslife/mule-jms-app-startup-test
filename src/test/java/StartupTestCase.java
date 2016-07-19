
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;


public class StartupTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "jms.consumer.xml";
    }

    @Test
    public void startApplication() throws Exception
    {


        //If we get here the application has either started or stopped but is not blocking
        Assert.assertTrue(true);

        //MuleClient client = muleContext.getClient();

        //client.dispatch("tcp://localhost:61616","TEST",null);
        //System.out.println("Message sent");


    }

}
