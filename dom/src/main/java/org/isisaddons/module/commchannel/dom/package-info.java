/**
 * Defines {@link org.isisaddons.module.commchannel.dom.CommunicationChannel} as an abstraction of a means for
 * two parties to interact with each other, along with a number of concrete implementations: 
 * {@link org.isisaddons.module.commchannel.dom.PhoneOrFaxNumber},
 * {@link org.isisaddons.module.commchannel.dom.EmailAddress} and
 * {@link org.isisaddons.module.commchannel.dom.PostalAddress}.
 * 
 * <p>
 * Every channel has a {@link org.isisaddons.module.commchannel.dom.CommunicationChannel#getType() associated}
 * {@link org.isisaddons.module.commchannel.dom.CommunicationChannelType type}, this acting as a power-type.
 *  
 * <p>
 * Every channel also has an {@link org.isisaddons.module.commchannel.dom.CommunicationChannel#getOwner() associated}
 * {@link org.isisaddons.module.commchannel.dom.CommunicationChannelOwner owner}; this is a polymorphic association
 * with the implementations being <tt>FixedAsset</tt> and <tt>Party</tt>.
 * 
 */
package org.isisaddons.module.commchannel.dom;