package jaxrs.resources

import services.DataService
import services.HelloJson
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("helloWorld")
class HelloWorldResource
@Inject constructor(private val dataService: DataService)
{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun helloWorld() : List<HelloJson> = dataService.all()
}
