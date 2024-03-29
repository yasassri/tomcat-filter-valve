/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wso2.support.valves;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.AccessLogValve;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletException;

/**
 * Concrete implementation of <code>RequestFilterValve</code> that filters
 * based on the remote client's host name optionally combined with the
 * server connector port number.
 *
 * @author Craig R. McClanahan
 */

public final class WSO2HeaderLogValve extends RequestFilterValve {

    private static final Log log = LogFactory.getLog(WSO2HeaderLogValve.class);


    // ----------------------------------------------------- Instance Variables


    /**
     * The descriptive information related to this implementation.
     */
    private static final String info =
            "org.apache.catalina.valves.RemoteHostValve/1.0";


    /**
     * Flag deciding whether we add the server connector port to the property
     * compared in the filtering method. The port will be appended
     * using a ";" as a separator.
     */
    protected volatile boolean addConnectorPort = false;


    // ------------------------------------------------------------- Properties


    /**
     * Return descriptive information about this Valve implementation.
     */
    @Override
    public String getInfo() {

        return (info);

    }


    /**
     * Get the flag deciding whether we add the server connector port to the
     * property compared in the filtering method. The port will be appended
     * using a ";" as a separator.
     */
    public boolean getAddConnectorPort() {
        return addConnectorPort;
    }


    /**
     * Set the flag deciding whether we add the server connector port to the
     * property compared in the filtering method. The port will be appended
     * using a ";" as a separator.
     *
     * @param addConnectorPort The new flag
     */
    public void setAddConnectorPort(boolean addConnectorPort) {
        this.addConnectorPort = addConnectorPort;
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Extract the desired request property, and pass it (along with the
     * specified request and response objects) to the protected
     * <code>process()</code> method to perform the actual filtering.
     * This method must be implemented by a concrete subclass.
     *
     * @param request The servlet request to be processed
     * @param response The servlet response to be created
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {

        Enumeration<String >iter = request.getHeaderNames();
        log.info("========== WSO2 Support : In the invoke method.....");
        while (iter.hasMoreElements()) {
            String header = iter.nextElement();
            log.info("The Header: " + header + " Value: " + request.getHeader(header));
        }

        String property;
        if (addConnectorPort) {
            property = request.getRequest().getRemoteHost() + ";" + request.getConnector().getPort();
        } else {
            property = request.getRequest().getRemoteHost();
        }
        process(property, request, response);
    }
}
