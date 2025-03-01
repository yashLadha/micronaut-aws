plugins {
    id("io.micronaut.build.internal.aws-module")
}
dependencies {
    api(libs.managed.aws.lambda.events)
    api(mn.micronaut.http.server)
    api(mn.micronaut.http.client.core)
    api(projects.micronautFunctionAws)
    api(mnServlet.micronaut.servlet.core)
    implementation(mnReactor.micronaut.reactor)
    implementation(mn.micronaut.http.netty)
    compileOnly(mnSecurity.micronaut.security)
    testImplementation(mn.micronaut.jackson.databind)
}

spotless {
    java {
        targetExclude("**/io/micronaut/function/aws/proxy/QueryStringDecoder.java")
    }
}

