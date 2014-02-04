package org.openinfobutton.console.factory;


import org.apache.log4j.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;


/**
 * User:     Kevin Balthaser
 * Contact:  kbalthaser@getwellnetwork.com
 * Date:     1/10/14
 * Time:     7:38 PM
 *
 * Producer class to allow the injection of a typed Logger instance.  Logger class will by typed
 * to the declaring class of the injection point.
 */
public class LoggerFactory {


    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {

        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

}
