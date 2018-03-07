#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package domainapp.modules.base;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;

@XmlRootElement(name = "module")
public class BaseModule extends ModuleAbstract {
}
