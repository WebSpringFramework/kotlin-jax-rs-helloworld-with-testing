package jaxrs

import org.glassfish.hk2.utilities.binding.AbstractBinder
import org.glassfish.jersey.logging.LoggingFeature
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.server.monitoring.ApplicationEvent
import org.glassfish.jersey.server.monitoring.ApplicationEventListener
import org.glassfish.jersey.server.monitoring.RequestEvent
import org.glassfish.jersey.server.monitoring.RequestEventListener
import services.DataService
import services.NullDataService
import services.StaticListDataService
import java.util.logging.Level
import java.util.logging.Logger


class Application(bindings: AbstractBinder = ProductionBindings()) : ResourceConfig() {
    init {
        packages("jaxrs.resources")

        register(LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME),
                Level.INFO,
                LoggingFeature.Verbosity.PAYLOAD_ANY,
                Integer.MAX_VALUE)
        )

        register(bindings)
        register(ExceptionLogger())
    }
}

class ProductionBindings : AbstractBinder() {
    override fun configure() {
        bind(StaticListDataService::class.java).to(DataService::class.java)
    }
}

class TestBindings : AbstractBinder() {
    override fun configure() {
        bind(NullDataService::class.java).to(DataService::class.java)
    }
}

class ExceptionLogger : ApplicationEventListener, RequestEventListener {
    private val logger = Logger.getAnonymousLogger()

    override fun onEvent(event: ApplicationEvent?) {
    }

    override fun onRequest(requestEvent: RequestEvent): RequestEventListener {
        return this
    }

    override fun onEvent(paramRequestEvent: RequestEvent) {
        if (paramRequestEvent.type != RequestEvent.Type.ON_EXCEPTION) {
            return
        }

        logger.log(Level.SEVERE, "Exception was thrown in request", paramRequestEvent.exception)
    }
}
