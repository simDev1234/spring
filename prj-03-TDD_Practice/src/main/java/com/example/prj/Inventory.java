package com.example.prj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    private int length;
    private int width;
    private int height;
    private InventoryTypeEnum type;
    private int capacity;
    private int current;

}
