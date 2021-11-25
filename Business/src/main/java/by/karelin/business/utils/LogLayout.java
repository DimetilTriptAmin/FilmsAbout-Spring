package by.karelin.business.utils;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

public class LogLayout extends Layout {
    @Override
    public void activateOptions()
    {
    }

    @Override
    public String format(LoggingEvent event) {
        LocationInfo locationInfo = event.getLocationInformation();
        String output = locationInfo.getClassName() + '_' + locationInfo.getMethodName();

        return output;
    }

    @Override
    public boolean ignoresThrowable() {
        return false;
    }
}
