plugins {
    war
}

group = "my.web"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/javax.servlet.jsp.jstl/jstl-api
    implementation("javax.servlet.jsp.jstl:jstl-api:1.2")
    // https://mvnrepository.com/artifact/javax.servlet/jstl
//    implementation("javax.servlet:jstl:1.2")
    // https://mvnrepository.com/artifact/jakarta.servlet.jsp.jstl/jakarta.servlet.jsp.jstl-api
    implementation("jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:3.0.0")

    // https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")

    // https://mvnrepository.com/artifact/com.sun.faces/jsf-api
    implementation("com.sun.faces:jsf-api:2.2.20")
    // https://mvnrepository.com/artifact/javax.persistence/javax.persistence-api
    implementation("javax.persistence:javax.persistence-api:2.2")
    // https://mvnrepository.com/artifact/com.sun.faces/jsf-impl
    implementation("com.sun.faces:jsf-impl:2.2.20")

    // https://mvnrepository.com/artifact/org.apache.tomcat/tomcat-servlet-api
//    implementation("org.apache.tomcat:tomcat-servlet-api:11.0.0-M11")

    // SLF4J

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:2.0.9")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    implementation("org.slf4j:slf4j-simple:2.0.9")


    // ORM

    // Hibernate
    // https://mvnrepository.com/artifact/org.hibernate/hibernate-core
    implementation("org.hibernate:hibernate-core:5.6.15.Final")
    // https://mvnrepository.com/artifact/org.hibernate/hibernate-entitymanager
    implementation("org.hibernate:hibernate-entitymanager:5.6.15.Final")

    // EclipseLink
    // https://mvnrepository.com/artifact/org.eclipse.persistence/eclipselink
//    implementation("org.eclipse.persistence:eclipselink:4.0.2")
    // https://mvnrepository.com/artifact/org.eclipse.persistence/org.eclipse.persistence.jpa
    implementation("org.eclipse.persistence:org.eclipse.persistence.jpa:4.0.2")

//    // https://mvnrepository.com/artifact/com.h2database/h2
//    testImplementation("com.h2database:h2:2.2.224")

    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.6.0")

    // UI
    // https://mvnrepository.com/artifact/org.primefaces/primefaces
    implementation("org.primefaces:primefaces:6.2")
    // https://mvnrepository.com/artifact/org.icefaces/icefaces-ace
//    implementation("org.icefaces:icefaces-ace:4.3.0")


    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}