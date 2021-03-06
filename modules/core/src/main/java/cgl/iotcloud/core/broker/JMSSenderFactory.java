package cgl.iotcloud.core.broker;

import cgl.iotcloud.core.Endpoint;
import cgl.iotcloud.core.message.jms.JMSControlMessageFactory;

public class JMSSenderFactory {
    /**
     * Create a Sender using the Endpoint
     * @param endpoint endpoint to be used
     * @return a sender
     */
    public JMSSender create(Endpoint endpoint) {
        ConnectionsFactory fac = new ConnectionsFactory();
        Connections connections = fac.create(endpoint.getName(), endpoint.getProperties());

        return new JMSSender(connections, endpoint.getAddress());
    }

    /**
     * Create a Sender using the Endpoint
     * @param endpoint endpoint to be used
     * @return a sender
     */
    public JMSSender createControlSender(Endpoint endpoint) {
        ConnectionsFactory fac = new ConnectionsFactory();
        Connections connections = fac.create(endpoint.getName(), endpoint.getProperties());

        JMSSender sender = new JMSSender(connections, endpoint.getAddress());
        sender.setMessageFactory(new JMSControlMessageFactory());
        return sender;
    }
}
