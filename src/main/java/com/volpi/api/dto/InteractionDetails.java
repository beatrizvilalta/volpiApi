package com.volpi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InteractionDetails {
    private  Boolean isSaved = false;
    private Boolean isSupported = false;
    private int saveCount;
    private int supportCount;
}
