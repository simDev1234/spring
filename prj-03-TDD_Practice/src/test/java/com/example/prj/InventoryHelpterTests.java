package com.example.prj;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InventoryHelpterTests {

    @Test
    void testInventoryHelper_getUsableCapa(){
        InventoryHelper inventoryHelper = new InventoryHelper();
        Inventory inventory = new Inventory();

        inventory.setCapacity(10);
        inventory.setCurrent(5);

        int usableCapa = inventoryHelper.getUsableCapa(inventory);

        Assertions.assertEquals(5, usableCapa);

    }

    @Test
    void testInventoryHelper_inbound_true(){
        InventoryHelper inventoryHelper = new InventoryHelper();

        Inventory inventory = new Inventory();

        inventory.setCapacity(40);

        inventoryHelper.inbound(inventory, 10);

        Assertions.assertTrue(inventoryHelper.inbound(inventory, 10));
        Assertions.assertEquals(10, inventory.getCurrent());
    }

    @Test
    void testInventoryHelper_inbound_false(){
        InventoryHelper inventoryHelper = new InventoryHelper();

        Inventory inventory = new Inventory();

        inventory.setCapacity(10);

        inventoryHelper.inbound(inventory, 30);

        Assertions.assertFalse(inventoryHelper.inbound(inventory, 30));
        Assertions.assertEquals(0, inventory.getCurrent());
    }

    @Test
    void testInventoryHelper_getInboundable_true(){
        InventoryHelper inventoryHelper = new InventoryHelper();

        Inventory inventory = new Inventory();

        inventory.setCapacity(10);
        inventory.setCurrent(5);

        Assertions.assertTrue(inventoryHelper.getInboundable(inventory,3));

    }

    @Test
    void testInventoryHelper_getInboundable_false(){
        InventoryHelper inventoryHelper = new InventoryHelper();

        Inventory inventory = new Inventory();

        inventory.setCapacity(10);
        inventory.setCurrent(5);

        Assertions.assertFalse(inventoryHelper.getInboundable(inventory,6));

    }

    @Test
    void testInventoryHelper_newInventory(){
        InventoryHelper inventoryHelper = new InventoryHelper();

        Inventory inventory = inventoryHelper.createInventory();

        Assertions.assertTrue(inventory instanceof Inventory);
    }

    @Test
    void testInventoryHelper_setInventory_capa(){

    }

}
