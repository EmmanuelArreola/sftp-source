package com.cloudgen.n3xgen.SFTP.utils;

import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.client.ClientCache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientCacheFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnablePdx;

/**
 * TODO
 * <p>
 * This is the copy of {@code org.springframework.boot.data.geode.autoconfigure.ClientCacheAutoConfiguration}
 * until {@code geode-spring-boot-starter} is released.
 *
 * @author John Blum
 */
@Configuration
@ConditionalOnClass({ClientCacheFactoryBean.class, ClientCache.class})
@ConditionalOnMissingBean(GemFireCache.class)
@ClientCacheApplication
@EnablePdx
public class ClientCacheAutoConfiguration {

}