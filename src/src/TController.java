package src;

import src.annotations.*;
import src.interfaces.Establishment;

@Controller
public class TController {

    @Autowired(verbose = true)
    @Qualifier(value = "TZoo")
    Establishment establishment1;

    public TController() {
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


