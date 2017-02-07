package ca.uhnresearch.pughlab.tracker.server.httpd;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MessageFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        return record.getMessage().concat("\n");
    }

}
