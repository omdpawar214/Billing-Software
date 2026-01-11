package com.BillingSoftware.Server.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class categoryRequest {

    private String name;
    private String description;
    private String imageUrl;

}
