package org.isisaddons.module.publishmq.dom.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import org.isisaddons.module.publishmq.dom.canonical.aim.ActionInvocationMementoDto;

/**
 * A Camel {@link Processor} that can unmarshal a {@link Message} whose {@link Message#getBody() body}
 * contains a {@link org.isisaddons.module.publishmq.aim.ActionInvocationMementoDto}, and attaches a
 * number of headers from the AIM's metadata for downstream routing.
 *
 * <p>
 * The headers that are attached are:
 * <ul>
 *      <li><tt>messageId</tt> - a String, the concatentation of <tt>transactionId</tt>:<tt>sequence</tt></li>
 *      <li><tt>transactionId</tt> - a String, representing the Isis transaction in which this event was published</li>
 *      <li><tt>sequence</tt> - an int, being the 0-based sequence number of this event within the transaction (when more than one event is published)</li>
 *      <li><tt>timestamp</tt> - a long, the epoch-based timestamp that the event was original published</li>
 *      <li><tt>actionIdentifier</tt> - the action identifier</li>
 *      <li><tt>user</tt> - a string, an identifier of the user whose action caused the event to be published</li>
 * </ul>
 * <p>
 * If using Spring DSL, register as follows: 
 * <pre>
 *  &lt;bean id=&quot;addExchangeHeaders&quot; class=&quot;org.isisaddons.module.publishmq.dom.camel.AddExchangeHeaders&quot;/&gt;
 * </pre>
 * 
 * <p>
 * Then, for a route where the message contains an {@link org.isisaddons.module.publishmq.dom.canonical.aim.ActionInvocationMementoDto}, route using something like:
 * <pre>
 *   ...
 *   &lt;camel:process ref=&quot;addExchangeHeaders&quot;/&gt;
 *   &lt;camel:choice&gt;
 *       &lt;camel:when&gt;
 *           &lt;camel:simple&gt;${header.actionIdentifier} == 'dom.todo.ToDoItem#completed()'&lt;/camel:simple&gt;
 *           ...
 *      &lt;/camel:when&gt;
 *  &lt;/camel:choice&gt;
 * </pre>
 */
public class AddExchangeHeaders implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Message inMessage = exchange.getIn();
        Object body = inMessage.getBody();

        if(!(body instanceof ActionInvocationMementoDto)) {
            throw new IllegalArgumentException("Expected body to contain a PublishedEvent");
        } 
        final ActionInvocationMementoDto aim = (ActionInvocationMementoDto)body;

        final ActionInvocationMementoDto.Metadata metadata = aim.getMetadata();
        inMessage.setHeader("messageId", metadata.getTransactionId() + ":" + metadata.getSequence());
        inMessage.setHeader("transactionId", metadata.getTransactionId());
        inMessage.setHeader("sequence", metadata.getSequence());
        inMessage.setHeader("actionIdentifier", metadata.getActionIdentifier());
        inMessage.setHeader("timestamp", metadata.getTimestamp());
        inMessage.setHeader("user", metadata.getUser());
    }


}
