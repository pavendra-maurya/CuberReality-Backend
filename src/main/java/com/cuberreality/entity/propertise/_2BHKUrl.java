package com.cuberreality.entity.propertise;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class _2BHKUrl {
    @Field("Type1")
    private List<String> type1;
    @Field("Type2")
    private List<String> type2;
    @Field("Type3")
    private List<String> type3;
    @Field("Type4")
    private List<String> type4;
    @Field("Type5")
    private List<String> type5;
    @Field("Type6")
    private List<String> type6;
}
