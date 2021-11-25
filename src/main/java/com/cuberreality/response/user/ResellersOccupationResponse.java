package com.cuberreality.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResellersOccupationResponse {

    private List<String> resellersOccupation;
}
