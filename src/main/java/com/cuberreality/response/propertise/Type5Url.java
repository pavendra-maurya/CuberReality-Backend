package com.cuberreality.response.propertise;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class Type5Url {

    @Field("Type5")
    private List<String> urls;
}
