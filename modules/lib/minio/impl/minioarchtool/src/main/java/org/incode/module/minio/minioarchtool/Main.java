package org.incode.module.minio.minioarchtool;

import java.io.IOException;

import com.google.common.io.Resources;

import org.apache.log4j.PropertyConfigurator;

import org.incode.module.minio.dopclient.DomainObjectPropertyClient;
import org.incode.module.minio.minioarchlib.MinioArchiver;
import org.incode.module.minio.minioclient.MinioUploadClient;

import static java.util.Arrays.asList;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class Main {

    public static void main(final String[] args) throws IOException {

        PropertyConfigurator.configure(
                Resources.getResource(Main.class, "logging.properties"));

        Main main = new Main(args);

        main.archiveAll();
    }

    private final MinioArchiver minioArchiver;

    public Main(String[] args) {

        final OptionParser parser = new OptionParser();

        final ArgumentAcceptingOptionSpec<String> b = parser.accepts("b")
                .withRequiredArg().ofType(String.class)
                .describedAs("DocBlobServer (Apache Isis app) base URL")
                .defaultsTo("http://localhost:8080/restful/");
        final ArgumentAcceptingOptionSpec<String> u = parser.accepts("u")
                .withRequiredArg().ofType(String.class)
                .describedAs("DocBlobServer (Apache Isis app) username")
                .defaultsTo("sven");
        final ArgumentAcceptingOptionSpec<String> p = parser.accepts("p")
                .withRequiredArg().ofType(String.class)
                .describedAs("DocBlobServer (Apache Isis app) password")
                .defaultsTo("pass");

        final ArgumentAcceptingOptionSpec<String> m = parser.accepts("m")
                .withRequiredArg().ofType(String.class)
                .describedAs("Minio base URL")
                .required();
        final ArgumentAcceptingOptionSpec<String> a = parser.accepts("a")
                .withRequiredArg().ofType(String.class)
                .describedAs("Minio Access Key")
                .defaultsTo("minio");
        final ArgumentAcceptingOptionSpec<String> s = parser.accepts("s")
                .withRequiredArg().ofType(String.class)
                .describedAs("Minio Secret Key")
                .defaultsTo("minio123");
        final ArgumentAcceptingOptionSpec<String> k = parser.accepts("k")
                .withRequiredArg().ofType(String.class)
                .required()
                .describedAs("Minio Bucket");
        final ArgumentAcceptingOptionSpec<String> r = parser.accepts("r")
                .withRequiredArg().ofType(String.class)
                .describedAs("Minio ObjectName Prefix")
                .defaultsTo("db");

        parser.acceptsAll( asList( "h", "?" ), "show help" ).forHelp();

        final OptionSet optionSet = parser.parse(args);

        final DomainObjectPropertyClient domainObjectPropertyClient = new DomainObjectPropertyClient();
        domainObjectPropertyClient.setBase(optionSet.valueOf(b));
        domainObjectPropertyClient.setUsername(optionSet.valueOf(u));
        domainObjectPropertyClient.setPassword(optionSet.valueOf(p));
        domainObjectPropertyClient.init();

        final MinioUploadClient minioUploadClient = new MinioUploadClient();
        minioUploadClient.setUrl(optionSet.valueOf(m));
        minioUploadClient.setAccessKey(optionSet.valueOf(a));
        minioUploadClient.setSecretKey(optionSet.valueOf(s));
        minioUploadClient.setBucket(optionSet.valueOf(k));
        minioUploadClient.setPrefix(optionSet.valueOf(r));
        minioUploadClient.init();

        minioArchiver = new MinioArchiver();
        minioArchiver.setDomainObjectPropertyClient(domainObjectPropertyClient);
        minioArchiver.setMinioUploadClient(minioUploadClient);
    }

    private void archiveAll() {
        int numArchived = archiveSome();
        while(numArchived > 0) {
            numArchived = archiveSome();
        }
    }

    private int archiveSome() {
        return minioArchiver.archive("batch");
    }

}
