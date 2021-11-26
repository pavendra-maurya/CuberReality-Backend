package com.cuberreality.response.propertise;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Type1Url {

    @Field("Type1")
    private Object urls;
}
