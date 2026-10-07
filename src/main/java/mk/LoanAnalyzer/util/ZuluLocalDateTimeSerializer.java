package mk.LoanAnalyzer.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 *
 *
 * Created by fja on 14.10.2015.
 */
public class ZuluLocalDateTimeSerializer extends LocalDateTimeSerializer
{
    private static final long serialVersionUID = 1L;

    public static final ZuluLocalDateTimeSerializer INSTANCE = new ZuluLocalDateTimeSerializer();


    @Override
    public void serialize(LocalDateTime time, JsonGenerator generator, SerializerProvider provider) throws IOException
    {
        if (useTimestamp(provider)) {
            super.serialize(time, generator, provider);
        } else {
            generator.writeString(DateUtil.toIsoFromLocalDateTime(time)); // XXX Use our own util
        }
    }
}
