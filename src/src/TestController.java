package src;

@Controller
public class TestController {

    @Autowired(verbose = false)
    String testField;

    public TestController() {
    }

    @GET
    @Path(path = "/path1")
    public String path1() {
        return "Stigli ste do /path1!";

    }

    @POST
    @Path(path = "/path2")
    public String path2() {
        return "Stigli ste do /path2!";
    }


}


