package com.cuberreality.entity.propertise;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class _2BHK {
    @Field("Type1")
    private Type1 type1;
    @Field("Type2")
    private Type2 type2;
    @Field("Type3")
    private Type3 type3;
    @Field("Type4")
    private Type4 type4;
    @Field("Type5")
    private Type5 type5;
    @Field("Type6")
    private Type6 type6;
}
