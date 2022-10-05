package com.example.fastlms.admin.model;

import lombok.*;

@Getter
@Setter
public class CategoryInput {

    private String categoryName;
    private long id;
    private int sortValue;
    private boolean usingYn;

}
