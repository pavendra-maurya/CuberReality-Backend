package com.cuberreality.entity.propertise;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class Type2Url {

    @Field("Type2")
    private List<String> urls;
}
