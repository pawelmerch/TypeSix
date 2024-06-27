module io.mipt.typesix.core {
    requires spring.aop;
    requires micrometer.core;
    requires spring.beans;
    requires spring.context;
    requires lombok;
    requires spring.web;
    requires org.apache.tomcat.embed.core;
    requires spring.security.core;
    requires spring.security.web;
    requires io.swagger.v3.oas.models;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires com.nimbusds.jose.jwt;
    requires spring.security.config;
    requires spring.security.oauth2.authorization.server;
    requires spring.security.oauth2.resource.server;
    requires spring.security.oauth2.jose;
    requires spring.security.crypto;
    requires spring.security.oauth2.core;
    requires spring.core;
    requires spring.boot.actuator.autoconfigure;
    requires spring.webmvc;
    requires spring.tx;
    requires spring.rabbit;
    requires spring.amqp;
    requires spring.data.redis;
    requires spring.security.oauth2.client;
    requires com.fasterxml.jackson.databind;

    requires io.mipt.typesix.businesslogic;

    opens io.mipt.typesix.core;
    opens io.mipt.typesix.core.metrics;
    opens io.mipt.typesix.core.rabbit;
    opens io.mipt.typesix.core.redis;
    opens io.mipt.typesix.core.web.debug;
    opens io.mipt.typesix.core.web.security.config;
    opens io.mipt.typesix.core.web.security.oauth2;
    opens io.mipt.typesix.core.web.swagger;
    opens io.mipt.typesix.core.web.controller;
    opens io.mipt.typesix.core.web.security.form;
}