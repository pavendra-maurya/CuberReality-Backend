package com.cuberreality.entity.propertisesearch;

import com.cuberreality.entity.propertisesearch.Country;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigInteger;
import java.util.List;

@Document("Search")
@Data
public class SearchSchema{

    @Field("_id")
    private BigInteger mongoId;

    @Field("Country")
    private List<Country> country;
}

