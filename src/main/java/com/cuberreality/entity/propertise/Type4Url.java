package com.cuberreality.entity.propertise;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class Type4Url {

    @Field("Type4")
    private List<String> urls;
}
