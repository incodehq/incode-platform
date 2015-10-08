/**
 * Defines {@link org.incode.module.commchannel.dom.CommunicationChannel} as an abstraction of a means for
 * two parties to interact with each other, along with a number of concrete implementations: 
 * {@link org.incode.module.commchannel.dom.PhoneOrFaxNumber},
 * {@link org.incode.module.commchannel.dom.EmailAddress} and
 * {@link org.incode.module.commchannel.dom.PostalAddress}.
 * 
 * <p>
 * Every channel has a {@link org.incode.module.commchannel.dom.CommunicationChannel#getType() associated}
 * {@link org.incode.module.commchannel.dom.CommunicationChannelType type}, this acting as a power-type.
 *  
 * <p>
 * Every channel also has an {@link org.incode.module.commchannel.dom.CommunicationChannel#getOwner() associated}
 * {@link org.incode.module.commchannel.dom.CommunicationChannelOwner owner}; this is a polymorphic association
 * with the implementations being <tt>FixedAsset</tt> and <tt>Party</tt>.
 * 
 */
package org.incode.module.commchannel.dom;