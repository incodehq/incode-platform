package org.isisaddons.module.publishmq.dom.camel;

import com.google.common.collect.ImmutableMap;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import org.apache.isis.schema.common.v1.PeriodDto;
import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.apache.isis.schema.ixn.v1.MemberExecutionDto;

/**
 * A Camel {@link Processor} that can unmarshal a {@link Message} whose {@link Message#getBody() body}
 * contains a {@link org.apache.isis.schema.ixn.v1.InteractionDto}, and attaches a
 * single 'aim' header which is a map of AIM's metadata, for downstream routing.
 *
 * <p>
 * The header's keys that are attached are:
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
 * Then, for a route where the message contains a {@link org.apache.isis.schema.ixn.v1.InteractionDto}, route using something like:
 * <pre>
 *   ...
 *   &lt;camel:process ref=&quot;addExchangeHeaders&quot;/&gt;
 *   &lt;camel:choice&gt;
 *       &lt;camel:when&gt;
 *           &lt;camel:simple&gt;${header.interaction[memberIdentifier]} == 'dom.todo.ToDoItem#completed()'&lt;/camel:simple&gt;
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

        if(!(body instanceof InteractionDto)) {
            throw new IllegalArgumentException("Expected body to contain a PublishedEvent");
        } 
        final InteractionDto interactionDto = (InteractionDto)body;
        final MemberExecutionDto executionDto = interactionDto.getExecution();
        final PeriodDto timings = executionDto.getTimings();

        final ImmutableMap<String, Object> interactionHeader = ImmutableMap.<String,Object>builder()
                .put("messageId", interactionDto.getTransactionId() + ":" + executionDto.getSequence())
                .put("transactionId", interactionDto.getTransactionId())
                .put("sequence", executionDto.getSequence())
                .put("user", executionDto.getUser())
                .put("memberIdentifier", executionDto.getMemberIdentifier())
                .put("timestamp", timings.getStartedAt())
                .build();

        inMessage.setHeader("interaction", interactionHeader);
    }


}
