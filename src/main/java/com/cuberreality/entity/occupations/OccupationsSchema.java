package com.cuberreality.entity.occupations;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

import static com.cuberreality.constant.Schema.OCCUPATIONS_SCHEMA;

@Document(OCCUPATIONS_SCHEMA)
@Data
public class OccupationsSchema {

    @Field("_id")
    private String mongoId;

    @Field("resellersOccupation")
    public List<String> resellersOccupation;
}

