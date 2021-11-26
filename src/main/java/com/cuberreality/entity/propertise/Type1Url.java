package com.cuberreality.entity.propertise;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class Type1Url {

    @Field("Type1")
    private Object urls;
}
