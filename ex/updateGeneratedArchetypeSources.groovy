import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

def cli = new CliBuilder(usage: 'updateGeneratedArchetypeSources.groovy -n [name] -v [version]')
cli.with {
    n(longOpt: 'name', args: 1, required: true, argName: 'name', 'Application name (eg \'quickstart\')')
    v(longOpt: 'version', args: 1, required: true, argName: 'version', 'Isis core version to use as parent POM')
}


/////////////////////////////////////////////////////
//
// constants
//
/////////////////////////////////////////////////////

def BASE="target/generated-sources/archetype/"
def ROOT=BASE + "src/main/resources/"



/////////////////////////////////////////////////////
//
// Parse command line
//
/////////////////////////////////////////////////////

def options = cli.parse(args)
if (!options) {
    return
}

application_name=options.n
isis_version=options.v

/////////////////////////////////////////////////////
//
// update archetype's own pom.xml's groupId
//
/////////////////////////////////////////////////////

def pomFile=new File(BASE+"pom.xml")

println "updating ${pomFile.path}"

// read file, ignoring XML pragma
def pomFileText = stripXmlPragma(pomFile)

def pomXml = new XmlSlurper(false,true).parseText(pomFileText)
pomXml.groupId='org.incode.platform.archetype'
pomXml.artifactId='quickstart-archetype'

def fragmentToAdd = new XmlSlurper( false, true ).parseText( '''<properties>
    <archetype.test.skip>true</archetype.test.skip>
</properties>''' )
pomXml.appendNode(fragmentToAdd)

def pomSmb = new groovy.xml.StreamingMarkupBuilder().bind {
    mkp.declareNamespace("":"http://maven.apache.org/POM/4.0.0")
    mkp.yield(pomXml)
}


def pomTempFile = File.createTempFile("temp",".xml")
def indentedXml = indentXml(pomSmb.toString())
pomTempFile.text = indentedXml
def pomXmlText = stripXmlPragma(pomTempFile)


pomFile.text = 
    pomXmlText





/////////////////////////////////////////////////////
//
// update archetype-metadata.xml
//
/////////////////////////////////////////////////////


def metaDataFile=new File(ROOT+"META-INF/maven/archetype-metadata.xml")

println "updating ${metaDataFile.path}"


// read file, ignoring XML pragma
def metaDataFileText = stripXmlPragma(metaDataFile)

def metaDataXml = new XmlSlurper().parseText(metaDataFileText)
metaDataXml.modules.module.fileSets.fileSet.each { fileSet ->
    if(fileSet.directory=='ide/eclipse') {
        fileSet.@filtered='true'
    }
}

def metaDataSmb = new groovy.xml.StreamingMarkupBuilder().bind {
    mkp.xmlDeclaration()
    mkp.declareNamespace("":"http://maven.apache.org/plugins/maven-archetype-plugin/archetype-descriptor/1.0.0")
    mkp.yield(metaDataXml)
}

def tempFile = File.createTempFile("temp",".xml")
tempFile.text = indentXml(metaDataSmb.toString())
def metaDataXmlText = stripXmlPragma(tempFile)


metaDataFile.text = metaDataXmlText





///////////////////////////////////////////////////
//
// helper methods
//
///////////////////////////////////////////////////

String indentXml(xml) {
    def factory = TransformerFactory.newInstance()
    factory.setAttribute("indent-number", 4);

    Transformer transformer = factory.newTransformer()
    transformer.setOutputProperty(OutputKeys.INDENT, 'yes')
    StreamResult result = new StreamResult(new StringWriter())
    transformer.transform(new StreamSource(new ByteArrayInputStream(xml.toString().bytes)), result)
    return result.writer.toString().replaceAll("\\?><", "\\?>\n<")
}

String stripXmlPragma(File file) {
    def sw = new StringWriter()
    file.filterLine(sw) { ! (it =~ /^\<\?xml/ ) }
    return sw.toString()
}

