#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
FROM incodehq/tomcat
RUN rm -rf ${symbol_dollar}{DEPLOYMENT_DIR}/ROOT
COPY ${symbol_dollar}{docker-plugin.resource.include} ${symbol_dollar}{DEPLOYMENT_DIR}/ROOT.war
EXPOSE 8080