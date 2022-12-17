package com.ruoyi.my.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiyUserAccessoriesList {

    private Long id;
    private Long userId;
    private Long AccessoriesListId;
}
