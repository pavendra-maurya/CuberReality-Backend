package com.cuberreality.response.propertise;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class Type6Url {

    @Field("Type6")
    private List<String> urls;
}
