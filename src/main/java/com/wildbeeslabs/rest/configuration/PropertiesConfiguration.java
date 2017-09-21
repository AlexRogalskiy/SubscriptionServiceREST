/*
 * The MIT License
 *
 * Copyright 2017 Pivotal Software, Inc..
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wildbeeslabs.rest.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * Application Properties Configuration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Component
public final class PropertiesConfiguration {

    //@Value("${server.endPointPath}")
    private static String endPointURI;
    //@Value("${datasource.subscriptionapp.maxPoolSize:10}")
    private static int maxPoolSize;
    //@Value("#{'${server.basePath}'.concat(':').concat('${server.port}').concat('${server.contextPath}')}")
    private static String restServiceURI;

    @Value("${server.endPointPath}")
    public void setEndPointURI(final String endPointURI) {
        PropertiesConfiguration.endPointURI = endPointURI;
    }

    public String getEndPointURI() {
        return PropertiesConfiguration.endPointURI;
    }

    @Value("${datasource.subscriptionapp.maxPoolSize:10}")
    public void setMaxPoolSize(final int maxPoolSize) {
        PropertiesConfiguration.maxPoolSize = maxPoolSize;
    }

    public int getMaxPoolSize() {
        return PropertiesConfiguration.maxPoolSize;
    }

    @Value("#{'${server.basePath}'.concat(':').concat('${server.port}').concat('${server.contextPath}')}")
    public void setRestServiceURI(final String restServiceURI) {
        PropertiesConfiguration.restServiceURI = restServiceURI;
    }

    public String getRestServiceURI() {
        return PropertiesConfiguration.restServiceURI;
    }
}
