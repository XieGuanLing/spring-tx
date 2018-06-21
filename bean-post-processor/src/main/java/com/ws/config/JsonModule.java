package com.ws.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ws.dto.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 */
@Component
public class
JsonModule extends SimpleModule {

    public JsonModule() {

//        addSerializer(Date.class, new JsonSerializer<Date>() {
//
//            private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//
//            @Override
//            public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//                gen.writeString(df.format(value));
//            }
//        });


        addSerializer(JsonResponse.class, new JsonSerializer<JsonResponse>() {
            @Override
            public void serialize(JsonResponse value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeStartObject();
                gen.writeBooleanField("success", value.getSuccess());
                if (StringUtils.isNotBlank(value.getCode()))
                    gen.writeStringField("code", value.getCode());
                if (StringUtils.isNotBlank(value.getMsg()))
                    gen.writeStringField("msg", value.getMsg());
                if (value.getResultObject() != null) {
                    gen.writeFieldName("resultObject");
                    serializers.defaultSerializeValue(value.getResultObject(), gen);
                }
                if (value.getDetailErrors() != null) {
                    gen.writeFieldName("detailErrors");
                    serializers.defaultSerializeValue(value.getDetailErrors(), gen);
                }
                gen.writeEndObject();

            }
        });

        addSerializer(UserDto.class, new JsonSerializer<UserDto>() {
            @Override
            public void serialize(UserDto value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                gen.writeStartObject(value);
                gen.writeEndObject();
            }
        });

    }


}
