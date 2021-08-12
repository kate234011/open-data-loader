package ru.fns.suppliers.service.client;

import org.apache.camel.Consumer;
import org.apache.camel.Endpoint;
import org.apache.camel.support.DefaultPollingConsumerPollStrategy;
import org.apache.camel.support.EndpointHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fns.suppliers.service.log.LogService;

public class PollOncePollStrategy extends DefaultPollingConsumerPollStrategy {

    private LogService logService;

    public PollOncePollStrategy(){}

    public PollOncePollStrategy(LogService logService){
        this.logService = logService;
    }

    @Override
    public void commit(Consumer consumer, Endpoint endpoint, int polledMessages) {
        Logger logger = LoggerFactory.getLogger(PollOncePollStrategy.class);
        try {
            if (polledMessages == 0) {
                logger.info("No polled messages, stopping consumer");

                logService.writeLog();

                endpoint.getCamelContext().createProducerTemplate().sendBody(String.format("controlbus:route?async=true&action=stop&routeId=%s", EndpointHelper.getRouteIdFromEndpoint(endpoint)), null);

                endpoint.getCamelContext().stop();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
