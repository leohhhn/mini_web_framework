package src;

@Controller
public class TestController {

    public static TestController tcInstance = null;

    private TestController() {
    }

    @GET
    @Path(path = "/path1")
    public String path1() {
        return "Stigli ste do /path1!";
    }


    public TestController getInstance() {
        if (tcInstance == null)
            return new TestController();
        return tcInstance;
    }

}


