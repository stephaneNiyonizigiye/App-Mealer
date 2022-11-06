package com.example.mealer;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.mealer.structure.Chef;

import org.hamcrest.core.Is;
import org.junit.Test;

public class ChefSuspensionFunctionalityTesting {

    @Test
    public void verifyNoChefSuspension() {
        Chef chef = new Chef("Nathan", "Nathan", "nate@cool.ca", "45 Cool Street", 902902, "Super Cool");
        Boolean IsSuspended = chef.getIsSuspended();
        assertFalse("Verify chef has no suspension", IsSuspended);
    }

    @Test
    public void verifyChefHasSuspension() {
        Chef chef = new Chef("Cool", "Dude", "nate@cool.ca", "45 Cool Street", 902902, "Super Cool");
        chef.setIsSuspended(true);
        Boolean n = chef.getIsSuspended();
        assertTrue("Verify chef has a suspension", n);
    }

    @Test
    public void verifyChefNullSuspension() {
        Chef chef = new Chef("Nathan", "Nathan", "nate@cool.ca", "45 Cool Street", 902902, "Super Cool");
        String suspensionLength = chef.getSuspensionLength();
        assertEquals("Verify a null suspension length", "none", suspensionLength);
    }

    @Test
    public void verifyChefSpecificSuspension() {
        Chef chef = new Chef("Nathan", "Nathan", "nate@cool.ca", "45 Cool Street", 902902, "Super Cool");
        chef.setSuspensionLength("15 days");
        String suspensionLength = chef.getSuspensionLength();
        assertEquals("Verify a null suspension length", "15 days", suspensionLength);
    }




}