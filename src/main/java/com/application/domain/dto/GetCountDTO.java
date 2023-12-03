package com.application.domain.dto;

import com.application.domain.FbrModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCountDTO extends FbrModel {
    Long resultCount;
}