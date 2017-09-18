import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource


// constants
def BASE="target/generated-sources/archetype/"
def ROOT=BASE + "src/main/resources/"
def ARCHROOT=ROOT + "archetype-resources/"



//
// update archetype's own pom.xml's groupId
//

def pomFile=new File(BASE+"pom.xml")

println "updating ${pomFile.path}"

// read file, ignoring XML pragma
def pomFileText = stripXmlPragma(pomFile)

def pomXml = new XmlSlurper(false,true).parseText(pomFileText)
pomXml.groupId='org.incode.platform.archetype'
pomXml.artifactId='quickstart-archetype'


println "updating ${pomFile.path}"

def scriptDir = new File(getClass().protectionDomain.codeSource.location.path).parent
def releaseProfileFile=new File(scriptDir+"/release-profile.xml")
def releaseProfileFileText = stripXmlPragma(releaseProfileFile)

def fragmentToAdd = new XmlSlurper( false, true ).parseText(releaseProfileFileText)
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





//
// update archetype-metadata.xml
//

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




//
// ensure optional modules are commented out
//

def xmlCommentStart = ' <!--'
def xmlCommentEnd = '--> '
def javaCommentStart = ' /*'
def javaCommentEnd = '*/ '


def x1_find = '<!-- Uncomment to include example modules demonstrating platform usage: START -->'
def x1_replace = x1_find + xmlCommentStart

def x2_find = '<!-- Uncomment to include example modules demonstrating platform usage: END -->'
def x2_replace = xmlCommentEnd + x2_find

def x3_find = '/* Uncomment to include example modules demonstrating platform usage: START */'
def x3_replace = x3_find + javaCommentStart

def x4_find = '/* Uncomment to include example modules demonstrating platform usage: END */'
def x4_replace = javaCommentEnd + x4_find


def x5_find = '<!-- Uncomment to include example modules that set up embedded camel: START -->'
def x5_replace = x5_find + xmlCommentStart

def x6_find = '<!-- Uncomment to include example modules that set up embedded camel: END -->'
def x6_replace = xmlCommentEnd + x6_find

def x7_find = '/* Uncomment to include example modules that set up embedded camel: START */'
def x7_replace = x7_find + javaCommentStart

def x8_find = '/* Uncomment to include example modules that set up embedded camel: END */'
def x8_replace = javaCommentEnd + x8_find


def xA_find = xmlCommentStart + xmlCommentStart
def xA_replace = xmlCommentStart

def xB_find = xmlCommentEnd + xmlCommentEnd
def xB_replace = xmlCommentEnd

def xC_find = javaCommentStart + javaCommentStart
def xC_replace = javaCommentStart

def xD_find = javaCommentEnd + javaCommentEnd
def xD_replace = javaCommentEnd


[ ARCHROOT+"pom.xml",
  ARCHROOT+"appdefn/pom.xml",
  ARCHROOT+"appdefn/src/main/java/domainapp/appdefn/DomainAppAppManifest.java",
  ARCHROOT+"webapp/pom.xml",
  ARCHROOT+"webapp/src/main/webapp/WEB-INF/web.xml",
].each {
    def ant = new AntBuilder()
    println it
    ant.replace(file: it, token: xA_find, value: xA_replace)
    ant.replace(file: it, token: xB_find, value: xB_replace)
    ant.replace(file: it, token: xC_find, value: xC_replace)
    ant.replace(file: it, token: xD_find, value: xD_replace)

    ant.replace(file: it, token: x1_find, value: x1_replace)
    ant.replace(file: it, token: x2_find, value: x2_replace)
    ant.replace(file: it, token: x3_find, value: x3_replace)
    ant.replace(file: it, token: x4_find, value: x4_replace)
    ant.replace(file: it, token: x5_find, value: x5_replace)
    ant.replace(file: it, token: x6_find, value: x6_replace)
    ant.replace(file: it, token: x7_find, value: x7_replace)
    ant.replace(file: it, token: x8_find, value: x8_replace)

    ant.replace(file: it, token: xA_find, value: xA_replace)
    ant.replace(file: it, token: xB_find, value: xB_replace)
    ant.replace(file: it, token: xC_find, value: xC_replace)
    ant.replace(file: it, token: xD_find, value: xD_replace)
}




//
// helper methods
//

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

